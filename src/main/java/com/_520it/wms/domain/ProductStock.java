package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter@Getter
public class ProductStock extends BaseDomian{

    private BigDecimal price;

    private BigDecimal storeNumber;

    private BigDecimal amount;

    private Product product;

    private Depot depot;

}