<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->

<#--<script src="${base}/assets/pages/item-list.js" />-->

<div class="content-page">
    <!-- Start content -->
    <div class="content">
        <div class="container">

            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">商品管理</a></li>
                            <li class="active"><a href="#">商品模板库</a></li>
                        </ol>
                        <h4 class="page-title"><b>商品模板库</b></h4>
                    </div>
                </div>
            </div>

            <div class="row small_page">
                <div class="col-sm-12">
                    <form class="form-inline">
                        <button id="addBtn" type="button" class="btn waves-effect waves-light btn-primary "><i
                                class="fa fa-pencil"></i> 添加商品模板
                        </button>
                        <button id="manyDelBtn" type="button" class="btn waves-effect waves-light btn-primary ">
                            批量删除
                        </button>
                        <div class="input-group">
                            <div class="input-group-btn">
                                <button id="searchMenu" type="button" search_name="名称"
                                        class="btn btn-default waves-effect waves-light dropdown-toggle"
                                        data-toggle="dropdown">按名称搜索 <span class="caret"></span></button>
                                <ul class="dropdown-menu" id="searchType">
                                    <li><a href="javascript:void(0)" search_name="名称" search_type="name">按名称搜索</a></li>
                                    <li><a href="javascript:void(0)" search_name="自定义编码" search_type="define_code">按自定义编码搜索</a>
                                    </li>
                                    <li><a href="javascript:void(0)" search_name="条形码" search_type="barcode">按条形码搜索</a>
                                    </li>
                                </ul>
                            </div>
                            <input type="text" id="searchKey"
                                   class="form-control" placeholder="按名称查">
                            <span class="input-group-btn">
                                    <button id="searchBtn" type="button"
                                            class="btn waves-effect waves-light btn-primary"><i
                                            class="fa fa-search"></i></button>
                                </span>
                        </div>
                        <div class="form-group pull-right">
                            <div class="btn-group">
                                <button type="button"
                                        class="btn btn-default waves-effect waves-light dropdown-toggle"
                                        data-toggle="dropdown" aria-expanded="false">导出 商品模板库 <span
                                        class="caret"></span></button>
                                <ul class="dropdown-menu">
                                    <li><a href="#" id="outExcelBtn">To Excel</a></li>
                                    <li><a href="#" id="outPdfBtn">To PDF</a></li>
                                    <li class="divider"></li>
                                    <li><a href="#" id="outCopyBtn">To 剪贴板</a></li>
                                </ul>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <div class="row small_page">
                <div class="col-sm-12">
                    <table class="table table-striped table-bordered m-t-10">
                        <thead class="table_head">
                        <tr>
                            <th>
                                <div class="checkbox checkbox-primary checkbox-inline">
                                    <input id="allSelectBtn" type="checkbox">
                                    <label for="allSelectBtn">
                                        全选
                                    </label>
                                </div>
                            </th>
                            <th>商品ID</th>
                            <th>商品名称</th>
                            <th>商品自定义编码</th>
                            <th>唯一条形码</th>
                            <th>商品类型</th>
                            <th>单位</th>
                            <th>成本价(元)</th>
                            <th>零售价(元)</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="itemListTable">

                        <#if (itemlist?size > 0)>
                            <#list itemlist as item>
                            <tr>
                                <td>
                                    <div class="checkbox checkbox-primary checkbox-inline">
                                        <input id="checkbox_${item_index}" type="checkbox" data_id="${item.id?c}">
                                        <label for="checkbox_${item_index}">
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                        </label>
                                    </div>
                                </td>
                                <td>${item.id?c}</td>
                                <td>${item.name}</td>
                                <td>${item.defineCode}</td>
                                <td>${item.barcode}</td>
                                <td>${item.typeName}</td>
                                <td>${item.unitName}</td>
                                <td>${item.costPrice}</td>
                                <td>${item.defaultPrice}</td>
                                <td>${item.uploadTime}</td>
                                <td>
                                    <button id="editBtn" type="button"
                                            class="btn waves-effect waves-light btn-warning btn-sm"
                                            data_id="${item.id?c}">编辑
                                    </button>
                                    <button id="delBtn" type="button"
                                            class="btn waves-effect waves-light btn-danger btn-sm"
                                            data_id="${item.id?c}">
                                        删除
                                    </button>
                                </td>
                            </tr>

                            </#list>
                        <#else>
                        <tr>
                            <td rowspan="11">
                                <p class="text-center">暂无任何数据</p>
                            </td>
                        </tr>
                        </#if>
                        </tbody>
                    </table>
                </div>
            </div>


        <#if (itemlist?size > 15)>
            <div class="row small_page">
                <#include "../common/paginate.ftl">
                    <@paginate page=1 count=10 action="sdf" />
            </div>
        </#if>

        <!-- end container -->
        </div>

        <script type="text/javascript">

            $(document).ready(function () {

                //默认按名称
                var searchTypeValue = "name";
                var _searchMenu = $("#searchMenu");
                var _searchKey = $("#searchKey");
                var _itemListTable = $("#itemListTable");
                var _allSelectBtn = $("#allSelectBtn");

                //搜索选择
                $("#searchType li a").on('click', function () {
                    var sName = $(this).attr("search_name");
                    var txt = "按" + sName + "搜索";
                    searchTypeValue = $(this).attr("search_type")
                    _searchMenu.html(txt + "<span class=\"caret\"></span>");
                    _searchMenu.attr("search_name", sName)
                    _searchKey.attr("placeholder", txt);
                });

                $("#searchBtn").on('click', function () {
                    //搜索
                    var sValue = _searchKey.val();
                    if (sValue == "") {
                        swal("请输入商品" + _searchMenu.attr("search_name") + "进行搜索!");
                        return;
                    }
                    location.href = "${base}/market/manager/item/itemList.do?searchType=" + searchTypeValue + "&searchKey=" + sValue;
                });

                //添加新品
                $("#addBtn").on('click', function () {
                    location.href = "${base}/market/manager/item/itemEdit.do";
                });

            <#if (itemlist?size > 0)>

                //批量删除
                $("#manyDelBtn").on('click', function () {
                    //查找选中的项
                    var ids = "";
                    _itemListTable.find('input:checkbox').each(function () {
                        if ($(this).is(':checked')) {
                            ids = ids + $(this).attr("data_id") + ",";
                        }
                    });
                    if (ids != "") {
                        showDelTi(function () {

                        });
                    }
                });

                //全选按钮
                _allSelectBtn.on('click', function () {
                    var s = _allSelectBtn.is(':checked')
                    _itemListTable.find('input:checkbox').each(function () {
                        $(this).prop('checked', s);
                    });
                });

                //单项编辑
                _itemListTable.find('button[id=editBtn]').each(function () {
                    $(this).on('click', function () {
                        location.href = "${base}/market/manager/item/itemEdit.do?id=" + $(this).attr("data_id");
                    });
                });

                //单项删除
                _itemListTable.find('button[id=delBtn]').each(function () {
                    $(this).on('click', function () {
                        showDelTi(function () {

                        });
                    });
                });

            </#if>

            });
        </script>