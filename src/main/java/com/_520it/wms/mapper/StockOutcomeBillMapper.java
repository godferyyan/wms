package com._520it.wms.mapper;

import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.domain.StockOutcomeBillItem;
import com._520it.wms.query.StockOutcomeBillQueryObject;

import java.util.List;

public interface StockOutcomeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockOutcomeBill record);

    StockOutcomeBill selectByPrimaryKey(Long id);

    int updateByPrimaryKey(StockOutcomeBill record);

    int queryForCount(StockOutcomeBillQueryObject qo);

    List<StockOutcomeBill> queryForList(StockOutcomeBillQueryObject qo);

    void deleteItemsByBillId(Long billId);

    void insertItem(StockOutcomeBillItem item);

    void audit(StockOutcomeBill bill);

}