package com._520it.wms.mapper;

import java.util.List;

import com._520it.wms.domain.Employee;
import com._520it.wms.domain.Permission;
import com._520it.wms.query.QueryObject;

public interface PermissionMapper {
	void save(Permission permission);

	void delete(Long id);

	List<Permission> listAll();

	Integer queryForCount(QueryObject qo);

	List<Employee> queryForList(QueryObject qo);
}
