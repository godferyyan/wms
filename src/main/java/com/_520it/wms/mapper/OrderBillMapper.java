package com._520it.wms.mapper;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.domain.OrderBillItem;
import com._520it.wms.query.OrderBillQueryObject;

import java.util.List;

public interface OrderBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderBill record);

    OrderBill selectByPrimaryKey(Long id);

    int updateByPrimaryKey(OrderBill record);

    int queryForCount(OrderBillQueryObject qo);

    List<OrderBill> queryForList(OrderBillQueryObject qo);

    void insertItems(OrderBillItem item);

    void deleteItemsByBillId(Long billId);

    void audit(OrderBill bill);
}