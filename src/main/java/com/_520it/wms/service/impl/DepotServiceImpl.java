package com._520it.wms.service.impl;

import com._520it.wms.domain.Depot;
import com._520it.wms.mapper.DepotMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepotService;
import lombok.Setter;

import java.util.List;

public class DepotServiceImpl implements IDepotService {

    @Setter
    private DepotMapper depotMapper;

    @Override
    public void save(Depot depot) {
        depotMapper.insert(depot);
    }

    @Override
    public void delete(Long id) {
        depotMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Depot depot) {
        depotMapper.updateByPrimaryKey(depot);

    }

    @Override
    public Depot get(Long id) {

        return depotMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Depot> listAll() {

        return depotMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer totalCount = depotMapper.queryForCount(qo);

        if (totalCount == 0) {
            return new PageResult().emptyPageResult(qo.getPageSize());
        }

        List<Depot> listData = depotMapper.queryForList(qo);

        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

}
