package com._520it.wms.mapper;

import com._520it.wms.query.OrderChartQueryObject;
import com._520it.wms.query.SaleChartQueryObject;

import java.util.List;
import java.util.Map;

/**
 * Created by 阎神 on 2017/9/26.
 */
public interface ChartMapper {
    List<Map<String, Object>> orderChart(OrderChartQueryObject qo);

    List<Map<String, Object>> saleChart(SaleChartQueryObject qo);

}
