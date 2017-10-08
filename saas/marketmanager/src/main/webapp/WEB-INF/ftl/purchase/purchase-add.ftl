<div class="content-page">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">采购端管理</a></li>
                            <li class="active"><a href="#">采购单列表</a></li>
                        </ol>
                        <h4 class="page-title "><b>新建采购单</b></h4>
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-primary" style="padding-right:0px 20px;margin-bottom: 22px;" onclick="javascript:history.go(-1);"><i class="fa fa-backward"></i> 返回列表</button>
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
                                            <td style="background-color: #f9f9f9">仓库名称</td>
                                            <td>
                                                <select class="form-control" id="houseSelect">
                                                    <option value="">请选择仓库</option>
                                                    <#if warehouseItem?exists >
                                                        <#list warehouseItem as warehouse>
                                                            <option value="${warehouse.warehouseCode}">${warehouse.warehouseName}</option>
                                                        </#list>
                                                    </#if>
                                                </select>
                                            </td>
                                            <td style="background-color: #f9f9f9">供应商名称</td>
                                            <td>
                                                <select class="form-control" id="supplierSelect">
                                                    <option value="">请选择供应商</option>
                                                    <#if supperlierItem?exists >
                                                        <#list supperlierItem as supplier>
                                                            <option value="${supplier.id?c}">${supplier.supplierName}</option>
                                                        </#list>
                                                    </#if>
                                                </select>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <div class="advert_container">
                            <ol class="breadcrumb pull-right">
                                <li><button class="btn-primary" onclick="addTr2('tab',-1);">添加商品</button></li>
                            </ol>
                            <h5 class="page-title" style="padding-top: 20px"><b>采购信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive advert_detail_table">
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th style="background-color: #f9f9f9">商品分类</th>
                                            <th style="background-color: #f9f9f9">商品名称</th>
                                            <th style="background-color: #f9f9f9">条形码</th>
                                            <th style="background-color: #f9f9f9">采购日期</th>
                                            <th style="background-color: #f9f9f9">采购数量</th>
                                            <th style="background-color: #f9f9f9">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tab">
                                    <tr id="tr_1">
                                        <td>
                                            <select class="form-control" onchange="changeSelect(this)">
                                                <option value="">请选择商品分类</option>
                                                <#if (itemTypes?size > 0)>
                                                    <#list itemTypes as iType>
                                                        <option value=${iType.id?c} <#if item?exists && iType.id == item.typeId>selected </#if>><#if iType.parentId == 0>${iType.title}
                                                        <#else>&nbsp;&nbsp;&nbsp;&nbsp;${iType.title}</#if></option>
                                                    </#list>
                                                <#else><option value='0'>找不到商品</option>
                                                </#if>
                                            </select>
                                        </td>
                                        <td>
                                            <select class="form-control" onchange="changeCode(this)">
                                                <option value="">请选择商品名称</option>
                                            </select>
                                        </td>
                                        <td>
                                            <fieldset disabled>
                                            <input type="text" class="form-control">
                                            </fieldset>
                                        </td>
                                        <td>
                                            <div class="input-group">
                                                <input id="endTime" type="text" class="form-control">
                                                <span class="input-group-addon bg-default" onClick="jeDate({dateCell:'#endTime',isTime:true,format:'YYYY-MM-DD'})">
                                                    <i class="fa fa-calendar"></i>
                                                </span>
                                            </div>
                                        </td>
                                        <td>
                                            <input type="text" class="form-control" placeholder="输入采购数量">
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-default" disabled="disabled">不可删</button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="m-t-40">
                                <button id="saveBtn" type="button" class="btn btn-primary col-md-1 statusBtn">提交</button>
                                <button id="backBtn" type="button" class="btn btn-warning statusBtn">保存草稿</button>
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
    function addTr2(tab, row){

        var trHtml="<tr><td>" +
                "<select class='form-control' onchange='changeSelect(this)'>" +
                "<option value=''>请选择商品分类</option>" +
                "<#if (itemTypes?size > 0)><#list itemTypes as iType> <option value='${iType.id?c}' <#if item?exists && iType.id == item.typeId>selected </#if>>" +
                "<#if iType.parentId == 0>${iType.title}<#else>&nbsp;&nbsp;&nbsp;&nbsp;${iType.title}</#if></option></#list><#else><option value='0'>无</option></#if>" +
                "</select>" +
                "</td>" +
                "<td><select class='form-control' onchange='changeCode(this)'><option value=''>请选择商品名称</option></select></td>" +
                "<td><fieldset disabled><input type='text' class='form-control'></fieldset></td>" +
                "<td>" +
                "<div class='input-group'><input id='endTime' type='text' class='form-control'>" +
                "<span class='input-group-addon bg-default' onClick=\"jeDate({dateCell:\'#endTime\',isTime:true,format:\'YYYY-MM-DD\'})\"><i class='fa fa-calendar'></i></span>" +
                "</div>" +
                "</td>" +
                "<td><input type='text' class='form-control' placeholder='输入采购数量'></td>" +
                "<td><button class='btn-danger' onclick='deleTr(this);'>删除</button></td>" +
                "</tr>";
        setTimeout(function(){
            var len = $('#tab tr').length;
            $("#tab").children().last().attr("id",'tr_' + len);
        },100);
        addTr(tab, row, trHtml);
    }
    function addTr(tab, row, trHtml){
        //获取table最后一行 $("#tab tr:last")
        //获取table第一行 $("#tab tr").eq(0)
        //获取table倒数第二行 $("#tab tr").eq(-2)
//        var $tr=$("#"+tab+" tr").eq(row);
        var $tr=$("#tab");
        if($tr.size()==0){
            swal("指定的table id或行数不存在！");
            return;
        }
//        $tr.after(trHtml);
        $tr.append(trHtml);
    }
    function deleTr(nowTr) {
        $(nowTr).parent().parent().remove();
    }
    function changeSelect($s1) {
        var _id = $($s1).val();
        var $tr = $($s1).parent().parent();
        var $td = $tr.find("td:eq(1)");
        var $s2 = $td.find('select');
        var $in = $tr.find("td:eq(2)").find('input');
        if(_id !=""){//当商品分类不为空
//            if(!$s1.data(_id)){//不在缓冲区中,需要向服务器请求
//            var _url = "http://192.168.1.166:17777/item/item/getItemTemplateIdAndNames.do?itemTypeId=";
            var url = "${base}/purchase/itemsByitemTypeId.do?itemTypeId=";
            $.get(url+_id,function(data) {
                console.log(data);
                var _obj = data.response.datas;
                if(data.code==0) {//返回的数据不为空
                    $s2.empty();//清空第二个下拉列表
                    $s2.html("");
                    $in.val("");//清空条形码输入框
                    $("<option value=''>请选择商品名称</option>").appendTo($s2);
                    for(var i = 0; i < _obj.length; i++) {
                        $("<option value ='" + _obj[i].id + "' data-id ='" + _obj[i].barcode + "'> " + _obj[i].name + "</option>").appendTo($s2);
                    }
                } else {//返回的数据为空
                    $s2.empty();
                    $s2.html("");
                    $("<option value=''>该分类下没有商品</option>").appendTo($s2);
                    $in.val("");
                }
//                    $s2.data(_id, data);
            }, "json");
//            }else{//在缓冲区
//                var data = $s1.data(_id);
//                if(data.length != 0) {//返回的数据不为空
//                    $s2.html("");
//                    $("<option value=''>请选择商品分类</option>").appendTo($s2);
//                    for(var i = 0; i < data.length; i++) {
//                        $("<option value =' " + data[i].id + " '> " + data[i].name + "</option>").appendTo($s2);
//                    }
//                    $s2.parent().show();
//                    $s1.next().show();
//                } else {//返回的数据为空
//                    $s2.parent().hide();
//                    $s1.next().hide();
//                }
//            }
        }else{//商品分类为空的情况，隐藏第二个下拉框
            $s2.empty();
            $in.val("");
        }
    }
    function changeCode($se) {
       var _code = $($se).find('option:selected').attr('data-id');
       $($se).parent().parent().find("td:eq(2)").find('input').val(_code);
    }
    $(document).ready(function () {
        //提交
        $(".statusBtn").click(function () {
            var warehouseID = $("#houseSelect").val();//仓库id
            var supplierID = $("#supplierSelect").val();//供应商id
            var itemIDs = "";//商品id
            var itemNames = "";//商品名称
            var itemTypeIDs = "";//商品类型id
            var itemTypeTitles = "";//商品类型名称
            var barcodes = "";//条形码
            var purchaseDates = "";//商品采购时间
            var purchaseNumbers = "";//商品采购数量
            $("#tab tr").each(function() {
                var sTxt1 = $(this).children("td:eq(0)").find('option:selected').text();
                var sVal1 = $(this).children("td:eq(0)").find('select').val();
                var sTxt2 = $(this).children("td:eq(1)").find('option:selected').text();
                var sVal2 = $(this).children("td:eq(1)").find('select').val();
                var code =  $(this).children("td:eq(2)").find('input').val();
                var inputTxt1 = $(this).children("td:eq(3)").find('input').val();
                var inputTxt2 = $(this).children("td:eq(4)").find('input').val();
                itemIDs += sVal2 + ",";
                itemNames += sTxt2 + ",";
                itemTypeIDs += sVal1 + ",";
                itemTypeTitles += sTxt1 + ",";
                barcodes += code + ",";
                purchaseDates += inputTxt1 + ",";
                purchaseNumbers += inputTxt2 + ",";
            });
            itemIDs = itemIDs.length > 0 ? itemIDs.substring(0, itemIDs.length - 1) : "";
            itemNames = itemNames.length > 0 ? itemNames.substring(0, itemNames.length - 1) : "";
            itemTypeIDs = itemTypeIDs.length > 0 ? itemTypeIDs.substring(0, itemTypeIDs.length - 1) : "";
            itemTypeTitles = itemTypeTitles.length > 0 ? itemTypeTitles.substring(0, itemTypeTitles.length - 1) : "";
            barcodes = barcodes.length > 0 ? barcodes.substring(0, barcodes.length - 1) : "";
            purchaseDates = purchaseDates.length > 0 ? purchaseDates.substring(0, purchaseDates.length - 1) : "";
            purchaseNumbers = purchaseNumbers.length > 0 ? purchaseNumbers.substring(0, purchaseNumbers.length - 1) : "";
            var para0 = "?warehouseCode="+warehouseID+"&supplierID="+supplierID+"&itemIDs="+itemIDs+"&itemNames="+itemNames+"&itemTypeIDs="+itemTypeIDs
                        +"&itemTypeTitles="+itemTypeTitles+"&purchaseTimes="+purchaseDates+"&purchaseNumbers="+purchaseNumbers+"&status=0&barcodes="+barcodes;
            var para1 = "?warehouseCode="+warehouseID+"&supplierID="+supplierID+"&itemIDs="+itemIDs+"&itemNames="+itemNames+"&itemTypeIDs="+itemTypeIDs
                        +"&itemTypeTitles="+itemTypeTitles+"&purchaseTimes="+purchaseDates+"&purchaseNumbers="+purchaseNumbers+"&status=1&barcodes="+barcodes;
            var eleId = $(this).attr('id');
            if(eleId=='saveBtn'){
                $.post("${base}/purchase/savePurchase.do"+para1, function (data) {//提交
                    //重新刷新
                    console.log(data);
                    if (data.code == "0") {
                        swal("提示", "更新成功", "success");
                        setTimeout(function () {
                            location.href="${base}/purchase/purchasePage.do"
                        }, 1000);
                    } else {
                        swal("提示", data.msg, "error");
                    }
                }, "json");
            }else {
                $.post("${base}/purchase/savePurchase.do"+para0, function (data) {//保存草稿
                    //重新刷新
                    console.log(data);
                    if (data.code == "0") {
                        swal("提示", "更新成功", "success");
                        setTimeout(function () {
                            location.href="${base}/purchase/purchasePage.do"
                        }, 1000);
                    } else {
                        swal("提示", data.msg, "error");
                    }
                }, "json");
            }
        });

    });
</script>
