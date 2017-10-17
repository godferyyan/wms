package com._520it.wms.service.impl;

import com._520it.wms.domain.Employee;
import com._520it.wms.domain.Permission;
import com._520it.wms.domain.Role;
import com._520it.wms.mapper.EmployeeMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.EmployeeQueryObject;
import com._520it.wms.service.IEmployeeService;
import com._520it.wms.util.MD5;
import com._520it.wms.util.UserContext;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeServiceImpl implements IEmployeeService {

    @Setter
    private EmployeeMapper employeeMapper;

    @Override
    public void save(Employee employee) {

        //为新增用户的密码加密
        employee.setPassword(MD5.encode(employee.getPassword()));

        employeeMapper.save(employee);

        List<Role> roles = employee.getRoles();

        for (Role role : roles) {
            employeeMapper.updateRelation(employee.getId(), role.getId());
        }

    }

    @Override
    public void delete(Long id) {

        employeeMapper.deleteRelation(id);

        employeeMapper.delete(id);
    }

    @Override
    public void batchDelete(List<Long> ids) {
        //判断一下传入的集合中是否有数据,如果没有数据,则不执行删除操作
        if (ids.size() > 0) {
            employeeMapper.batchDeleteRelation(ids);
            employeeMapper.batchDelete(ids);
        }
    }

    @Override
    public void update(Employee employee) {

        employeeMapper.update(employee);

        //执行更新关系前要先删除原来的权限,否则就会出现重复的权限
        employeeMapper.deleteRelation(employee.getId());

        List<Role> roles = employee.getRoles();
        for (Role role : roles) {
            employeeMapper.updateRelation(employee.getId(), role.getId());
        }

    }

    @Override
    public Employee get(Long id) {

        return employeeMapper.get(id);
    }

    @Override
    public List<Employee> listAll() {

        return employeeMapper.listAll();
    }

    @Override
    public PageResult query(EmployeeQueryObject qo) {

        Integer totalCount = employeeMapper.queryForCount(qo);

        if (totalCount == 0) {
            return new PageResult().emptyPageResult(qo.getPageSize());
        }

        List<Employee> listData = employeeMapper.queryForList(qo);

        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public Employee checkLogin(String username, String password) {

        //防止直接访问login.action报空指针异常
        if (password == null){
            return null;
        }

        /*String salt = "%@%" ; //为MD5加密加盐
        password = password + salt;*/

        Employee employee = employeeMapper.checkLogin(username, MD5.encode(password));

        if (employee != null) {
            Set<String> expressions = new HashSet<>();
            List<Role> roles = employee.getRoles();

            for (Role role : roles) {
                List<Permission> permissions = role.getPermissions();

                for (Permission permission : permissions) {
                    expressions.add(permission.getExpression());
                }

            }

            UserContext.setPermissionExpressions(expressions);
        }

        return employee;
    }

}
