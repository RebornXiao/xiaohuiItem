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

            <div class="row m-t-10" style="padding-left: 20px;padding-right: 20px">
                <div class="col-sm-12">
                    <div class="row">
                        <div class="col-md-2">
                            <button id="addBtn" type="button" class="btn waves-effect waves-light btn-default "><i
                                    class="fa fa-pencil"></i> 添加商品模板
                            </button>
                            <button id="manyDelBtn" type="button" class="btn waves-effect waves-light btn-default "> 批量删除</button>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <div class="input-group">
                                    <input type="text" id="example-input3-group3" name="example-input3-group3"
                                           class="form-control" placeholder="输入商品名称或者商品条码进行模糊搜索">
                                            <span class="input-group-btn">
                                                <button id="searchBtn" type="button"
                                                        class="btn waves-effect waves-light btn-default"> <i
                                                        class="fa fa-search"></i> 搜索 </button>
                                            </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group pull-right">
                                <button id="inBtn" type="button" class="btn btn-default waves-effect waves-light">导入
                                    Excel 商品模板库
                                </button>
                                <div class="btn-group">
                                    <button type="button"
                                            class="btn btn-default dropdown-toggle waves-effect waves-light"
                                            data-toggle="dropdown" aria-expanded="false">导出 商品模板库 <span
                                            class="caret"></span></button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#" id="outExcelBtn">To Excel</a></li>
                                        <li><a href="#" id="outPdfBtn">To PDF</a></li>
                                        <li class="divider"></li>
                                        <li><a href="#" id="outCopyBtn">To 剪贴板</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <table class="table table-striped table-bordered">
                            <thead class="table_head">
                            <tr>
                                <th>
                                    <div class="checkbox checkbox-primary checkbox-inline">
                                        <input type="checkbox" id="selectAllBtn">
                                        <label for="inlineCheckbox2">全选</label>
                                    </div>
                                </th>
                                <th>商品自定义编码</th>
                                <th>商品名称</th>
                                <th>唯一条形码</th>
                                <th>商品类型</th>
                                <th>单位</th>
                                <th>成本价</th>
                                <th>零售价</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="itemListTable">

                            <#list itemlist as item>

                            <tr>
                                <td>
                                    <div class="checkbox checkbox-primary checkbox-inline checkbox-single">
                                        <input type="checkbox">
                                        <label></label>
                                    </div>
                                </td>
                                <td>${item.defineCode}</td>
                                <td>${item.name}</td>
                                <td>${item.barcode}</td>
                                <td>${item.typeName}</td>
                                <td>${item.unitName}</td>
                                <td>${item.castPrice}</td>
                                <td>${item.defaultPrice}</td>
                                <td>${item.uploadTime}</td>
                                <td>
                                    <button type="button" class="btn waves-effect waves-light btn-warning btn-sm">编辑
                                    </button>
                                    <button type="button" class="btn waves-effect waves-light btn-danger btn-sm">删除
                                    </button>
                                </td>
                            </tr>

                            </#list>
                            </tbody>
                        </table>
                        <!-- </div> -->
                    </div>
                </div>
            </div>

            <div class="row">


            </div>


            <!-- end container -->
        </div>
        <!-- end content -->

