package com._520it.wms.web.action;

import com._520it.wms.domain.ProductStock;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.query.ProductStockQueryObject;
import com._520it.wms.service.IBrandService;
import com._520it.wms.service.IDepotService;
import com._520it.wms.service.IProductStockService;
import lombok.Getter;
import lombok.Setter;

public class ProductStockAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private IProductStockService productStockService;

    @Setter
    private IDepotService depotService;
    
    @Setter
    private IBrandService brandService;

    @Getter
    private ProductStockQueryObject qo = new ProductStockQueryObject();

    @Getter
    private ProductStock productStock = new ProductStock();

    @Override
    @RequiredPermission("即时库存报表列表")
    public String execute() {
        try {

            putContext("depots", depotService.listAll());
            putContext("brands",brandService.listAll());
            putContext(RESULT, productStockService.query(qo));

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦" + e.getMessage());
        }

        return LIST;
    }

}
