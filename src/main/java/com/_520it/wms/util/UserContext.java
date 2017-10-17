package com._520it.wms.util;

import com._520it.wms.domain.Employee;
import org.apache.struts2.ServletActionContext;

import java.util.Set;

/**
 * Created by 阎神 on 2017/9/17.
 */
//用于将用户信息及权限表达式存入session以及从session中取出的工具
public class UserContext {
    private UserContext() {
    }

    public static final String USER_IN_SESSION = "EMPLOYEE_IN_SESSION";
    public static final String EXPRESSIONS_IN_SESSION = "EXPRESSIONS_IN_SESSION";

    public static void setUser(Employee employee) {
        ServletActionContext.getRequest().getSession().setAttribute("EMPLOYEE_IN_SESSION", employee);
    }

    public static void setPermissionExpressions(Set<String> expressions) {
        ServletActionContext.getRequest().getSession().setAttribute("EXPRESSIONS_IN_SESSION", expressions);
    }

    public static Employee getUser() {
        return (Employee) ServletActionContext.getRequest().getSession().getAttribute("EMPLOYEE_IN_SESSION");
    }

    public static Set<String> getPermissionExpressions() {
        return (Set<String>) ServletActionContext.getRequest().getSession().getAttribute("EXPRESSIONS_IN_SESSION");
    }

}
