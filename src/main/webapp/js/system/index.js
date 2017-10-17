//加载当前日期
function loadDate() {
    var time = new Date();
    var myYear = time.getFullYear();
    var myMonth = time.getMonth() + 1;
    var myDay = time.getDate();
    if (myMonth < 10) {
        myMonth = "0" + myMonth;
    }
    document.getElementById("day_day").innerHTML = myYear + "." + myMonth + "." + myDay;
}

/**
 * 隐藏或者显示侧边栏
 *
 **/
function switchSysBar(flag) {
    var side = $('#side');
    var left_menu_cnt = $('#left_menu_cnt');
    if (flag == true) {	// flag==true
        left_menu_cnt.show(500, 'linear');
        side.css({width: '280px'});
        $('#top_nav').css({width: '77%', left: '304px'});
        $('#main').css({left: '280px'});
    } else {
        if (left_menu_cnt.is(":visible")) {
            left_menu_cnt.hide(10, 'linear');
            side.css({width: '60px'});
            $('#top_nav').css({width: '100%', left: '60px', 'padding-left': '28px'});
            $('#main').css({left: '60px'});
            $("#show_hide_btn").find('img').attr('src', 'images/common/nav_show.png');
        } else {
            left_menu_cnt.show(500, 'linear');
            side.css({width: '280px'});
            $('#top_nav').css({width: '77%', left: '304px', 'padding-left': '0px'});
            $('#main').css({left: '280px'});
            $("#show_hide_btn").find('img').attr('src', 'images/common/nav_hide.png');
        }
    }
}

$(function () {
    loadDate();

    // 显示隐藏侧边栏
    $("#show_hide_btn").click(function () {
        switchSysBar();
    });
});

//控制iframe的src属性
$(function () {
    $("#dleft_tab1 a").click(function () {
        var url = $(this).data("url");
        $("#rightMain").prop("src", url);
    });
});

$(function () {
    loadMenu("business");

    $("#TabPage2 li").click(function () {
        //清空操作
        $("#TabPage2 li").removeClass("selected");
        $.each($("#TabPage2 li"), function (index, item) {
            $(item).find("img").prop("src", "/images/common/" + (index + 1) + ".jpg");
        });
        //给当前点击的最左侧到导航栏添加样式以及更换图片
        $(this).addClass("selected");
        $(this).find("img").prop("src", "/images/common/" + ($(this).index() + 1) + "_hover.jpg");

        $("#nav_module").find("img").prop("src", "/images/common/module_" + ($(this).index() + 1) + ".png")

        loadMenu($(this).data("rootmenu"));

    });
});

//
var setting = {
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: function (event, treeId, treeNode) {
            if (treeNode.action) {
                $("#rightMain").prop("src", "/" + treeNode.action + ".action");
                $("#here_area").html("当前位置："+treeNode.getParentNode().name+">"+treeNode.name);
            }
        }
    },
    async: {
        enable:true,
        url:"/systemMenu_queryMenusByParentSn.action",
        autoParam:["sn=qo.parentSn"]
    }
};


var business = [
    {id: 1, pId: 0, name: "业务模块",isParent:true,sn:"business"}
];

var systemManage = [
    {id: 2, pId: 0, name: "系统模块",isParent:true,sn:"system"}
];

var charts = [
    {id: 3, pId: 0, name: "报表模块",isParent:true,sn:"chart"}
];

var zNodes = {
    business: business,
    systemManage: systemManage,
    charts: charts
};

function loadMenu(menuName) {
    $.fn.zTree.init($("#dleft_tab1"), setting, zNodes[menuName]);
}


