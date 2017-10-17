package com._520it.wms.service.impl;

import com._520it.wms.mapper.ChartMapper;
import com._520it.wms.query.OrderChartQueryObject;
import com._520it.wms.query.SaleChartQueryObject;
import com._520it.wms.service.IChartService;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Created by 阎神 on 2017/9/26.
 */
public class ChartServiceImpl implements IChartService {

    @Setter
    private ChartMapper chartMapper;

    @Override
    public List<Map<String, Object>> orderChart(OrderChartQueryObject qo) {
        return chartMapper.orderChart(qo);
    }

    @Override
    public List<Map<String, Object>> saleChart(SaleChartQueryObject qo) {
        return chartMapper.saleChart(qo);
    }


}
