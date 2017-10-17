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
                                                    <option value="">请选择优惠券类型</option>
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
                                            <td>
                                                <#--<div class="col-sm-2">-->
                                                    <#--<label class="radio-inline">-->
                                                        <#--<input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1">不限量-->
                                                    <#--</label>-->
                                                <#--</div>-->
                                                <#--<div class="col-sm-10">-->
                                                    <#--<label class="radio-inline col-sm-12">-->
                                                        <#--<input type="radio" class="pull-left" name="inlineRadioOptions" id="inlineRadio1" value="option2">-->
                                                        <#--<input type="text" class="form-inline" id="couponNumber" placeholder="输入优惠券总发行数量"/>-->
                                                    <#--</label>-->
                                                <#--</div>-->
                                                <form class="form-inline col-sm-12" id="form1">
                                                    <div class="form-group pull-left col-sm-2" style="margin-bottom: 0px">
                                                        <label class="radio-inline" style="color: #999">
                                                            <input type="radio" name="numberRadio" id="inlineRadio1" value="option1">不限量
                                                        </label>
                                                    </div>
                                                    <div class="form-group col-sm-10 pull-right" style="margin-bottom: 0px">
                                                        <label class="radio-menu-item">
                                                            <input type="radio" name="numberRadio" id="inlineRadio2" value="option2">
                                                        </label>
                                                        <div class="input-group col-sm-10">
                                                            <input type="text" class="form-control" id="amountInput" placeholder="输入优惠券总发行数量（单位：张）">
                                                        </div>
                                                    </div>
                                                </form>
                                            </td>
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
                    <div class="col-sm-10">
                        <div class="advert_container">
                            <h5 class="page-title" style="padding-top: 20px"><b>联系信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive advert_detail_table">
                                <table class="table table-bordered">
                                    <tbody>
                                        <tr>
                                            <td style="background-color: #f9f9f9">使用门槛</td>
                                            <td>
                                                <form class="form-inline col-sm-10" id="form2">
                                                    <div class="form-group pull-left col-sm-2" style="margin-bottom: 0px">
                                                        <label class="radio-inline" style="color: #999">
                                                            <input type="radio" name="sumRadio" id="inlineRadio3" value="option3">不限金额
                                                        </label>
                                                    </div>
                                                    <div class="form-group col-sm-8 pull-left" style="margin-bottom: 0px">
                                                        <label class="radio-menu-item">
                                                            <input type="radio" name="sumRadio" id="inlineRadio4" value="option4">
                                                        </label>
                                                        <div class="input-group col-sm-6">
                                                            <input type="text" class="form-control" id="sumInput" placeholder="购满多少可以使用（单位：元）">
                                                        </div>
                                                    </div>
                                                </form>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background-color: #f9f9f9">每人限领</td>
                                            <td><div class="form-inline"><text class="pull-left" style="margin-left:50px;color: #999">1&nbsp;张</text></div></td>
                                        </tr>
                                        <tr>
                                            <td style="background-color: #f9f9f9">可使用商品</td>
                                            <td>
                                                <form class="form-inline col-sm-12" id="form3">
                                                    <div class="form-group pull-left col-sm-2" style="margin-bottom: 0px">
                                                        <label class="radio-inline" style="color: #999">
                                                            <input type="radio" name="goodsRadio" id="inlineRadio5" value="option5">全部商品
                                                        </label>
                                                    </div>
                                                </form>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background-color: #f9f9f9">有效时间</td>
                                            <td>
                                                <form class="form-inline col-sm-12" id="form2">
                                                    <div class="form-group col-sm-7 pull-left" style="margin-bottom: 0px">
                                                        <label class="radio-inline" style="color: #999">
                                                            <input type="radio" name="timeRadio" id="inlineRadio6" value="option6">起止时间：
                                                        </label>
                                                        <div class="input-group">
                                                            <input id="startTime" type="text" class="form-control" name="beginTime" readonly>
                                                            <span class="input-group-addon bg-default"
                                                                  onClick="jeDate({dateCell:'#startTime',isTime:true,format:'YYYY-MM-DD'})"><i
                                                                    class="fa fa-calendar"></i></span>
                                                        </div>
                                                        <label style="color: #999">-</label>
                                                        <div class="input-group">
                                                            <input id="endTime" type="text" class="form-control" name="endTime" readonly>
                                                            <span class="input-group-addon bg-default"
                                                                  onClick="jeDate({dateCell:'#endTime',isTime:true,format:'YYYY-MM-DD'})"><i
                                                                    class="fa fa-calendar"></i></span>
                                                        </div>
                                                    </div>
                                                    <div class="form-group col-sm-5 pull-left" style="margin-bottom: 0px">
                                                        <label class="radio-menu-item">
                                                            <input type="radio" name="timeRadio" id="inlineRadio7" value="option7">
                                                        </label>
                                                        <div class="input-group">
                                                            <input type="text" class="form-control" id="dayInput" placeholder="输入有效天数(单位：天)">
                                                        </div>
                                                    </div>
                                                </form>
                                            </td>
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
                            <div class="m-t-40 pull-right">
                                <button id="backBtn" type="button" class="btn waves-effect waves-light btn-default pull-left" onclick="history.go(-1);">返回</button>
                                <button id="saveBtn" type="button" class="btn waves-effect waves-light btn-primary pull-right">确定</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${res}/assets/plugins/jedate/jedate.min.js"></script>
<script type="text/javascript">
    function checkInput (obj){//检查表单是否有空项，空格验证方法待加
        if(obj == "") {
            return false;
        } else {
            return true;
        }
    }
    $(document).ready(function () {
        $("#inlineRadio2").click(function(){
            $("#amountInput").focus();
        });
        $("#inlineRadio4").click(function(){
            $("#sumInput").focus();
        });
        $("#inlineRadio6").click(function(){

        });
        $("#inlineRadio7").click(function(){
            $("#dayInput").focus();
        });
        $("#saveBtn").on('click',function () {
            $(this).button('loading').delay(500).queue(function() {
                $(this).button('reset'); //重置按钮
                $(this).dequeue();
                var fs = '';//优惠券数量
                var arr = {
                    "v1": $("#couponSelect").val(),//优惠券类型
                    "v2": $("#couponName").val(),//优惠券名称
                    "v3": $("#couponTitle").val(),//优惠券标题
                    "v4": $("#couponSum").val(),//优惠券金额
                    "v5": $("#couponTime").val(),//有效时间
                    "v6": $("#couponRule").val(),//使用规则
                    "v7": $("#couponRemark").val()//备注
                };
                $('input[name="numberRadio"]:checked').each(function () {
                    var id= $(this).attr("id");
                    if(id=="inlineRadio1"){
                        fs=$("#"+id).val();
                    }else if(id=="inlineRadio2"){
                        fs=$("#amountInput").val();
                    }else{
                        fs='';
                    }
                });
                alert(fs);
//                alert(radio1);
//                alert(arr.v1);
//                alert(arr.v2);
//                alert(arr.v3);
//                alert(arr.v4);
//                alert(arr.v5);
//                alert(arr.v6);
                var input1 = checkInput(arr.v1);
                var input2 = checkInput(arr.v2);
                var input3 = checkInput(arr.v3);
                var input4 = checkInput(arr.v4);
                var input5 = checkInput(arr.v5);
                var input6 = checkInput(arr.v6);
                if (input1 && input2 && input3 && input4 && input5 && input6) {
                    <#--var url = "${base}/coupon/updateCoupon.do?id=${supplier.id}&supplierName="-->
                            <#--+ arr.name + "&address=" + arr.address + "&supplierType=" + arr.select + "&deliverPeriod="-->
                            <#--+ arr.time + "&salesmanName=" + arr.sname + "&phone=" + arr.phone;-->
                    <#--$.post(url, function (data) {-->
                        <#--//重新刷新-->
                        <#--console.log(data);-->
                        <#--if (data.code == "0") {-->
                            <#--swal("提示", "添加成功", "success");-->
                            <#--setTimeout(function () {-->
                                <#--location.href = "${base}/coupon/couponPage.do";-->
                            <#--}, 1000);-->
                        <#--} else {-->
                            <#--swal("提示", data.msg, "error");-->
                        <#--}-->
                    <#--}, "json");-->
                <#--} else {-->
                    <#--swal("提示", "请检查表单是否有漏填项！", "info");-->
                }
            });
        });
    });
</script>
