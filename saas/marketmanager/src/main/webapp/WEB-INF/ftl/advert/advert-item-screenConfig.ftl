<div class="content-page" style="background-color: #fff">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">广告端管理</a></li>
                            <li class="active"><a href="#">广告详情</a></li>
                        </ol>
                        <h4 class="page-title "><b>屏幕配置详情</b></h4>
                    </div>
                </div>
                <button type="button" id="advertReturnBtn" class="btn btn-primary" style="padding-right: 20px;padding-left: 20px" onclick="window.location='${base}/advert/screens.do'"><i class="fa fa-backward"></i> 返回列表</button>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <div class="advert_container">
                        <div>
                            <ol class="breadcrumb pull-right">
                                <#--<li><a href="#" data-toggle="modal" data-target="#editButton">编辑</a></li>-->
                                <#--<li><a href="#" data-toggle="modal" data-target="#deleteButton">删除</a></li>-->
                            </ol>
                            <h5 class="page-title" style="padding-top: 20px"><b>屏幕信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive advert_detail_table">
                                <table class="table table-bordered">
                                    <colgroup>
                                        <col class="col-xs-3">
                                        <col class="col-xs-3">
                                        <col class="col-xs-3">
                                        <col class="col-xs-3">
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <td style="background-color: #f9f9f9">编号</td>
                                        <td>${screen.code}</td>
                                        <td style="background-color: #f9f9f9">屏幕分辨率</td>
                                        <td>${screen.screenSize}</td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td style="background-color: #f9f9f9">屏幕MAC</td>
                                        <td>${screen.mac}</td>
                                        <td style="background-color: #f9f9f9">屏幕请求时间</td>
                                        <td>${screen.requireTime}s</td>
                                    </tr>
                                    </tbody>
                                    <tbody>
                                    <tr>
                                        <td style="background-color: #f9f9f9">门店信息</td>
                                        <td>${screen.marketName}</td>
                                        <td style="background-color: #f9f9f9">正在投放广告数</td>
                                        <td>${screen.advertCount}</td>
                                    </tr>
                                    </tbody>
                                    <tbody>
                                    <tr>
                                        <td style="background-color: #f9f9f9">广告备注</td>
                                        <td colspan="3">${screen.screenRemark}</td>
                                    </tr>
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
                            <h5 class="page-title" style="padding-top: 20px"><b>已投放的广告</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive advert_detail_table">
                                <table class="table table-bordered">
                                    <colgroup>
                                        <col class="col-xs-4">
                                        <col class="col-xs-4">
                                        <col class="col-xs-4">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th style="background-color: #f9f9f9">播放排序</th>
                                        <th style="background-color: #f9f9f9">广告标题</th>
                                        <th style="background-color: #f9f9f9">广告时长</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#if (adverts?size > 0)>
                                        <#list adverts as advert>
                                    <tr>
                                        <td>${advert.playOrder}</td>
                                        <td>${advert.title}</td>
                                        <td>${advert.timeSize}</td>
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
<!--编辑广告弹窗-->
<div class="modal fade" id="editButton" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">编辑广告</h4>
            </div>
            <div class="modal-body">
                <div class="modalAdvertStyle">
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalAdvertTitle">广告标题：</label>
                            <input type="text" style="width: 80%" class="form-control" id="modalAdvertTitle" placeholder="输入广告标题"/>
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group">
                            <label for="modalAdvertTitle">广告时长：</label>
                            <input type="text" class="form-control" id="modalAdvertTitle" placeholder="输入广告时长"/>&nbsp;&nbsp;&nbsp;S
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalAdvertTitle">广告备注：</label>
                            <input type="text" style="width: 80%" class="form-control" id="modalAdvertTitle" placeholder="输入广告备注"/>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer" style="text-align: center">
                <button type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
                <button type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!--删除屏幕弹窗-->
<div class="modal fade" id="deleteButton" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">删除屏幕</h4>
            </div>
            <div class="modal-body">
                <p style="text-align: center">确定要删除该屏幕配置吗？</p>
            </div>
            <div class="modal-footer" style="text-align: center">
                <button type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
                <button type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>