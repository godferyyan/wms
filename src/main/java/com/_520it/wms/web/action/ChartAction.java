package com._520it.wms.web.action;

import com._520it.wms.domain.RequiredPermission;
import com._520it.wms.query.OrderChartQueryObject;
import com._520it.wms.query.SaleChartQueryObject;
import com._520it.wms.service.IBrandService;
import com._520it.wms.service.IChartService;
import com._520it.wms.service.IClientService;
import com._520it.wms.service.ISupplierService;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -5599265026860755709L;

    @Setter
    private IChartService chartService;

    @Setter
    private ISupplierService supplierService;

    @Setter
    private IBrandService brandService;

    @Setter
    private IClientService clientService;

    @Getter
    private OrderChartQueryObject oqo = new OrderChartQueryObject();
    @Getter
    private SaleChartQueryObject sqo = new SaleChartQueryObject();


    @RequiredPermission("订单报表")
    public String orderChart() throws Exception {

        try {

            List<Map<String, Object>> maps = chartService.orderChart(oqo);

            putContext(RESULT, maps);
            putContext("suppliers", supplierService.listAll());
            putContext("brands", brandService.listAll());

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦:" + e.getMessage());
        }

        return "orderChart";
    }

    @RequiredPermission("销售报表")
    public String saleChart() throws Exception {

        try {

            List<Map<String, Object>> maps = chartService.saleChart(sqo);

            putContext(RESULT, maps);
            putContext("clients", clientService.listAll());
            putContext("brands", brandService.listAll());

        } catch (Exception e) {
            e.printStackTrace();
            addActionError("出错啦:" + e.getMessage());
        }

        return "saleChart";
    }

    public String saleChartByLine() throws Exception {
        List<Map<String, Object>> maps = chartService.saleChart(sqo);
        List<Object> x = new ArrayList<>();
        List<Object> y = new ArrayList<>();

        for (Map<String, Object> map : maps) {
            x.add(map.get("groupType"));
            y.add(map.get("totalAmount"));
        }

        String subtext = "销售人员";
        switch (sqo.getGroupType()) {
            case "p.name":
                subtext = "货品名称";
                break;
            case "c.name":
                subtext = "客户";
                break;
            case "b.name":
                subtext = "货品品牌";
                break;
            case "date_format(sc.vdate,'%Y-%m')":
                subtext = "销售日期(月)";
                break;
            case "date_format(sc.vdate,'%Y-%m-%d')":
                subtext = "销售日期(日)";
        }
        putContext("subtext", subtext);
        putContext("x", JSON.toJSONString(x));
        putContext("y", JSON.toJSONString(y));


        return "saleChartByLine";
    }

    public String saleChartByPie() throws Exception {
        List<Map<String, Object>> maps = chartService.saleChart(sqo);

        String subtext = "销售人员";

        List<Map<String, Object>> datas = new ArrayList<>();

        BigDecimal max = BigDecimal.ZERO;

        for (Map<String, Object> map : maps) {

            Map<String, Object> data = new HashMap<>();
            data.put("name", map.get("groupType"));
            data.put("value", map.get("totalAmount"));
            datas.add(data);
            if (max.compareTo((BigDecimal) map.get("totalAmount")) < 0) {
                max = (BigDecimal) map.get("totalAmount");
            }

        }

        switch (sqo.getGroupType()) {
            case "p.name":
                subtext = "货品名称";
                break;
            case "c.name":
                subtext = "客户";
                break;
            case "b.name":
                subtext = "货品品牌";
                break;
            case "date_format(sc.vdate,'%Y-%m')":
                subtext = "销售日期(月)";
                break;
            case "date_format(sc.vdate,'%Y-%m-%d')":
                subtext = "销售日期(日)";
        }

        putContext("subtext", subtext);
        putContext("datas", JSON.toJSONString(datas));
        putContext("max", max);


        return "saleChartByPie";
    }


}
