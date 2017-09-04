<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->

<link href="${res}/assets/plugins/bootstrap-select2/select2.min.css" rel="stylesheet" type="text/css">

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
                            <li class="active"><a href="#">店铺商品列表</a></li>
                        </ol>
                        <h4 class="page-title"><b>店铺商品列表</b></h4>
                    </div>
                </div>
            </div>

            <div class="card-box">
                <div class="row">
                    <div class="col-sm-12">
                        <form class="form-inline" role="form">

                            <button id="addBtn" type="button" class="btn waves-effect waves-light btn-primary "><i
                                    class="fa fa-pencil"></i> 添加店铺商品
                            </button>

                            <div class="form-group">
                                <label>选择店铺：</label>
                                <select id="sMarket" style="width:200px;">
                                    <option data_id="0" selected>选择店铺</option>
                                <#if markets?exists && (markets?size > 0)>
                                    <#list markets as market>
                                        <option data_id="${market.id?c}" <#if marketId == market.id>
                                                selected </#if>>
                                        ${market.name}
                                        </option>
                                    </#list>
                                </#if>
                                </select>
                            </div>

                            <div class="input-group m-l-15">
                                <div class="input-group-btn">
                                    <button id="searchMenu" type="button"
                                            class="btn btn-default waves-effect waves-light dropdown-toggle"
                                            data-toggle="dropdown">按商品模板名称搜索 <span class="caret"></span></button>
                                    <ul class="dropdown-menu" id="searchType">
                                        <li><a href="javascript:void(0)" search_name="商品模板名称" search_type="define_code">按商品模板名称搜索</a>
                                        </li>
                                        <li><a href="javascript:void(0)" search_name="商品模板ID" search_type="name">按商品模板ID搜索</a>
                                        </li>
                                    </ul>
                                </div>
                                <input type="text" id="searchKeyTxt"
                                       class="form-control" placeholder="按商品模板ID搜索">
                                <span class="input-group-btn">
                                    <button id="searchBtn" type="button"
                                            class="btn waves-effect waves-light btn-primary"><i
                                            class="fa fa-search"></i></button>
                                </span>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-12">

                    <table class="table table-striped table-bordered">
                        <thead class="table_head">
                        <tr>
                            <th>ID</th>
                            <th>商品模板ID</th>
                            <th>商品名称</th>
                            <#if marketId == 0>
                                <th>所在店铺ID</th>
                            </#if>
                            <th>成本价(元)</th>
                            <th>一般销售价(元)</th>
                            <th>一般市场售价(元)</th>
                            <th>促销价(元)</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="itemsTable">

                        <#if items?exists && (items?size > 0)>
                            <#list items as item >
                            <tr>
                                <td>${item.id?c}</td>
                                <td>${item.itemTemplateId?c}</td>
                                <td>${item.defineName}</td>
                                <#if marketId == 0>
                                    <td>${item.ownerId?c}</td>
                                </#if>
                                <td>${item.costPrice}</td>
                                <td>${item.sellPrice}</td>
                                <td>${item.marketPrice}</td>
                                <td>${item.discountPrice}</td>
                                <td>
                                    <#if item.status == 0>
                                        <span class="label label-success">未上架</span>
                                    </#if>
                                    <#if item.status == 1>
                                        <span class="label label-success">可售</span>
                                    </#if>
                                    <#if item.status == 2>
                                        <span class="label label-success">失效</span>
                                    </#if>
                                </td>
                                <td>
                                    <button id="editBtn" type="button" data_id="${item.id?c}"
                                            class="btn waves-effect waves-light btn-warning btn-sm">编辑
                                    </button>
                                </td>
                            </tr>
                            </#list>
                        <#else>
                        <tr id="emptyTr">
                            <td colSpan="8" height="200px">
                                <p class="text-center">暂无任何数据</p>
                            </td>
                        </tr>
                        </#if>

                        </tbody>
                    </table>


                </div>
            </div>

        <#if items?exists && (items?size > 0)>
            <div class="row small_page">
                <div class="col-sm-12">
                    <#include "../common/paginate.ftl">
                    <@paginate nowPage=pageIndex itemCount=count action="${base}/market/marketItems.do" />
                </div>
            </div>
        </#if>
            <!-- end container -->
        </div>


        <script type="text/javascript">

            $(document).ready(function () {

                var s_marketId = ${marketId?c};
                var s_searchType = "${searchType}";
                //默认按名称
                var _searchMenu = $("#searchMenu");
                var _searchKeyTxt = $("#searchKeyTxt");

                var _sMarket = $("#sMarket");

                //根据搜索类型，设置选中项
                if (s_searchType == null || s_searchType == "") {
                    s_searchType = "name";
                }

                if (s_searchType == "itemTemplateName") {
                    _searchMenu.html("按商品模板名称搜索<span class=\"caret\"></span>");
                    _searchKeyTxt.attr("placeholder", "按商品模板名称搜索");
                } else {
                    s_searchType = "itemTemplateId";
                    _searchMenu.html("按商品模板ID搜索<span class=\"caret\"></span>");
                    _searchKeyTxt.attr("placeholder", "按商品模板ID搜索");
                }

                //设置搜索框内容
                _searchKeyTxt.val("${searchKey}");

                //搜索选择
                $("#searchType li a").on('click', function () {
                    var sName = $(this).attr("search_name");
                    var txt = "按" + sName + "搜索";
                    s_searchType = $(this).attr("search_type")
                    _searchMenu.html(txt + "<span class=\"caret\"></span>");
                    _searchMenu.attr("search_name", sName)
                    _searchKeyTxt.attr("placeholder", txt);
                });

                _sMarket.select2();
                _sMarket.change(function () {
                    var s_searchKey = _searchKeyTxt.val();
                    var select_obj = _sMarket.find("option:selected");
                    s_marketId = select_obj.attr("data_id");

                    location.href = "${base}/market/marketItems.do?marketId=" + s_marketId+"&searchType="+s_searchType+"&searchKey="+s_searchKey;
                });

                //添加新品
                $("#addBtn").on('click', function () {

                    var select_obj = _sMarket.find("option:selected");
                    s_marketId = select_obj.attr("data_id");

                    location.href = "${base}/market/marketItemAdd.do?marketId="+s_marketId;
                });

                <#if items?exists && (items?size > 0) >
                    //单项编辑
                    $("#itemsTable").find('button[id=editBtn]').each(function () {
                        $(this).on('click', function () {
                            location.href = "${base}/market/marketItemEdit.do?marketId=" + s_marketId + "&id=" + $(this).attr("data_id");
                        });
                    });
                </#if>
            });
        </script>