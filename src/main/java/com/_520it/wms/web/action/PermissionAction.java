package com._520it.wms.web.action;

import com._520it.wms.domain.Permission;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IPermissionService;

import lombok.Getter;
import lombok.Setter;

public class PermissionAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private IPermissionService permissionService;

    @Getter
    private QueryObject qo = new QueryObject();

    @Getter
    private Permission permission = new Permission();

    @Override
    @RequiredPermission("权限列表")
    public String execute() throws Exception {

        try {
            PageResult result = permissionService.query(qo);
            putContext(RESULT, result);
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("查询失败!" + e.getMessage());
        }

        return LIST;
    }

    @RequiredPermission("权限删除")
    public String delete() {

        try {
            if (permission.getId() != null) {
                permissionService.delete(permission.getId());
                putMsg("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("删除失败!" + e.getMessage());
        }

        return NONE;
    }

    @RequiredPermission("权限加载")
    public String reload() {

        try {
            permissionService.reload();
            putMsg("权限加载成功!");
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("权限加载失败!" + e.getMessage());
        }

        return NONE;
    }

}
