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
                            <#list markets as market>
                                <option value="${market.id?c}">${market.name}</option>
                            </#list>
                        </#if>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <label>屏幕分辨率：</label>
                        <select id="storeInfo_select" class="form-control" name="size">
                            <option value="">选择屏幕分辨率</option>
                            <option value="1920*1080">1920*1080</option>
                            <option value="1680*1050">1680*1050</option>
                            <option value="1600*900">1600*900</option>
                            <option value="1440*900">1440*900</option>
                            <option value="1400*1050">1400*1050</option>
                            <option value="1366*768">1366*768</option>
                            <option value="1360*768">1360*768</option>
                            <option value="1280*1024">1280*1024</option>
                            <option value="1280*960">1280*960</option>
                            <option value="1280*800">1280*800</option>
                            <option value="1280*768">1280*768</option>
                            <option value="1280*720">1280*720</option>
                            <option value="1280*600">1280*600</option>
                            <option value="1152*864">1152*864</option>
                            <option value="1024*768">1024*768</option>
                            <option value="800*600">800*600</option>
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
                            <tr id="tr_${screen_index}">
                                <td>${screen.code}</td>
                                <td>${screen.screenSize}</td>
                                <td>${screen.marketName}</td>
                                <td>${screen.advertCount}</td>
                                <td>
                                    <button id="lookBtn" type="button"
                                            class="btn btn-primary btn-sm"
                                            data_id="${screen.mac}">查看
                                    </button>
                                    <button id="deleBtn" type="button" data-target="#deleModel"
                                            class="btn btn-danger btn-sm"
                                            market_id="${screen.marketId?c}"  data_id="${screen.screenID?c}">删除
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
        //鼠标经过效果
        $("tr[id^='tr_']").hover(
            function(){ // onmouseover
                $(this).css("background-color", "#FFFFBF"); // 设置背景颜色
            },
            function(){ // onmouseout
                // 代表当前行对应的checkbox没有选中
                if (!$(this.id.replace("tr_", "")).attr("checked")){
                    $(this).css("background-color", "#FFFFFF"); // 还原背景颜色
                }
            }
        );
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
                    $.post("${base}/advert/delScreen.do?screenID=" + $(that).attr("data_id")+"&marketID=" + $(that).attr("market_id"), function(data) {
                        //重新刷新
                        if(data.code == "0") {
                            $("#deleteButton").modal('hide');
                            swal("提示", "删除成功", "success");
                            setTimeout(function(){location.href = "${base}/advert/screens.do";},500);
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
                    <form class="form-horizontal" id="addScreenForm" name="addScreenForm">
                        <div class="form-group">
                            <label class="col-md-4 control-label">门店信息：</label>
                            <div class="col-md-4">
                                <select class="form-control" id="marketID2" name="marketID2">
                                <#if markets?exists >
                                    <#list markets as merket>
                                        <option value="${merket.id?c}">${merket.name}</option>
                                    </#list>
                                </#if>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">屏幕编号：</label>
                            <div class="col-md-4">
                                <input type="text" class="form-control" id="aCode" name="aCode" placeholder="屏幕编号">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">屏幕分辨率：</label>
                            <div class="col-md-4">
                                <input type="text" class="form-control" id="size" name="size" placeholder="屏幕分辨率">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">屏幕MAC：</label>
                            <div class="col-md-4">
                                <input type="text" class="form-control" id="mac" name="mac" placeholder="输入屏幕MAC"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <fieldset disabled>
                            <label class="col-md-4 control-label">屏幕请求时间：</label>
                            <div class="col-md-2">
                                <input type="text" class="form-control" id="requireTime" name="requireTime" value="300"/>
                            </div>
                            </fieldset>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">屏幕备注：</label>
                            <div class="col-md-6">
                                <textarea class="form-control" rows="4" id="screenRemark" name="screenRemark"></textarea>
                            </div>
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