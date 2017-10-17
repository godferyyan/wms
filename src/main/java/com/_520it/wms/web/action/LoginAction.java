package com._520it.wms.web.action;

import com._520it.wms.domain.Employee;
import com._520it.wms.service.IEmployeeService;
import com._520it.wms.util.UserContext;
import lombok.Setter;

public class LoginAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6840332812347325760L;

	@Setter
	private String username;
	@Setter
	private String password;

	@Setter
	private IEmployeeService employeeService;

	@Override
	public String execute() throws Exception {

		Employee employee = employeeService.checkLogin(username, password);

		if (employee == null) {
			super.addActionError("亲,用户名或者密码输入错误!");
			return LOGIN;
		}

        UserContext.setUser(employee);
		
		return SUCCESS;
	}
}
