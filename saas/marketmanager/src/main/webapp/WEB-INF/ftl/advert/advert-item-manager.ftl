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
                        <label for="l_storeInfo">门店信息:</label>
                        <select id="storeInfo" class="form-control">
                            <option>选择门店</option>
                            <option>杨琪店杨琪店杨琪店</option>
                            <option>淘金店</option>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <label for="l_screenNum">屏幕编号:</label>
                        <input type="text" class="form-control" id="screenNum" placeholder="输入编号">
                    </div>
                    <div class="form-group m-l-15">
                        <label for="l_advertNavTitle">广告标题:</label>
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
                        <label>是否下载：</label>
                        <button type="button" class="btn btn-primary">全部</button>
                        <button type="button" class="btn btn-default">是</button>
                        <button type="button" class="btn btn-default">否</button>
                    </div>
                    <div class="form-group m-l-15 m-t-20">
                        <label>播放状态：</label>
                        <button type="button" class="btn btn-primary">全部</button>
                        <button type="button" class="btn btn-default">待播放</button>
                        <button type="button" class="btn btn-default">播放中</button>
                        <button type="button" class="btn btn-default">已停止</button>
                        <button type="button" class="btn btn-default">已移除</button>
                    </div>
                    <div class="form-group  m-l-25 m-t-20">
                        <button type="button" class="btn btn-primary" id="searchBtn"><i class="fa fa-search"></i> 搜索</button>
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
                        <#--<#if (advertScreens?size > 0)>-->
                            <#--<#list advertScreens as advert>-->
                            <#--<tr>-->
                                <#--<td>${advert_index +1}</td>-->
                                <#--<td>${advert.title}</td>-->
                                <#--<td>${advert.timeSize}</td>-->
                                <#--<td>${advert.createTime}</td>-->
                                <#--<td>-->
                                    <#--<button id="deleBtn" type="button" data-target="#deleteButton"-->
                                            <#--class="btn waves-effect waves-light btn-danger btn-sm"-->
                                            <#--a_id="${advert.advertID?c}" s_id="${advert.screenID?c}">删除-->
                                    <#--</button>-->
                                <#--</td>-->
                            <#--</tr>-->
                            <#--</#list>-->
                        <#--<#else>-->
                        <#--<tr>-->
                            <#--<td colSpan="11" height="200px">-->
                                <#--<p class="text-center" style="line-height: 200px">暂无任何数据</p>-->
                            <#--</td>-->
                        <#--</tr>-->
                        <#--</#if>-->
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
                                <button id="linkBtn" type="button" data-target="#deleteButton" class="btn btn-primary btn-sm" data_id="}">查看</button>
                                <button id="editBtn" type="button" data-toggle="model" data-target="#editBtn" class="btn btn-primary btn-sm" data_id="}">编辑</button>
                                <button id="deleBtn" type="button" data-toggle="model" data-target="#deleBtn" class="btn btn-primary btn-sm" data_id="}">移除</button>
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
                </div>
            </div>
            <!--/分页-->

        </div>
    </div>
</div>
<script type="text/javascript" src="${res}/assets/plugins/jedate/jedate.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //取url参数给表单赋值
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)return  unescape(r[2]); return null;
        }
        var add_title = GetQueryString("title");
        var add_timeType = GetQueryString("timeType");
        document.getElementById("advertNavTitle").value=add_title;

        //搜索功能
        $("#searchBtn").on('click', function () {
            var str = " 00:00:00";
            var s_store = $("#storeInfo").get(0).selectedIndex;//选中index
            var s_number = $("#screenNum").val();//屏幕编号
            var s_title = $("#advertNavTitle").val();//广告标题
            var stime = $("#startTime").val();//开始时间
                stime = stime.substring(0, 10);
                stime = stime + str;
            var etime = $("#endTime").val();//结束时间
                etime = etime.substring(0, 10);
                etime = etime + str;
            var s_isDown = '';//下载状态
            var s_playStatus = '';//播放状态
            alert(stime);
            var url ="${base}/advert/advertScreens.do?marketID="+s_store+"&code="+s_number+"&title="+s_title+"&beginTime="+stime+"&endTime="+etime+"&isDown="+s_isDown+"&playStatus="+s_playStatus;
            $.get(url, function(data) {
                if(data.code == "0") {
                    location.reload();
                } else {
                    swal(data.msg);
                }
            }, "json");
        });

        //移除功能
        <#if (advertScreens?size > 0)>
            $("#advertInfoTable").find('button[id=deleBtn]').each(function () {
                var that = this;
                $(this).on('click', function () {
                    console.log($(that).attr("a_id"));
                    $("#deleBtn").modal('show');
                    $("#deleNoBtn").on('click',function () {
                        $("#deleBtn").modal('hide');
                    });
                    $("#deleOkBtn").on('click',function () {
                        $.post("${base}/advert/delScreenAdvert.do?advertID=" +$(that).attr("a_id")+ "&screenID=" +$(that).attr("s_id"), function(data) {
                            //重新刷新
                            if(data.code == "0") {
                                swal("提示", "删除成功", "success");
                                $("#deleBtn").modal('hide');
                                    location.reload();
                            } else {
                                swal(data.msg);
                            }
                        }, "json");
                    });
                });
            });
        </#if>
        $("#adScreenEditBtn").on('click', function () {//屏幕配置
            location.href = "${base}/advert/adverts/screen.do";
        });
        $("#addPlayButton").on('click', function () {//添加广告播放
            location.href = "${base}/advert/adverts/addPlay.do";
        });
    });
</script>

<!--编辑弹窗-->
<div class="modal fade" id="editBtn" tabindex="-1" role="dialog">
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
<div class="modal fade" id="deleBtn" tabindex="-1" role="dialog">
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