package com._520it.wms.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryObject {

	private Integer currentPage = 1;
	private Integer pageSize = 10;

	public Integer getBeginIndex() {
		return (currentPage - 1) * pageSize;
	}

}
