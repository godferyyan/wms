package com._520it.wms.service;

import com._520it.wms.domain.Product;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.ProductQueryObject;

import java.util.List;

public interface IProductService {
	
	void save(Product product);

	void delete(Long id);

	void update(Product product);

	Product get(Long id);

	List<Product> listAll();

	PageResult query(ProductQueryObject qo);

}
