package com._520it.wms.domain;

import com._520it.wms.util.FileUploadUtil;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Product extends BaseDomian {

    private Long id;

    private String name;

    private String sn;

    private BigDecimal costPrice;

    private BigDecimal salePrice;

    private String imagePath;

    private String intro;

    private Brand brand;

    public String getSmallImagePath() {

        if (imagePath != null) {
            int index = imagePath.lastIndexOf(".");
            return imagePath.substring(0, index) + FileUploadUtil.suffix + imagePath.substring(index);
        }
        return null;
    }

    public String getSelectedItem() {

        Map<String, Object> item = new HashMap<>();

        item.put("name",name);
        item.put("id",getId());
        item.put("brand",brand == null? "":brand.getName());
        item.put("costPrice",costPrice);
        item.put("salePrice",salePrice);

        return JSON.toJSONString(item);
    }

}