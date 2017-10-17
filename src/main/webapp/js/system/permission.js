//加载权限
$(function () {
    $(".btn_reload").click(function () {
        var url = $(this).data("url");

        showDialogMsg("","	亲,重新加载权限可能需要耗费很长的时间,你确定加载吗?",function () {
            $.get(url,function (data) {
                showDialogMsg("",data,function () {
                    window.location.reload();
                },"");
            });
        },true)

    });
});