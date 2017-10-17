//禁用掉在提交数组参数时为参数名加上[]的功能
jQuery.ajaxSettings.traditional = true;
$(function () {

    //页面跳转操作
    $(".btn_page").click(function () {
        var pageInfo = $(this).data("page") || $("input[name='qo.currentPage']").val();
        $("input[name='qo.currentPage']").val(pageInfo);
        $("#searchForm").submit();
    });

    //改变页面大小操作
    $(":input[name='qo.pageSize']").change(function () {
        $("input[name='qo.currentPage']").val(1);
        $("#searchForm").submit();
    });

    //新增操作
    $(".btn_input").click(function () {
        var url = $(this).data("url"); //为对应的按钮添加一个要跳转的url的自定义属性,这里取出,注意取出属性必须加"",从而达到通用性
        //console.log(url);
        window.location.href = url;
    });

    /*//为批量删除按钮绑定事件
    $(".btn_batchDelete").click(function () {
        {
            //获取要删除的员工id
            //var ids = $(".acb:checked").data("id"); //只能获取到其中1个数据
            var ids = $.map($(".acb:checked"), function (item, index) { //map()中第一个参数为要参与转换的数组,第二个参数是要转换成另外一个单独的数组中的数据,用一个函数来进行计算,函数中的第一个参数为当前迭代的数组中的每一个元素对象(DOM对象),第二个是当前对象在原数组中的索引,最终map的返回值就是一个由第二个参数组成的新的数组
                return $(item).data("oid");
            });
            //console.log(ids);
            //发送Ajax请求更新数据
            $.get("/employee_batchDelete.action", {"ids": ids}, function () {
                //执行更新前,先清一下所有选择框的checked属性,防止默认勾选问题
                $(".acb").prop("checked", "");
                //进行页面刷新
                window.location.reload();
            });
        }
    });*/

    //优化批量删除操作
    $(".btn_batchDelete").click(function () {
        //获取到所有要进行批量删除的员工的id
        var ids = $.map($(".acb:checked"),function (item,index) {
            return $(item).data("oid");
        });

        //获取到批量删除要发送的请求的url
        var url = $(this).data("url");

        //判断是否有选中员工,如果没有,就给出提示
        if(ids.length == 0){
            showDialogMsg("","请选择要删除的员工","","");
            return;
        }
        showDialogMsg("","确定要批量删除么?",function(){
            $.get(url,{"ids":ids},function (data) {
                showDialogMsg("",data,function () {
                    $(".acb").prop("checked","");
                    window.location.reload();
                },"");
            });
        },true);
    });

    //添加删除的异步请求操作
    $(".btn_delete").click(function () {
        //获取到当前删除按钮的url
        var url =  $(this).data("url");
        
        showDialogMsg("","确认要删除么?",function () {
            $.get(url,function (data) {
                showDialogMsg("",data,function () {
                    window.location.reload();
                })
            });

        },true);
        
    });

    //全选/全不选操作
    $("#all").click(function () {
        $(".acb").prop("checked", $(this).prop("checked"));//原生属性用prop设置
    });

});

function showDialogMsg(title,content,ok,cancel) {
    $.dialog({
        title: title || "温馨提示",
        content: content || "",
        ok: ok || true,
        cancel: cancel || false,
        icon: "face-sad"
    });
}