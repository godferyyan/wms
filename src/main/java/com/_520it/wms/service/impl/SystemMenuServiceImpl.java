package com._520it.wms.service.impl;

import com._520it.wms.domain.SystemMenu;
import com._520it.wms.mapper.SystemMenuMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.SystemMenuQueryObject;
import com._520it.wms.service.ISystemMenuService;
import com._520it.wms.util.UserContext;
import lombok.Setter;

import java.util.*;

public class SystemMenuServiceImpl implements ISystemMenuService {

    @Setter
    private SystemMenuMapper systemMenuMapper;

    @Override
    public void save(SystemMenu systemMenu) {
        systemMenuMapper.insert(systemMenu);
    }

    @Override
    public void delete(Long id) {

        SystemMenuQueryObject qo = new SystemMenuQueryObject();
        qo.setParentId(id);

        if (systemMenuMapper.queryForCount(qo) > 0) {
            throw new RuntimeException("有子菜单不能删除!");
        }

        systemMenuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(SystemMenu systemMenu) {
        systemMenuMapper.updateByPrimaryKey(systemMenu);

    }

    @Override
    public SystemMenu get(Long id) {

        return systemMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemMenu> listAll() {

        return systemMenuMapper.selectAll();
    }

    @Override
    public PageResult query(SystemMenuQueryObject qo) {
        Integer totalCount = systemMenuMapper.queryForCount(qo);

        if (totalCount == 0) {
            return new PageResult().emptyPageResult(qo.getPageSize());
        }

        List<SystemMenu> listData = systemMenuMapper.queryForList(qo);

        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public List<Map<String, Object>> queryForParents(Long parentId) {
        List<Map<String, Object>> parents = new ArrayList<>();

        SystemMenu current = get(parentId);

        while (current != null) {

            Map<String, Object> map = new HashMap<>();
            map.put("parentId", current.getId());
            map.put("parentName", current.getName());
            parents.add(map);
            if (current.getParent() != null) {
                current = get(current.getParent().getId());
            } else {
                current = null;
            }
        }

        Collections.reverse(parents);

        return parents;
    }

    @Override
    public List<SystemMenu> listChildMenus() {

        return systemMenuMapper.listChildMenus();
    }

    @Override
    public List<Map<String, Object>> queryMenusBySn(String parentSn) {
        //先检查当前登录用户是否是超级管理员,如果是,就直接按照查询子菜单,否则还要按照当前用户的菜单权限进行查找
        if (!UserContext.getUser().isAdmin()) {
            return systemMenuMapper.queryMenusBySnAndEmpId(UserContext.getUser().getId(), parentSn);
        }

        return systemMenuMapper.queryMenusBySn(parentSn);
    }

}
