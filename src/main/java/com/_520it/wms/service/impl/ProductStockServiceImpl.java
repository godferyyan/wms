package com._520it.wms.service.impl;

import com._520it.wms.domain.Depot;
import com._520it.wms.domain.Product;
import com._520it.wms.domain.ProductStock;
import com._520it.wms.mapper.ProductStockMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.ProductStockQueryObject;
import com._520it.wms.service.IProductStockService;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 阎神 on 2017/9/26.
 */
public class ProductStockServiceImpl implements IProductStockService {

    @Setter
    private ProductStockMapper productStockMapper;

    @Override
    public PageResult query(ProductStockQueryObject qo) {
        Integer totalCount = productStockMapper.queryForCount(qo);

        if (totalCount == 0) {
            return new PageResult().emptyPageResult(qo.getPageSize());
        }

        List<ProductStock> listData = productStockMapper.queryForList(qo);

        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void income(BigDecimal costPrice, BigDecimal number, Product product, Depot depot) {

        ProductStock productStock = productStockMapper.selectItemByDepotIdAndProductId(depot.getId(), product.getId());

        if (productStock == null) {

            productStock = new ProductStock();
            productStock.setPrice(costPrice);
            productStock.setStoreNumber(number);
            productStock.setAmount(costPrice.multiply(number).setScale(2,BigDecimal.ROUND_HALF_UP));
            productStock.setProduct(product);
            productStock.setDepot(depot);
            productStockMapper.insert(productStock);

        } else {

            productStock.setAmount(productStock.getAmount().add(costPrice.multiply(number).setScale(2,BigDecimal.ROUND_HALF_UP)));
            productStock.setStoreNumber(productStock.getStoreNumber().add(number));
            productStock.setPrice(productStock.getAmount().divide(productStock.getStoreNumber(), 2, BigDecimal.ROUND_HALF_EVEN));
            productStockMapper.updateByPrimaryKey(productStock);

        }
    }

    @Override
    public BigDecimal outcome(BigDecimal number, Product product, Depot depot) {

        ProductStock ps = productStockMapper.selectItemByDepotIdAndProductId(depot.getId(), product.getId());

        if (ps == null) {
            throw new RuntimeException(depot.getName()+"的"+product.getName()+" 没有库存! 请及时补充库存!");
        }

        if (ps.getStoreNumber().compareTo(number) < 0) {
            throw new RuntimeException(depot.getName() + "的" + product.getName() + " 库存仅剩" + ps.getStoreNumber() + "件! 请及时补充库存!");
        }

        ps.setStoreNumber(ps.getStoreNumber().subtract(number).setScale(2,BigDecimal.ROUND_HALF_UP));
        ps.setAmount(ps.getStoreNumber().multiply(ps.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP));
        productStockMapper.updateByPrimaryKey(ps);

        return ps.getPrice();
    }
}
