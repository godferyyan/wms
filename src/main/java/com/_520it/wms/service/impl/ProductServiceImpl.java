package com._520it.wms.service.impl;

import com._520it.wms.domain.Product;
import com._520it.wms.mapper.ProductMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.ProductQueryObject;
import com._520it.wms.service.IProductService;
import lombok.Setter;

import java.util.List;

public class ProductServiceImpl implements IProductService {

    @Setter
    private ProductMapper productMapper;

    @Override
    public void save(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(Long id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKey(product);

    }

    @Override
    public Product get(Long id) {

        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Product> listAll() {

        return productMapper.selectAll();
    }

    @Override
    public PageResult query(ProductQueryObject qo) {
        Integer totalCount = productMapper.queryForCount(qo);

        if (totalCount == 0) {
            return new PageResult().emptyPageResult(qo.getPageSize());
        }

        List<Product> listData = productMapper.queryForList(qo);

        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

}
