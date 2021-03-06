package com._520it.wms.service.impl;

import com._520it.wms.domain.Client;
import com._520it.wms.mapper.ClientMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IClientService;
import lombok.Setter;

import java.util.List;

public class ClientServiceImpl implements IClientService {

    @Setter
    private ClientMapper clientMapper;

    @Override
    public void save(Client client) {
        clientMapper.insert(client);
    }

    @Override
    public void delete(Long id) {
        clientMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Client client) {
        clientMapper.updateByPrimaryKey(client);

    }

    @Override
    public Client get(Long id) {

        return clientMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Client> listAll() {

        return clientMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer totalCount = clientMapper.queryForCount(qo);

        if (totalCount == 0) {
            return new PageResult().emptyPageResult(qo.getPageSize());
        }

        List<Client> listData = clientMapper.queryForList(qo);

        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

}
