package com._520it.wms.mapper;

import com._520it.wms.domain.Employee;
import com._520it.wms.query.EmployeeQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
	void save(Employee employee);

	void delete(Long id);

	void batchDelete(List<Long> ids);

	void update(Employee employee);

	Employee get(Long id);

	List<Employee> listAll();

	Integer queryForCount(EmployeeQueryObject qo);

	List<Employee> queryForList(EmployeeQueryObject qo);
	
	void deleteRelation(Long empId);

    void batchDeleteRelation(List<Long> ids);
	
	void updateRelation(@Param("empId")Long empId,@Param("roleId")Long roleId);

	Employee checkLogin(@Param("username")String username, @Param("password")String password);
}
