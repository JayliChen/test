package com.zhongxingwang.center.user.util;

import cn.hutool.core.util.IdUtil;

import javax.servlet.http.HttpServletRequest;


public class TokenUtil {

    /** 用户token常量 */
    private static final String TOKEN = "token";

    /**
     * 获取一个随机生成的Token
     *
     * @return TOKEN
     */
    public static String newRandomToken() {
        return IdUtil.fastSimpleUUID();
    }

    /**
     * 从请求头中获取token
     *
     * @return TOKEN
     */
    public static String getHeaderToken() {
        return getHeaderToken(HttpServletUtil.getRequest());
    }

    /**
     * 从请求头中获取token
     *
     * @param request 请求
     * @return TOKEN
     */
    public static String getHeaderToken(HttpServletRequest request) {
        return request.getHeader(TOKEN);
    }

    /**
     * 从请求Url中获取token
     *
     * @return TOKEN
     */
    public static String getUrlToken() {
        return getUrlToken(HttpServletUtil.getRequest());
    }

    /**
     * 从请求Url中获取token
     *
     * @param request 请求
     * @return TOKEN
     */
    public static String getUrlToken(HttpServletRequest request) {
        return request.getParameter(TOKEN);
    }

//    /**
//     * 获取Header或者url里面的token
//     *
//     * @return token
//     */
//    public static String getHeaderOrUrlToken() {
//        //获取请求
//        HttpServletRequest request = HttpServletUtil.getRequest();
//        //获取Header中的token
//        String token = getHeaderToken(request);
//        //判断是否为空
//        if (StrUtil.isEmpty(token)) {
//            //获取url后面的token
//            token = getUrlToken(request);
//        }
//        //判断token是否存在
//        if (StrUtil.isEmpty(token)) {
//            throw new ParameterException("缺少参数token!");
//        }
//        //返回结果;
//        return token;
//    }


    /**
     * 向Redis存入token
     *
     * @param userId 用户id
     * @param token  用的对应的token
     * @return 结果
     */
//    public static boolean setRedisToken(Integer userId, String token) {
//        return setRedisToken(userId.toString(), token);
//    }

    /**
     * 向Redis存入token
     *
     * @param userId 用户id
     * @param token  用的对应的token
     * @return 结果
     */
//    public static boolean setRedisToken(String userId, String token) {
//        return RedisUtil.hset(Constants.WEB_REDIS_TOKEN_KEY, userId, token);
//    }

    /**
     * 从Redis中获取--当前登录用户的token
     *
     * @return TOKEN
     */
//    public static String getRedisToken() {
//        return getRedisToken(UserUtil.getHeaderUserId());
//    }

    /**
     * 从Redis中获取--当前登录用户的token
     *
     * @param request 请求
     * @return TOKEN
     */
//    public static String getRedisToken(HttpServletRequest request) {
//        return getRedisToken(UserUtil.getHeaderUserId(request));
//    }

    /**
     * 从Redis中获取--当前登录用户的token
     *
     * @return TOKEN
     */
//    public static String getRedisToken(Integer userId) {
//        return getRedisToken(userId.toString());
//    }

    /**
     * 从Redis中获取--当前登录用户的token
     *
     * @return TOKEN
     */
//    public static String getRedisToken(String userId) {
//        //查询数据
//        Object obj = RedisUtil.hget(Constants.WEB_REDIS_TOKEN_KEY, userId);
//        //判断token是否存在
//        if (obj == null) {
//            throw new AuthTokenException("用户登录信息不存在,请从新登录!");
//        }
//        //返回
//        return obj.toString();
//    }

    /**
     * 删除Redis中指定用户token
     *
     * @param userId 用户id
     */
//    public static void delRedisToken(Integer userId) {
//        delRedisToken(userId.toString());
//    }

    /**
     * 删除Redis中指定用户token
     *
     * @param userId 用户id
     */
//    public static void delRedisToken(String userId) {
//        RedisUtil.hdel(Constants.WEB_REDIS_TOKEN_KEY, userId);
//    }
}
