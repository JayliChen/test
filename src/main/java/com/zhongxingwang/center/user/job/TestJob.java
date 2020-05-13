package com.zhongxingwang.center.user.job;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.mail.MailUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.sun.corba.se.impl.util.Version.PROJECT_NAME;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/5/11 17:07
 */
@Service
public class TestJob {

    @Scheduled(cron = "0/30 * * * * ?")
    public void test() {
        MailUtil.sendText("1047614059@qq.com", PROJECT_NAME , "hahahha");
    }
}
