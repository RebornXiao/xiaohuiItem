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
                            <li class="active"><a href="#">店铺任务列表</a></li>
                        </ol>
                        <h4 class="page-title"><b>店铺任务列表</b></h4>
                    </div>
                </div>
            </div>

            <div class="card-box">
                <div class="row">
                    <div class="col-sm-12">
                        <form class="form-inline" role="form">

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
                            <th>任务ID</th>
                            <th>弹夹编码</th>
                            <th>商品模版ID</th>
                            <th>商品名称</th>
                            <th>商品数量</th>
                            <th>商品条码</th>
                            <th>商品单位</th>
                            <th>任务状态</th>
                            <th>期望执行的日期</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="clipListTable">


                        <#if tasks?exists && (tasks?size > 0)>
                            <#list tasks as task>
                            <tr>
                                <td>${task.taskId?c}</td>
                                <td>${task.locationCode}</td>
                                <td>${task.itemTemplateId}</td>
                                <td>${task.itemName}</td>
                                <td>${task.itemQuantity}</td>
                                <td>${task.barcode}</td>
                                <td>${task.unitName}</td>
                                <td>${task.status}</td>
                                <td>${task.hopeExecutorDate}</td>
                                <td>
                                    <button id="editBtn" type="button"
                                            class="btn waves-effect waves-light btn-warning btn-sm"
                                            data_id="${task.taskId?c}">编辑
                                    </button>
                                </td>
                            </tr>

                            </#list>
                        <#else>

                        <tr>
                            <td colSpan="11" height="200px">
                                <p class="text-center">暂无任何数据</p>
                            </td>
                        </tr>
                        </#if>
                        </tbody>
                    </table>
                </div>
            </div>

        <#if tasks?exists && (tasks?size > 0)>
            <div class="row small_page">
                <div class="col-sm-12">
                    <#include "../common/paginate.ftl">
                    <@paginate nowPage=pageIndex itemCount=count action="${base}/market/marketTasks.do" />
                </div>
            </div>
        </#if>
            <!-- end container -->
        </div>


        <script type="text/javascript">


            $(document).ready(function () {

                $("#sMarket").select2();
            });
        </script>