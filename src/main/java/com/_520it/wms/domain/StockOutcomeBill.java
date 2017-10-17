package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//销售出库单
@Setter@Getter
public class StockOutcomeBill extends BaseDomian{

    public static final int NOMAL = 0; //未审核
    public static final int AUDIT = 1; //已审核


    private String sn;

    private Date vdate;

    private int status = NOMAL;

    private BigDecimal totalAmount;

    private BigDecimal totalNumber;

    private Date auditTime;

    private Date inputTime;

    private Employee inputUser;

    private Employee auditor;

    private Depot depot;

    private Client client;

    private List<StockOutcomeBillItem> items = new ArrayList<>();

}