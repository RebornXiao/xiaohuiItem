<link href="${res}/assets/plugins/bootstrap-select2/select2.min.css" rel="stylesheet" type="text/css">

<script src="${res}/assets/plugins/bootstrap-select2/select2.min.js"></script>
<script src="${res}/assets/plugins/bootstrap-select2/zh-CN.js"></script>

<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->
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
                            <li><a href="#">店铺商品管理</a></li>
                            <li class="active"><#if marketItem?exists>编辑<#else>添加</#if>店铺商品</li>
                        </ol>
                        <h4 class="page-title"><b><#if marketItem?exists>编辑<#else>添加</#if>店铺商品</b></h4>
                    </div>
                </div>
            </div>

            <div class="row m-t-30">
                <div class="col-md-8">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-md-4 control-label">选择店铺：</label>
                            <div class="col-md-8">
                                <select id="sMarket" style="width:200px;">
                                <#if markets?exists && (markets?size > 0)>
                                    <#list markets as market>
                                        <option data_id="${market.id?c}" <#if marketId == market.id>
                                                selected </#if>>
                                        ${market.name}
                                        </option>
                                    </#list>
                                <#else>
                                    <option data_id="0" selected>选择店铺</option>
                                </#if>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">选择商品模板：</label>
                            <div class="col-md-8">
                                <select id="sItemTypes" style="width:200px;">
                                <#if itemTypes?exists && (itemTypes?size > 0)>
                                    <option data_id="0">选择分类</option>
                                    <#list itemTypes as iType>
                                        <option data_id="${iType.id?c}" data_v="${iType.title}">
                                            <#if iType.parentId == 0>
                                            ${iType.title}
                                            <#else>
                                                &nbsp;&nbsp;&nbsp;&nbsp;${iType.title}
                                            </#if>
                                        </option>
                                    </#list>
                                <#else>
                                    <option data_id="0">选择分类</option>
                                </#if>
                                </select>
                                <select id="sItem" style="width:200px;"></select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">成本价(分)：</label>
                            <div class="col-md-8">
                                <input id="itemUnitTitle" type="number" class="form-control" <#if marketItem?exists>
                                       value="${marketItem.title}" </#if>>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">销售价(分)：</label>
                            <div class="col-md-8">
                                <input id="itemUnitTitle" type="number" class="form-control" <#if marketItem?exists>
                                       value="${marketItem.title}" </#if>>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">市场售价(分)：</label>
                            <div class="col-md-8">
                                <input id="itemUnitTitle" type="number" class="form-control" <#if marketItem?exists>
                                       value="${marketItem.title}" </#if>>
                            </div>
                        </div>

                        <br><br>

                        <div class="form-group">
                            <div class="col-md-4"></div>
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

            <!-- end container -->
        </div>


        <script type="text/javascript">

            $(document).ready(function () {

                $("#sMarket").select2();

                //商品内容
                var items = {};//以分类ID储存

                var _sItemTypes = $("#sItemTypes");
                var _sItem = $("#sItem");

                _sItemTypes.select2();
                _sItemTypes.change(function () {
                    //获取商品
                    var select_obj = _sItemTypes.find("option:selected");
                    var data_id = select_obj.attr("data_id");
                    _sItem.empty();
                    _sItem.append("<option data_id=\"0\" >选择商品</option>");

                    //如果有商品，则不理会，否则，弹出选择
                    if (items[data_id] != null) {
                        //添加
                        $.each(items[data_id], function (k, v) {
                            var option = '<option data_id="' + k + '" data_v="' + v + '">' + v + '</option>';
                            _sItem.append(option);
                        });
                    } else {
                        //从网络获取
                        $.get("${base}/item/idNameItems.do?itemTypeId=" + data_id, function (json) {
                            if (json.code != 0) {
                                swal(json.msg);
                            } else {
                                items[data_id] = json.response.datas;
                                $.each(items[data_id], function (k, v) {
                                    var option = '<option data_id="' + k + '" data_v="' + v + '">' + v + '</option>';
                                    _sItem.append(option);
                                });
                            }
                        }, "json");
                    }
                });

                _sItem.append("<option data_id=\"0\" >选择商品</option>");
                _sItem.select2();

                $("#backBtn").on('click', function () {
                    history.back(-1);
                });

                //保存商品单位
                $("#saveBtn").on('click', function () {

                <#--$.post("${base}/item/itemUnitEditSave.do?id=" + itemUnitId + "&title=" + title + "&status=" + status, function (data) {-->

                <#--}, "json");-->
                });
            });
        </script>