package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Employee extends BaseDomian {

    /**
     *
     */
    private static final long serialVersionUID = 7594174867827395473L;

    private String name;
    private String password;
    private String email;
    private Integer age;
    private boolean admin;
    private Department dept;
    private List<Role> roles = new ArrayList<>();

    @Override
    public String toString() {
        return "Employee [name=" + name + ", password=" + password + ", email=" + email + ", age=" + age + ", admin="
                + admin + "]";
    }

}
