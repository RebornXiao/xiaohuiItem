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
                <a href="${base}/advert/advertScreens.do" class="btn btn-default btn-lg" role="button"><i class="fa fa-play-circle"></i> 广告播放</a>
                <a href="${base}/advert/screens.do" class="btn btn-primary btn-lg" role="button"><i class="fa fa-edit"></i> 屏幕配置</a>
            </div>
            <div class="card-box">
                <form class="form-inline" role="form" action="${base}/advert/screens.do">
                    <div class="form-group m-l-15">
                        <label for="screenNumber">屏幕编号：</label>
                        <input type="text" class="form-control" id="code" name="code" value="${code}" placeholder="输入编号">
                    </div>
                    <div class="form-group m-l-15">
                        <label for="storeInfo">门店信息：</label>
                        <select class="form-control" id="marketID" name="marketID" style="width:150px">
                            <option value="">全部店铺</option>
                        <#if markets?exists >
                            <#list markets as merket>
                                <option data_id="${market.id?c}" <#if marketID == market.id>
                                        selected </#if>>
                                ${market.name}
                                </option>
                            </#list>
                        </#if>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <label>屏幕分辨率：</label>
                        <select id="storeInfo_select" class="form-control" name="size">
                            <option value="">选择屏幕分辨率</option>
                            <option value="123">100*100</option>
                            <option value="1234">200*200</option>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
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
                        <#if (screens?size > 0)>
                            <#list screens as screen>
                            <tr>
                                <td>${screen.code}</td>
                                <td>${screen.size}</td>
                                <td>${screen.marketName}</td>
                                <td>${screen.advertCount}</td>
                                <td>
                                    <button id="lookBtn" type="button"
                                            class="btn btn-primary btn-sm"
                                            data_id="${screen.mac}">查看
                                    </button>
                                    <button id="deleBtn" type="button" data-target="#deleModel"
                                            class="btn btn-danger btn-sm"
                                            data_id="${screen.screenID?c}">删除
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
            <!--分页-->
            <div class="row small_page">
                <div class="col-sm-12">
                <#include "../common/paginate.ftl">
                    <@paginate nowPage=pageIndex itemCount=count action="${base}/advert/screens.do"/>
                </div>
            </div>
            <!--/分页-->
            <!--/分页-->
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        //添加屏幕
        $("#addButton").on('click', function () {
            var screen = {
                "marketID":$("#marketID2").val(),
                "marketName":$("#marketID2").find("option:selected").text(),
                "size":$("#size").val(),
                "code":$("#aCode").val(),
                "mac":$("#mac").val(),
                "requireTime":$("#requireTime").val(),
                "screenRemark":$("#screenRemark").val(),
            };

            $.ajax({
                type: "POST",
            <#--url:"${base}/advert/addAdvert.do?title=" +up_title+ "&timeSize=" +up_time+ "&remark=" +up_remark+ "&file=" +path,-->
                url: "${base}/advert/addScreen.do",
                data: screen,
                success: function (result) {
                    if ('yes' == result) {
                        swal("提示", "添加成功", "success");
                        setTimeout(function(){location.reload();},5000);
                    } else {
                        swal("提示", "添加失败", "success");
                        setTimeout(function(){location.reload();},5000);
                    }
                },
                error: function (result) {
                    alert("添加屏幕失败！");
                }
            });
        });
        //删除
    <#if (screens?size > 0)>
        $("#screenInfoListTable").find('button[id=deleBtn]').each(function () {
            var that = this;
            $(this).on('click', function () {
                //console.log($(that).attr("data_id"));
                $("#deleteButton").modal('show');
                $("#deleNoBtn").on('click',function () {
                    $("#deleteButton").modal('hide');
                });
                $("#deleOkBtn").on('click',function () {
                    $.post("${base}/advert/delScreen.do?screenID=" + $(that).attr("data_id"), function(data) {
                        //重新刷新
                        if(data.code == "0") {
                            swal("提示", "删除成功", "success");
                            setTimeout(function(){location.reload();},5000);
                        } else {
                            swal(data.msg);
                        }
                    }, "json");
                });
            });
        });
    </#if>

        //查看
    <#if (screens?size > 0)>
        $("#screenInfoListTable").find('button[id=lookBtn]').each(function () {
            var that = this;
            $(this).on('click', function () {
                location.href="${base}/advert/getScreenBy.do?mac="+$(that).attr("data_id");
            });
        });
    </#if>
    })
</script>

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
                    <form class="form-inline" id="addScreenForm" name="addScreenForm">
                        <div class="form-group" style="width: 100%">
                            <label for="modalStoreInfo">门店信息：</label>
                            <select class="form-control" id="marketID2" name="marketID2" style="width:150px">
                            <#if markets?exists >
                                <#list markets as merket>
                                    <option value="${merket.id?c}">${merket.name}</option>
                                </#list>
                            </#if>
                            </select>
                        </div>
                        <div class="form-group" style="width: 100%">
                            <label for="modalScreenLv">屏幕编号：</label>
                            <input type="text" class="form-control" id="aCode" name="aCode" placeholder="屏幕编号">
                        </div>
                        <div class="form-group" style="width: 100%">
                            <label for="modalScreenLv">屏幕分辨率：</label>
                            <input type="text" class="form-control" id="size" name="size" placeholder="屏幕分辨率">
                        </div>

                        <div class="form-group" style="width: 100%">
                            <label for="modalScreenMAC">屏幕MAC：</label>
                            <input type="text" style="width: 50%" class="form-control" id="mac" name="mac" placeholder="输入屏幕MAC"/>
                        </div>
                        <div class="form-group" style="width: 100%">
                            <fieldset disabled>
                                <label for="modalPlaySort">屏幕请求时间：</label>
                                <input type="text" style="width: 20%" class="form-control" id="requireTime" name="requireTime" value="300" placeholder="300"/> (默认300秒)
                            </fieldset>
                        </div>
                        <div class="form-group" style="width: 100%">
                            <label for="modalScreenRtime">屏幕备注：</label>
                            <textarea class="form-control" rows="3" id="screenRemark" name="screenRemark" style="width: 50%"></textarea>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer" style="text-align: center">
                <button type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="addButton" style="padding:10px 80px">确定</button>
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
                <button id="deleNoBtn" type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button id="deleOkBtn" type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
            </div>
        </div>
    </div>
</div>