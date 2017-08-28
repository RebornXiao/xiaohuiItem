<div class="content-page" style="background-color: #fff">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">广告端管理</a></li>
                            <li class="active"><a href="#">广告</a></li>
                        </ol>
                        <h4 class="page-title"><b>广告管理</b></h4>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="row-sm-12">
                    <form class="form-inline">
                        <div class="advert_filter_nav">
                            <div class="form-group">
                                <label for="advertNavTitle">广告标题</label>
                                <input type="text" class="form-control" id="advertNavTitle" placeholder="输入广告标题关键字">
                            </div>
                            <div class="form-group">
                                <label for="advertNavTime">广告时长</label>
                                <select id="advertNavTime" class="form-control">
                                    <option>选择广告时长</option>
                                    <option>15以内</option>
                                    <option>16s-30s</option>
                                    <option>31s-60s</option>
                                    <option>61s-90s</option>
                                    <option>91s-120s</option>
                                    <option>121s以上</option>
                                </select>
                            </div>
                            <div class="form-group pull-right">
                                <label for="advertNavStatus">投放状态：</label>
                                <button type="button" class="btn btn-primary">全部</button>
                                <button type="button" class="btn btn-default">已投放</button>
                                <button type="button" class="btn btn-default">待投放</button>
                                <button type="button" class="btn btn-primary" style="padding-left:30px;padding-right:30px">搜索</button>
                            </div>
                        </div>
                    </form>
                    <hr style="height:1px;width:100%;border:none;border-top:1px solid #ccc;" />
                    <button type="button" class="btn btn-primary pull-right" data-toggle="modal" data-target="#uploadButton" style="padding-left: 30px;padding-right: 30px;margin-right: 30px;margin-bottom: 20px">上传广告</button>
                    <div class="advert_table_control">
                        <table class="table table-striped">
                            <tbody>
                            <tr>
                                <th style="width: 10%;text-align: center;">序号</th>
                                <th style="width: 20%;text-align: center;">广告标题</th>
                                <th style="width: 10%;text-align: center;">广告时长</th>
                                <th style="width: 20%;text-align: center;">更新时间</th>
                                <th style="width: 10%;text-align: center;">广告状态</th>
                                <th style="width: 30%;text-align: center;">操作</th>
                            </tr>
                            <tr>
                                <td style="text-align: center">1</td>
                                <td style="width:100%;text-align: center;display:block;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">xxx的广告</td>
                                <td style="text-align: center">30s</td>
                                <td style="text-align: center">2017-08-22 10:47:22</td>
                                <td style="text-align: center">待投放</td>
                                <td style="text-align: center">
                                    <a href="adverts/detail.do">查看</a>
                                    <a data-toggle="modal" data-target="#editButton">编辑</a>
                                    <a data-toggle="modal" data-target="#deleteButton">删除</a>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center">1</td>
                                <td style="width:100%;text-align: center;display:block;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">xxx的广告</td>
                                <td style="text-align: center">30s</td>
                                <td style="text-align: center">2017-08-22 10:47:22</td>
                                <td style="text-align: center">待投放</td>
                                <td style="text-align: center">
                                    <a>查看</a>
                                    <a>编辑</a>
                                    <a>删除</a>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center">1</td>
                                <td style="width:100%;text-align: center;display:block;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">xxx的广告</td>
                                <td style="text-align: center">30s</td>
                                <td style="text-align: center">2017-08-22 10:47:22</td>
                                <td style="text-align: center">待投放</td>
                                <td style="text-align: center">
                                    <a>查看</a>
                                    <a>编辑</a>
                                    <a>删除</a>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center">1</td>
                                <td style="width:100%;text-align: center;display:block;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">xxx的广告</td>
                                <td style="text-align: center">30s</td>
                                <td style="text-align: center">2017-08-22 10:47:22</td>
                                <td style="text-align: center">待投放</td>
                                <td style="text-align: center">
                                    <a>查看</a>
                                    <a>编辑</a>
                                    <a>删除</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="uploadButton" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">上传广告</h4>
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
                    <form class="form-inline">
                        <div class="form-group" style="width: 80%">
                            <label for="modalAdvertTitle">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件：</label>
                            <button class="btn btn-primary" type="button" style="padding-left: 25px;padding-right: 25px">上传</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer" style="text-align: center">
                <button type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
            </div>
        </div>
    </div>
</div>

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
            </div>
        </div>
    </div>
</div>

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
                <button type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
                <button type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>