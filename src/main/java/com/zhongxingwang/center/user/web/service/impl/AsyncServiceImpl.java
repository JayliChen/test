package com.zhongxingwang.center.user.web.service.impl;

import com.zhongxingwang.center.user.config.SpringUtil;
import com.zhongxingwang.center.user.web.entity.UserAccountEo;
import com.zhongxingwang.center.user.web.entity.UserDocument;
import com.zhongxingwang.center.user.web.service.IAsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/27 15:53
 */
@Service
public class AsyncServiceImpl implements IAsyncService {
    Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);
    /**
     * 生成单据线程
     */
    @Async("asyncServiceExecutor")
    @Override
    public void createDocument(UserDocument userDocument, CountDownLatch countDownLatch) {
        logger.info("生成单据线程启动={}",userDocument);
        UserDocumentServiceImpl userDocumentService = SpringUtil.getBean(UserDocumentServiceImpl.class);
        userDocumentService.insert(userDocument);
        countDownLatch.countDown();
    }

    /**
     * 更新余额线程
     * @param userAccountEo
     */
    @Async("asyncServiceExecutor")
    @Override
    public void uploadBalance(UserAccountEo userAccountEo, CountDownLatch countDownLatch) {
        logger.info("更新余额线程启动={}",userAccountEo);
        UserAccountServiceImpl userAccountService = SpringUtil.getBean(UserAccountServiceImpl.class);
        userAccountService.update(userAccountEo);
        countDownLatch.countDown();
    }
}
