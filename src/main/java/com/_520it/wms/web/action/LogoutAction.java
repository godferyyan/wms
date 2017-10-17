package com._520it.wms.web.action;

import com.opensymphony.xwork2.ActionContext;

public class LogoutAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8399373537887274926L;
	
	
	@Override
	public String execute() throws Exception {
		
		ActionContext.getContext().getSession().clear();
		
		return LOGIN;
	}
	
}
