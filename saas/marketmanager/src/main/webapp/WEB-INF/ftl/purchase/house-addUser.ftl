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
                        <h4 class="page-title "><b>添加仓管员</b></h4>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-8">
                    <div class="advert_container">
                        <div>
                            <ol class="breadcrumb pull-right">
                                <li><a href="#">添加仓管员</a></li>
                            </ol>
                            <h5 class="page-title" style="padding-top: 20px"><b>基本信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive advert_detail_table">
                                <table class="table table-bordered">
                                    <#--<#if advert?exists>-->
                                        <tbody>
                                        <tr>
                                            <td style="background-color: #f9f9f9">账号</td>
                                            <td><input id="userName" type="text" class="form-control" placeholder="请输入账号"></td>
                                            <td style="background-color: #f9f9f9">备注</td>
                                            <td><input id="remark" type="text" class="form-control" placeholder="请输入相关说明"></td>
                                        </tr>
                                        </tbody>
                                    <#--</#if>-->
                                </table>
                            </div>
                            <div class="form-group m-t-20">
                                <button id="saveBtn" type="button"
                                        class="btn waves-effect waves-light btn-primary col-md-2">确定
                                </button>
                                <button id="backBtn" type="button"
                                        class="btn waves-effect waves-light btn-default col-md-2">返回
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

