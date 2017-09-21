<div class="content-page">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">采购端管理</a></li>
                            <li class="active"><a href="#">供应商列表</a></li>
                        </ol>
                        <h4 class="page-title "><b>供应商详情</b></h4>
                    </div>
                </div>
            </div>
            <button type="button" id="returnBtn" class="btn btn-primary" style="padding-right:0px 20px;" onclick="javascript:history.go(-1);"><i class="fa fa-backward"></i> 返回列表</button>
            <div class="row">
                <div class="col-sm-12">
                    <div class="advert_container">
                        <#--<ol class="breadcrumb pull-right">-->
                            <#--<li><a href="#" data-toggle="modal" data-target="#editButton">编辑</a></li>-->
                            <#--<li><a href="#" data-toggle="modal" data-target="#deleteButton">删除</a></li>-->
                        <#--</ol>-->
                        <h5 class="page-title" style="padding-top: 20px"><b>基本信息</b></h5>
                        <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                        <div class="table-responsive advert_detail_table">
                            <table class="table table-bordered">
                                <#if supplier?exists>
                                    <tbody>
                                    <tr>
                                        <td style="background-color: #f9f9f9">供应商名称</td>
                                        <td>${supplier.supplierName}</td>
                                        <td style="background-color: #f9f9f9">供应商地址</td>
                                        <td>${supplier.address}</td>
                                    </tr>
                                    </tbody>
                                    <tbody>
                                    <tr>
                                        <td style="background-color: #f9f9f9">供应商属性</td>
                                        <td>${supplier.supplierType}</td>
                                        <td style="background-color: #f9f9f9">出货周期</td>
                                        <td>${supplier.deliverPeriod}</td>
                                    </tr>
                                    </tbody>
                                    <tbody>
                                    <tr>
                                        <td style="background-color: #f9f9f9">状态</td>
                                        <td>${supplier.status}</td>
                                        <td style="background-color: #f9f9f9">创建时间</td>
                                        <td>${supplier.createTime}</td>
                                    </tr>
                                    </tbody>
                                    <tbody>
                                    <tr>
                                        <td style="background-color: #f9f9f9">停止使用原因</td>
                                        <td colspan="3"></td>
                                    </tr>
                                    </tbody>
                                </#if>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-8">
                    <div class="advert_container">
                        <h5 class="page-title" style="padding-top: 20px"><b>联系信息</b></h5>
                        <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th style="background-color: #f9f9f9">对接业务员</th>
                                    <th style="background-color: #f9f9f9">联系电话</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#if supplier?exists>
                                    <tr>
                                        <td>${supplier.salesmanName}</td>
                                        <td>${supplier.phone}</td>
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
                                    <th style="background-color: #f9f9f9">仓库名称</th>
                                    <th style="background-color: #f9f9f9">采购时间</th>
                                    <th style="background-color: #f9f9f9">商品名称</th>
                                    <th style="background-color: #f9f9f9">商品数量</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#--<#if (screens?size > 0)>-->
                                    <#--<#list screens as screen>-->
                                    <#--<tr>-->
                                        <#--<td>${screen.marketName}</td>-->
                                        <#--<td>${screen.code}</td>-->
                                        <#--<td>${screen.marketName}</td>-->
                                        <#--<td>${screen.code}</td>-->
                                    <#--</tr>-->
                                    <#--</#list>-->
                                <#--<#else>-->
                                    <#--<tr>-->
                                        <#--<td colSpan="11" height="100px">-->
                                            <#--<p class="text-center" style="line-height: 100px">暂无任何数据</p>-->
                                        <#--</td>-->
                                    <#--</tr>-->
                                <#--</#if>-->
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

