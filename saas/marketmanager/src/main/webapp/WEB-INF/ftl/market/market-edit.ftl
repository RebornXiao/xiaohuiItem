<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->

<link href="${res}/assets/plugins/bootstrap-select2/select2.min.css" rel="stylesheet" type="text/css">

<script src="${res}/assets/plugins/address/area.js"></script>
<script src="${res}/assets/plugins/address/location.js"></script>

<script src="${res}/assets/plugins/bootstrap-select2/select2.min.js"></script>
<script src="${res}/assets/plugins/bootstrap-select2/zh-CN.js"></script>

<div class="content-page">
    <!-- Start content -->
    <div class="content">
        <div class="container">

            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">店铺管理</a></li>
                            <li class="active"><#if market?exists>编辑<#else>添加</#if>店铺</li>
                        </ol>
                        <h4 class="page-title"><b><#if market?exists>编辑<#else>添加</#if>店铺</b></h4>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-12">
                    <div class="row m-t-30">
                        <div class="col-md-8">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label class="col-md-4 control-label">店铺名称：</label>
                                    <div class="col-md-8">
                                        <input type="text" id="mName" class="form-control" <#if market?exists>
                                               value="${market.name}" </#if>>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="example-email">地区：</label>
                                    <div class="col-md-8">
                                        <select id="loc_province" style="width:120px;"></select>
                                        <select id="loc_city" style="width:120px; margin-left: 10px"></select>
                                        <select id="loc_town" style="width:120px;margin-left: 10px"></select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-4 control-label">街道牌号：</label>
                                    <div class="col-md-8">
                                        <input type="text" id="mStreetNumber" class="form-control" <#if market?exists>
                                               value="${market.streetNumber}" </#if>>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-4 control-label">具体地址：</label>
                                    <div class="col-md-8">
                                        <input type="text" id="mAddress" class="form-control" <#if market?exists>
                                               value="${market.address}" </#if>>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-4 control-label">经纬度：</label>
                                    <div class="col-md-4">
                                        <input type="text" id="mLocation" class="form-control" <#if market?exists>
                                               value="${market.location}" </#if>>
                                    </div>
                                    <div class="col-md-4">
                                        <button id="baiduBtn" type="button"
                                                class="btn waves-effect waves-light btn-default">设置经纬度</button>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-4 control-label">配送方式：</label>
                                    <div class="col-md-8">
                                        <select class="form-control" id="mDeliveryMode" style="width:150px">
                                            <option data_id="1">仅自提</option>
                                            <option data_id="2">仅送货</option>
                                            <option data_id="3">可自提也可送货</option>
                                            <option data_id="4">AI(智能)</option>
                                            <option data_id="5">可自提也可AI配送</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-4 control-label">覆盖的配送距离：</label>
                                    <div class="col-md-8">
                                        <input type="text" id="mDistance" class="form-control" <#if market?exists>
                                               value="${market.coveringDistance}" </#if>>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-4 control-label">配送费：</label>
                                    <div class="col-md-8">
                                        <input type="text" id="mDeliveryCost" class="form-control" <#if market?exists>
                                               value="${market.deliveryCost}" </#if>>
                                    </div>
                                </div>

                                <div class="form-group m-t-20">
                                    <div class="col-md-4">
                                    </div>
                                    <button id="saveBtn" type="button"
                                            class="btn waves-effect waves-light btn-primary col-md-2">确定
                                    </button>
                                    <button id="backBtn" type="button"
                                            class="btn waves-effect waves-light btn-default col-md-2">返回
                                    </button>
                                </div>

                                <br><br><br><br><br>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- end container -->
        </div>

        <script type="text/javascript">
            $(document).ready(function () {

                showLocation();

                $("#backBtn").on('click', function () {
                    history.back(-1);
                });

                //添加商品
                $("#saveBtn").on('click', function () {

                    //检测所有项

                <#--$.post("${base}/item/itemUnitEditSave.do?id="+itemUnitId+"&title="+title+"&status="+status, function(data) {-->
                <#--//重新刷新-->
                <#--if(data.code == "0") {-->
                <#--swal("提示", "操作成功", "success");-->
                <#--location.reload();-->
                <#--} else {-->
                <#--swal(data.msg);-->
                <#--}-->
                <#--}, "json");-->
                });
            });
        </script>