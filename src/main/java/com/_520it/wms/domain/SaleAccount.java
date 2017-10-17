package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

//销售帐
@Setter@Getter
public class SaleAccount extends BaseDomian {

    private Date vdate; //stockOutcomeBill的vdate一致,销售出库单的业务时间

    private BigDecimal number; //销售的数量

    private BigDecimal costPrice; //该销售帐记录对应商品的出库时的成本价

    private BigDecimal costAmount; //该商品的成本总价

    private BigDecimal salePrice; //该商品的销售单价

    private BigDecimal saleAmount; //该商品的销售总价

    private Product product; //该销售帐记录对应的商品

    private Employee inputUser; //销售出库单的制单人,也就是销售人员

    private Client client;//该商品销售的客户


}