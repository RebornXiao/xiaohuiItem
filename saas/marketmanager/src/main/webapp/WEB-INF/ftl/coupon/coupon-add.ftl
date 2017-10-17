<div class="content-page">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">优惠券管理</a></li>
                            <li class="active"><a href="#">优惠券列表</a></li>
                        </ol>
                        <h4 class="page-title "><b>添加优惠券</b></h4>
                    </div>
                </div>
            </div>

            <div class="card-box">
                <div class="row">
                    <div class="col-sm-8">
                        <div class="advert_container">
                            <div>
                                <h5 class="page-title" style="padding-top: 20px"><b>基本信息</b></h5>
                                <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                                <div class="table-responsive advert_detail_table">
                                    <table class="table table-bordered">
                                        <tbody>
                                        <tr>
                                            <td style="background-color: #f9f9f9">优惠券类型</td>
                                            <td>
                                                <select id="couponSelect" class="form-control">
                                                    <option value="1">注册赠券</option>
                                                    <option value="2">注册赠券</option>
                                                    <option value="3">注册赠券</option>
                                                    <option value="4">注册赠券</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background-color: #f9f9f9">优惠券名称</td>
                                            <td><input type="text" class="form-control" id="couponName" placeholder="输入优惠券名称"/></td>
                                        </tr>
                                        <tr>
                                            <td style="background-color: #f9f9f9">优惠券标题</td>
                                            <td><input type="text" class="form-control" id="couponTitle" placeholder="输入优惠券标题"/></td>
                                        </tr>
                                        <tr>
                                            <td style="background-color: #f9f9f9">优惠券总张数</td>
                                            <td><input type="text" class="form-control" id="couponNumber" placeholder="输入优惠券名称"/></td>
                                        </tr>
                                        <tr>
                                            <td style="background-color: #f9f9f9">优惠金额</td>
                                            <td><input type="text" class="form-control" id="couponSum" placeholder="输入优惠券金额（单位：元）"/></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="advert_container">
                            <h5 class="page-title" style="padding-top: 20px"><b>联系信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive">
                                <table class="table table-bordered">
                                    <tbody>
                                        <tr>
                                            <td style="background-color: #f9f9f9">使用门槛</td>
                                            <td><input type="text" class="form-control" id="couponSum"/></td>
                                        </tr>
                                        <tr>
                                            <td style="background-color: #f9f9f9">每人限领</td>
                                            <td><input type="text" class="form-control" id="couponSum"/></td>
                                        </tr>
                                        <tr>
                                            <td style="background-color: #f9f9f9">可使用商品</td>
                                            <td><input type="text" class="form-control" id="couponGoods"/></td>
                                        </tr>
                                        <tr>
                                            <td style="background-color: #f9f9f9">有效时间</td>
                                            <td><input type="text" class="form-control" id="couponTime" placeholder="输入优惠券的使用时间（单位：天）"/></td>
                                        </tr>
                                        <tr>
                                            <td style="background-color: #f9f9f9">使用规则</td>
                                            <td><input type="text" class="form-control" id="couponRule" placeholder="输入使用优惠券的规则"/></td>
                                        </tr>
                                        <tr>
                                            <td style="background-color: #f9f9f9">备注</td>
                                            <td><input type="text" class="form-control" id="couponRemark" placeholder="输入备注信息"/></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="m-t-40">
                                <button id="saveBtn" type="button" class="btn waves-effect waves-light btn-primary col-md-2">确定</button>
                                <button id="backBtn" type="button" class="btn waves-effect waves-light btn-default col-md-2" onclick="history.go(-1);">返回</button>
                            </div>
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
            if(obj == "") {
                return false;
            } else {
                return true;
            }
        }
        $("#saveBtn").on('click',function () {
            $(this).button('loading').delay(500).queue(function() {
                $(this).button('reset'); //重置按钮
                $(this).dequeue();
                var arr = {
                    "name": $("#editSupplierName").val(),
                    "address": $("#editSupplierAddress").val(),
                    "select": $("#supplierSelect").val(),
                    "time": $("#editSupplierTime").val(),
                    "sname": $("#saleName").val(),
                    "phone": $("#salePhone").val()
                };
                var input1 = checkInput(arr.name);
                var input2 = checkInput(arr.address);
                var input3 = checkInput(arr.select);
                var input4 = checkInput(arr.time);
                var input5 = checkInput(arr.sname);
                var input6 = checkInput(arr.phone);
                if (input1 && input2 && input3 && input4 && input5 && input6) {
                    var url = "${base}/purchase/updateSupplier.do?id=${supplier.id}&supplierName="
                            + arr.name + "&address=" + arr.address + "&supplierType=" + arr.select + "&deliverPeriod="
                            + arr.time + "&salesmanName=" + arr.sname + "&phone=" + arr.phone;
                    $.post(url, function (data) {
                        //重新刷新
                        console.log(data);
                        if (data.code == "0") {
                            swal("提示", "更新成功", "success");
                            setTimeout(function () {
                                location.href = "${base}/purchase/supplierPage.do";
                            }, 1000);
                        } else {
                            swal("提示", data.msg, "error");
                        }
                    }, "json");
                } else {
                    swal("提示", "请检查表单是否有漏填项！", "info");
                }
            });
        });
    });
</script>
