<div class="content-page" style="background-color: #fff">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">广告端管理</a></li>
                            <li class="active"><a href="#">广告管理</a></li>
                        </ol>
                        <h4 class="page-title"><b>广告管理</b></h4>
                    </div>
                </div>
            </div>

            <div class="card-box">
                <form class="form-inline" role="form">
                    <div class="form-group m-l-15">
                        <label for="advertNavTitle">广告标题：</label>
                        <input type="text" class="form-control" id="advertNavTitle" placeholder="输入广告标题关键字">
                    </div>
                    <div class="form-group m-l-15">
                        <label for="advertNavTime">广告时长：</label>
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
                    <div class="form-group m-l-15">
                        <button type="button" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
                    </div>
                </form>
             </div>
        <#--<hr style="height:1px;width:100%;border:none;border-top:1px solid #ccc;" />-->
            <button type="button" class="btn btn-primary pull-right" data-toggle="modal" data-target="#uploadButton" style="padding-left: 30px;padding-right: 30px;margin-right: 30px;margin-bottom: 20px"><i class="fa fa-upload"></i> 上传广告</button>
            <div class="row">
                <div class="col-sm-12">
                    <table class="table table-striped table-bordered">
                        <thead class="table_head">
                        <tr>
                            <th>序号</th>
                            <th>广告标题</th>
                            <th>广告时长</th>
                            <th>更新时间</th>
                            <th>广告状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="advertInfoTable">
                        <tr>
                            <td style="text-align: center">1</td>
                            <td style="text-align: center">xxx的广告</td>
                            <td style="text-align: center">30s</td>
                            <td style="text-align: center">2017-08-24 00:00:00</td>
                            <td style="text-align: center">已删除</td>
                            <td style="text-align: center">
                                <a href="${base}/advert/adverts/detail.do">查看</a>
                                <a data-toggle="modal" data-target="#editButton">编辑</a>
                                <a data-toggle="modal" data-target="#deleteButton">删除</a>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center">1</td>
                            <td style="text-align: center">xxx的广告</td>
                            <td style="text-align: center">30s</td>
                            <td style="text-align: center">2017-08-24 00:00:00</td>
                            <td style="text-align: center">已删除</td>
                            <td style="text-align: center">
                                <a href="${base}/advert/adverts/detail.do">查看</a>
                                <a data-toggle="modal" data-target="#editButton">编辑</a>
                                <a data-toggle="modal" data-target="#deleteButton">删除</a>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center">1</td>
                            <td style="text-align: center">xxx的广告</td>
                            <td style="text-align: center">30s</td>
                            <td style="text-align: center">2017-08-24 00:00:00</td>
                            <td style="text-align: center">已删除</td>
                            <td style="text-align: center">
                                <a href="${base}/advert/adverts/detail.do">查看</a>
                                <a data-toggle="modal" data-target="#editButton">编辑</a>
                                <a data-toggle="modal" data-target="#deleteButton">删除</a>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center">1</td>
                            <td style="text-align: center">xxx的广告</td>
                            <td style="text-align: center">30s</td>
                            <td style="text-align: center">2017-08-24 00:00:00</td>
                            <td style="text-align: center">已删除</td>
                            <td style="text-align: center">
                                <a href="${base}/advert/adverts/detail.do">查看</a>
                                <a data-toggle="modal" data-target="#editButton">编辑</a>
                                <a data-toggle="modal" data-target="#deleteButton">删除</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <!--分页-->
            <div class="row small_page">
                <div class="col-sm-12">
                <#--<#include "../common/paginate.ftl">-->
                    <#--<@paginate nowPage=pageIndex itemCount=count action="${base}/item/itemList.do?searchType=${searchType}&searchKey=${searchKey}" />-->

                    <form class="form-inline pull-right">
                        <ul class="pagination">
                            <li class="paginate_button previous">
                                <a href="javascript:void(0);" onclick="clickGoto(0)">上一页</a>
                            </li>
                            <li class="paginate_button active"><a href="#">1</a></li>
                            <li class="paginate_button"><a href="#">2</a></li>
                            <li class="paginate_button"><a href="#">3</a></li>
                            <li class="paginate_button next">
                                <a href="javascript:void(0);" onclick="clickGoto(4})">下一页</a>
                            </li>
                            <li>
                                <div class="input-group">
                                    <span class="input-group-addon">共5页</span>
                                    <input type="text" class="form-control" id="pageNum" style="width: 60px;">
                                    <span class="input-group-addon">页</span>
                                    <span class="input-group-btn">
			                            <button type="button" class="btn btn-primary waves-effect waves-light" onclick="clickGoto(0)">跳转</button>
			                        </span>
                                </div>
                            </li>
                        </ul>
                    </form>

                </div>
            </div>
            <!--/分页-->
        </div>
    </div>
</div>

<!--上传弹窗-->
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
                            <label for="modalAdvertTime">广告时长：</label>
                            <input type="text" class="form-control" id="modalAdvertTitle" placeholder="输入广告时长"/>&nbsp;&nbsp;&nbsp;s (以秒为计算单位)
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalAdvertRemark">广告备注：</label>
                            <input type="text" style="width: 80%" class="form-control" id="modalAdvertTitle" placeholder="输入广告备注"/>
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 80%">
                            <label for="modalAdvertFile">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件：</label>
                            <button class="btn btn-primary" type="button" style="padding-left: 25px;padding-right: 25px">上传</button>
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group style="width: 80%">
                            <div class="progress progress-striped active">
                                <div class="bar" style="width: 40%;"></div>
                            </div>
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
                            <label for="modalAdvertTime">广告时长：</label>
                            <input type="text" class="form-control" id="modalAdvertTitle" placeholder="输入广告时长"/>&nbsp;&nbsp;&nbsp;s (以秒为计算单位)
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalAdvertRemark">广告备注：</label>
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
                <button type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
                <button type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>