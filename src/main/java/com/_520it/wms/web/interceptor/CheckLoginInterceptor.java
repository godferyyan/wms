package com._520it.wms.web.interceptor;

import com._520it.wms.util.UserContext;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckLoginInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4082216469265937484L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		Object employee = UserContext.getUser();

		if (employee == null) {

			return Action.LOGIN;
		}

		return invocation.invoke();
	}

}
