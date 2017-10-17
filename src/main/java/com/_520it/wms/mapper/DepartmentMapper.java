package com._520it.wms.mapper;

import java.util.List;

import com._520it.wms.domain.Department;
import com._520it.wms.query.QueryObject;

public interface DepartmentMapper {
	void save(Department department);

	void delete(Long id);

	void update(Department department);

	Department get(Long id);

	List<Department> listAll();

	Integer queryForCount(QueryObject qo);

	List<Department> queryForList(QueryObject qo);
}
