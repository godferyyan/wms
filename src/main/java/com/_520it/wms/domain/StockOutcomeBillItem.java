package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by 阎神 on 2017/9/23.
 */
//销售出库单明细
@Setter
@Getter
public class StockOutcomeBillItem extends BaseDomian {

    private BigDecimal salePrice;

    private BigDecimal number;

    private BigDecimal amount;

    private String remark;

    private Product product;

    private StockOutcomeBill stockOutcomeBill;

}
