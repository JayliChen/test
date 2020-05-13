package com.zhongxingwang.center.user.web.service;

import com.zhongxingwang.center.user.web.dto.PlaceOrderReqDto;
import com.zhongxingwang.center.user.web.entity.UserDocument;
import java.util.List;

/**
 * 用户订单表(UserDocument)表服务接口
 *
 * @author 陈杰攀、
 * @since 2020-04-27 16:00:58
 */
public interface UserDocumentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserDocument queryById(Long id);
    /**
     * 用户下单接口
     * @param placeOrderReqDto
     * @return
     */
    String placeOrder(PlaceOrderReqDto placeOrderReqDto) throws Exception;

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserDocument> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userDocument 实例对象
     * @return 实例对象
     */
    UserDocument insert(UserDocument userDocument);

    /**
     * 修改数据
     *
     * @param userDocument 实例对象
     * @return 实例对象
     */
    UserDocument update(UserDocument userDocument);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}