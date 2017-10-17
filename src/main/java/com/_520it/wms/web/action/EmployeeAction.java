package com._520it.wms.web.action;

import com._520it.wms.domain.Department;
import com._520it.wms.domain.Employee;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.domain.Role;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.EmployeeQueryObject;
import com._520it.wms.service.IDepartmentService;
import com._520it.wms.service.IEmployeeService;
import com._520it.wms.service.IRoleService;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private IEmployeeService employeeService;

    @Setter
    private IRoleService roleService;

    @Setter
    private IDepartmentService departmentService;

    @Setter
    private Integer page;

    @Setter
    private Integer rows;

    @Getter
    private EmployeeQueryObject qo = new EmployeeQueryObject();

    @Setter
    private String admin;

    @Getter
    private Employee employee = new Employee();

    @Setter
    private List<Long> ids = new ArrayList<>();

    @Override
    @RequiredPermission("员工列表")
    @InputConfig(methodName = "input")
    public String execute() throws Exception {
        try {
            qo.setCurrentPage(page);
            qo.setPageSize(rows);
            PageResult result = employeeService.query(qo);

            List<?> listData = result.getListData();
            Map<String, Object> map = new HashMap<>();
            map.put("total", result.getTotalCount());
            map.put("rows", listData);
            String s = JSON.toJSONString(map);
            ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
            ServletActionContext.getResponse().getWriter().print(s);
            //System.out.println(s);


        /*List<Department> depts = departmentService.listAll();

        putContext("depts", depts);

        putContext(RESULT, result);*/


            //int a = 1/0 ;
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦" + e.getMessage());
        }

        return NONE;
    }

    //供员工新增页面查询所有部门
    public String dept() throws Exception {
        try {
            List<Department> departments = departmentService.listAll();

            Department headDept = new Department();
            headDept.setId(-1L);
            headDept.setName("全部");
            departments.add(headDept);

            String s = JSON.toJSONString(departments);
            ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
            ServletActionContext.getResponse().getWriter().print(s);

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦" + e.getMessage());
        }

        return NONE;
    }

    //供员工新增页面查询所有角色
    public String role() throws Exception {
        try {
            List<Role> roles = roleService.listAll();
            String s = JSON.toJSONString(roles);
            ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
            ServletActionContext.getResponse().getWriter().print(s);

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦" + e.getMessage());
        }

        return NONE;
    }

    @RequiredPermission("员工删除")
    public String delete() {
        Map<String, Object> map = new HashMap<>();
        try {
            if (employee.getId() != null) {
                employeeService.delete(employee.getId());
                map.put("success", true);
                map.put("msg", "删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "删除失败!"+e.getMessage());
        }

        try {
            String s = JSON.toJSONString(map);
            ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
            ServletActionContext.getResponse().getWriter().print(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return NONE;
    }

    /*@RequiredPermission("员工录入")
    public String input() throws Exception {

        if (employee.getId() != null) {
            employee = employeeService.get(employee.getId());

        }

        List<Department> depts = departmentService.listAll();

        putContext("depts", depts);

        List<Role> roles = roleService.listAll();

        putContext("roles", roles);

        return INPUT;
    }*/

    @RequiredPermission("员工保存或更新")
    public String saveOrUpdate() {
        Map<String, Object> map = new HashMap<>();
        try {

            if (employee.getId() != null) {
                employeeService.update(employee);
                map.put("success", true);
                map.put("msg", "更新成功");
            } else {
                employeeService.save(employee);
                map.put("success", true);
                map.put("msg", "保存成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "保存失败!"+e.getMessage());

        }

        try {
            String s = JSON.toJSONString(map);
            ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
            ServletActionContext.getResponse().getWriter().print(s);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return NONE;
    }

    @RequiredPermission("员工批量删除")
    public String batchDelete() {
        Map<String, Object> map = new HashMap<>();
        try {
            employeeService.batchDelete(ids);
            map.put("success", true);
            map.put("msg", "批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "批量删除失败!"+e.getMessage());
        }

        try {
            String s = JSON.toJSONString(map);
            ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
            ServletActionContext.getResponse().getWriter().print(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return NONE;
    }


}
