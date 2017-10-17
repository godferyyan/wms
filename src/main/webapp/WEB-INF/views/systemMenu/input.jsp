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
    <%--<script type="text/javascript" src="/js/plugins/jquery.validate.js"></script>--%>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.source.js?skin=opera"></script>
    <script type="text/javascript" src="js/commonAll.js"></script>
    <%--<script type="text/javascript" src="/js/system/systemMenu.js"></script>--%>
    <%@include file="/WEB-INF/views/common/common_msg.jsp" %>
</head>
<body>
<s:form name="editForm" action="systemMenu_saveOrUpdate" namespace="/" id="editForm">
    <s:hidden name="systemMenu.id"/>
    <s:hidden name="qo.parentId"/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">系统菜单编辑</span>
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
                    <td class="ui_text_rt" width="140">上级菜单名称</td>
                    <td class="ui_text_lt">
                        <s:textfield name="parentName" class="ui_input_txt02" style="background-color:pink" readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">菜单编码</td>
                    <td class="ui_text_lt">
                        <s:textfield name="systemMenu.sn" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">菜单名称</td>
                    <td class="ui_text_lt">
                        <s:textfield name="systemMenu.name" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">URL</td>
                    <td class="ui_text_lt">
                        <s:textfield name="systemMenu.url" class="ui_input_txt02"/>
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

