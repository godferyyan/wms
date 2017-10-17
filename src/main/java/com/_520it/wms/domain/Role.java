package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Role extends BaseDomian {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8579073329083400962L;

	private String name;
	private String sn;
	private List<Permission> permissions = new ArrayList<>();
	private List<SystemMenu> menus = new ArrayList<>();


	@Override
	public String toString() {
		return "Role [name=" + name + ", sn=" + sn + "]";
	}

}
