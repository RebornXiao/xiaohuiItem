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
                <form class="form-inline" role="form" action="${base}/advert/adverts.do">
                    <div class="form-group m-l-15">
                        <label for="advertNavTitle">广告标题：</label>
                        <input type="text" class="form-control" id="advertNavTitle" placeholder="输入广告标题关键字" name="title">
                    </div>
                    <div class="form-group m-l-15">
                        <label for="advertNavTime">广告时长：</label>
                        <select id="advertNavTime" class="form-control" name="timeType">
                            <option value="-1">选择广告时长</option>
                            <option value="0">15以内</option>
                            <option value="1">16s-30s</option>
                            <option value="2">31s-60s</option>
                            <option value="3">61s-90s</option>
                            <option value="4">91s-120s</option>
                            <option value="5">121s以上</option>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <button type="submit" class="btn btn-primary" id="searchButton"><i class="fa fa-search"></i> 搜索</button>
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
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="advertInfoTable">
                        <#if (advertList?size > 0)>
                            <#list advertList as advert>
                            <tr>
                                <td>${advert_index +1}</td>
                                <td>${advert.title}</td>
                                <td>${advert.timeSize}</td>
                                <td>${advert.createTime}</td>
                                <td>
                                <#--<a id="deleteButton" data-toggle="modal" data-target="#deleteButton" data-id="${advert.advertID}">删除</a>-->
                                    <button id="deleBtn" type="button" data-target="#deleteButton"
                                            class="btn waves-effect waves-light btn-danger btn-sm"
                                            data_id="${advert.advertID?c}">删除
                                    </button>
                                </td>
                            </tr>
                            </#list>
                        <#else>
                        <tr>
                            <td colSpan="11" height="200px">
                                <p class="text-center" style="line-height: 200px">暂无任何数据</p>
                            </td>
                        </tr>
                        </#if>
                        </tbody>
                    </table>
                </div>
            </div>

            <!--分页-->
            <div class="row small_page">
                <div class="col-sm-12">
                <#include "../common/paginate.ftl">
                    <@paginate nowPage=pageIndex itemCount=count action="${base}/advert/adverts.do?title=test7&timeType=1"/>
                </div>
            </div>
            <!--/分页-->
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {

        function GetQueryString(name) {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)return  unescape(r[2]); return null;
        }
        console.log(GetQueryString("advertID"));
        var add_title = GetQueryString("advertID");
        $("#advertNavTitle").value=add_title;
        //搜索功能
        /*$("#searchButton").on('click', function () {
            var s_title = $("#advertNavTitle").val();
            var s_time = $("#advertNavTime").get(0).selectedIndex;//选中index
            if (s_title == "") {
                swal("请输入广告标题进行搜索!");
                return;
            }
            if (containSpecial.test(s_title)) {
                swal("包括了特殊符号，无法搜索!");
                return;
            }
            var actionUrl = "${base}/advert/adverts.do?title=" + s_title + "&timeType=" + s_time;
            location.href = actionUrl;
        });*/
        //上传广告
        $("#uploadAdvertButton").on('click', function () {
            var up_title = $("modalAdvertTitle").val();
            var up_time = $("modalAdvertTime").val();
            var up_remark = $("modalAdvertRemark").val();
            var up_fileUrl = $("modalAdvertFile").val();
            alert(up_fileUrl);
        });
        //删除广告
    <#if (advertList?size > 0)>
        $("#advertInfoTable").find('button[id=deleBtn]').each(function () {
            var that = this;
            $(this).on('click', function () {
                console.log($(that).attr("data_id"));
                $("#deleteButton").modal('show');
                $("#deleNoBtn").on('click',function () {
                    $("#deleteButton").modal('hide');
                });
                $("#deleOkBtn").on('click',function () {
                    $.get("${base}/advert/delAdvert.do?advertID=" + $(that).attr("data_id"), function(data) {
                        //重新刷新
                        if(data.code == "0") {
                            swal("提示", "删除成功", "success");
                            $("#deleteButton").modal('hide');
//                                location.reload();
                            location.href = "${base}/advert/adverts.do"
                        } else {
                            swal(data.msg);
                        }
                    }, "json");
                })
            });
        });
    </#if>
    });
</script>
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
                            <input type="text" class="form-control" id="modalAdvertTime" placeholder="输入广告时长"/>&nbsp;&nbsp;&nbsp;s (以秒为计算单位)
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalAdvertRemark">广告备注：</label>
                            <input type="text" style="width: 80%" class="form-control" id="modalAdvertRemark" placeholder="输入广告备注"/>
                        </div>
                    </form>
                    <form class="form-inline">
                        <div class="form-group" style="width: 100%">
                            <label for="modalAdvertFile">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件：</label>
                            <input id="modalAdvertFile" type="file" multiple="multiple" name="file"/>
                        <#--<button class="btn btn-primary" id="uploadAdvertButton" type="button" style="padding-left: 25px;padding-right: 25px">上传</button>-->
                        </div>
                    </form>
                <#--<form class="form-inline">-->
                <#--<div class="form-group style="width: 80%">-->
                <#--<div class="progress progress-striped active">-->
                <#--<div class="bar" style="width: 40%;"></div>-->
                <#--</div>-->
                <#--</div>-->
                <#--</form>-->
                </div>
            </div>
            <div class="modal-footer" style="text-align: center">
                <button type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" style="padding:10px 80px " id="uploadAdvertButton">确定</button>
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
                <button id="deleNoBtn" type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button id="deleOkBtn" type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
            </div>
        </div>
    </div>
</div>