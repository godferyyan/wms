jQuery.ajaxSettings.traditional = true;
// extend the 'equals' rule
$.extend($.fn.validatebox.defaults.rules, {
    equals: {
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: 'Field do not match.'
    }
});

$(function() {
	$("#myDatagrid").datagrid({
		url : 'employee.action',
		width : 300,
		height : 200,
		fit : true,
		pagination : true,
		rownumbers : true,
		checkOnSelect : true,
		scrollbarSize : 0,// 最右侧滚动条,默认为18,即使数据很少没有滚动条,也会多出那一小部分,如果不想要最右边那一小部分,设置为0即可
		fitColumns : true, // 这里设置了自适应后,具体列中的width属性设置的值就不再是真实长度而是一个宽度占比了
		striped : true,
		toolbar : "#toolbar",
		columns : [ [ {
			field : 'id',
			checkbox : 'true'
		}, {
			title : '用户名',
			field : 'name',
			align : 'center',
			sortable : 'true',
			resizable : 'true',
			width : 100
		},{
            title : 'EMAIL',
            field : 'email',
            align : 'center',
            sortable : 'true',
            resizable : 'true',
            width : 100,
        }, {
			title : '年龄',
			field : 'age',
			align : 'center',
			sortable : 'true',
			resizable : 'true',
			width : 100,
		}, {
			title : '所属部门',
			field : 'dept',
			align : 'center',
			sortable : 'true',
			resizable : 'true',
			formatter : function(value, rowData, rowIndex) {
				return value ? value.name : '';
			},
			width : 100
		} ] ]
	});

	$("#emp_mydialog").dialog({
		width : 500,
		height : 600,
		buttons : "#myBtn",
		closed : true,
        resizable:true
	});

});

// 新增操作
function add() {

	// 先清空上一次填写的数据
	$("#emp_form").form("clear");

	// 设置弹出框标题
	$("#emp_mydialog").dialog("setTitle", "员工新增");

	// 打开弹出框
	$("#emp_mydialog").dialog("open");
}

// 编辑操作
function edit() {
	// 先判断是否有选中记录或者选中了多条记录
	// 使用getSelections取得的每一条记录,不仅仅含有设置的列的信息,而是会包含所有当初加载datagrid数据的时候json对象中包含的属性值
	var records = $("#myDatagrid").datagrid("getSelections");
	if (records.length != 1) {
		$.messager.alert("温馨提示", "请选中一行记录", "warning"); // 给出警告信息并结束当前方法
		return;
	}
	// console.log(records[0]);

	// 先清空上一次填写的数据
	$("#emp_form").form("clear");

	// 设置表单数据进行数据回显
	/*
	 * $('#emp_form').form('load',{ name:records[0].name, age:records[0].age,
	 * inputTime:records[0].inputTime, 'dept.id':records[0].dept.id });
	 */
	// 但是上述的要设置回显的数据直接给一个employee的json对象就可以,但是要给employee这个json对象设置一个与表单中name='dept.id'同名的属性并赋其属性值
	var employee = records[0];

	employee['employee.dept.id'] = employee.dept.id;
	employee['employee.name'] = employee.name;
	employee['employee.age'] = employee.age;
	employee['employee.email'] = employee.email;
	employee['employee.id'] = employee.id;
	//利用同名匹配原则设置employee的checked属性为true貌似不行,所以这里通过判断直接设置值
    if (employee.admin) {
        $("#emp_form :checkbox").prop("checked",true);
    }

    //消灭密码框与其密码验证框
    $("#emp_form :password").closest("tr").remove();


	//将employee中所有的角色的id取出来存放到 属性名为 employee.roles.id中
	var roleIds =  $.map(employee.roles,function (item,index) {
        return item.id;
    });
	employee['employee.roles.id'] = roleIds;

    console.log(employee);

	// 使用该种方法设置的数据利用的规则为同名匹配,也就是将与表单中控件name同名的json对象的属性值赋给控件的value,如果是设置的下拉框,匹配的就是option中的value(也就是dept的id),显示其文本内容
	$("#emp_form").form("load", employee);

	// 设置弹出框标题
	$("#emp_mydialog").dialog("setTitle", "员工编辑");

	// 打开弹出框
	$("#emp_mydialog").dialog("open");
}

// 保存操作
function save() {
	/*
	 * console.log(url); console.log($("[name=id]").val());
	 * console.log($("[name=id]"));
	 */

	// 使用easyui的表单组件达成异步提交数据的要求
	$('#emp_form').form('submit', {
		url : "employee_saveOrUpdate.action",
		/*onSubmit : function() { // 提交之前执行的方法
            if (!$("#emp_form").form("validate")) { //如果写了onSumbmit属性,这里就必须手动调用form的validate方法来进行数据的教研,如果不写onSubmit属性,就会自动进行提交前的数据校验 结合validate组件使用,如果不符合格式,返回false就不会提交,如果不使用这个方法,则即使验证了也无效,也能保存
                return false;
            }
		},*/
		success : function(data) {
			var data = $.parseJSON(data); // 该方式返回的数据为字符串,需要转换为JSON格式
			// 保存成功
			if (data.success) {
				// 弹出提示
				$.messager.alert("温馨提示", data.msg, "info", function() {
					// 在窗口关闭的时候触发该回调函数
					// 关闭弹出框
					$("#emp_mydialog").dialog("close");
					// 重新加载数据表格,load会回到第一页(默认会携带rows(相当于pageSize),page(相当于currentPage),这两个参数发送回去)
					$("#myDatagrid").datagrid("load");
				})
			} else {
				// 弹出提示,保存失败应该回到弹出框,不用关闭弹出框
				$.messager.alert("温馨提示", data.msg, "info");
			}
		}
	});
}

// 弹出框关闭
function cancel() {
	$("#emp_mydialog").dialog("close");
}

// 移除操作
function remove() {
	// 也是先判断是否有选中
	var records = $("#myDatagrid").datagrid("getSelections");
	if (records.length < 1) {
		$.messager.alert("温馨提示", "请选中一行或多行记录", "warning"); // 给出警告信息并结束当前方法
		return;
	}

	// 如果选中了,则给出提示确认是否要删除,如果确认删除,再执行删除相关的操作
	$.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
		// console.log(r); // r 为true或者false, 如果点击确认,则返回ture,点击取消则返回false
		if (r) {

			var id;
			var ids;
			// 判断是选中单条还是多条
			if (records.length == 1) {
				// 获取到选中记录的id
				id = records[0].id;
				// 发送异步请求删除对应记录(单条)
				$.get("employee_delete.action", {
					"employee.id" : id
				}, function(data) {
					// $.get()方式发送请求,若返回的是json格式,底层会帮我们转为json对象,不需要我们再去进行转换
                    console.log(data);
					console.log(data["success"]);
					console.log(data["msg"]);
					console.log(data.success);
					console.log(data.msg);
                    if (data.success) {
						$.messager.alert("温馨提示", data.msg, "info", function() {
							// 删除成功点击确定后刷新页面但是要留在当前页面
                            console.log(data.success);
                            console.log(data.msg);
							$("#myDatagrid").datagrid("reload");
						});
					} else {
					        console.log("------------------");
						$.messager.alert("温馨提示", data.msg, "info");
					}
				})
			} else {
				// 获取所有选中的ids
				ids = $.map(records, function(item, index) {
					return item.id;
				});
				// 发送异步请求删除对应记录(多条)
				$.get("employee_batchDelete.action", {
					"ids" : ids
				}, function(data) {
					// $.get()方式发送请求,若返回的是json格式,底层会帮我们转为json对象,不需要我们再去进行转换
					if (data.success) {
						$.messager.alert("温馨提示", data.msg, "info", function() {
							// 删除成功点击确定后刷新页面但是要留在当前页面
							$("#myDatagrid").datagrid("reload");
						});
					} else {
						$.messager.alert("温馨提示", data.msg, "info");
					}
				})
			}

		}
	});
}

//高级查询
function searchKeyword(){
	//重新加载页面,并自动携带分页(page,rows)参数到后台
	$("#myDatagrid").datagrid("load",{
		"qo.keyword":$("[name='qo.keyword']").val(), //将高级查询的值传递到后台
		"qo.deptId":$("[name='qo.deptId']").val() //将高级查询的值传递到后台
	})
}


/*//角色移动
$(function () {
    $("#select").click(function(){
        $(".roleAll option:selected").appendTo($(".roleSelected"));
    });
    $("#selectAll").click(function(){
        $(".roleAll option").appendTo($(".roleSelected"));
    });
    $("#deselect").click(function(){
        $(".roleSelected option:selected").appendTo($(".roleAll"));
    });
    $("#deselectAll").click(function(){
        $(".roleSelected option").appendTo($(".roleAll"));
    });
});

//角色列表的表单角色项去重
$(function(){
    var selectedIds =  $.map($(".roleSelected option"),function(item,index){
        return $(item).val();
    });
    $.each($(".roleAll option"),function(index,item){
        if($.inArray($(item).val(),selectedIds) > -1){
            $(item).remove();
        }
    })
});*/


