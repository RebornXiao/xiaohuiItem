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
                            <li><a href="#">商品管理</a></li>
                            <li class="active"><a href="#">商品分类</a></li>
                        </ol>
                        <h4 class="page-title"><b>商品分类</b></h4>
                    </div>
                </div>
            </div>

            <div class="row m-t-10" style="padding-left: 20px;padding-right: 20px">
                <div class="col-sm-12">
                    <div class="row">
                        <div class="col-md-2">
                            <button type="button" class="btn waves-effect waves-light btn-primary "><i
                                    class="fa fa-pencil"></i> 添加商品一级分类
                            </button>
                            <button type="button" class="btn waves-effect waves-light btn-primary "> 批量删除</button>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <div class="input-group">
                                    <input type="text" id="example-input3-group3" name="example-input3-group3"
                                           class="form-control" placeholder="输入商品分类名称进行模糊搜索">
                                            <span class="input-group-btn">
                                                    <button type="button"
                                                            class="btn waves-effect waves-light btn-primary"> <i
                                                            class="fa fa-search"></i> 搜索 </button>
                                                </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                        </div>
                        <table id="datatable" class="table table-striped table-bordered">
                            <thead class="table_head">
                            <tr>
                                <th>
                                    <div class="checkbox checkbox-primary checkbox-inline">
                                        <input type="checkbox"/>
                                        <label id="inlineCheckbox2">全选</label>
                                    </div>
                                </th>
                                <th>ID</th>
                                <th>商品类型名称</th>
                                <th>类型级别</th>
                                <th>状态</th>
                                <th>排序值</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            <#list itemTypes as type >
                            <tr>
                                <td>
                                    <div class="checkbox checkbox-primary checkbox-inline checkbox-single">
                                        <input type="checkbox" id="singleCheckbox2" value="option2" checked=""
                                               aria-label="Single checkbox Two">
                                        <label></label>
                                    </div>
                                </td>
                                <td>${type.id}</td>
                                <td>${type.id}</td>
                                <td><#if type.parentId == 0>一级分类</#if></td>
                                <td>饮料</td>
                                <td>1</td>
                                <td>2</td>
                                <td>
                                    <button type="button" class="btn waves-effect waves-light btn-warning btn-sm ">编辑
                                    </button>
                                    <button type="button" class="btn waves-effect waves-light btn-danger btn-sm">删除
                                    </button>
                                    <button type="button" class="btn waves-effect waves-light btn-danger btn-sm">添加子类
                                    </button>
                                    <button type="button" class="btn waves-effect waves-light btn-danger btn-sm">查看子类
                                    </button>
                                    <button type="button" class="btn waves-effect waves-light btn-danger btn-sm">排序子类
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
            <!-- end container -->
        </div>
        <!-- end content -->
