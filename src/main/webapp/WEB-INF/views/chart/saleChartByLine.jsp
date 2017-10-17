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
                    trigger: 'axis'
                },
                toolbox: {
                    show : true,
                    feature : {
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        data : <s:property value="#x" escapeHtml="false"/>
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'销售总额',
                        type:'bar',
                        data:<s:property value="#y" escapeHtml="false"/>
                        /*markPoint : {
                            data : [
                                {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
                                {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name : '平均值'}
                            ]
                        }*/
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

