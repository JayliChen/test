//package com.zhongxingwang.center.user.util;
//
//import cn.hutool.core.util.StrUtil;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author chenjiepan
// * @version 1.0
// * @date 2020/4/16 10:00
// */
//public class ListMap2JavaBean<T> {
//    public List<T> ListMap2JavaBean(List<Map<String, Object>> datas, Class<T> beanClass) {
//        // 返回数据集合
//        List<T> list = null;
//        // 对象字段名称
//        String fieldname = "";
//        // 对象方法名称
//        String methodname = "";
//        // 对象方法需要赋的值
//        Object methodsetvalue = "";
//        try {
//            list = new ArrayList<T>();
//            // 得到对象所有字段
//            Field fields[] = beanClass.getDeclaredFields();
//            // 遍历数据
//            for (Map<String, Object> mapdata : datas) {
//                // 创建一个泛型类型实例
//                T t = beanClass.newInstance();
//                // 遍历所有字段，对应配置好的字段并赋值
//                for (Field field : fields) {
//                    // 获取注解配置
//                    JavaBean javaBean = field.getAnnotation(JavaBean.class);
//                    if (null != javaBean) {  // 有注解配置，下一步操作
//                        // 全部转化为大写
//                        String dbfieldname = javaBean.dbfieldname().toUpperCase();
//                        // 获取字段名称
//                        fieldname = field.getName();
//                        // 拼接set方法
//                        methodname = "set" + StrUtil.capitalize(fieldname);
//                        // 获取data里的对应值
//                        methodsetvalue = mapdata.get(dbfieldname);
//                        // 赋值给字段
//                        Method m = beanClass.getDeclaredMethod(methodname, field.getType());
//                        m.invoke(t, methodsetvalue);
//                    }
//                }
//                // 存入返回列表
//                list.add(t);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 返回
//        return list;
//    }
//}
