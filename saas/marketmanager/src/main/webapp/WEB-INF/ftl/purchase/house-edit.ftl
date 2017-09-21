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
                                        <input type="text" id="houseCode" class="form-control" placeholder="输入仓库编码">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">仓库名称：</label>
                                    <div class="col-md-8">
                                        <input type="text" id="houseName" class="form-control" placeholder="输入仓库名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">仓库地址：</label>
                                    <div class="col-md-8">
                                        <input type="text" id="houseAddress" class="form-control" placeholder="输入仓库地址">
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
                                        <input type="text" id="keyInput" class="form-control" placeholder="请输入与WMS对接Key">
                                    </div>
                                </div>
                                <div class="form-group m-t-20">
                                    <div class="col-md-4">
                                    </div>
                                    <button id="saveBtn" type="button"
                                            class="btn waves-effect waves-light btn-primary col-md-2">确定
                                    </button>
                                    <button id="backBtn" type="button" onclick="history.go(-1);"
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
        function checkInput (obj){//检查表单是否有空项，空格验证方法待加
            if(obj == "") {return false;} else {return true;}
        }
        $("#saveBtn").on('click', function () {//确定
            var arr = {
                "code":$("#houseCode").val(),
                "name":$("#houseName").val(),
                "address":$("#houseAddress").val(),
                "remark":$("#remark").val(),
                "key":$("#keyInput").val(),
            };
            var input1 = checkInput(arr.code);
            var input2 = checkInput(arr.name);
            var input3 = checkInput(arr.address);
            var input4 = checkInput(arr.key);
            if(input1&&input2&&input3&&input4){
                var url="${base}/purchase/updateHouse.do?id=${house.id}&warehouseCode="
                        +arr.code+ "&warehouseName=" +arr.name+ "&address=" +arr.address+ "&remark="
                        +arr.remark+ "&key=" +arr.key;
                $.ajax({
                    type: "POST",
                    url: "${base}/advert/addScreenAdvert.do",
                    data: arr,
                    success: function (data) {
                        console.log(data);
                        if (data.code =='0') {
                            swal("提示", "更新成功", "success");
                            setTimeout(function(){location.href="${base}/purchase/warehousePage.do"},1000);
                        } else {
                            swal("提示", "更新失败", "error");
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
    });
</script>
