package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Permission extends BaseDomian {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8577705573181882414L;

	private String name;
	private String expression;

	@Override
	public String toString() {
		return "Permission [name=" + name + ", expression=" + expression + "]";
	}

}
