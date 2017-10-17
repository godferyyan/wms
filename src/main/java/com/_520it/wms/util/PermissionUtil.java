package com._520it.wms.util;


import java.lang.reflect.Method;

/**
 * Created by 阎神 on 2017/9/17.
 */
public class PermissionUtil {

    private PermissionUtil(){}

    public static String createPermissionExpressions(Method method){
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        return className + ":" + methodName;
    }
}
