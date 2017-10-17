package com._520it.wms.service;

import com._520it.wms.domain.SystemMenu;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.SystemMenuQueryObject;

import java.util.List;
import java.util.Map;

public interface ISystemMenuService {
	
	void save(SystemMenu systemMenu);

	void delete(Long id);

	void update(SystemMenu systemMenu);

	SystemMenu get(Long id);

	List<SystemMenu> listAll();

	PageResult query(SystemMenuQueryObject qo);

    List<Map<String,Object>> queryForParents(Long parentId);

    List<SystemMenu> listChildMenus();

    List<Map<String, Object>> queryMenusBySn(String parentSn);
}
