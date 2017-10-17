package com._520it.wms.page;

import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class PageResult {
	private List<?> listData;
	private Integer totalCount;
	private Integer currentPage;
	private Integer pageSize;
	private Integer totalPage;
	private Integer prevPage;
	private Integer nextPage;
	
	public PageResult() {}
	
	public PageResult(List<?> listData, Integer totalCount, Integer currentPage, Integer pageSize) {
		super();
		this.listData = listData;
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.pageSize = pageSize;

		totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
		prevPage = currentPage - 1 >= 1 ? currentPage - 1 : 1;
		nextPage = currentPage + 1 <= totalPage ? currentPage + 1 : totalPage;

	}

	public PageResult emptyPageResult(Integer pageSize) {
		return new PageResult(Collections.emptyList(), 0, 1, pageSize);
	}

}
