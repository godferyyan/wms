package com._520it.wms.web.action;

import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.query.StockOutcomeBillQueryObject;
import com._520it.wms.service.IClientService;
import com._520it.wms.service.IDepotService;
import com._520it.wms.service.IStockOutcomeBillService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;

public class StockOutcomeBillAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private IStockOutcomeBillService stockOutcomeBillService;

    @Setter
    private IDepotService depotService;
    
    @Setter
    private IClientService clientService;

    @Getter
    private StockOutcomeBillQueryObject qo = new StockOutcomeBillQueryObject();

    @Getter
    private StockOutcomeBill stockOutcomeBill = new StockOutcomeBill();

    @Override
    @RequiredPermission("销售出库单列表")
    @InputConfig(methodName = "input")
    public String execute() {
        try {

            putContext("depots", depotService.listAll());
            putContext("clients",clientService.listAll());
            putContext(RESULT, stockOutcomeBillService.query(qo));

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦" + e.getMessage());
        }

        return LIST;
    }

    @RequiredPermission("销售出库单删除")
    public String delete() {

        try {
            if (stockOutcomeBill.getId() != null) {
                stockOutcomeBillService.delete(stockOutcomeBill.getId());
                putMsg("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("删除失败! " + e.getMessage());
        }

        return NONE;
    }

    @RequiredPermission("销售出库单录入")
    public String input() {

        try {

            if (stockOutcomeBill.getId() != null) {
                stockOutcomeBill = stockOutcomeBillService.get(stockOutcomeBill.getId());
                //禁止通过浏览器传参的方式访问已审核入库单的编辑页面,需要做个判断,如果是已审核的入库单,就返回list页面并给出错误提示!
                if (stockOutcomeBill.getStatus() == StockOutcomeBill.AUDIT) {
                    addActionMessage("已审核的入库单不能编辑!");
                    return SUCCESS;
                }

            }

            putContext("depots", depotService.listAll());
            putContext("clients",clientService.listAll());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return INPUT;
    }

    @RequiredPermission("销售出库单保存或更新")
    public String saveOrUpdate() {
        try {

            if (stockOutcomeBill.getId() != null) {

                if (stockOutcomeBillService.get(stockOutcomeBill.getId()).getStatus() == StockOutcomeBill.AUDIT) {
                    addActionMessage("更新失败! 已审核入库单不能被编辑!");
                    return SUCCESS;
                }
                stockOutcomeBillService.update(stockOutcomeBill);
                addActionMessage("更新成功!");

            } else {
                stockOutcomeBillService.save(stockOutcomeBill);
                addActionMessage("保存成功!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("保存失败! " + e.getMessage());
        }

        return SUCCESS;
    }


    @RequiredPermission("已审核的销售出库单的查看")
    public String show() {

        try {
            if (stockOutcomeBill.getId() != null) {
                stockOutcomeBill = stockOutcomeBillService.get(stockOutcomeBill.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "show";
    }

    public String audit() {
        try {
            if (stockOutcomeBill.getId() != null) {
                stockOutcomeBillService.audit(stockOutcomeBill.getId());
                putMsg("审核成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("审核失败: "+e.getMessage());
        }
        return NONE;
    }

}
