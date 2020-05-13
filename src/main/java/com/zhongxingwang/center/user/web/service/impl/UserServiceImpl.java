package com.zhongxingwang.center.user.web.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhongxingwang.center.user.common.Enumeration;
import com.zhongxingwang.center.user.common.ErrorType;
import com.zhongxingwang.center.user.excel.OfficeExportUtil;
import com.zhongxingwang.center.user.exception.UnicomRuntimeException;
import com.zhongxingwang.center.user.redis.RedisLock;
import com.zhongxingwang.center.user.redis.RedisUtil;
import com.zhongxingwang.center.user.rocketmq.base.ByteUtils;
import com.zhongxingwang.center.user.util.TokenUtil;
import com.zhongxingwang.center.user.web.dao.UserAccountMapper;
import com.zhongxingwang.center.user.web.dao.UserMapper;
import com.zhongxingwang.center.user.web.dto.*;
import com.zhongxingwang.center.user.web.entity.UserAccountEo;
import com.zhongxingwang.center.user.web.entity.UserEo;
import com.zhongxingwang.center.user.web.service.IMqProducer;
import com.zhongxingwang.center.user.web.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisLock redisLock;
    @Autowired
    private IMqProducer mqProducer;
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private RedisUtil redisUtil;


    /**
     * 新增用户
     *
     * @param userDto
     * @return
     */
    @Override
    public Long add(UserDto userDto) {
        UserEo userEo = new UserEo();
        BeanUtils.copyProperties(userDto, userEo);
        userEo.setPassWord(ByteUtils.serialize(userDto.getPassWord()));
        UserAccountEo userAccountEo = new UserAccountEo();
        BeanUtils.copyProperties(userDto, userAccountEo);
        userMapper.insertOne(userEo);
        userAccountEo.setParentId(userEo.getId());
        userAccountMapper.insert(userAccountEo);
        return userEo.getId();
    }

    /**
     * 根据组建id删除用户
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        if (userMapper.selectByPrimaryKey(id) != null) {
            userMapper.deleteByPrimaryKey(id);
        } else {
            throw new UnicomRuntimeException(ErrorType.SOURCE_NULL);
        }
    }

    /**
     * 根据主键id获取用户信息
     *
     * @param id
     * @return
     */
    @Override
    public UserDetailRespDto detail(Long id) {
        UserDetailRespDto userDetailRespDto = userMapper.selectById(id);
        if (userDetailRespDto != null) {
            userDetailRespDto.setRealPassWord((String) ByteUtils.deSerialize(userDetailRespDto.getPassWord()));
            return userDetailRespDto;
        } else {
            throw new UnicomRuntimeException(ErrorType.SOURCE_NULL);
        }
    }

    /**
     * 根据主键id跟新传进来的用户信息
     *
     * @param userUpdateReqDto
     */
    @Override
    public void update(UserUpdateReqDto userUpdateReqDto) {
        long time = System.currentTimeMillis() + Enumeration.TIME_OUT;
        if (!redisLock.lock(String.valueOf(userUpdateReqDto.getId()), String.valueOf(time))) {
            throw new UnicomRuntimeException(ErrorType.OPERATION_SCOPE_FAILED, "该用户信息正在修改中，请稍后重试！");
        }
        try {
//            if (userMapper.selectByPrimaryKey(userUpdateReqDto.getId()) != null) {
            UserEo userEo = new UserEo();
            BeanUtils.copyProperties(userUpdateReqDto, userEo);
            if (userUpdateReqDto.getPassWord() != null && !"".equals(userUpdateReqDto.getPassWord())) {
                userEo.setPassWord(ByteUtils.serialize(userUpdateReqDto.getPassWord()));
            }
            userMapper.updateByPrimaryKeySelective(userEo);
//            } else {
//                throw new UnicomRuntimeException(ErrorType.SOURCE_NULL);
//            }
        } catch (Exception e) {
            throw new UnicomRuntimeException(ErrorType.ERROR, "系统异常，更新数据失败！");
        } finally {
            //释放锁
            redisLock.release(String.valueOf(userUpdateReqDto.getId()), String.valueOf(time));
            System.out.println("释放锁的时间戳：" + time);
        }
    }

    /**
     * 分页查询用户列表
     *
     * @param userPageInfoReqDto
     * @return
     */
    @Override
    public PageInfo<UserPageInfoRespDto> pageList(UserPageInfoReqDto userPageInfoReqDto) {
        PageHelper.startPage(userPageInfoReqDto.getPageNum(), userPageInfoReqDto.getPageSize());
        List<UserEo> userEoList = userMapper.getPageList(userPageInfoReqDto);
        PageInfo<UserEo> userEoPageInfo = new PageInfo<>(userEoList);
        PageInfo<UserPageInfoRespDto> userPageInfoRespDtoPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(userEoPageInfo, userPageInfoRespDtoPageInfo);
        if (!CollectionUtils.isEmpty(userEoList)) {
            //数据转换、数据处理
            List<UserPageInfoRespDto> userPageInfoRespDtoList = new ArrayList<>();
            userEoList.forEach(userEo -> {
                UserPageInfoRespDto userPageInfoRespDto = new UserPageInfoRespDto();
                BeanUtils.copyProperties(userEo, userPageInfoRespDto);
                userPageInfoRespDto.setPassWord((String) ByteUtils.deSerialize(userEo.getPassWord()));
                userPageInfoRespDtoList.add(userPageInfoRespDto);
            });
            userPageInfoRespDtoPageInfo.setList(userPageInfoRespDtoList);
        }
        return userPageInfoRespDtoPageInfo;
    }

    @Override
    public List<UserPageInfoRespDto> list(UserListReqDto userListReqDto) {
        List<UserEo> userEoList = userMapper.list(userListReqDto);
        List<UserPageInfoRespDto> userPageInfoRespDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userEoList)) {
            userEoList.forEach(userEo -> {
                UserPageInfoRespDto userPageInfoRespDto = new UserPageInfoRespDto();
                BeanUtils.copyProperties(userEo, userPageInfoRespDto);
                userPageInfoRespDto.setPassWord((String) ByteUtils.deSerialize(userEo.getPassWord()));
                userPageInfoRespDtoList.add(userPageInfoRespDto);
            });
        }
        return userPageInfoRespDtoList;
    }

    @Override
    public ExcelImportResultView importUser(MultipartFile file) throws Exception {
        //创建结果bean
        ExcelImportResultView resultInfo = new ExcelImportResultView();
        ImportParams params = new ImportParams();
        params.setNeedVerify(true);
        //导入结果
        ExcelImportResult<UserImportDto> result = ExcelImportUtil.importExcelMore(file.getInputStream(), UserImportDto.class, params);
        //excel满足校验条件的全部数据
        List<UserImportDto> list = result.getList();
        //excel不满足校验条件的全部数据
        List<UserImportDto> failList = result.getFailList();
        //创建满足过滤条件的集合
        List<UserImportDto> passList = new ArrayList<>();
        //对导入数据过滤
        excelImportDataFilter(list, failList, passList);
        //如果通过的数据不为空 做批量插入
        if (!CollectionUtils.isEmpty(passList)){
            List<UserEo> userEoList = new ArrayList<>();
            Map<String,Long> parentIdMap = new HashMap<>();
            //数据处理和转换
            passList.forEach(userImportDto -> {
                UserEo userEo = new UserEo();
                BeanUtils.copyProperties(userImportDto, userEo);
                userEo.setPassWord(ByteUtils.serialize(userImportDto.getPassWord()));
                userEoList.add(userEo);
            });
            //批量新增用户
            userMapper.insertBatch(userEoList);
            userEoList.forEach(userEo -> parentIdMap.put(userEo.getIdentityCard(),userEo.getId()));
            //账户处理
            List<UserAccountEo> userAccountEoList = new ArrayList<>();
            passList.forEach(userImportDto -> {
                UserAccountEo userAccountEo = new UserAccountEo();
                BeanUtils.copyProperties(userImportDto, userAccountEo);
                userAccountEo.setParentId(parentIdMap.get(userImportDto.getIdentityCard()));
                userAccountEoList.add(userAccountEo);
            });
            //批量新增用户数据
            userAccountMapper.insertBatch(userAccountEoList);
        }
        //唯一token值
        String errorToken = null;
        //判断是否有导入异常的数据
        if (failList.size() > 0) {
            //生成token
            errorToken = TokenUtil.newRandomToken();
            //把导入失败的集合存入redis   ---  有效时长5分钟
            redisUtil.lSet(Enumeration.USER_IMPORT_ERROR_CODE + errorToken, failList, 300000);
        }
        resultInfo.setTotalNum(list.size() );
        resultInfo.setSuccessNum(passList.size());
        resultInfo.setErrorNum(failList.size());
        resultInfo.setErrorToken(errorToken);
        return resultInfo;
    }

    public void excelImportDataFilter(List<UserImportDto> list, List<UserImportDto> failList, List<UserImportDto> passList) {
        //todo 仅做身份份证号码校验，其他校验正式系统需加上
        //先做表格中自校验
        list = distinctAddMsg(list.stream().collect(Collectors.groupingBy(UserImportDto::getIdentityCard)), "表格中身份证号码重复");
        //获取系统中的身份证号码
        List<String> identityCardSys = userMapper.getIdentityCard();
        list.forEach(userImportDto -> {
            if (identityCardSys.contains(userImportDto.getIdentityCard())) {
                setErrorMsg(userImportDto, "此身份证号码系统已存在");
            }
            if (StrUtil.isEmpty(userImportDto.getErrorMsg())) {
                passList.add(userImportDto);
            } else {
                failList.add(userImportDto);
            }
        });
    }

    /**
     * 校验表格中的身份证号码
     *
     * @param map
     * @param msg
     * @return
     */
    private List<UserImportDto> distinctAddMsg(Map<String, List<UserImportDto>> map, String msg) {
        //手机号不重复的数据集合
        List<UserImportDto> list = new ArrayList<>();
        //遍历分组后数据
        map.forEach((key, value) -> {
            //判断value
            if (value.size() > 1) {
                //添加错误信息
                value.forEach(obj -> setErrorMsg(obj, msg));
            }
            list.addAll(value);
        });
        //返回数据
        return list;
    }

    /**
     * 保存报错信息
     *
     * @param person
     * @param msg
     */
    private void setErrorMsg(UserImportDto person, String msg) {
        //获取msg
        String errorMsg = person.getErrorMsg();
        //返回结果
        person.setErrorMsg(StrUtil.isEmpty(errorMsg) ? msg : errorMsg + StrUtil.COMMA + msg);
    }

    @Override
    public void downLoadUserTemplate() {
        List<UserImportTemplateDto> userImportTemplateDtoList = new ArrayList<>();
        UserImportTemplateDto userImportTemplateDto = new UserImportTemplateDto();
        userImportTemplateDto.setName("张三");
        userImportTemplateDto.setUserName("zhangsan");
        userImportTemplateDto.setPassWord("123456");
        userImportTemplateDto.setAge(22);
        userImportTemplateDto.setSex(0);
        userImportTemplateDto.setPhone("18815882152");
        userImportTemplateDto.setIdentityCard("450881199701100850");
        userImportTemplateDto.setRemark("备注");
        userImportTemplateDto.setBankCardNumber("6222023202054725709");
        userImportTemplateDto.setBalance(new BigDecimal(1000));
        userImportTemplateDtoList.add(userImportTemplateDto);
        //导出excel
        OfficeExportUtil.exportExcel(OfficeExportUtil.getWorkbook(null, "用户导入模板", UserImportTemplateDto.class, userImportTemplateDtoList), "用户导入模板");
    }

    @Override
    public void downLoadError(String errorToken) throws Exception {
        //创建导出数据list
        List<UserImportDto> noPassList = new ArrayList<>();
        //判断errorToken
        if (!StrUtil.isEmpty(errorToken)) {
            //从redis中查询数据
            Object obj = redisUtil.get(Enumeration.USER_IMPORT_ERROR_CODE + errorToken);
            //判空
            if (obj != null) {
                noPassList = (ArrayList<UserImportDto>) obj;
                //删除redis数据
                redisUtil.del(Enumeration.USER_IMPORT_ERROR_CODE + errorToken);
            }
        }
        //导出excel
        OfficeExportUtil.exportExcel(OfficeExportUtil.getWorkbook(null, "导入异常用户信息", UserImportDto.class, noPassList), "导入异常用户信息");

    }

    @Override
    public void downLoadUser(UserListReqDto userListReqDto) {
        List<UserEo> userEoList = userMapper.list(userListReqDto);
        List<UserExportTemplateDto> userExportTemplateDtoList = new ArrayList<>();
        //做导出数据处理
        userEoList.forEach(userEo -> {
            UserExportTemplateDto userExportTemplateDto = new UserExportTemplateDto();
            BeanUtils.copyProperties(userEo,userExportTemplateDto);
            userExportTemplateDto.setPassWord((String) ByteUtils.deSerialize(userEo.getPassWord()));
            userExportTemplateDtoList.add(userExportTemplateDto);
        });
        //导出excel
        OfficeExportUtil.exportExcel(OfficeExportUtil.getWorkbook(null, "导入用户信息", UserExportTemplateDto.class, userExportTemplateDtoList), "导入用户信息");
    }

    @Override
    public Long serializable(UserDto userDto) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("D:\\project\\user\\target\\user.txt")));
        oos.writeObject(userDto);
        oos.close();
        return 1L;
    }

//    @Test
    public void testSerializable() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("D:\\project\\user\\target\\user.txt")));
        UserDto userDto = (UserDto) ois.readObject();
        System.out.println(userDto);
    }

    @Override
    public String send(UserDto userDto) throws Exception {
        String result = mqProducer.sendMessage(Enumeration.USER_MQ_TAG, userDto);
        logger.info("消息发送成功={}", result);
        return result;
    }

}
