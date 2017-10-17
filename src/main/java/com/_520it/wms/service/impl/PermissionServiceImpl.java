package com._520it.wms.service.impl;

import com._520it.wms.domain.Employee;
import com._520it.wms.domain.Permission;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.mapper.PermissionMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IPermissionService;
import com._520it.wms.util.PermissionUtil;
import com._520it.wms.web.action.BaseAction;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.*;

public class PermissionServiceImpl implements IPermissionService, ApplicationContextAware {

    @Setter
    private PermissionMapper permissionMapper;
    private ApplicationContext ctx;

    @Override
    public void save(Permission permission) {

        permissionMapper.save(permission);

    }

    @Override
    public void delete(Long id) {
        permissionMapper.delete(id);

    }

    @Override
    public List<Permission> listAll() {

        return permissionMapper.listAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer totalCount = permissionMapper.queryForCount(qo);

        if (totalCount == 0) {
            return new PageResult().emptyPageResult(qo.getPageSize());
        }

        List<Employee> listData = permissionMapper.queryForList(qo);

        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());

    }

    @Override
    public void reload() {
        //获取容器对象
        Map<String, BaseAction> actions = ctx.getBeansOfType(BaseAction.class);

        Collection<BaseAction> values = actions.values();

        List<Permission> permissions = listAll();

        Set<String> expressions = new HashSet<>();

        for (Permission permission : permissions) {
            expressions.add(permission.getExpression());
        }

        for (BaseAction action : values) {

            Method[] methods = action.getClass().getDeclaredMethods();

            for (Method method : methods) {

                if (method.isAnnotationPresent(RequiredPermission.class)) {

                    String expression = PermissionUtil.createPermissionExpressions(method);

                    if (!expressions.contains(expression)) {
                        String name = method.getAnnotation(RequiredPermission.class).value();
                        Permission permission = new Permission();
                        permission.setName(name);
                        permission.setExpression(expression);
                        save(permission);
                    }
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }

}
