package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Depot extends BaseDomian {

    private Long id;

    private String location;

    private String name;

}