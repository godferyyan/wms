package com._520it.wms.web.action;

import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.domain.SystemMenu;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.SystemMenuQueryObject;
import com._520it.wms.service.ISystemMenuService;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;

import java.util.List;
import java.util.Map;

public class SystemMenuAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private ISystemMenuService systemMenuService;

    @Getter
    private SystemMenuQueryObject qo = new SystemMenuQueryObject();

    @Getter
    private SystemMenu systemMenu = new SystemMenu();

    @Override
    @RequiredPermission("系统菜单列表")
    @InputConfig(methodName = "input")
    public String execute() throws Exception {

        List<Map<String, Object>> parents = systemMenuService.queryForParents(qo.getParentId());
        putContext("parents", parents);

        try {
            PageResult result = systemMenuService.query(qo);

            putContext(RESULT, result);

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦:" + e.getMessage());
        }

        return LIST;
    }

    @RequiredPermission("系统菜单删除")
    public String delete() {

        try {
            if (systemMenu.getId() != null) {
                systemMenuService.delete(systemMenu.getId());
                putMsg("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("删除失败:" + e.getMessage());
        }

        return NONE;
    }

    @RequiredPermission("系统菜单录入")
    public String input() throws Exception {

        if (qo.getParentId() > 0) {
            SystemMenu parent = systemMenuService.get(qo.getParentId());
            putContext("parentName", parent.getName());
        } else {
            putContext("parentName", "根目录");
        }

        if (systemMenu.getId() != null) {
            systemMenu = systemMenuService.get(systemMenu.getId());
        }

        return INPUT;
    }

    @RequiredPermission("系统菜单保存或更新")
    public String saveOrUpdate() throws Exception {
        //不需要进行判断,因为如果是根目录下的模块保存,qo.parentId就是-1,查询出来的parent本身就是null那么设置给parent就是null也没毛病,因为即使进行判断了,也会设置为null
        SystemMenu parent = systemMenuService.get(qo.getParentId());
        systemMenu.setParent(parent);

        try {
            if (systemMenu.getId() != null) {
                System.out.println(systemMenu.getId());
                systemMenuService.update(systemMenu);
                addActionMessage("更新成功!");
            } else {
                systemMenuService.save(systemMenu);
                addActionMessage("保存成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("保存失败!" + e.getMessage());
        }

        return SUCCESS;
    }

    public String queryMenusByParentSn() {

        try {

            List<Map<String, Object>> menus = systemMenuService.queryMenusBySn(qo.getParentSn());

            ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
            ServletActionContext.getResponse().getWriter().print(JSON.toJSONString(menus));

        } catch (Exception e) {

            e.printStackTrace();
            putMsg("子菜单刷新失败! :" + e.getMessage());
        }

        return NONE;
    }
}
