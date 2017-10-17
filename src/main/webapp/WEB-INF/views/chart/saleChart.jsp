<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.source.js?skin=opera"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/iframeTools.source.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript">
        $(function () {
            $("[name='sqo.beginDate']").click(function () {
                var endDate = $("[name='sqo.endDate']").val();
                WdatePicker({
                    minDate: "1900-01-01 00:00:00",
                    maxDate: endDate || new Date()
                });
            });
            $("[name='sqo.endDate']").click(function () {
                var beginDate = $("[name='sqo.beginDate']").val();
                WdatePicker({
                    minDate: beginDate || "1900-01-01 00:00:00",
                    maxDate: new Date()
                });
            });
            $("#graph").change(function () {
                url = $(this).val();
                condition = $("#searchForm").serialize();
                $.dialog.open(url+"?"+condition,{
                    title:"销售报表",
                    id:"graph",
                    width:"600px",
                    height:"400px"
                });
            });
        });
    </script>
    <title>PSS-销售报表</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<s:debug></s:debug>
<s:form id="searchForm" action="chart_saleChart" namespace="/">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        业务时间
                        <s:date name="sqo.beginDate" format="yyyy-MM-dd" var="beginDate"/>
                        <s:textfield class="ui_input_txt02 Wdate" name="sqo.beginDate" value="%{#beginDate}"/>
                        ~
                        <s:date name="sqo.endDate" format="yyyy-MM-dd" var="endDate"/>
                        <s:textfield class="ui_input_txt02 Wdate" name="sqo.endDate" value="%{#endDate}"/>
                        货品
                        <s:textfield class="ui_input_txt02" name="sqo.keyword"/>
                        客户:
                        <s:select class="ui_select01" name="sqo.clientId" list="#clients" listKey="id"
                                  listValue="name"
                                  headerKey="-1" headerValue="全部"/>
                        品牌:
                        <s:select class="ui_select01" name="sqo.brandId" list="#brands" listKey="id"
                                  listValue="name"
                                  headerKey="-1" headerValue="全部"/>
                        图表:
                        <s:select class="ui_select01" id="graph" list="#{
                        '/chart_saleChartByLine.action':'线形图',
                        '/chart_saleChartByPie.action':'扇形图'
                        }" />
                        分组
                        <s:select class="ui_select01" name="sqo.groupType" list='#{
                        "inputUser.name":"销售人员",
                        "p.name":"货品名称",
                        "c.name":"客户",
                        "b.name":"货品品牌",
                        "date_format(sc.vdate,\'%Y-%m\')":"销售日期(月)",
                        "date_format(sc.vdate,\'%Y-%m-%d\')":"销售日期(日)"}'/>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>分组类型</th>
                        <th>销售总数量</th>
                        <th>销售总金额</th>
                        <th>毛利润</th>
                    </tr>
                    <tbody>
                    <s:iterator value="#result">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb"/>
                            </td>
                            <td><s:property value="groupType"/></td>
                            <td><s:property value="totalNumber"/></td>
                            <td><s:property value="totalAmount"/></td>
                            <td><s:property value="grossProfit"/></td>
                    </s:iterator>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</s:form>
</body>
</html>

