package com._520it.wms.web.action;

import com._520it.wms.domain.Client;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IClientService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;

public class ClientAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private IClientService clientService;

    @Getter
    private QueryObject qo = new QueryObject();

    @Getter
    private Client client = new Client();

    @Override
    @RequiredPermission("客户列表")
    @InputConfig(methodName = "input")
    public String execute() throws Exception {

        try {
            PageResult result = clientService.query(qo);

            putContext(RESULT, result);

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦:" + e.getMessage());
        }

        return LIST;
    }

    @RequiredPermission("客户删除")
    public String delete() {

        try {
            if (client.getId() != null) {
                clientService.delete(client.getId());
                putMsg("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("删除失败");
        }

        return NONE;
    }

    @RequiredPermission("客户录入")
    public String input() throws Exception {

        if (client.getId() != null) {
            client = clientService.get(client.getId());
        }

        return INPUT;
    }

    @RequiredPermission("客户保存或更新")
    public String saveOrUpdate() throws Exception {

        try {
            if (client.getId() != null) {
                System.out.println(client.getId());
                clientService.update(client);
                addActionMessage("更新成功!");
            } else {
                clientService.save(client);
                addActionMessage("保存成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("保存失败!" + e.getMessage());
        }

        return SUCCESS;
    }

}
