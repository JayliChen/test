package com.zhongxingwang.center.user.rocketmq.base;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.zhongxingwang.center.user.exception.UnicomRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author chenjiepan
 * @version 1.0
 * @date 2020/4/26 16:00
 */
public class ByteUtils {
    private static Logger logger = LoggerFactory.getLogger(ByteUtils.class);

    public ByteUtils() {
    }

    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream baos = null;
        HessianOutput output = null;

        try {
            baos = new ByteArrayOutputStream(1024);
            output = new HessianOutput(baos);
            output.startCall();
            output.writeObject(obj);
            output.completeCall();
        } catch (Exception var11) {
            logger.error("SerializeCode 转换byte[]时出错", var11);
            throw new UnicomRuntimeException("SerializeCode 转换byte[]时出错", var11);
        } finally {
            if(output != null) {
                try {
                    output.close();
                    baos.close();
                } catch (IOException var10) {
                    ;
                }
            }

        }

        return baos != null?baos.toByteArray():null;
    }

    public static Object deSerialize(byte[] in) {
        Object obj = null;
        ByteArrayInputStream bais = null;
        HessianInput input = null;

        try {
            bais = new ByteArrayInputStream(in);
            input = new HessianInput(bais);
            input.startReply();
            obj = input.readObject();
            input.completeReply();
        } catch (Throwable var12) {
            logger.error("SerializeCode 转换byte[]出错", var12);
            throw new UnicomRuntimeException("SerializeCode 转换object时出错", var12);
        } finally {
            if(input != null) {
                try {
                    input.close();
                    bais.close();
                } catch (IOException var11) {
                    throw new UnicomRuntimeException("输入流关闭异常，请联系管理员");
                }
            }

        }
        return obj;
    }
}
