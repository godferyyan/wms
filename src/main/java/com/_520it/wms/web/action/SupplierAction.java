package com._520it.wms.web.action;

import com._520it.wms.domain.Supplier;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.ISupplierService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;

public class SupplierAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private ISupplierService supplierService;

    @Getter
    private QueryObject qo = new QueryObject();

    @Getter
    private Supplier supplier = new Supplier();

    @Override
    @RequiredPermission("供应商列表")
    @InputConfig(methodName = "input")
    public String execute() throws Exception {

        try {
            PageResult result = supplierService.query(qo);

            putContext(RESULT, result);

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦:" + e.getMessage());
        }

        return LIST;
    }

    @RequiredPermission("供应商删除")
    public String delete() {

        try {
            if (supplier.getId() != null) {
                supplierService.delete(supplier.getId());
                putMsg("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("删除失败");
        }

        return NONE;
    }

    @RequiredPermission("供应商录入")
    public String input() throws Exception {

        if (supplier.getId() != null) {
            supplier = supplierService.get(supplier.getId());
        }

        return INPUT;
    }

    @RequiredPermission("供应商保存或更新")
    public String saveOrUpdate() throws Exception {

        try {
            if (supplier.getId() != null) {
                System.out.println(supplier.getId());
                supplierService.update(supplier);
                addActionMessage("更新成功!");
            } else {
                supplierService.save(supplier);
                addActionMessage("保存成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("保存失败!" + e.getMessage());
        }

        return SUCCESS;
    }

}
