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
                            <th>成本价</th>
                            <th>售价</th>
                            <th>市场售价</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="itemsTable">
                        <tr id="emptyTr">
                            <td colSpan="8" height="200px">
                                <p class="text-center" id="errorInfo">暂无任何数据</p>
                            </td>
                        </tr>
                        </tbody>
                    </table>


                </div>
            </div>

        <#if marketItems?exists && (marketItems?size > 0)>
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

                $("#sMarket").select2();

                var s_marketId = ${marketId};

                //添加新品
                $("#addBtn").on('click', function () {
                    location.href = "${base}/market/marketItemEdit.do?marketId=" + s_marketId;
                });
            });
        </script>