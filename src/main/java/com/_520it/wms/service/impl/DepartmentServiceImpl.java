package com._520it.wms.service.impl;

import java.util.List;

import com._520it.wms.domain.Department;
import com._520it.wms.mapper.DepartmentMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepartmentService;

import lombok.Setter;

public class DepartmentServiceImpl implements IDepartmentService {

	@Setter
	private DepartmentMapper departmentMapper;

	@Override
	public void save(Department department) {
		departmentMapper.save(department);
	}

	@Override
	public void delete(Long id) {
		departmentMapper.delete(id);
	}

	@Override
	public void update(Department department) {
		departmentMapper.update(department);

	}

	@Override
	public Department get(Long id) {

		return departmentMapper.get(id);
	}

	@Override
	public List<Department> listAll() {

		return departmentMapper.listAll();
	}

	@Override
	public PageResult query(QueryObject qo) {
		Integer totalCount = departmentMapper.queryForCount(qo);

		if (totalCount == 0) {
			return new PageResult().emptyPageResult(qo.getPageSize());
		}

		List<Department> listData = departmentMapper.queryForList(qo);

		return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
	}

}
