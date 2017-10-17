package com._520it.wms.web.action;

import com._520it.wms.domain.Department;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepartmentService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;

public class DepartmentAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5599265026860755709L;

	@Setter
	private IDepartmentService departmentService;

	@Getter
	private QueryObject qo = new QueryObject();

	@Getter
	private Department department = new Department();

	@Override
	@RequiredPermission("部门列表")
    @InputConfig(methodName = "input")
	public String execute() throws Exception {

        try {
            PageResult result = departmentService.query(qo);

            putContext(RESULT, result);

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦:" + e.getMessage());
        }

        return LIST;
	}

	@RequiredPermission("部门删除")
	public String delete() {

        try {
            if (department.getId() != null) {
                departmentService.delete(department.getId());
                putMsg("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("删除失败");
        }

        return NONE;
	}

	@RequiredPermission("部门录入")
	public String input() throws Exception {

		if (department.getId() != null) {
			department = departmentService.get(department.getId());
		}

		return INPUT;
	}

	@RequiredPermission("部门保存或更新")
	public String saveOrUpdate() throws Exception {

        try {
            if (department.getId() != null) {
                System.out.println(department.getId());
                departmentService.update(department);
                addActionMessage("更新成功!");
            } else {
                departmentService.save(department);
                addActionMessage("保存成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("保存失败!"+e.getMessage());
        }

        return SUCCESS;
	}

}
