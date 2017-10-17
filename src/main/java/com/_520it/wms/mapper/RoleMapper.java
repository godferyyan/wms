package com._520it.wms.mapper;

import com._520it.wms.domain.Role;
import com._520it.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
	
	void save(Role role);

	void delete(Long id);

	void update(Role role);

	Role get(Long id);

	List<Role> listAll();

	Integer queryForCount(QueryObject qo);

	List<Role> queryForList(QueryObject qo);

	void updateRelation(@Param("roleId")Long roleId, @Param("permissionId")Long permissionId);

	void deleteRelation(Long id);

    void deleteMenuRelation(Long id);

    void updateMenuRelation(@Param("roleId") Long roleId,@Param("menuId")  Long menuId);

    void batchDeleteRelation(List<Long> ids);

    void batchDeleteMenuRelation(List<Long> ids);

    void batchDelete(List<Long> ids);
}
