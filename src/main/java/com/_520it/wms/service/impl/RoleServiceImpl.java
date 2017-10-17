package com._520it.wms.service.impl;

import com._520it.wms.domain.Permission;
import com._520it.wms.domain.Role;
import com._520it.wms.domain.SystemMenu;
import com._520it.wms.mapper.RoleMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IRoleService;
import lombok.Setter;

import java.util.List;

public class RoleServiceImpl implements IRoleService {

	@Setter
	private RoleMapper roleMapper;

	@Override
	public void save(Role role) {
		
		roleMapper.save(role);
		
		//新增之后也要添加关系,先保存角色,获取自动生成的主键后再进行关系更新操作
		List<Permission> permissions = role.getPermissions();
		for (Permission permission : permissions) {
			roleMapper.updateRelation(role.getId(), permission.getId());
		}

        List<SystemMenu> menus = role.getMenus();
        for (SystemMenu menu : menus) {
            roleMapper.updateMenuRelation(role.getId(), menu.getId());
        }

	}

	@Override
	public void delete(Long id) {
		//先删除关系表中的数据,再删除原表中的记录
		
		roleMapper.deleteRelation(id);
		roleMapper.deleteMenuRelation(id);
		
		roleMapper.delete(id);
	}

	@Override
	public void update(Role role) {
		//正常更新角色表
		roleMapper.update(role);

		roleMapper.deleteRelation(role.getId());
		roleMapper.deleteMenuRelation(role.getId());

		//更新角色权限关系表
		List<Permission> permissions = role.getPermissions();
		for (Permission permission : permissions) {
			roleMapper.updateRelation(role.getId(),permission.getId());
		}

		//更新角色系统菜单关系表
        List<SystemMenu> menus = role.getMenus();
        for (SystemMenu menu : menus) {
            roleMapper.updateMenuRelation(role.getId(), menu.getId());
        }

    }

	@Override
	public Role get(Long id) {

		return roleMapper.get(id);
	}

	@Override
	public List<Role> listAll() {

		return roleMapper.listAll();

	}

	@Override
	public PageResult query(QueryObject qo) {
		Integer totalCount = roleMapper.queryForCount(qo);

		if (totalCount == 0) {
			return new PageResult().emptyPageResult(qo.getPageSize());
		}

		List<Role> listData = roleMapper.queryForList(qo);

		return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
	}

    @Override
    public void batchDelete(List<Long> ids) {
        if (ids.size() > 0){
            roleMapper.batchDeleteRelation(ids);
            roleMapper.batchDeleteMenuRelation(ids);
            roleMapper.batchDelete(ids);
        }
    }

}
