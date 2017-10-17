package com._520it.wms.service.impl;

import com._520it.wms.domain.Depot;
import com._520it.wms.domain.SaleAccount;
import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.domain.StockOutcomeBillItem;
import com._520it.wms.mapper.StockOutcomeBillMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.StockOutcomeBillQueryObject;
import com._520it.wms.service.IProductStockService;
import com._520it.wms.service.ISaleAccountService;
import com._520it.wms.service.IStockOutcomeBillService;
import com._520it.wms.util.UserContext;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class StockOutcomeBillServiceImpl implements IStockOutcomeBillService {

    @Setter
    private StockOutcomeBillMapper stockOutcomeBillMapper;

    @Setter
    private IProductStockService productStockService;

    @Setter
    private ISaleAccountService saleAccountService;

    @Override
    public void save(StockOutcomeBill stockOutcomeBill) {

        stockOutcomeBill.setAuditor(null);
        stockOutcomeBill.setAuditTime(null);
        stockOutcomeBill.setInputTime(new Date());
        stockOutcomeBill.setInputUser(UserContext.getUser());
        stockOutcomeBill.setStatus(StockOutcomeBill.NOMAL);

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;

        for (StockOutcomeBillItem item : stockOutcomeBill.getItems()) {
            BigDecimal amount = item.getNumber().multiply(item.getSalePrice()).setScale(2,BigDecimal.ROUND_HALF_UP);
            item.setAmount(amount);
            totalNumber = totalNumber.add(item.getNumber()).setScale(2,BigDecimal.ROUND_HALF_UP);
            totalAmount = totalAmount.add(amount).setScale(2,BigDecimal.ROUND_HALF_UP);
        }

        stockOutcomeBill.setTotalAmount(totalAmount);
        stockOutcomeBill.setTotalNumber(totalNumber);

        stockOutcomeBillMapper.insert(stockOutcomeBill);

        for (StockOutcomeBillItem item : stockOutcomeBill.getItems()) {
            item.setStockOutcomeBill(stockOutcomeBill);
            stockOutcomeBillMapper.insertItem(item);
        }

    }

    @Override
    public void delete(Long id) {

        if (stockOutcomeBillMapper.selectByPrimaryKey(id).getStatus() == StockOutcomeBill.AUDIT) {
            throw  new RuntimeException("已审核的销售出库单不能被删除!");
        }

        stockOutcomeBillMapper.deleteItemsByBillId(id);
        stockOutcomeBillMapper.deleteByPrimaryKey(id);

    }

    @Override
    public void update(StockOutcomeBill stockOutcomeBill) {

        stockOutcomeBillMapper.deleteItemsByBillId(stockOutcomeBill.getId());

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (StockOutcomeBillItem item : stockOutcomeBill.getItems()) {

            BigDecimal amount = item.getNumber().multiply(item.getSalePrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
            item.setAmount(amount);
            item.setStockOutcomeBill(stockOutcomeBill);
            stockOutcomeBillMapper.insertItem(item);

            totalAmount = totalAmount.add(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
            totalNumber = totalNumber.add(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        stockOutcomeBill.setTotalNumber(totalNumber);
        stockOutcomeBill.setTotalAmount(totalAmount);

        stockOutcomeBillMapper.updateByPrimaryKey(stockOutcomeBill);

    }

    @Override
    public StockOutcomeBill get(Long id) {

        return stockOutcomeBillMapper.selectByPrimaryKey(id);
    }


    @Override
    public PageResult query(StockOutcomeBillQueryObject qo) {
        Integer totalCount = stockOutcomeBillMapper.queryForCount(qo);

        if (totalCount == 0) {
            return new PageResult().emptyPageResult(qo.getPageSize());
        }

        List<StockOutcomeBill> listData = stockOutcomeBillMapper.queryForList(qo);

        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void audit(Long id){

        StockOutcomeBill bill = stockOutcomeBillMapper.selectByPrimaryKey(id);
        if (bill.getStatus() == StockOutcomeBill.AUDIT) {
            throw  new RuntimeException("已审核的销售订单不能再审核!");
        }

        Depot depot = bill.getDepot();

        for (StockOutcomeBillItem item : bill.getItems()) {

            //出产品库
            BigDecimal costPrice = productStockService.outcome(item.getNumber(), item.getProduct(), depot);

            //进销售帐
            SaleAccount saleAccount = new SaleAccount();
            saleAccount.setCostPrice(costPrice);
            saleAccount.setNumber(item.getNumber());
            saleAccount.setCostAmount(costPrice.multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP));
            saleAccount.setSalePrice(item.getSalePrice());
            saleAccount.setSaleAmount(item.getAmount());
            saleAccount.setInputUser(bill.getInputUser());
            saleAccount.setProduct(item.getProduct());
            saleAccount.setClient(bill.getClient());
            saleAccount.setVdate(bill.getVdate());
            saleAccountService.save(saleAccount);
        }

        bill.setStatus(StockOutcomeBill.AUDIT);
        bill.setAuditTime(new Date());
        bill.setAuditor(UserContext.getUser());
        stockOutcomeBillMapper.audit(bill);
    }

}
