package com.zhongxingwang.center.user.web.service;

import com.zhongxingwang.center.user.web.entity.UserAccountEo;
import com.zhongxingwang.center.user.web.entity.UserDocument;

import java.util.concurrent.CountDownLatch;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/27 15:53
 */
public interface IAsyncService {

    /**
     * 生成单据线程
     */
    void createDocument(UserDocument userDocument, CountDownLatch countDownLatch);

    /**
     * 更新余额
     * @param userAccountEo
     */
    void uploadBalance(UserAccountEo userAccountEo, CountDownLatch countDownLatch);
}
