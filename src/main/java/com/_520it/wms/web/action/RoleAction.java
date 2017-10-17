package com._520it.wms.web.action;

import com._520it.wms.domain.Permission;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.domain.Role;
import com._520it.wms.domain.SystemMenu;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IPermissionService;
import com._520it.wms.service.IRoleService;
import com._520it.wms.service.ISystemMenuService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class RoleAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private IRoleService roleService;

    @Setter
    private IPermissionService permissionService;

    @Setter
    private ISystemMenuService systemMenuService;

    @Getter
    private QueryObject qo = new QueryObject();

    @Getter
    private Role role = new Role();

    @Setter
    private List<Long> ids = new ArrayList<>();

    @Override
    @RequiredPermission("角色列表")
    @InputConfig(methodName = "input")
    public String execute() throws Exception {

        try {
            PageResult result = roleService.query(qo);

            putContext(RESULT, result);
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("角色查询失败!" + e.getMessage());
        }

        return LIST;
    }

    @RequiredPermission("角色删除")
    public String delete() {

        try {
            if (role.getId() != null) {
                roleService.delete(role.getId());
                putMsg("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("删除失败" + e.getMessage());
        }

        return NONE;
    }

    @RequiredPermission("角色录入")
    public String input() throws Exception {

        if (role.getId() != null) {
            role = roleService.get(role.getId());
        }

        List<Permission> permissions = permissionService.listAll();
        List<SystemMenu> menus = systemMenuService.listChildMenus();

        putContext("permissions", permissions);
        putContext("menus", menus);

        return INPUT;
    }

    @RequiredPermission("角色保存或更新")
    public String saveOrUpdate() throws Exception {

        try {
            if (role.getId() != null) {
                roleService.update(role);
                addActionMessage("更新成功!");
            } else {
                roleService.save(role);
                addActionMessage("保存成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("保存失败!");
        }

        return SUCCESS;
    }

    @RequiredPermission("角色批量删除")
    public String batchDelete() {

        try {
            //int i = 1 / 0;
            roleService.batchDelete(ids);
            putMsg("批量删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("批量删除失败!" + e.getMessage());
        }

        return NONE;
    }

}
