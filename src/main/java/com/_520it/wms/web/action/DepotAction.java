package com._520it.wms.web.action;

import com._520it.wms.domain.Depot;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepotService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;

public class DepotAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private IDepotService depotService;

    @Getter
    private QueryObject qo = new QueryObject();

    @Getter
    private Depot depot = new Depot();

    @Override
    @RequiredPermission("仓库列表")
    @InputConfig(methodName = "input")
    public String execute() throws Exception {

        try {
            PageResult result = depotService.query(qo);

            putContext(RESULT, result);

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦:" + e.getMessage());
        }

        return LIST;
    }

    @RequiredPermission("仓库删除")
    public String delete() {

        try {
            if (depot.getId() != null) {
                depotService.delete(depot.getId());
                putMsg("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("删除失败");
        }

        return NONE;
    }

    @RequiredPermission("仓库录入")
    public String input() throws Exception {

        if (depot.getId() != null) {
            depot = depotService.get(depot.getId());
        }

        return INPUT;
    }

    @RequiredPermission("仓库保存或更新")
    public String saveOrUpdate() throws Exception {

        try {
            if (depot.getId() != null) {
                System.out.println(depot.getId());
                depotService.update(depot);
                addActionMessage("更新成功!");
            } else {
                depotService.save(depot);
                addActionMessage("保存成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("保存失败!" + e.getMessage());
        }

        return SUCCESS;
    }

}
