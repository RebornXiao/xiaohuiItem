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
                <a href="${base}/advert/admanager.do" class="btn btn-default btn-lg" role="button"><i class="fa fa-play-circle"></i> 广告播放</a>
                <a href="${base}/advert/adverts/screen.do" class="btn btn-primary btn-lg" role="button"><i class="fa fa-edit"></i> 屏幕配置</a>
            </div>
            <div class="card-box">
                <form class="form-inline" role="form">
                    <div class="form-group m-l-15">
                        <label for="screenNumber">屏幕编号：</label>
                        <input type="text" class="form-control" id="advertNavTitle" placeholder="输入编号">
                    </div>
                    <div class="form-group m-l-15">
                        <label for="storeInfo">门店信息：</label>
                        <select id="storeInfo_select" class="form-control">
                            <option>选择门店</option>
                            <option>杨琪店杨琪店杨琪店</option>
                            <option>淘金店</option>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <label>屏幕分辨率：</label>
                        <select id="storeInfo_select" class="form-control">
                            <option>选择屏幕分辨率</option>
                            <option>100*100</option>
                            <option>200*200</option>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <button type="button" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
                    </div>
                </form>
            </div>

            <button type="button" class="btn btn-primary pull-right m-b-15" data-toggle="modal" data-target="#addScreenButton"><i class="fa fa-pencil"></i> 添加屏幕</button>

            <div class="row">
                <div class="col-sm-12">
                    <table class="table table-striped table-bordered">
                        <thead class="table_head">
                        <tr>
                            <th>编号</th>
                            <th>屏幕分辨率</th>
                            <th>门店信息</th>
                            <th>正在投放广告数</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="screenInfoListTable">
                        <tr>
                            <td style="text-align: center">10001-01</td>
                            <td style="text-align: center">100*100</td>
                            <td style="text-align: center">杨箕店</td>
                            <td style="text-align: center">5</td>
                            <td style="text-align: center">
                                <a href="${base}/advert/adverts/screenConfig.do">查看</a>
                                <a data-toggle="modal" data-target="#deleteButton">删除</a>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center">10001-02</td>
                            <td style="text-align: center">200*500</td>
                            <td style="text-align: center">淘金店</td>
                            <td style="text-align: center">12</td>
                            <td style="text-align: center">
                                <a href="${base}/advert/adverts/screenConfig.do">查看</a>
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

<!--添加屏幕-->
<div class="modal fade" id="addScreenButton" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加屏幕</h4>
            </div>
            <div class="modal-body">
                <div class="modalAdvertStyle">
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
                            <label for="modalScreenLv">屏幕分辨率：</label>
                            <select id="modalScreenLv" class="form-control" style="width: 50%">
                                <option>100*100</option>
                                <option>200*200</option>
                                <option>300*300</option>
                            </select>
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalScreenMAC">屏幕MAC：</label>
                            <input type="text" style="width: 50%" class="form-control" id="modalStartTime" placeholder="输入屏幕MAC"/>
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <fieldset disabled>
                                <label for="modalPlaySort">屏幕请求时间：</label>
                                <input type="text" style="width: 20%" class="form-control" id="modalPlaySort" placeholder="30"/> (默认30秒)
                            </fieldset>
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalScreenRtime">屏幕备注：</label>
                            <textarea class="form-control" rows="3" style="width: 50%"></textarea>
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
                <h4 class="modal-title" id="myModalLabel">删除屏幕</h4>
            </div>
            <div class="modal-body">
                <p style="text-align: center">确定要删除该屏幕吗？</p>
            </div>
            <div class="modal-footer" style="text-align: center">
                <button type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
            </div>
        </div>
    </div>
</div>