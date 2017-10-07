<div class="content-page">
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

            <div class="row">
                <div class="col-sm-12">
                    <div class="row m-t-30">
                        <div class="col-md-8">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label class="col-md-4 control-label">门店信息：</label>
                                    <div class="col-md-4">
                                        <select id="storeSelect" class="form-control">
                                        <#if markets?exists >
                                            <#list markets as market>
                                                <option value="${market.id?c}">${market.name}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">屏幕编号：</label>
                                    <div class="col-md-4">
                                        <select id="screenNumSelect" class="form-control">
                                            <#--<option value="000">该门店没有配置屏幕编号</option>-->
                                        <#if screens?exists >
                                            <#list screens as screen>
                                                <option value="${screen.code}">${screen.code}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">广告标题：</label>
                                    <div class="col-md-4">
                                        <select id="advertTitle" class="form-control">
                                        <#if adverts?exists >
                                            <#list adverts as advert>
                                                <option value="${advert.advertID?c}">${advert.title}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">开始时间：</label>
                                    <div class="col-md-4">
                                        <input type="text" id="startTime" class="form-control">
                                    </div>
                                    <div class="col-md-1">
                                        <span class="input-group-addon bg-default"
                                              onClick="jeDate({dateCell:'#startTime',isTime:true,format:'YYYY-MM-DD 00:00'})">
                                            <i class="fa fa-calendar"></i>
                                        </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">结束时间：</label>
                                    <div class="col-md-4">
                                        <input type="text" id="endTime" class="form-control">
                                    </div>
                                    <div class="col-md-1">
                                        <span class="input-group-addon bg-default"
                                              onClick="jeDate({dateCell:'#endTime',isTime:true,format:'YYYY-MM-DD 00:00'})">
                                            <i class="fa fa-calendar"></i>
                                        </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">排序：</label>
                                    <div class="col-md-2">
                                        <input type="text" id="sort" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">播放备注：</label>
                                    <div class="col-lg-8">
                                        <textarea class="form-control" rows="6" id="playRemark" name="screenRemark"></textarea>
                                    </div>
                                </div>
                                <div class="form-group m-t-20">
                                    <div class="col-md-4">
                                    </div>
                                    <button id="saveBtn" type="button"
                                            class="btn waves-effect waves-light btn-primary col-md-2">确定
                                    </button>
                                    <button id="backBtn" type="button"
                                            class="btn waves-effect waves-light btn-default col-md-2">返回
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${res}/assets/plugins/jedate/jedate.min.js"></script>
<script type="text/javascript">
    function checkInput(obj) {
        if (obj == "") {
            return false;
        } else {
            return true;
        }
    }
    $(document).ready(function () {
        $("#saveBtn").on('click', function () {//确定
            $(this).button('loading').delay(500).queue(function() {
                $(this).button('reset'); //重置按钮
                $(this).dequeue();
                var adverts = {
                    "marketID": $("#storeSelect").val(),
                    "screenID": $("#screenNumSelect").val(),
                    "advertID": $("#advertTitle").val(),
                    "beginTime": $("#startTime").val(),
                    "endTime": $("#endTime").val(),
                    "playOrder": $("#sort").val(),
                    "remark": $("#playRemark").val(),
                };
                console.log(adverts.screenID);
                var input1 = checkInput(adverts.marketID);
                var input2 = checkInput(adverts.screenID);
                var input3 = checkInput(adverts.advertID);
                var input4 = checkInput(adverts.beginTime);
                var input5 = checkInput(adverts.endTime);
                var input6 = checkInput(adverts.playOrder);
                var input7 = checkInput(adverts.remark);
                if (input1 && input2 && input3 && input4 && input5 && input6) {
                    $.ajax({
                        type: "POST",
                        url: "${base}/advert/addScreenAdvert.do",
                        data: adverts,
                        success: function (data) {
                            console.log(data);
                            if (data.code == '0') {
                                swal("提示", "添加成功", "success");
                                setTimeout(function () {
                                    location.href = "${base}/advert/advertScreens.do"
                                }, 1000);
                            } else {
                                swal("提示", "添加失败", "error");
                            }
                        },
                        error: function (data) {
                            swal("提示", "服务器出错", "info");
                        }
                    });
                } else {
                    swal("提示", "请检查表单是否有漏填项！", "info");
                }
            });
        });
        //下拉列表级联
        var select1 = $("#storeSelect");
        var select2 = $("#screenNumSelect");
        select1.change(function () {
            var infoValue = select1.val();
            if(infoValue !=""){//当门店值不为空时
                if(!select1.data(infoValue)){//不在缓冲区中,需要向服务器请求
                    $.post("${base}/advert/getScreenListBy.do?marketId="+infoValue,function(data) {
                        var screenItem = data.response.data;
                        if((screenItem.length != 0)&& data) {//返回的数据不为空
                            select2.html("");
                            for(var i = 0; i < screenItem.length; i++) {
                                $("<option value ='" + screenItem[i].code + "'> " + screenItem[i].code + "</option>").appendTo(select2);
                            }
                            select2.parent().show();
                            select2.next().show();
                        } else {//返回的数据为空
                            select2.html("");
                            $("<option value='000'>该门店没有配置屏幕编号</option>").appendTo(select2);
                        }
                        select2.data(infoValue, data);
                    }, "json");
                }else{//在缓冲区
                    var data = select1.data(infoValue);
                    if(data.length != 0) {//返回的数据不为空
                        select2.html("");
                        $("<option value=''>请选择屏幕编号</option>").appendTo(select2);
                        for(var i = 0; i < data.length; i++) {
                            $("<option value =' " + data[i] + " '> " + data[i] + "</option>").appendTo(select2);
                        }
                        select2.parent().show();
                        select1.next().show();
                    } else {//返回的数据为空
                        select2.parent().hide();
                        select1.next().hide();
                    }
                }
            }else{//门店值为空的情况，隐藏第二个下拉框
                select2.parent().hide();
                select1.next().hide();
            }
        });
        $("#backBtn").on('click',function () {//返回
            history.go(-1);
        });
    });
</script>

<!--删除弹窗-->
<div class="modal fade" id="deleModel" tabindex="-1" role="dialog">
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
                <button type="button" id="deleNoBtn" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button type="button" id="deleYesBtn" class="btn btn-primary" style="padding:10px 80px">确定</button>
            </div>
        </div>
    </div>
</div>