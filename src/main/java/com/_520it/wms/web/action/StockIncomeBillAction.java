package com._520it.wms.web.action;

import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.domain.Depot;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IStockIncomeBillService;
import com._520it.wms.service.IDepotService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class StockIncomeBillAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private IStockIncomeBillService stockIncomeBillService;

    @Setter
    private IDepotService depotService;

    @Getter
    private QueryObject qo = new QueryObject();

    @Getter
    private StockIncomeBill stockIncomeBill = new StockIncomeBill();

    @Override
    @RequiredPermission("采购入库单列表")
    @InputConfig(methodName = "input")
    public String execute() {
        try {

            PageResult result = stockIncomeBillService.query(qo);

            putContext(RESULT, result);


        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦" + e.getMessage());
        }

        return LIST;
    }

    @RequiredPermission("采购入库单删除")
    public String delete() {

        try {
            if (stockIncomeBill.getId() != null) {
                stockIncomeBillService.delete(stockIncomeBill.getId());
                putMsg("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("删除失败! " + e.getMessage());
        }

        return NONE;
    }

    @RequiredPermission("采购入库单录入")
    public String input() {

        try {

            if (stockIncomeBill.getId() != null) {
                stockIncomeBill = stockIncomeBillService.get(stockIncomeBill.getId());
                //禁止通过浏览器传参的方式访问已审核入库单的编辑页面,需要做个判断,如果是已审核的入库单,就返回list页面并给出错误提示!
                if (stockIncomeBill.getStatus() == StockIncomeBill.AUDIT) {
                    addActionMessage("已审核的入库单不能编辑!");
                    return SUCCESS;
                }

            }

            List<Depot> depots = depotService.listAll();

            putContext("depots", depots);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return INPUT;
    }

    @RequiredPermission("采购入库单保存或更新")
    public String saveOrUpdate() {
        try {

            if (stockIncomeBill.getId() != null) {

                if (stockIncomeBillService.get(stockIncomeBill.getId()).getStatus() == StockIncomeBill.AUDIT) {
                    addActionMessage("更新失败! 已审核入库单不能被编辑!");
                    return SUCCESS;
                }

                stockIncomeBillService.update(stockIncomeBill);

                addActionMessage("更新成功!");
            } else {
                stockIncomeBillService.save(stockIncomeBill);
                addActionMessage("保存成功!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("保存失败! " + e.getMessage());
        }

        return SUCCESS;
    }


    @RequiredPermission("已审核的采购入库单的查看")
    public String show() {

        try {

            if (stockIncomeBill.getId() != null) {
                stockIncomeBill = stockIncomeBillService.get(stockIncomeBill.getId());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "show";
    }

    public String audit() {
        try {
            if (stockIncomeBill.getId() != null) {
                stockIncomeBillService.audit(stockIncomeBill.getId());
                putMsg("审核成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("审核失败"+e.getMessage());
        }
        return NONE;
    }

}
