package com._520it.wms.service;

import com._520it.wms.domain.Depot;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface IDepotService {
	
	void save(Depot depot);

	void delete(Long id);

	void update(Depot depot);

	Depot get(Long id);

	List<Depot> listAll();

	PageResult query(QueryObject qo);

}
