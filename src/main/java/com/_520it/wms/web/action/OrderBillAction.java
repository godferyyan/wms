package com._520it.wms.web.action;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.domain.Supplier;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.OrderBillQueryObject;
import com._520it.wms.service.IOrderBillService;
import com._520it.wms.service.ISupplierService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class OrderBillAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private IOrderBillService orderBillService;


    @Setter
    private ISupplierService supplierService;

    @Getter
    private OrderBillQueryObject qo = new OrderBillQueryObject();

    @Getter
    private OrderBill orderBill = new OrderBill();

    @Override
    @RequiredPermission("采购订单列表")
    @InputConfig(methodName = "input")
    public String execute() {
        try {

            PageResult result = orderBillService.query(qo);

            List<Supplier> suppliers = supplierService.listAll();

            putContext("suppliers", suppliers);

            putContext(RESULT, result);


        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦" + e.getMessage());
        }

        return LIST;
    }

    @RequiredPermission("采购订单删除")
    public String delete() {

        try {
            if (orderBill.getId() != null) {
                orderBillService.delete(orderBill.getId());
                putMsg("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("删除失败! " + e.getMessage());
        }

        return NONE;
    }

    @RequiredPermission("采购订单录入")
    public String input() {

        try {

            if (orderBill.getId() != null) {
                orderBill = orderBillService.get(orderBill.getId());
                //禁止通过浏览器传参的方式访问已审核订单的编辑页面,需要做个判断,如果是已审核的订单,就返回list页面并给出错误提示!
                if (orderBill.getStatus() == OrderBill.AUDIT) {
                    addActionMessage("已审核的订单不能编辑!");
                    return SUCCESS;
                }

            }

            List<Supplier> suppliers = supplierService.listAll();

            putContext("suppliers", suppliers);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return INPUT;
    }

    @RequiredPermission("采购订单保存或更新")
    public String saveOrUpdate() {
        try {

            if (orderBill.getId() != null) {

                if (orderBillService.get(orderBill.getId()).getStatus() == OrderBill.AUDIT) {
                    addActionMessage("更新失败! 已审核订单不能被编辑!");
                    return SUCCESS;
                }

                orderBillService.update(orderBill);

                addActionMessage("更新成功!");
            } else {
                orderBillService.save(orderBill);
                addActionMessage("保存成功!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("保存失败! " + e.getMessage());
        }

        return SUCCESS;
    }


    @RequiredPermission("已审核的采购订单的查看")
    public String show() {

        try {

            if (orderBill.getId() != null) {
                orderBill = orderBillService.get(orderBill.getId());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "show";
    }

    public String audit() {
        try {
            if (orderBill.getId() != null) {

                orderBillService.audit(orderBill.getId());
                putMsg("审核成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("审核失败"+e.getMessage());
        }
        return NONE;
    }

}
