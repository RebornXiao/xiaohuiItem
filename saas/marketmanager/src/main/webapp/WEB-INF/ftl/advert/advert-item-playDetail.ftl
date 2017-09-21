<div class="content-page">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">广告端管理</a></li>
                            <li class="active"><a href="#">广告播放详情</a></li>
                        </ol>
                        <h4 class="page-title "><b>广告播放详情</b></h4>
                    </div>
                </div>
                <button type="button" id="advertReturnBtn" class="btn btn-primary" style="padding-right: 20px;padding-left: 20px" onclick="javascript:history.go(-1);"><i class="fa fa-backward"></i> 返回列表</button>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <div class="advert_container">
                        <div>
                            <ol class="breadcrumb pull-right">
                                <#--<li><a href="#" data-toggle="modal" data-target="#editButton">编辑</a></li>-->
                                <#--<li><a href="#" data-toggle="modal" data-target="#deleteButton">删除</a></li>-->
                            </ol>
                            <h5 class="page-title" style="padding-top: 20px"><b>播放信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive advert_detail_table">
                                <table class="table table-bordered">
                                    <colgroup>
                                        <col class="col-xs-3">
                                        <col class="col-xs-3">
                                        <col class="col-xs-3">
                                        <col class="col-xs-3">
                                    </colgroup>
                                    <#if advertScreen?exists >
                                        <tbody>
                                        <tr>
                                            <td style="background-color: #f9f9f9">门店信息</td>
                                            <td>${advertScreen.marketName}</td>
                                            <td style="background-color: #f9f9f9">屏幕编号</td>
                                            <td>${advertScreen.sCode}</td>
                                        </tr>
                                        </tbody>
                                        <tbody>
                                        <tr>
                                            <td style="background-color: #f9f9f9">开始时间</td>
                                            <td>${advertScreen.beginTime}</td>
                                            <td style="background-color: #f9f9f9">结束时间</td>
                                            <td>${advertScreen.endTime}</td>
                                        </tr>
                                        </tbody>
                                        <tbody>
                                        <tr>
                                            <td style="background-color: #f9f9f9">是否下载</td>
                                            <td>${advertScreen.isDown}</td>
                                            <td style="background-color: #f9f9f9">播放状态</td>
                                            <td>${advertScreen.playStatus}</td>
                                        </tr>
                                        </tbody>
                                        <tbody>
                                        <tr>
                                            <td style="background-color: #f9f9f9">播放排序</td>
                                            <td>${advertScreen.playOrder}</td>
                                            <td style="background-color: #f9f9f9">播放备注</td>
                                            <td>${advertScreen.remark}</td>
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
                            <h5 class="page-title" style="padding-top: 20px"><b>广告信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive advert_detail_table">
                                <table class="table table-bordered">
                                    <colgroup>
                                        <col class="col-xs-3">
                                        <col class="col-xs-3">
                                        <col class="col-xs-3">
                                    </colgroup>
                                    <#if advert?exists >
                                        <tbody>
                                        <tr>
                                            <td style="background-color: #f9f9f9">广告标题</td>
                                            <td>${advert.title}</td>
                                            <td style="background-color: #f9f9f9">广告时长</td>
                                            <td>${advert.timeSize} s</td>
                                        </tr>
                                        </tbody>
                                        <tbody>
                                        <tr>
                                            <td style="background-color: #f9f9f9">广告备注</td>
                                            <td colspan="3">${advert.remark}</td>
                                        </tr>
                                        </tbody>
                                    </#if>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--编辑弹窗-->
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
                <button type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
            </div>
        </div>
    </div>
</div>

<!--删除弹窗-->
<div class="modal fade" id="deleteButton" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">删除广告</h4>
            </div>
            <div class="modal-body">
                <p style="text-align: center">确定要删除该广告吗？</p>
            </div>
            <div class="modal-footer" style="text-align: center">
                <button type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
            </div>
        </div>
    </div>
</div>