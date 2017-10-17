package com._520it.wms.query;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class EmployeeQueryObject extends QueryObject {
	
	private String keyword;
	private Long deptId = -1L;
	
}
