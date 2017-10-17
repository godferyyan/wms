package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SystemMenu extends BaseDomian {

    private String name;

    private String url;

    private String sn;


    private SystemMenu parent;

    private List<SystemMenu> children = new ArrayList<>();

    public String getParentName() {
        return parent == null ? "根目录" : parent.getName();
    }
}