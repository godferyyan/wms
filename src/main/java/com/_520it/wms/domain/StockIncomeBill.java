package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class StockIncomeBill extends BaseDomian {


    public static final int NORMAL = 0;
    public static final int AUDIT = 1;

    private String sn;

    private Date vdate;

    private int status = NORMAL;

    private BigDecimal totalAmount;

    private BigDecimal totalNumber;

    private Date auditTime;

    private Date inputTime;

    private Employee inputUser;

    private Employee auditor;

    private Depot depot;

    private List<StockIncomeBillItem> items = new ArrayList<>();

}