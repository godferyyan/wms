package com._520it.wms.mapper;

import com._520it.wms.domain.ProductStock;
import com._520it.wms.query.ProductStockQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductStockMapper {


    int insert(ProductStock record);

    int updateByPrimaryKey(ProductStock record);

    ProductStock selectItemByDepotIdAndProductId(@Param("depotId") Long depotId, @Param("productId") Long productId);

    int queryForCount(ProductStockQueryObject qo);

    List<ProductStock> queryForList(ProductStockQueryObject qo);
}