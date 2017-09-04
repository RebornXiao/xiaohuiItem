<div class="content-page" style="background-color: #fff">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12 ">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">广告端管理</a></li>
                            <li class="active"><a href="#">投放广告</a></li>
                        </ol>
                        <h4 class="page-title"><b>投放管理</b></h4>
                    </div>
                </div>
            </div>

            <div class="" style="padding:0px 20px 20px 20px">
                <button id="adPlayBtn" type="button" class="btn btn-primary btn-lg"><i class="fa fa-play-circle"></i> 广告播放</button>
                <button id="adScreenEditBtn" type="button" class="btn btn-default btn-lg"><i class="fa fa-edit"></i> 屏幕配置</button>
            </div>
            <div class="card-box">
                <form class="form-inline" role="form">
                    <div class="form-group m-l-15">
                        <label for="storeInfo">门店信息:</label>
                        <select id="storeInfo_select" class="form-control">
                            <option>选择门店</option>
                            <option>杨琪店杨琪店杨琪店</option>
                            <option>淘金店</option>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <label for="advertNavTitle">屏幕编号:</label>
                        <input type="text" class="form-control" id="advertNavTitle" placeholder="输入编号">
                    </div>
                    <div class="form-group m-l-15">
                        <label for="advertNavTitle">广告标题:</label>
                        <input type="text" class="form-control" id="advertNavTitle" placeholder="输入广告标题关键字">
                    </div>
                    <div class="form-group m-l-15">
                        <label>日期:</label>
                        <div class="input-group">
                            <input id="startTime" type="text" class="form-control" readonly>
                            <span class="input-group-addon bg-default"
                                  onClick="jeDate({dateCell:'#startTime',isTime:true,format:'YYYY-MM-DD hh:mm:ss'})"><i
                                    class="fa fa-calendar"></i></span>
                        </div>
                        <label>至</label>
                        <div class="input-group">
                            <input id="endTime" type="text" class="form-control" readonly>
                            <span class="input-group-addon bg-default"
                                  onClick="jeDate({dateCell:'#endTime',isTime:true,format:'YYYY-MM-DD hh:mm:ss'})"><i
                                    class="fa fa-calendar"></i></span>
                        </div>
                    </div>
                    <div class="form-group pull-right m-l-15">
                        <label for="advertNavStatus">是否下载：</label>
                        <button type="button" class="btn btn-primary">全部</button>
                        <button type="button" class="btn btn-default">是</button>
                        <button type="button" class="btn btn-default">否</button>
                    </div>
                    <div class="form-group m-l-15 m-t-20">
                        <label for="advertNavStatus">播放状态：</label>
                        <button type="button" class="btn btn-primary">全部</button>
                        <button type="button" class="btn btn-default">待播放</button>
                        <button type="button" class="btn btn-default">播放中</button>
                        <button type="button" class="btn btn-default">已停止</button>
                        <button type="button" class="btn btn-default">已移除</button>
                    </div>
                    <div class="form-group  m-l-25 m-t-20">
                        <button type="button" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
                    </div>
                </form>
            </div>

            <button type="button" class="btn btn-primary pull-right m-b-15" data-toggle="modal" id="addPlayButton"><i class="fa fa-pencil"></i> 添加广告播放</button>
            <div class="row">
                <div class="col-sm-12">
                    <table class="table table-striped table-bordered">
                        <thead class="table_head">
                        <tr>
                            <th>序号</th>
                            <th>门店信息</th>
                            <th>屏幕编号</th>
                            <th>广告标题</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>是否下载</th>
                            <th>播放状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="storeInfoListTable">
                        <tr>
                            <td style="text-align: center">2</td>
                            <td style="text-align: center">杨琪店</td>
                            <td style="text-align: center">10002-02</td>
                            <td style="text-align: center">xxx的广告</td>
                            <td style="text-align: center">2017-08-26 00:00:00</td>
                            <td style="text-align: center">2017-08-28 00:00:00</td>
                            <td style="text-align: center" class="text-danger">否</td>
                            <td style="text-align: center"><span class="label label-warning">待播放</span></td>
                            <td style="text-align: center">
                                <a href="${base}/advert/adverts/playDetail.do">查看</a>
                                <a data-toggle="modal" data-target="#editButton">编辑</a>
                                <a data-toggle="modal" data-target="#deleteButton">移除</a>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center">1</td>
                            <td style="text-align: center">淘金店</td>
                            <td style="text-align: center">10001-01</td>
                            <td style="text-align: center">xxx的广告</td>
                            <td style="text-align: center">2017-08-24 00:00:00</td>
                            <td style="text-align: center">2017-08-27 00:00:00</td>
                            <td style="text-align: center" class="text-primary">是</td>
                            <td style="text-align: center"><span class="label label-danger">已停止</span></td>
                            <td style="text-align: center">
                                <a href="${base}/advert/adverts/playDetail.do">查看</a>
                                <a data-toggle="modal" data-target="#editButton">编辑</a>
                                <a data-toggle="modal" data-target="#deleteButton">停止</a>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center">2</td>
                            <td style="text-align: center">杨琪店</td>
                            <td style="text-align: center">10002-02</td>
                            <td style="text-align: center">xxx的广告</td>
                            <td style="text-align: center">2017-08-26 00:00:00</td>
                            <td style="text-align: center">2017-08-28 00:00:00</td>
                            <td style="text-align: center" class="text-danger">否</td>
                            <td style="text-align: center"><span class="label label-primary">播放中</span></td>
                            <td style="text-align: center">
                                <a href="${base}/advert/adverts/playDetail.do">查看</a>
                                <a data-toggle="modal" data-target="#editButton">编辑</a>
                                <a data-toggle="modal" data-target="#deleteButton">移除</a>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center">1</td>
                            <td style="text-align: center">淘金店</td>
                            <td style="text-align: center">10001-01</td>
                            <td style="text-align: center">xxx的广告</td>
                            <td style="text-align: center">2017-08-24 00:00:00</td>
                            <td style="text-align: center">2017-08-27 00:00:00</td>
                            <td style="text-align: center" class="text-primary">是</td>
                            <td style="text-align: center"><span class="label label-danger">已停止</span></td>
                            <td style="text-align: center">
                                <a href="${base}/advert/adverts/playDetail.do">查看</a>
                                <a data-toggle="modal" data-target="#editButton">编辑</a>
                                <a data-toggle="modal" data-target="#deleteButton">停止</a>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center">1</td>
                            <td style="text-align: center">淘金店</td>
                            <td style="text-align: center">10001-01</td>
                            <td style="text-align: center">xxx的广告</td>
                            <td style="text-align: center">2017-08-24 00:00:00</td>
                            <td style="text-align: center">2017-08-27 00:00:00</td>
                            <td style="text-align: center" class="text-primary">是</td>
                            <td style="text-align: center"><span class="label label-danger">已停止</span></td>
                            <td style="text-align: center">
                                <a href="${base}/advert/adverts/playDetail.do">查看</a>
                                <a data-toggle="modal" data-target="#editButton">编辑</a>
                                <a data-toggle="modal" data-target="#deleteButton">停止</a>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center">1</td>
                            <td style="text-align: center">淘金店</td>
                            <td style="text-align: center">10001-01</td>
                            <td style="text-align: center">xxx的广告</td>
                            <td style="text-align: center">2017-08-24 00:00:00</td>
                            <td style="text-align: center">2017-08-27 00:00:00</td>
                            <td style="text-align: center" class="text-primary">是</td>
                            <td style="text-align: center"><span class="label label-danger">已停止</span></td>
                            <td style="text-align: center">
                                <a href="${base}/advert/adverts/playDetail.do">查看</a>
                                <a data-toggle="modal" data-target="#editButton">编辑</a>
                                <a data-toggle="modal" data-target="#deleteButton">停止</a>
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
<script type="text/javascript" src="${res}/assets/plugins/jedate/jedate.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#adScreenEditBtn").on('click', function () {
            location.href = "${base}/advert/adverts/screen.do";
        });
        $("#addPlayButton").on('click', function () {
            location.href = "${base}/advert/adverts/addPlay.do";
        });
    });
</script>

<!--编辑弹窗-->
<div class="modal fade" id="editButton" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">编辑广告播放</h4>
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
                        <div class="form-group" style="width: 100%">
                            <label for="modalAdvertTime">广告时长：</label>
                            <input type="text" style="width: 20%" class="form-control" id="modalAdvertTitle" placeholder="输入广告时长"/>&nbsp;&nbsp;&nbsp;s (以秒为计算单位)
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalAdvertRemark">广告备注：</label>
                            <input type="text" style="width: 80%" class="form-control" id="modalAdvertTitle" placeholder="输入广告备注"/>
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalStoreInfo">门店信息：</label>
                            <select id="selectStore" class="form-control" style="width: 50%">
                                <option>淘金店</option>
                                <option>杨琪店</option>
                                <option>淘金店</option>
                            </select>
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalScreenNum">屏幕编号：</label>
                            <select id="selectScreenNum" class="form-control" style="width: 50%">
                                <option>10001-001</option>
                                <option>10002-002</option>
                                <option>10023-005</option>
                            </select>
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalStartTime">开始时间：</label>
                            <input type="text" style="width: 50%" class="form-control" id="modalStartTime" placeholder=""/>
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalEndTime">结束时间：</label>
                            <input type="text" style="width: 50%" class="form-control" id="modalEndTime" placeholder=""/>
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalPlaySort">播放排序：</label>
                            <input type="text" style="width: 20%" class="form-control" id="modalPlaySort" placeholder=""/>
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalPlayRemark">播放备注：</label>
                            <textarea class="form-control" style="width: 80%" rows="3"></textarea>
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