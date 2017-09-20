<div class="content-page">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">采购端管理</a></li>
                            <li class="active"><a href="#">仓库列表</a></li>
                        </ol>
                        <h4 class="page-title "><b>仓库详情</b></h4>
                    </div>
                </div>
            </div>
            <button type="button" id="returnBtn" class="btn btn-primary" style="padding-right:0px 20px;" onclick="javascript:history.go(-1);"><i class="fa fa-backward"></i> 返回列表</button>
            <div class="row">
                <div class="col-sm-12">
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
                                    <colgroup>
                                        <col class="col-xs-3">
                                        <col class="col-xs-3">
                                        <col class="col-xs-3">
                                        <col class="col-xs-3">
                                    </colgroup>
                                    <#if advert?exists>
                                        <tbody>
                                        <tr>
                                            <td style="background-color: #f9f9f9">仓库编码</td>
                                            <td>${advert.title}</td>
                                            <td style="background-color: #f9f9f9">创建时间</td>
                                            <td>${advert.isDelete}</td>
                                        </tr>
                                        </tbody>
                                        <tbody>
                                        <tr>
                                            <td style="background-color: #f9f9f9">仓库名称</td>
                                            <td>${advert.timeSize}</td>
                                            <td style="background-color: #f9f9f9">仓库地址</td>
                                            <td>${advert.createTime}</td>
                                        </tr>
                                        </tbody>
                                        <tbody>
                                        <tr>
                                            <td style="background-color: #f9f9f9">状态</td>
                                            <td>${advert.timeSize}</td>
                                            <td style="background-color: #f9f9f9">停用原因</td>
                                            <td>${advert.createTime}</td>
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
                <div class="col-sm-12">
                    <div class="advert_container">
                        <div>
                            <h5 class="page-title" style="padding-top: 20px"><b>对接信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive advert_detail_table">
                                <table class="table table-bordered">
                                    <colgroup>
                                        <col class="col-xs-4">
                                        <col class="col-xs-4">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <td style="background-color: #f9f9f9">key</td>
                                        <td style="background-color: #f9f9f9">key</td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#if (screens?size > 0)>
                                        <#list screens as screen>
                                        <tr>
                                            <td>${screen.marketName}</td>
                                            <td>${screen.code}</td>
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
            <div class="row">
                <div class="col-sm-12">
                    <div class="advert_container">
                        <div>
                            <h5 class="page-title" style="padding-top: 20px"><b>采购信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive advert_detail_table">
                                <table class="table table-bordered">
                                    <colgroup>
                                        <col class="col-xs-4">
                                        <col class="col-xs-4">
                                        <col class="col-xs-4">
                                        <col class="col-xs-4">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th style="background-color: #f9f9f9">创建时间</th>
                                        <th style="background-color: #f9f9f9">账号</th>
                                        <th style="background-color: #f9f9f9">备注</th>
                                        <th style="background-color: #f9f9f9">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#if (screens?size > 0)>
                                        <#list screens as screen>
                                        <tr>
                                            <td>${screen.marketName}</td>
                                            <td>${screen.code}</td>
                                            <td>${screen.marketName}</td>
                                            <td>${screen.code}</td>
                                        </tr>
                                        </#list>
                                    <#else>
                                        <tr>
                                            <td colSpan="11" height="100px">
                                                <p class="text-center" style="line-height: 100px">该仓库没有分配仓管员</p>
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

