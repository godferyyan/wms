package com._520it.wms.service.impl;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.domain.OrderBillItem;
import com._520it.wms.mapper.OrderBillMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.OrderBillQueryObject;
import com._520it.wms.service.IOrderBillService;
import com._520it.wms.util.UserContext;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderBillServiceImpl implements IOrderBillService {

    @Setter
    private OrderBillMapper orderBillMapper;



    @Override
    public void save(OrderBill orderBill) {
        //保存需要注意的操作
        orderBill.setStatus(OrderBill.NORMAL);
        orderBill.setInputUser(UserContext.getUser());
        orderBill.setInputTime(new Date());
        orderBill.setAuditor(null);

        List<OrderBillItem> items = orderBill.getItems();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (OrderBillItem item : items) {
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            item.setAmount(amount);
            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }

        orderBill.setTotalNumber(totalNumber);
        orderBill.setTotalAmount(totalAmount);
        orderBillMapper.insert(orderBill);

        for (OrderBillItem item : items) {
            item.setOrderBill(orderBill);
            orderBillMapper.insertItems(item);
        }
    }

    @Override
    public void delete(Long id) {

        OrderBill orderBill = orderBillMapper.selectByPrimaryKey(id);

        //如果是通过浏览器发送请求直接访问删除方法,并传来一个不存在的订单id,那么执行下面的操作的时候就可能出现异常
        if (orderBill != null) {
            if (orderBill.getStatus() == OrderBill.AUDIT) {
                throw new RuntimeException("已审核的订单不能删除!");
            }
            orderBillMapper.deleteItemsByBillId(id);
            orderBillMapper.deleteByPrimaryKey(id);
        } else {
            throw new RuntimeException("该订单不存在!");
        }
    }

    @Override
    public void update(OrderBill orderBill) {

        orderBillMapper.deleteItemsByBillId(orderBill.getId());

        List<OrderBillItem> items = orderBill.getItems();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (OrderBillItem item : items) {
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            item.setAmount(amount);
            item.setOrderBill(orderBill);
            orderBillMapper.insertItems(item);
            totalAmount = totalAmount.add(totalAmount);
            totalNumber = totalNumber.add(totalNumber).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }

        orderBill.setTotalAmount(totalAmount);
        orderBill.setTotalNumber(totalNumber);

        orderBillMapper.updateByPrimaryKey(orderBill);

    }

    @Override
    public OrderBill get(Long id) {

        return orderBillMapper.selectByPrimaryKey(id);
    }


    @Override
    public PageResult query(OrderBillQueryObject qo) {
        Integer totalCount = orderBillMapper.queryForCount(qo);

        if (totalCount == 0) {
            return new PageResult().emptyPageResult(qo.getPageSize());
        }

        List<OrderBill> listData = orderBillMapper.queryForList(qo);

        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void audit(Long id) {

        OrderBill orderBill = orderBillMapper.selectByPrimaryKey(id);
        if (orderBill.getStatus() == OrderBill.AUDIT) {
            throw new RuntimeException("已审核的订单不能再次审核!");
        }

        orderBill.setStatus(OrderBill.AUDIT);
        orderBill.setAuditor(UserContext.getUser());
        orderBill.setAuditTime(new Date());
        orderBillMapper.audit(orderBill);

    }

}
