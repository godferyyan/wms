package com._520it.wms.service.impl;

import com._520it.wms.domain.Brand;
import com._520it.wms.mapper.BrandMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IBrandService;
import lombok.Setter;

import java.util.List;

public class BrandServiceImpl implements IBrandService {

    @Setter
    private BrandMapper brandMapper;

    @Override
    public void save(Brand brand) {
        brandMapper.insert(brand);
    }

    @Override
    public void delete(Long id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);

    }

    @Override
    public Brand get(Long id) {

        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Brand> listAll() {

        return brandMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer totalCount = brandMapper.queryForCount(qo);

        if (totalCount == 0) {
            return new PageResult().emptyPageResult(qo.getPageSize());
        }

        List<Brand> listData = brandMapper.queryForList(qo);

        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

}
