package com._520it.wms.service;

import com._520it.wms.domain.Role;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface IRoleService {

	void save(Role role);

	void delete(Long id);

	void update(Role role);

	Role get(Long id);

	List<Role> listAll();

	PageResult query(QueryObject qo);

    void batchDelete(List<Long> ids);
}
