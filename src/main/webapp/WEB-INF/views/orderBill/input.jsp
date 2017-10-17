<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="../../../style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="../../../style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../../../js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.validate.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.source.js?skin=opera"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/iframeTools.source.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <%@include file="/WEB-INF/views/common/common_msg.jsp" %>
    <script type="text/javascript">
        $(function () {
            $("#edit_table_body").on("click",".searchproduct",function () {
                var currentTr = $(this).closest("tr");
                var url = "/product_showItems.action";
                $.dialog.open(url,{
                    id: 'productList',
                    title: '选择商品',
                    width: 1000,
                    height: 600,
                    close: function () {
                        //子页面关闭之前的操作
                        var productJson = $.dialog.data("productJson");
                        if (productJson) {
                            $(currentTr).find("[tag=name]").val(productJson.name);
                            $(currentTr).find("[tag=pid]").val(productJson.id);
                            $(currentTr).find("[tag=brand]").html(productJson.brand);
                            $(currentTr).find("[tag=costPrice]").val(productJson.costPrice);
                        }
                    }
                });
            }).on("change","[tag=costPrice],[tag=number]",function () {
                var currentTr = $(this).closest("tr");
                var costPrice = $(currentTr).find("[tag=costPrice]").val();
                var number = $(currentTr).find("[tag=number]").val();
                if (costPrice && number) {
                    var amount = (costPrice * number).toFixed(2);
                    $(currentTr).find("[tag=amount]").html(amount);
                }
            }).on("click",".removeItem",function () {
                var currentTr = $(this).closest("tr");
                if ($("#edit_table_body tr").size() > 1) {
                    $(currentTr).remove();
                }else {
                    $(currentTr).find("[tag=name]").val("");
                    $(currentTr).find("[tag=pid]").val("");
                    $(currentTr).find("[tag=brand]").html("");
                    $(currentTr).find("[tag=costPrice]").val("");
                    $(currentTr).find("[tag=number]").val("");
                    $(currentTr).find("[tag=amount]").html("");
                    $(currentTr).find("[tag=remark]").val("");
                }
            });

            $(".appendRow").click(function () {
                var newTr = $("#edit_table_body tr:first").clone();
                $(newTr).find("[tag=name]").val("");
                $(newTr).find("[tag=pid]").val("");
                $(newTr).find("[tag=brand]").html("");
                $(newTr).find("[tag=costPrice]").val("");
                $(newTr).find("[tag=number]").val("");
                $(newTr).find("[tag=amount]").html("");
                $(newTr).find("[tag=remark]").val("");
                $(newTr).appendTo("#edit_table_body");
            });

            $("#editForm").submit(function () {
                $.each($("#edit_table_body tr"),function (index, item) {
                    $(item).find("[tag=pid]").prop("name","orderBill.items["+index+"].product.id");
                    $(item).find("[tag=costPrice]").prop("name","orderBill.items["+index+"].costPrice");
                    $(item).find("[tag=number]").prop("name","orderBill.items["+index+"].number");
                    $(item).find("[tag=remark]").prop("name","orderBill.items["+index+"].remark");
                });
            });

        });
    </script>
</head>
<body>
<s:form name="editForm" action="orderBill_saveOrUpdate" namespace="/" id="editForm">
    <s:hidden name="orderBill.id"/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">采购订单编辑</span>
            <div id="page_close">
                <a>
                    <img src="../../../images/common/page_close.png" width="20" height="20"
                         style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">订单编号</td>
                    <td class="ui_text_lt">
                        <s:textfield name="orderBill.sn" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">供应商</td>
                    <td class="ui_text_lt">
                        <s:select name="orderBill.supplier.id" list="#suppliers" listKey="id" listValue="name"
                                  class="ui_select01"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">
                        <s:date name="orderBill.vdate" format="yyyy-MM-dd" var="vdate"/>
                        <s:textfield name="orderBill.vdate" value="%{vdate}" class="ui_input_txt02 Wdate" onclick="WdatePicker()"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">订单明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
                        <table class="edit_table" cellspacing="0" cellpadding="0" border="0" style="width: auto">
                            <thead>
                            <tr>
                                <th width="10"></th>
                                <th width="200">货品</th>
                                <th width="120">品牌</th>
                                <th width="80">价格</th>
                                <th width="80">数量</th>
                                <th width="80">金额小计</th>
                                <th width="150">备注</th>
                                <th width="60"></th>
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">
                            <s:if test="orderBill.id == null">
                                <tr>
                                    <td></td>
                                    <td>
                                        <s:textfield disabled="true" readonly="true" cssClass="ui_input_txt04"
                                                     tag="name"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <s:hidden name="orderBill.items[0].product.id" tag="pid"/>
                                    </td>
                                    <td><span tag="brand"></span></td>
                                    <td><s:textfield tag="costPrice" name="orderBill.items[0].costPrice"
                                                     cssClass="ui_input_txt04"/></td>
                                    <td><s:textfield tag="number" name="orderBill.items[0].number"
                                                     cssClass="ui_input_txt04"/></td>
                                    <td><span tag="amount"></span></td>
                                    <td><s:textfield tag="remark" name="orderBill.items[0].remark"
                                                     cssClass="ui_input_txt02"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
                                </tr>
                            </s:if>
                            <s:else>
                                <s:iterator value="orderBill.items">
                                    <tr>
                                        <td></td>
                                        <td>
                                            <s:textfield disabled="true" readonly="true" cssClass="ui_input_txt04"
                                                         tag="name" name="product.name"/>
                                            <img src="/images/common/search.png" class="searchproduct"/>
                                            <s:hidden name="product.id" tag="pid"/>
                                        </td>
                                        <td><span tag="brand"><s:property value="product.brand.name"/></span></td>
                                        <td><s:textfield tag="costPrice" name="costPrice"
                                                         cssClass="ui_input_txt04"/></td>
                                        <td><s:textfield tag="number" name="number"
                                                         cssClass="ui_input_txt04"/></td>
                                        <td><span tag="amount"><s:property value="amount"/></span></td>
                                        <td><s:textfield tag="remark" name="remark"
                                                         cssClass="ui_input_txt02"/></td>
                                        <td>
                                            <a href="javascript:;" class="removeItem">删除明细</a>
                                        </td>
                                    </tr>
                                </s:iterator>
                            </s:else>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<s:submit value="确定保存" class="ui_input_btn01"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</s:form>
</body>
</html>

