package com._520it.wms.service.impl;

import com._520it.wms.domain.Supplier;
import com._520it.wms.mapper.SupplierMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.ISupplierService;
import lombok.Setter;

import java.util.List;

public class SupplierServiceImpl implements ISupplierService {

    @Setter
    private SupplierMapper supplierMapper;

    @Override
    public void save(Supplier supplier) {
        supplierMapper.insert(supplier);
    }

    @Override
    public void delete(Long id) {
        supplierMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Supplier supplier) {
        supplierMapper.updateByPrimaryKey(supplier);

    }

    @Override
    public Supplier get(Long id) {

        return supplierMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Supplier> listAll() {

        return supplierMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer totalCount = supplierMapper.queryForCount(qo);

        if (totalCount == 0) {
            return new PageResult().emptyPageResult(qo.getPageSize());
        }

        List<Supplier> listData = supplierMapper.queryForList(qo);

        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

}
