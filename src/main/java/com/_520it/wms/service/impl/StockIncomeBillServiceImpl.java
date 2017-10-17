package com._520it.wms.service.impl;

import com._520it.wms.domain.Depot;
import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.domain.StockIncomeBillItem;
import com._520it.wms.mapper.StockIncomeBillMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IProductStockService;
import com._520it.wms.service.IStockIncomeBillService;
import com._520it.wms.util.UserContext;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class StockIncomeBillServiceImpl implements IStockIncomeBillService {

    @Setter
    private StockIncomeBillMapper stockIncomeBillMapper;

    @Setter
    private IProductStockService productStockService;

    @Override
    public void save(StockIncomeBill stockIncomeBill) {

        stockIncomeBill.setInputUser(UserContext.getUser());
        stockIncomeBill.setInputTime(new Date());
        stockIncomeBill.setStatus(StockIncomeBill.NORMAL);
        stockIncomeBill.setAuditTime(null);
        stockIncomeBill.setAuditor(null);

        List<StockIncomeBillItem> items = stockIncomeBill.getItems();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (StockIncomeBillItem item : items) {
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            item.setAmount(amount);
            totalAmount = totalAmount.add(amount).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            totalNumber = totalNumber.add(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }
        stockIncomeBill.setTotalNumber(totalNumber);
        stockIncomeBill.setTotalAmount(totalAmount);

        stockIncomeBillMapper.insert(stockIncomeBill);

        for (StockIncomeBillItem item : items) {
            item.setStockIncomeBill(stockIncomeBill);
            stockIncomeBillMapper.insertItems(item);
        }

    }

    @Override
    public void delete(Long id) {

        StockIncomeBill stockIncomeBill = stockIncomeBillMapper.selectByPrimaryKey(id);
        if (stockIncomeBill.getStatus() == StockIncomeBill.AUDIT) {
            throw new RuntimeException("已审核的采购入库单不能删除");
        }

        stockIncomeBillMapper.deleteItemsByBillId(id);
        stockIncomeBillMapper.deleteByPrimaryKey(id);

    }

    @Override
    public void update(StockIncomeBill stockIncomeBill) {

        stockIncomeBillMapper.deleteItemsByBillId(stockIncomeBill.getId());

        List<StockIncomeBillItem> items = stockIncomeBill.getItems();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (StockIncomeBillItem item : items) {
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            item.setAmount(amount);
            item.setStockIncomeBill(stockIncomeBill);
            stockIncomeBillMapper.insertItems(item);
            totalAmount = totalAmount.add(totalAmount).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            totalNumber = totalNumber.add(totalNumber).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }
        stockIncomeBill.setTotalNumber(totalNumber);
        stockIncomeBill.setTotalAmount(totalAmount);

        stockIncomeBillMapper.updateByPrimaryKey(stockIncomeBill);

    }

    @Override
    public StockIncomeBill get(Long id) {

        return stockIncomeBillMapper.selectByPrimaryKey(id);
    }


    @Override
    public PageResult query(QueryObject qo) {
        Integer totalCount = stockIncomeBillMapper.queryForCount(qo);

        if (totalCount == 0) {
            return new PageResult().emptyPageResult(qo.getPageSize());
        }

        List<StockIncomeBill> listData = stockIncomeBillMapper.queryForList(qo);

        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void audit(Long id) {

        StockIncomeBill stockIncomeBill = stockIncomeBillMapper.selectByPrimaryKey(id);

        if (stockIncomeBill.getStatus() == StockIncomeBill.AUDIT) {
            throw new RuntimeException("已审核的订单不能再次审核!");
        }

        Depot depot = stockIncomeBill.getDepot();
        List<StockIncomeBillItem> items = stockIncomeBill.getItems();

        for (StockIncomeBillItem item : items) {
            productStockService.income(item.getCostPrice(),item.getNumber(),item.getProduct(),depot);
        }

        stockIncomeBill.setAuditTime(new Date());
        stockIncomeBill.setAuditor(UserContext.getUser());
        stockIncomeBill.setStatus(StockIncomeBill.AUDIT);
        stockIncomeBillMapper.audit(stockIncomeBill);
    }

}
