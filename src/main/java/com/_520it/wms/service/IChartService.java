package com._520it.wms.service;

import com._520it.wms.query.OrderChartQueryObject;
import com._520it.wms.query.SaleChartQueryObject;

import java.util.List;
import java.util.Map;

public interface IChartService {
	
	List<Map<String,Object>> orderChart(OrderChartQueryObject qo);
	List<Map<String,Object>> saleChart(SaleChartQueryObject qo);

}
