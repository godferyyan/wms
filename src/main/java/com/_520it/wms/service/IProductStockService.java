package com._520it.wms.service;

import com._520it.wms.domain.Depot;
import com._520it.wms.domain.Product;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.ProductStockQueryObject;

import java.math.BigDecimal;

public interface IProductStockService {
	
	PageResult query(ProductStockQueryObject qo);

    /**
     * 审核采购入库单时的入库操作
     * @param costPrice 入库商品的成本价
     * @param number 入库商品的数量
     * @param product 入库的商品信息
     * @param depot 入库的仓库信息
     */
	void income(BigDecimal costPrice, BigDecimal number, Product product, Depot depot);

    /**
     * 审核销售出库单时的出库操作
     * @param number 要出库的商品数量
     * @param product 要出库的商品信息
     * @param depot 要出库的仓库信息
     * @return 返回库存商品的成本价格
     */
	BigDecimal outcome(BigDecimal number,Product product , Depot depot);
}
