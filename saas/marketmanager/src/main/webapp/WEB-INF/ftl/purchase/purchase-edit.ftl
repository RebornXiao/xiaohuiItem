<div class="content-page">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">采购端管理</a></li>
                            <li class="active"><a href="#">采购单列表</a></li>
                        </ol>
                        <h4 class="page-title "><b>编辑采购单</b></h4>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-8">
                    <div class="advert_container">
                        <div>
                            <h5 class="page-title" style="padding-top: 20px"><b>基本信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive advert_detail_table">
                                <table class="table table-bordered">
                                <#--<#if advert?exists>-->
                                    <tbody>
                                    <tr>
                                        <td style="background-color: #f9f9f9">仓库名称</td>
                                        <td>
                                            <select class="form-control">
                                                <option value="-1">请选择仓库</option>
                                                <option value="0">淘金仓</option>
                                            </select>
                                        </td>
                                        <td style="background-color: #f9f9f9">供应商名称</td>
                                        <td>
                                            <select class="form-control">
                                                <option value="-1">请选择供应商</option>
                                                <option value="0">沃尔玛</option>
                                            </select>
                                        </td>
                                    </tr>
                                    </tbody>
                                <#--</#if>-->
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <div class="advert_container">
                        <div>
                            <ol class="breadcrumb pull-right">
                                <li><a href="#">添加商品</a></li>
                            </ol>
                            <h5 class="page-title" style="padding-top: 20px"><b>采购信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive advert_detail_table">
                                <table class="table table-bordered">
                                    <thead>
                                    <tr>
                                        <th style="background-color: #f9f9f9">商品分类</th>
                                        <th style="background-color: #f9f9f9">商品名称</th>
                                        <th style="background-color: #f9f9f9">条形码</th>
                                        <th style="background-color: #f9f9f9">采购日期</th>
                                        <th style="background-color: #f9f9f9">采购数量</th>
                                        <th style="background-color: #f9f9f9">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>
                                            <select class="form-control">
                                                <option value="-1">饮料</option>
                                                <option value="0">零食</option>
                                            </select>
                                        </td>
                                        <td>
                                            <select class="form-control">
                                                <option value="-1">哇哈哈</option>
                                                <option value="0">营养快线</option>
                                            </select>
                                        </td>
                                        <td>234567890123</td>
                                        <td>2017-09-14</td>
                                        <td>10</td>
                                        <td><a>删除</a></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

