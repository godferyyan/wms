package com._520it.wms.web.action;

import com._520it.wms.domain.Brand;
import com._520it.wms.domain.Product;
import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.ProductQueryObject;
import com._520it.wms.service.IBrandService;
import com._520it.wms.service.IProductService;
import com._520it.wms.util.FileUploadUtil;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

public class ProductAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private IProductService productService;

    @Setter
    private IBrandService brandService;

    @Getter
    private ProductQueryObject qo = new ProductQueryObject();

    @Getter
    private Product product = new Product();

    @Setter
    private File pic;

    @Setter
    private String picFileName;

    @Override
    @RequiredPermission("商品列表")
    @InputConfig(methodName = "input")
    public String execute() {

        try {
            PageResult result = productService.query(qo);
            List<Brand> brands = brandService.listAll();
            putContext(RESULT, result);
            putContext("brands", brands);
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦:" + e.getMessage());
        }

        return LIST;
    }

    @RequiredPermission("商品删除")
    public String delete() {

        try {

            if (product.getId() != null) {

                if (StringUtils.isNotEmpty(product.getImagePath())) {
                    FileUploadUtil.deleteFile(product.getImagePath());
                }

                productService.delete(product.getId());
                putMsg("删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            putMsg("删除失败" + e.getMessage());
        }

        return NONE;
    }

    @RequiredPermission("商品录入")
    public String input() throws Exception {

        List<Brand> brands = brandService.listAll();
        putContext("brands", brands);

        if (product.getId() != null) {
            product = productService.get(product.getId());
        }

        return INPUT;
    }

    @RequiredPermission("商品保存或更新")
    public String saveOrUpdate() {

        try {

            if (pic != null) {

                if (StringUtils.isNotEmpty(product.getImagePath())) {
                    //删除原文件
                    FileUploadUtil.deleteFile(product.getImagePath());
                }

                String path = FileUploadUtil.uploadFile(pic, picFileName);
                product.setImagePath(path);

            }


            if (product.getId() != null) {
                System.out.println(product.getId());
                productService.update(product);
                addActionMessage("更新成功!");
            } else {
                productService.save(product);
                addActionMessage("保存成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("保存失败!" + e.getMessage());
        }

        return SUCCESS;
    }

    public String showItems() {

        try {
            qo.setPageSize(5);
            PageResult result = productService.query(qo);
            List<Brand> brands = brandService.listAll();
            putContext(RESULT, result);
            putContext("brands", brands);
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦:" + e.getMessage());
        }

        return "showItems";
    }
}
