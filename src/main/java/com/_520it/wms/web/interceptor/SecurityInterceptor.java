package com._520it.wms.web.interceptor;

import com._520it.wms.domain.Employee;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.util.PermissionUtil;
import com._520it.wms.util.UserContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import java.lang.reflect.Method;
import java.util.Set;

public class SecurityInterceptor extends AbstractInterceptor {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        //是否是超级管理员,如果是,就直接放行,否则进入下一步判断
        Employee employee = UserContext.getUser();
        if (employee.isAdmin()) {
            return invocation.invoke();
        }

        Set<String> expressions = UserContext.getPermissionExpressions();

        //要访问的action方法上是否贴有标志权限的注解,如果没有,则直接放行
        Object currentAction = invocation.getProxy().getAction();
        String methodName = invocation.getProxy().getMethod();
        Method method = currentAction.getClass().getDeclaredMethod(methodName);
        if (!method.isAnnotationPresent(RequiredPermission.class)) {
            return invocation.invoke();
        }

        //判断当前访问的用户对应的角色中对应的权限有没有访问该action的权限,如果有,就直接放行,如果没有,就进行拦截,跳转到提示页面
        String expression = PermissionUtil.createPermissionExpressions(method);
        if (expressions.contains(expression)) {
            return invocation.invoke();
        }

        return "nopermission";
    }

}
