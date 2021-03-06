<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>后台管理系统</title>
<#include "common.ftl"/>

    <!-- 配置文件 -->
    <script type="text/javascript" src="${PATH}/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="${PATH}/ueditor/ueditor.all.min.js"></script>

    <script>
        window.UEDITOR_HOME_URL = BASE_JS_URL + "/ueditor/";
    </script>

</head>
<body>

<div id="wrapper">
<#include "header.ftl"/>
    <div id="page-wrapper" class="clearfix">
        <div class="col-lg-10">
            <div class="row">
                <div class="col-lg-12">
                    <h4 class="page-header">购买须知 & 默认支付说明</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="col-lg-12">
            <#--<form class="createVenueForm" role="form" method="post" id="addCommodity" name="addCommodity">-->

                <div class="form-group">
                    <input type="hidden" name="description" class="description">
                    <input type="hidden" name="payment_note" class="payment_note">

                    <div><span class="star mr6"></span>类型:
                        <select name="flag" class="flag">
                            <option value="1" <#if flag == 1>selected</#if>>拼团</option>
                            <option value="2" <#if flag == 2>selected</#if>>福袋</option>
                            <option value="3" <#if flag == 3>selected</#if>>特惠</option>
                            <option value="4" <#if flag == 4>selected</#if>>咨询</option>
                            <option value="5" <#if flag == 5>selected</#if>>打卡</option>
                        </select>
                    </div>
                    <br/>

                    <h5>购买须知</h5>
                    <!-- 加载编辑器的容器 -->
                    <script id="container" name="content" type="text/plain"></script>

                    <!-- 实例化编辑器 -->
                    <script type="text/javascript">
                        var ue = UE.getEditor('container');
                        <#if buyNotice?exists && buyNotice.description?exists>
                        ue.ready(function () {
                            ue.setContent('${buyNotice.description}');
                        });
                        </#if>
                    </script>
                    <br/>

                    <h5>默认支付说明</h5>
                    <!-- 加载编辑器的容器 -->
                    <script id="payment_container" name="content" type="text/plain"></script>

                    <!-- 实例化编辑器 -->
                    <script type="text/javascript">
                        var ue_payment = UE.getEditor('payment_container');
                        <#if buyNotice?exists && buyNotice.paymentNote?exists>
                        ue_payment.ready(function () {
                            ue_payment.setContent('${buyNotice.paymentNote}');
                        });
                        </#if>
                    </script>
                </div>

                <div class="text-center col-lg-10 mt20">
                    <button type="button" class="btn btn-primary btn-lg" id="fusave" onclick="submit();">
                        保存
                    </button>
                </div>
            <#--</form>-->
            </div>
        </div>
    </div>
    <!-- /#page-wrapper -->

</div>

</body>

</html>
<script type="text/javascript" src="${PATH}/js/backend/buyNotice.js"></script>