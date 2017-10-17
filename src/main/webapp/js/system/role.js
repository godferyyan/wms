$(function () {
    $("#select").click(function () {
        $(".permissionAll option:checked").appendTo(".premissionSelected");
    });
    $("#deselect").click(function () {
        $(".premissionSelected option:checked").appendTo(".permissionAll");
    });
    $("#selectAll").click(function () {
        $(".permissionAll option").appendTo(".premissionSelected");
    });
    $("#deselectAll").click(function () {
        $(".premissionSelected option").appendTo(".permissionAll");
    });

    $("#mselect").click(function () {
        $(".menuAll option:checked").appendTo(".menuSelected");
    });
    $("#mdeselect").click(function () {
        $(".menuSelected option:checked").appendTo(".menuAll");
    });
    $("#mselectAll").click(function () {
        $(".menuAll option").appendTo(".menuSelected");
    });
    $("#mdeselectAll").click(function () {
        $(".menuSelected option").appendTo(".menuAll");
    });

    //解决提交表单选择丢失
    $("#editForm").submit(function () {
        $(".premissionSelected option").prop("selected", true);
        $(".menuSelected option").prop("selected", true);
    });

});


$(function () {
    //消除权限的左右选择框重复

    var ids = $.map($(".premissionSelected option"), function (item, index) {
        return $(item).prop("value");
    });

    $.each($(".permissionAll option"), function (index, item) {

        var id = $(item).prop("value");

        if ($.inArray(id, ids) > -1) {
            $(item).remove();
        }

    });

});

$(function () {
    //消除系统菜单的左右选择框重复

    var ids = $.map($(".menuSelected option"), function (item, index) {
        return $(item).prop("value");
    });

    $.each($(".menuAll option"), function (index, item) {

        var id = $(item).prop("value");

        if ($.inArray(id, ids) > -1) {
            $(item).remove();
        }

    });

});