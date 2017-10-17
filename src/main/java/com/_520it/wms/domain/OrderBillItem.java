package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by 阎神 on 2017/9/23.
 */
@Setter
@Getter
public class OrderBillItem extends BaseDomian {
    private BigDecimal costPrice;
    private BigDecimal number;
    private BigDecimal amount;
    private String remark;
    private Product product;
    private OrderBill orderBill;
}
