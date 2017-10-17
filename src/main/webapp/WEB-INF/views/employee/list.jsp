<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/js/plugins/jquery-easyui/themes/metro/easyui.css">
    <link rel="stylesheet" type="text/css" href="/js/plugins/jquery-easyui/themes/icon.css">
    <script type="text/javascript" src="/js/plugins/jquery-easyui/jquery.min.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/js/system/employee.js"></script>
</head>
<body>
<table id="myDatagrid"></table>
<div id="toolbar">
    <a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add()">新增</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit()">编辑</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="remove()">移除</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a>
    <input type="text" name="keyword">
    <a class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchKeyword()">查询</a><!-- 注意search()不能作为方法名起名,无效 -->
</div>

<div id="myBtn">
    <a class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="save()">保存</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cancel()">取消</a>
</div>

<div id="emp_mydialog">
    <form id="emp_form" action="employee_save.json" method="post">
        <input type="hidden" name="id"/>
        <table align="center">
            <tr>
                <td>姓　　名 :</td>
                <td>
                    <input type="text" name="name" />
                </td>
            </tr>
            <tr>
                <td>年　　龄 :</td>
                <td>
                    <input type="text" class="easyui-numberbox" name="age" data-options="min:0,max:120">
                </td>
            </tr>
            <br />
            <tr>
                <td>入职时间 :</td>
                <td>
                    <input type="text" class="easyui-datebox" name="inputTime">
                </td>
            </tr>
            <br />
            <tr>
                <td>部　　门 :</td>
                <td>
                    <input name="dept.id" class="easyui-combobox" data-options="url:'dept.json',valueField:'id',textField:'name',panelHeight:'auto'">
                </td>
            </tr>
            <br/>
        </table>
    </form>
</div>
</body>
</html>

