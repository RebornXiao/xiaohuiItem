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
                                <table class="table table-bordered" id="tab">
                                    <thead>
                                    <tr>
                                        <th style="background-color: #f9f9f9">商品分类</th>
                                        <th style="background-color: #f9f9f9">商品名称</th>
                                        <th style="background-color: #f9f9f9">采购日期</th>
                                        <th style="background-color: #f9f9f9">采购数量</th>
                                        <th style="background-color: #f9f9f9">操作</th>
                                    </tr>
                                    </thead>
                                    <#--<tbody>-->
                                    <#--<tr>-->
                                        <#--<td>-->
                                            <#--<select class="form-control">-->
                                                <#--<option value="-1">饮料</option>-->
                                                <#--<option value="0">零食</option>-->
                                            <#--</select>-->
                                        <#--</td>-->
                                        <#--<td>-->
                                            <#--<select class="form-control">-->
                                                <#--<option value="-1">哇哈哈</option>-->
                                                <#--<option value="0">营养快线</option>-->
                                            <#--</select>-->
                                        <#--</td>-->
                                        <#--<td>-->
                                            <#--<input id="endTime" type="text" class="form-control"  placeholder="如：2017-10-10">-->
                                            <#--&lt;#&ndash;<span class="input-group-addon bg-default" onClick="jeDate({dateCell:'#endTime',isTime:true,format:'YYYY-MM-DD 00:00'})">&ndash;&gt;-->
                                                <#--&lt;#&ndash;<i class="fa fa-calendar"></i>&ndash;&gt;-->
                                            <#--&lt;#&ndash;</span>&ndash;&gt;-->
                                        <#--</td>-->
                                        <#--<td>-->
                                            <#--<input type="text" class="form-control" id="purchaseCount" placeholder="输入采购数量">-->
                                        <#--</td>-->
                                        <#--<td><button onclick="deleTr(this);">删除</button></td>-->
                                    <#--</tr>-->
                                    <#--</tbody>-->
                                </table>
                            </div>
                            <div class="m-t-40">
                                <button id="saveBtn" type="button" class="btn waves-effect waves-light btn-primary col-md-1">提交</button>
                                <button id="backBtn" type="button" class="btn waves-effect waves-light btn-warning col-md-1">保存草稿</button>
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
                "<select class='form-control s1' id='listSelect'>" +
                "<option value='-1'>请选择商品分类</option>" +
                "<#if (itemTypes?size > 0)><#list itemTypes as iType> <option value='${iType.id?c}' <#if item?exists && iType.id == item.typeId>selected </#if>><#if iType.parentId == 0>${iType.title}<#else>&nbsp;&nbsp;&nbsp;&nbsp;${iType.title}</#if></option></#list><#else><option value='0'>无</option></#if>" +
                "</select>" +
                "</td><td><select class='form-control' id='nameSelect'><option value='-1'>请选择商品名称</option></select></td>" +
                "<td><input id='endTime' type='text' class='form-control'  placeholder='如：2017-10-10'></td>" +
                "<td><input type='text' class='form-control' id='purchaseCount' placeholder='输入采购数量'></td>" +
                "<td><button class='btn-danger' onclick='deleTr(this);'>删除</button></td>" +
                "</tr>";
        addTr(tab, row, trHtml);
    }
    function addTr(tab, row, trHtml){
        //获取table最后一行 $("#tab tr:last")
        //获取table第一行 $("#tab tr").eq(0)
        //获取table倒数第二行 $("#tab tr").eq(-2)
        var $tr=$("#"+tab+" tr").eq(row);
        if($tr.size()==0){
            alert("指定的table id或行数不存在！");
            return;
        }
        $tr.after(trHtml);
    }
    function deleTr(nowTr) {
        $(nowTr).parent().parent().remove();
    }
    function changeSelect(a) {
        alert(a.value);
    }
    $(document).ready(function () {
        var s1 = $("#houseSelect").val();
        var s2 = $("#supplierSelect").val();
        $("#saveBtn").on('click',function () {
           $("#tab tr").each(function () {
               var leng = $("#tab tr").length;
               alert()
               var numberStr = $("#tab tr").eq(leng).find("td:first").val();
               var a = $("this td:eq(0)").val();
//               var one=$(this).find("td:eq(2)").find("input").val();  //取第一列中input的值
//               var two=$(this).find("td:eq(1)").find("input").val();  // 取第二列中input的值
//               $(this).find("td:eq(2)").find("input").val(three);  // 赋值给第三列中的intput
           });
        });

        //级联下拉列表
        $(document).on('change','.s1',function () {
            var $s1 = $(this);
            var $s2 = $("#nameSelect");
            var _id = $(this).val();
            alert(_id);
            if(_id !=""){//当商品分类不为空时
                if(!$s1.data(_id)){//不在缓冲区中,需要向服务器请求
                    $.get("http://192.168.1.166:17777/item/item/getItemTemplateIdAndNames.do?itemTypeId=" + _id,function(data) {
                        var obj = data.response.datas;
                        if((obj.length != 0)&& data) {//返回的数据不为空
                            $s2.html("");
                            for(var i = 0; i < obj.length; i++) {
                                $("<option value ='" + obj[i].id + "'> " + obj[i].name + "</option>").appendTo($s2);
                            }
                            $s2.parent().show();
                            $s2.next().show();
                        } else {//返回的数据为空
                            $s2.html("");
                            $("<option value=''>该分类下没有商品</option>").appendTo($s2);
                        }
                        $s2.data(_id, data);
                    }, "json");
                }else{//在缓冲区
                    var data = $s1.data(_id);
                    if(data.length != 0) {//返回的数据不为空
                        $s2.html("");
                        $("<option value=''>请选择商品分类</option>").appendTo($s2);
                        for(var i = 0; i < data.length; i++) {
                            $("<option value =' " + data[i] + " '> " + data[i] + "</option>").appendTo($s2);
                        }
                        $s2.parent().show();
                        $s1.next().show();
                    } else {//返回的数据为空
                        $s2.parent().hide();
                        $s1.next().hide();
                    }
                }
            }else{//商品分类为空的情况，隐藏第二个下拉框
                $s2.parent().hide();
                $s1.next().hide();
            }
        });

    });
</script>
