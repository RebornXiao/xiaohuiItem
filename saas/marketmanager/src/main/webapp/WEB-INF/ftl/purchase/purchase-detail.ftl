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
                        <h4 class="page-title "><b>采购单详情</b></h4>
                    </div>
                </div>
            </div>
            <button type="button" id="returnBtn" class="btn btn-primary" style="padding-right:0px 20px;margin-bottom: 22px;" onclick="javascript:history.go(-1);"><i class="fa fa-backward"></i> 返回列表</button>
            <div class="card-box">
                <div class="row">
                    <div class="col-sm-8">
                        <div class="advert_container">
                            <div>
                                <ol class="breadcrumb pull-right">
                                    <#--<li><a href="#" data-toggle="modal" data-target="#editButton">编辑</a></li>-->
                                    <#--<li><a href="#" data-toggle="modal" data-target="#deleteButton">删除</a></li>-->
                                </ol>
                                <h5 class="page-title" style="padding-top: 20px"><b>基本信息</b></h5>
                                <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                                <div class="table-responsive advert_detail_table">
                                    <table class="table table-bordered">
                                        <#if purchase?exists>
                                            <tbody>
                                            <tr>
                                                <td style="background-color: #f9f9f9">仓库名称</td>
                                                <td>${purchase.warehouse_name}</td>
                                                <td style="background-color: #f9f9f9">仓库地址</td>
                                                <td>${purchase.address}</td>
                                            </tr>
                                            <tr>
                                                <td style="background-color: #f9f9f9">供应商名称</td>
                                                <td>${purchase.supplier_name}</td>
                                                <td style="background-color: #f9f9f9">入库状态</td>
                                                <td>
                                                <#if purchase.status=1><text class="text-danger">未入库</text>
                                                    <#elseif purchase.status=2><text class="text-danger">入库异常</text>
                                                    <#elseif purchase.status=3>完成入库
                                                    </#if>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="background-color: #f9f9f9">异常说明</td>
                                                <td colspan="3"><#if purchase.exception_remark!=''>${purchase.exception_remark}<#else>无</#if></td>
                                            </tr>
                                            </tbody>
                                        </#if>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="advert_container">
                            <h5 class="page-title" style="padding-top: 20px"><b>联系信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive advert_detail_table">
                                <table class="table table-bordered">
                                    <thead>
                                    <tr>
                                        <th style="background-color: #f9f9f9">对接业务员</th>
                                        <th style="background-color: #f9f9f9">联系电话</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#if purchase?exists>
                                        <tr>
                                            <td>${purchase.salesman_name}</td>
                                            <td>${purchase.phone}</td>
                                        </tr>
                                    </#if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="advert_container">
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
                                        <th style="background-color: #f9f9f9">入库日期</th>
                                        <th style="background-color: #f9f9f9">入库数量</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#if (commoditys?size > 0)>
                                        <#list commoditys as commodity>
                                        <tr>
                                            <td>${commodity.itemTypeTitle}</td>
                                            <td>${commodity.itemName}</td>
                                            <td>${commodity.barcode}</td>
                                            <td>${commodity.purchaseTime}</td>
                                            <td>${commodity.purchaseNumber}</td>
                                            <td><#if commodity.depositTime=''><#else>${commodity.depositTime}</#if></td>
                                            <td><#if commodity.depositNumber=0><#else>${commodity.depositNumber}</#if></td>
                                        </tr>
                                        </#list>
                                    <#else>
                                        <tr>
                                            <td colSpan="11" height="100px">
                                                <p class="text-center" style="line-height: 100px">暂无任何数据</p>
                                            </td>
                                        </tr>
                                    </#if>
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

