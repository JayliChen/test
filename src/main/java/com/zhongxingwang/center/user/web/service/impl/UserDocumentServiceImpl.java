package com.zhongxingwang.center.user.web.service.impl;

import com.zhongxingwang.center.user.common.Enumeration;
import com.zhongxingwang.center.user.common.ErrorType;
import com.zhongxingwang.center.user.exception.UnicomRuntimeException;
import com.zhongxingwang.center.user.redis.RedisUtil;
import com.zhongxingwang.center.user.web.dao.ProductMapper;
import com.zhongxingwang.center.user.web.dao.UserAccountMapper;
import com.zhongxingwang.center.user.web.dto.PlaceOrderReqDto;
import com.zhongxingwang.center.user.web.entity.ProductEo;
import com.zhongxingwang.center.user.web.entity.UserAccountEo;
import com.zhongxingwang.center.user.web.entity.UserDocument;
import com.zhongxingwang.center.user.web.dao.UserDocumentDao;
import com.zhongxingwang.center.user.web.service.IAsyncService;
import com.zhongxingwang.center.user.web.service.IMqProducer;
import com.zhongxingwang.center.user.web.service.UserDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 用户订单表(UserDocument)表服务实现类
 *
 * @author 陈杰攀、
 * @since 2020-04-27 16:00:58
 */
@Service("userDocumentService")
public class UserDocumentServiceImpl implements UserDocumentService {
    private final CountDownLatch countDownLatch = new CountDownLatch(2);

    @Autowired
    private UserDocumentDao userDocumentDao;
    @Autowired
    private IMqProducer mqProducer;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private IAsyncService asyncService;
    @Autowired
    private UserAccountMapper userAccountMapper;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserDocument queryById(Long id) {
        return this.userDocumentDao.queryById(id);
    }

    /**
     * 用户下单接口
     *
     * @param placeOrderReqDto
     * @return
     */
    @Override
    public String placeOrder(PlaceOrderReqDto placeOrderReqDto) throws Exception {
        //从redis抢购，判断是否抢购成功
        if (redisUtil.productCalculator(String.valueOf(placeOrderReqDto.getProductId()), placeOrderReqDto.getCount())) {
            //抢购成功，则发送消息，做后续出库等处理
            mqProducer.sendMessage(Enumeration.USER_PLACE_ORDER, placeOrderReqDto);
            return "下单成功";
        } else {
            throw new UnicomRuntimeException(ErrorType.METHOD_NOT_ALLOWED);
        }
    }

    /**
     * 用户下单回调方法
     *
     * @param placeOrderReqDto
     * @return
     */
    public String placeOrderCallBack(PlaceOrderReqDto placeOrderReqDto) {
        //启动生成单据的线程
        try {
            ProductEo productEo = productMapper.queryById(placeOrderReqDto.getProductId());
            UserDocument userDocument = new UserDocument();
            userDocument.setUserId(placeOrderReqDto.getUserId());
            userDocument.setProductId(placeOrderReqDto.getProductId());
            userDocument.setCount(placeOrderReqDto.getCount());
            userDocument.setPriceUnit(productEo.getPrice());
            asyncService.createDocument(userDocument,countDownLatch);
            //启动线程扣款
            UserAccountEo userAccountEo = userAccountMapper.queryByParentId(placeOrderReqDto.getUserId());
            BigDecimal count = new BigDecimal(placeOrderReqDto.getCount());
            userAccountEo.setBalance(userAccountEo.getBalance().subtract(count.multiply(productEo.getPrice())));
            asyncService.uploadBalance(userAccountEo,countDownLatch);
            countDownLatch.await();
            //主线程扣除库存
            productEo.setCount(String.valueOf(Integer.parseInt(productEo.getCount())-placeOrderReqDto.getCount()));
            productMapper.update(productEo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<UserDocument> queryAllByLimit(int offset, int limit) {
        return this.userDocumentDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userDocument 实例对象
     * @return 实例对象
     */
    @Override
    public UserDocument insert(UserDocument userDocument) {
        this.userDocumentDao.insert(userDocument);
        return userDocument;
    }

    /**
     * 修改数据
     *
     * @param userDocument 实例对象
     * @return 实例对象
     */
    @Override
    public UserDocument update(UserDocument userDocument) {
        this.userDocumentDao.update(userDocument);
        return this.queryById(userDocument.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.userDocumentDao.deleteById(id) > 0;
    }
}