package com._520it.wms.mapper;

import com._520it.wms.domain.SystemMenu;
import com._520it.wms.query.SystemMenuQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SystemMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemMenu record);

    SystemMenu selectByPrimaryKey(Long id);

    List<SystemMenu> selectAll();

    int updateByPrimaryKey(SystemMenu record);

    int queryForCount(SystemMenuQueryObject qo);

    List<SystemMenu> queryForList(SystemMenuQueryObject qo);

    List<SystemMenu> listChildMenus();

    List<Map<String, Object>> queryMenusBySn(String parentSn);

    List<Map<String, Object>> queryMenusBySnAndEmpId(@Param("empId") Long empId, @Param("parentSn") String parentSn);
}