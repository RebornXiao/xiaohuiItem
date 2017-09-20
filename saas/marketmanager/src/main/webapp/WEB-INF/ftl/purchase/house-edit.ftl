<div class="content-page">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">采购端管理</a></li>
                            <li class="active"><a href="#">供应商列表</a></li>
                        </ol>
                        <h4 class="page-title"><b>编辑仓库</b></h4>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-12">
                    <div class="row m-t-30">
                        <div class="col-md-8">
                            <h5 class="page-title" style="padding-top: 20px"><b>基本信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label class="col-md-4 control-label">仓库编码：</label>
                                    <div class="col-md-8">
                                        <input type="text" id="houseCode" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">仓库名称：</label>
                                    <div class="col-md-8">
                                        <input type="text" id="houseName" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">仓库地址：</label>
                                    <div class="col-md-8">
                                        <input type="text" id="houseAddress" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">备注：</label>
                                    <div class="col-md-8">
                                        <textarea class="form-control" rows="6" id="remark"></textarea>
                                    </div>
                                </div>
                                <h5 class="page-title" style="padding-top: 20px"><b>对接信息</b></h5>
                                <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">Key：</label>
                                    <div class="col-md-8">
                                        <input type="text" id="keyInput" class="form-control">
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
<script type="text/javascript">
    $(document).ready(function () {
        $("#saveBtn").on('click', function () {//确定
            var adverts = {
                "marketID":$("#storeSelect").val(),
                "screenID":$("#screenNumSelect").val(),
                "advertID":$("#advertTitle").val(),
                "beginTime":$("#startTime").val(),
                "endTime":$("#endTime").val(),
                "playOrder":$("#sort").val(),
                "remark":$("#playRemark").val(),
            };
            console.log(adverts.screenID);
            var input1 = checkInput(adverts.marketID);
            var input2 = checkInput(adverts.screenID);
            var input3 = checkInput(adverts.advertID);
            var input4 = checkInput(adverts.beginTime);
            var input5 = checkInput(adverts.endTime);
            var input6 = checkInput(adverts.playOrder);
            var input7 = checkInput(adverts.remark);
            function checkInput(obj) {
                if(obj == "") {return false;} else {return true;}
            }
            if(input1&&input2&&input3&&input4&&input5&&input6&&input7){
                $.ajax({
                    type: "POST",
                    url: "${base}/advert/addScreenAdvert.do",
                    data: adverts,
                    success: function (data) {
                        console.log(data);
                        if (data.code =='0') {
                            swal("提示", "添加成功", "success");
                            setTimeout(function(){location.href="${base}/advert/advertScreens.do"},1000);
                        } else {
                            swal("提示", "添加失败", "error");
                        }
                    },
                    error: function (data) {
                        swal("提示", "服务器出错", "info");
                    }
                });
            }else{
                swal("提示", "请检查表单是否有漏填项！", "info");
            }

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
                                $("<option value ='" + screenItem[i].screenID + "'> " + screenItem[i].code + "</option>").appendTo(select2);
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
