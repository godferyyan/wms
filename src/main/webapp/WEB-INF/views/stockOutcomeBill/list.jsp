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
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript">
        $(function () {
            $(".btn_audit").click(function () {
                var url = $(this).data("url");
                showDialogMsg("", "确定要审核么", function () {
                    $.get(url, function (data) {
                        showDialogMsg("", data, function () {
                            window.location.reload();
                        }, "")
                    });
                }, true)
            });

            $("[name='qo.beginDate']").click(function () {
                var endDate = $("[name='qo.endDate']").val();
                WdatePicker({
                    minDate: "1900-01-01 00:00:00",
                    maxDate: endDate || new Date()
                });
            });

            $("[name='qo.endDate']").click(function () {
                var beginDate = $("[name='qo.beginDate']").val();
                WdatePicker({
                    minDate: beginDate || "1900-01-01 00:00:00",
                    maxDate: new Date()
                });
            });
        });
    </script>
    <%@include file="/WEB-INF/views/common/common_msg.jsp" %>
    <title>PSS-销售出库单管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<s:debug></s:debug>
<s:form id="searchForm" action="stockOutcomeBill" namespace="/">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        业务时间
                        <s:date name="qo.beginDate" format="yyyy-MM-dd" var="beginDate"/>
                        <s:textfield class="ui_input_txt02 Wdate" name="qo.beginDate" value="%{#beginDate}"/>
                        ~
                        <s:date name="qo.endDate" format="yyyy-MM-dd" var="endDate"/>
                        <s:textfield class="ui_input_txt02 Wdate" name="qo.endDate" value="%{#endDate}"/>
                        入库仓库:
                        <s:select class="ui_select01" name="qo.depotId" list="#depots" listKey="id"
                                  listValue="name"
                                  headerKey="-1" headerValue="全部"/>
                        客户:
                        <s:select class="ui_select01" name="qo.clientId" list="#clients" listKey="id"
                                  listValue="name"
                                  headerKey="-1" headerValue="全部"/>
                        状态
                        <s:select class="ui_select01" name="qo.status" list="#{'-1':'全部','0':'未审核','1':'已审核'}"/>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_input"
                               data-url="<s:url namespace="/" action="stockOutcomeBill_input"/>"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>出库单编号</th>
                        <th>业务时间</th>
                        <th>仓库</th>
                        <th>客户</th>
                        <th>出库总数量</th>
                        <th>出库总金额</th>
                        <th>录入人</th>
                        <th>审核人</th>
                        <th>审核状态</th>
                        <th>操作</th>
                    </tr>
                    <tbody>
                    <s:iterator value="#result.listData">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb" data-oid="<s:property value="id"/>"/>
                            </td>
                            <td><s:property value="sn"/></td>
                            <td>
                                <s:date name="vdate" format="yyyy-MM-dd"/>
                            </td>
                            <td><s:property value="depot.name"/></td>
                            <td><s:property value="client.name"/></td>
                            <td><s:property value="totalNumber"/></td>
                            <td><s:property value="totalAmount"/></td>
                            <td><s:property value="inputUser.name"/></td>
                            <td><s:property value="auditor.name"/></td>
                            <s:if test="status == 0">
                                <td><span style="color: green">未审核</span></td>
                                <td>
                                    <s:url namespace="/" action="stockOutcomeBill_audit" var="auditUrl">
                                        <s:param name="stockOutcomeBill.id" value="id"/>
                                    </s:url>
                                    <a class="btn_audit" href="javascript:;"
                                       data-url="<s:property value="#auditUrl"/>">审核</a>

                                    <s:a namespace="/" action="stockOutcomeBill_input">
                                        <s:param name="stockOutcomeBill.id" value="id"/>
                                        编辑
                                    </s:a>

                                    <s:url namespace="/" action="stockOutcomeBill_delete" var="url">
                                        <s:param name="stockOutcomeBill.id" value="id"/>
                                    </s:url>
                                    <a class="btn_delete" href="javascript:;"
                                       data-url="<s:property value="#url"/>">删除</a>
                                </td>
                            </s:if>
                            <s:else>
                                <td><span style="color: red">已审核</span></td>
                                <td>
                                    <s:a namespace="/" action="stockOutcomeBill_show">
                                        <s:param name="stockOutcomeBill.id" value="id"/>
                                        查看
                                    </s:a>
                                </td>
                            </s:else>
                        </tr>
                    </s:iterator>
                    </tbody>
                </table>
            </div>
            <%@include file="/WEB-INF/views/common/common_page.jsp" %>
        </div>
    </div>
</s:form>
</body>
</html>

