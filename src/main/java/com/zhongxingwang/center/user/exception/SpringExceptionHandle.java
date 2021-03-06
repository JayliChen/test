package com.zhongxingwang.center.user.exception;

import com.zhongxingwang.center.user.common.BaseResponse;
import com.zhongxingwang.center.user.common.ErrorType;
import org.apache.ibatis.binding.BindingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.sql.SQLException;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/15 10:06
 */
@RestControllerAdvice(annotations={RestController.class, Controller.class})
public class SpringExceptionHandle {
    private static final Logger logger = LoggerFactory.getLogger(SpringExceptionHandle.class);
    /**
     * 请求参数类型错误异常的捕获
     * @param e
     * @return
     */
    @ExceptionHandler(value={BindException.class})
    @ResponseBody
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    public BaseResponse<String> badRequest(BindException e){
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return new BaseResponse<>(ErrorType.REQUEST_METHOD_ERROR);//错误的请求类型
    }

    /**
     * 404错误异常的捕获
     * @param e
     * @return
     */
    @ExceptionHandler(value={NoHandlerFoundException.class})
    @ResponseBody
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public BaseResponse<String> badRequestNotFound(BindException e){
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return new BaseResponse<>(null, ErrorType.NOT_FOUND);
    }

    /**
     * mybatis未绑定异常
     * @param e
     * @return
     */
    @ExceptionHandler(BindingException.class)
    @ResponseBody
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<String> mybatis(Exception e){
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return new BaseResponse<>(ErrorType.FUNCTION_NOT_FOUND);//找不到方法
    }
    /**
     * 自定义异常的捕获
     * 自定义抛出异常。统一的在这里捕获返回JSON格式的友好提示。
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value={UnicomRuntimeException.class})
    @ResponseBody
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> BaseResponse<T> sendError(UnicomRuntimeException exception, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        logger.error("occurs error when execute url ={} ,message {}",requestURI,exception.getMsg());
        return new BaseResponse<>(exception.getCode(),exception.getMsg());
    }
    /**
     * 数据库操作出现异常
     * @param e
     * @return
     */
    @ExceptionHandler(value={SQLException.class, DataAccessException.class})
    @ResponseBody
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<String> systemError(Exception e){
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return new BaseResponse<>( ErrorType.DATABASE_ERROR);
    }
    /**
     * 网络连接失败！
     * @param e
     * @return
     */
    @ExceptionHandler(value={ConnectException.class})
    @ResponseBody
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<String> connect(Exception e){
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return new BaseResponse<>( ErrorType.CONNECTION_ERROR);
    }

    @ExceptionHandler(value={Exception.class})
    @ResponseBody
    @ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED)
    public BaseResponse<String> notAllowed(Exception e){
        logger.error("occurs error when execute method ,message {}",e.getMessage());
        return new BaseResponse<>(ErrorType.METHOD_NOT_ALLOWED);
    }
}
