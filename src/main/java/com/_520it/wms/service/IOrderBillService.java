package com._520it.wms.service;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.OrderBillQueryObject;

public interface IOrderBillService {

    void save(OrderBill orderBill);

    void delete(Long id);

    void update(OrderBill orderBill);

    OrderBill get(Long id);

    PageResult query(OrderBillQueryObject qo);

    void audit(Long id);
}
