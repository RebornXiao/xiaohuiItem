<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->

<link href="${res}/assets/plugins/bootstrap-select2/select2.min.css" rel="stylesheet" type="text/css">

<script src="${res}/assets/plugins/address/address.js"></script>

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
                            <li class="active"><a href="#">店铺列表</a></li>
                        </ol>
                        <h4 class="page-title"><b>店铺列表</b></h4>
                    </div>
                </div>
            </div>

            <div class="card-box">
                <div class="row">
                    <div class="col-sm-12">
                        <form class="form-inline" role="form">

                            <button id="addBtn" type="button"
                                    class="btn waves-effect waves-light btn-primary">添加店铺</button>

                            <div class="form-group">
                                <label for="exampleInputName2">地区选择：</label>
                                <select id="loc_province" style="width:120px;"></select>
                                <select id="loc_city" style="width:120px; margin-left: 10px"></select>
                                <select id="loc_district" style="width:120px;margin-left: 10px"></select>
                                <select id="loc_street" style="width:120px;margin-left: 10px"></select>
                            </div>

                            <div class="form-group m-l-15">
                                <label for="exampleInputName2">店铺状态：</label>
                                <select class="form-control" id="itSelect" style="width:150px">
                                    <option data_id="1">正常</option>
                                    <option data_id="3">维护</option>
                                    <option data_id="2">关店</option>
                                    <option data_id="0">无效</option>
                                </select>
                            </div>

                            <div class="form-group m-l-15">
                                <label for="exampleInputName2">店铺配送方式：</label>
                                <select class="form-control" id="itSelect" style="width:150px">
                                    <option data_id="1">仅自提</option>
                                    <option data_id="2">仅送货</option>
                                    <option data_id="3">可自提也可送货</option>
                                    <option data_id="4">AI(智能)</option>
                                    <option data_id="5">可自提也可AI配送</option>
                                </select>
                            </div>

                            <div class="input-group m-l-15">
                                <input type="text" id="searchKeyTxt"
                                       class="form-control" placeholder="输入店铺名称搜索">
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
                            <th>商店名字</th>
                            <th>商店类型</th>
                            <th>店铺状态</th>
                            <th>所在地区</th>
                            <th>具体地址</th>
                            <th>配送方式</th>
                            <th>配送距离</th>
                            <th>配送费</th>
                            <th>建店时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="marketListTable">

                        <#if (markets?size > 0) >
                            <#list markets as market>
                            <tr>
                                <td>${market.id?c}</td>
                                <td>${market.name}</td>
                                <td>自营</td>
                                <td>
                                    <#if market.status == 0>
                                        <span class="label label-default">无效</span>
                                    </#if>
                                    <#if market.status == 1>
                                        <span class="label label-success">正常</span>
                                    </#if>
                                    <#if market.status == 2>
                                        <span class="label label-default">关店</span>
                                    </#if>
                                    <#if market.status == 3>
                                        <span class="label label-danger">维护</span>
                                    </#if>
                                </td>
                                <td>
                                    <#if market.province??>
                                    ${market.province}&nbsp;
                                    </#if>
                                    <#if market.city??>
                                    ${market.city}&nbsp;
                                    </#if>
                                    <#if market.district??>
                                    ${market.district}&nbsp;
                                    </#if>
                                    <#if market.street??>
                                    ${market.street}&nbsp;
                                    </#if>
                                    <#if market.streetName?? >
                                    ${market.streetName}&nbsp;
                                    </#if>
                                    <#if market.streetNumber?? >
                                    ${market.streetNumber}&nbsp;
                                    </#if>
                                </td>
                                <td><#if market.address??>
                                ${market.address}&nbsp;&nbsp;
                                </#if></td>
                                <td>
                                    <#if market.deliveryMode == 1>
                                        仅自提
                                    </#if>
                                    <#if market.deliveryMode == 2>
                                        仅送货
                                    </#if>
                                    <#if market.deliveryMode == 3>
                                        可自提也可送货
                                    </#if>
                                    <#if market.deliveryMode == 4>
                                        AI(智能)
                                    </#if>
                                    <#if market.deliveryMode == 5>
                                        可自提也可AI配送
                                    </#if>
                                </td>
                                <td>
                                    <#if market.coveringDistance == -1>
                                        无限制(全国)
                                    </#if>
                                    <#if market.coveringDistance == 0>
                                        不对外
                                    </#if>
                                    <#if (market.coveringDistance > 0)>
                                    ${market.coveringDistance?c}米
                                    </#if>
                                </td>
                                <td>
                                    <#if (market.deliveryCost > 0)>
                                        <#assign dCost = market.deliveryCost/100 >
                                    ${dCost}元
                                    </#if>
                                </td>
                                <td><#if (market.deliveryCost > 0)>
                                    ${market.createTime}
                                </#if></td>
                                <td>
                                    <button id="editBtn" type="button"
                                            class="btn waves-effect waves-light btn-warning btn-sm"
                                            data_id="${market.id?c}">编辑
                                    </button>
                                    <button id="seeBtn" type="button"
                                            class="btn waves-effect waves-light btn-pink btn-sm"
                                            data_id="${market.id?c}">查看商品
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

        <#if (markets?size > 0)>
            <div class="row small_page">
                <div class="col-sm-12">
                    <#include "../common/paginate.ftl">
                    <@paginate nowPage=pageIndex itemCount=count action="${base}/market/markets.do" />
                </div>
            </div>
        </#if>
            <!-- end container -->
        </div>


        <script type="text/javascript">

            $(document).ready(function () {

                var _marketListTable = $("#marketListTable");

                <#if streets?? >
                    <#assign streets_assign = streets >
                <#else>
                    <#assign streets_assign = "null" >
                </#if>

                showLocation(${provinceId}, ${cityId}, ${districtId}, ${streetId}, ${streets_assign}, function (id, func) {
                    alert("id="+id);
                    $.get("${base}/market/getStreets.do?districtId=" + id, function (data) {
                        func(id, data);
                    }, "json");
                });

                //添加
                $("#addBtn").on('click', function () {
                    location.href = "${base}/market/merketEdit.do";
                });

            <#if (markets?size > 0)>

                //单项编辑
                _marketListTable.find('button[id=editBtn]').each(function () {
                    $(this).on('click', function () {
                        location.href = "${base}/market/merketEdit.do?id=" + $(this).attr("data_id");
                    });
                });

                //单项查看商品
                _marketListTable.find('button[id=seeBtn]').each(function () {
                    $(this).on('click', function () {
                        location.href = "${base}/market/marketItems.do?id=" + $(this).attr("data_id");
                    });
                });

            </#if>
            });
        </script>