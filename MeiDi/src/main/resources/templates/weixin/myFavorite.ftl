<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="keywords" content="北"/>
    <meta name="description" content=""/>

    <title>我的收藏</title>
    <meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<#include "header.ftl"/>
</head>
<body style="background:#ffffff">
<div class="wrapper pr pb50">
    <div class="header pwhite bg  bor_b ">
        <a class="fs14 pa city iconfont icon-back base" href="${PATH}/my"></a>

        <p class="fs18">全部收藏</p>
    </div>
    <div class="pl20 pr20 pt50">

        <div class="itemlistbox">
        </div>
        <div class="tac">
            <a href="javascript:getFavoriteList(0);" class="getmore">点击加载更多...</a>
        </div>
    </div>

</div>

</body>
<script type="text/javascript" src="${PATH}/js/weixin/common-ajax.js"></script>
<script src="${PATH}/js/weixin/myFavorite.js"></script>
</html>