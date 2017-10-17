package com._520it.wms.service;

import com._520it.wms.domain.Client;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface IClientService {
	
	void save(Client client);

	void delete(Long id);

	void update(Client client);

	Client get(Long id);

	List<Client> listAll();

	PageResult query(QueryObject qo);

}
