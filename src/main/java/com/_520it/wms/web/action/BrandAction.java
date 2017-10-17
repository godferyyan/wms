package com._520it.wms.web.action;

import com._520it.wms.domain.Brand;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IBrandService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;

public class BrandAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private IBrandService brandService;

    @Getter
    private QueryObject qo = new QueryObject();

    @Getter
    private Brand brand = new Brand();

    @Override
    @RequiredPermission("品牌列表")
    @InputConfig(methodName = "input")
    public String execute() throws Exception {

        try {
            PageResult result = brandService.query(qo);

            putContext(RESULT, result);

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦:" + e.getMessage());
        }

        return LIST;
    }

    @RequiredPermission("品牌删除")
    public String delete() {

        try {
            if (brand.getId() != null) {
                brandService.delete(brand.getId());
                putMsg("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("删除失败");
        }

        return NONE;
    }

    @RequiredPermission("品牌录入")
    public String input() throws Exception {

        if (brand.getId() != null) {
            brand = brandService.get(brand.getId());
        }

        return INPUT;
    }

    @RequiredPermission("品牌保存或更新")
    public String saveOrUpdate() throws Exception {

        try {
            if (brand.getId() != null) {
                System.out.println(brand.getId());
                brandService.update(brand);
                addActionMessage("更新成功!");
            } else {
                brandService.save(brand);
                addActionMessage("保存成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("保存失败!" + e.getMessage());
        }

        return SUCCESS;
    }

}
