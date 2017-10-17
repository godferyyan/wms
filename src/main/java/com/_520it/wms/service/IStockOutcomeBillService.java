package com._520it.wms.service;

import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.StockOutcomeBillQueryObject;

public interface IStockOutcomeBillService {

    void save(StockOutcomeBill stockOutcomeBill);

    void delete(Long id);

    void update(StockOutcomeBill stockOutcomeBill);

    StockOutcomeBill get(Long id);

    PageResult query(StockOutcomeBillQueryObject qo);

    void audit(Long id);

}
