package com._520it.wms.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;

public class BaseAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8734838916513953542L;

	public static final String LIST = "list";

	public static final String RESULT = "result";

	public void putContext(String name, Object value) {
        ActionContext.getContext().put(name, value);
    }

    public void putMsg(String msg){

        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().print(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
