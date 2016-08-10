<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="keywords" content="北"/>
    <meta name="description" content=""/>

    <title>我的订单</title>
    <meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<#include "header.ftl"/>
</head>
<body style="background:#ffffff">
<div class="wrapper pr pb50 tal">
    <div class="header pwhite bg  bor_b ">
        <a class="fs14 pa iconfont icon-back base" href="${PATH}/my"></a>

        <p class="fs18">我的积分</p>
    </div>
    <div class="myjf p20">
        <div class="fs20">
            <p class="pd5 line30 mt10">该积分，等同您在聚会美预约项目并消费的实际金额，可用于享受多种积分福利。详情请移步“积分商城”</p>
        </div>
        <div class="fs28 mt10">
            <p>总积分：<span class="pf40000">#{user.integral}分</span></p>

            <div class="jfbox fs24">
                <div class="jdtit">
                    <div>日期</div>
                    <div>事件</div>
                    <div>积分增减</div>
                </div>
                <div class="tableList">

                </div>
                <div class="tac">
                    <a href="javascript:getList(0);" class="getmore">点击加载更多...</a>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
<script src="${PATH}/js/weixin/myIntegral.js"></script>
</html>