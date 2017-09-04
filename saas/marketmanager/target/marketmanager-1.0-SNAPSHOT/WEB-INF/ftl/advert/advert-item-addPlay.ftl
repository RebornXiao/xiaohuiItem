<div class="content-page" style="background-color: #fff">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">广告端管理</a></li>
                            <li class="active"><a href="#">广告播放</a></li>
                        </ol>
                        <h4 class="page-title"><b>添加广告播放</b></h4>
                    </div>
                </div>
            </div>

            <div class="card-box">
                <form class="form-inline" role="form">
                    <div class="form-group m-l-15">
                        <label for="storeInfo">门店信息：</label>
                        <select id="storeSelect" class="form-control">
                            <option>选择门店</option>
                            <option>淘金店</option>
                            <option>杨琪店</option>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <label for="screenNum">屏幕编号：</label>
                        <select id="screenNumSelect" class="form-control">
                            <option>10001-001</option>
                            <option>10023-002</option>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#selectButton"><i class="fa fa-check"></i> 选择广告</button>
                    </div>
                </form>
            </div>

            <div class="row">
                <div class="col-sm-12">
                    <table class="table table-striped table-bordered">
                        <thead class="table_head">
                            <tr>
                                <th>序号</th>
                                <th>广告标题</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>播放顺序</th>
                                <th>播放备注</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody id="addPlayInfoTable">
                            <tr>
                                <td style="text-align: center">1</td>
                                <td style="text-align: center">xxx的广告</td>
                                <td style="text-align: center">2017-08-24 00:00:00</td>
                                <td style="text-align: center">2017-08-24 00:00:00</td>
                                <td style="text-align: center">1</td>
                                <td style="text-align: center">**********</td>
                                <td style="text-align: center">
                                    <a data-toggle="modal" data-target="#deleteButton">删除</a>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center">1</td>
                                <td style="text-align: center">xxx的广告</td>
                                <td style="text-align: center">2017-08-24 00:00:00</td>
                                <td style="text-align: center">2017-08-24 00:00:00</td>
                                <td style="text-align: center">1</td>
                                <td style="text-align: center">**********</td>
                                <td style="text-align: center">
                                    <a data-toggle="modal" data-target="#deleteButton">删除</a>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center">1</td>
                                <td style="text-align: center">xxx的广告</td>
                                <td style="text-align: center">2017-08-24 00:00:00</td>
                                <td style="text-align: center">2017-08-24 00:00:00</td>
                                <td style="text-align: center">1</td>
                                <td style="text-align: center">**********</td>
                                <td style="text-align: center">
                                    <a data-toggle="modal" data-target="#deleteButton">删除</a>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center">1</td>
                                <td style="text-align: center">xxx的广告</td>
                                <td style="text-align: center">2017-08-24 00:00:00</td>
                                <td style="text-align: center">2017-08-24 00:00:00</td>
                                <td style="text-align: center">1</td>
                                <td style="text-align: center">**********</td>
                                <td style="text-align: center">
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

<!--选择广告弹窗-->
<div class="modal fade" id="selectButton" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">选择广告</h4>
            </div>
            <div class="modal-body">
                <div class="modalAdvertStyle">
                    <div class="row">
                        <div class="col-sm-12">
                            <table class="table table-striped table-bordered">
                                <thead class="table_head">
                                    <tr>
                                        <th>
                                            <input type="checkbox" value=""/>全选
                                        </th>
                                        <th>广告标题</th>
                                        <th>广告时长</th>
                                        <th>备注</th>
                                    </tr>
                                </thead>
                                <tbody id="advertInfoTable">
                                    <tr>
                                        <td style="text-align: center">
                                            <input type="checkbox" value=""/>
                                        </td>
                                        <td style="text-align: center">xxx的广告</td>
                                        <td style="text-align: center">30s</td>
                                        <td style="text-align: center">*******</td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: center">
                                            <input type="checkbox" value=""/>
                                        </td>
                                        <td style="text-align: center">xxx的广告</td>
                                        <td style="text-align: center">30s</td>
                                        <td style="text-align: center">*******</td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: center">
                                            <input type="checkbox" value=""/>
                                        </td>
                                        <td style="text-align: center">xxx的广告</td>
                                        <td style="text-align: center">30s</td>
                                        <td style="text-align: center">*******</td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: center">
                                            <input type="checkbox" value=""/>
                                        </td>
                                        <td style="text-align: center">xxx的广告</td>
                                        <td style="text-align: center">30s</td>
                                        <td style="text-align: center">*******</td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: center">
                                            <input type="checkbox" value=""/>
                                        </td>
                                        <td style="text-align: center">xxx的广告</td>
                                        <td style="text-align: center">30s</td>
                                        <td style="text-align: center">*******</td>
                                    </tr>
                                    <tr>
                                        <td style="text-align: center">
                                            <input type="checkbox" value=""/>
                                        </td>
                                        <td style="text-align: center">xxx的广告</td>
                                        <td style="text-align: center">30s</td>
                                        <td style="text-align: center">*******</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
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
                <h4 class="modal-title" id="myModalLabel">删除广告播放</h4>
            </div>
            <div class="modal-body">
                <p style="text-align: center">确定要删除该广告播放吗？</p>
            </div>
            <div class="modal-footer" style="text-align: center">
                <button type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
            </div>
        </div>
    </div>
</div>