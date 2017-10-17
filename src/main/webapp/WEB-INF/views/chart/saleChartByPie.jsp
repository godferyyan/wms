<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/echart/echarts-all.js"></script>
    <script type="text/javascript">
        $(function () {

            var myChart = echarts.init(document.getElementById('main'));

            var option = {
                title : {
                    text: '销售报表',
                    subtext: '按<s:property value="#subtext"/>分组',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                toolbox: {
                    show : true,
                    feature : {
                        dataView : {show: true, readOnly: false},
                        magicType : {
                            show: true,
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'center',
                                    max: <s:property value="#max"/>
                                }
                            }
                        },
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                series : [
                    {
                        name:'<s:property value="#subtext"/>',
                        type:'pie',
                        radius : ['40%', '60%'],
                        itemStyle : {
                            normal : {
                                label : {
                                    show : true
                                },
                                labelLine : {
                                    show : true
                                }
                            },
                            emphasis : {
                                label : {
                                    show : true,
                                    position : 'center',
                                    textStyle : {
                                        fontSize : '20',
                                        fontWeight : 'bold'
                                    }
                                }
                            }
                        },
                        data:<s:property value="#datas" escapeHtml="false"/>
                    }
                ]
            };


            // 为echarts对象加载数据
            myChart.setOption(option);

        });
    </script>
    <title>PSS-销售报表线形图</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
    <div id="main" style="height:400px;width: 600px"></div>
</body>
</html>

