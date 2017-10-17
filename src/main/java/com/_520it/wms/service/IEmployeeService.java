package com._520it.wms.service;

import com._520it.wms.domain.Employee;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.EmployeeQueryObject;

import java.util.List;

public interface IEmployeeService {
	
	void save(Employee employee);

	void delete(Long id);

	void batchDelete(List<Long> ids);

	void update(Employee employee);

	Employee get(Long id);

	List<Employee> listAll();

	PageResult query(EmployeeQueryObject qo);

	Employee checkLogin(String username, String password);

}
