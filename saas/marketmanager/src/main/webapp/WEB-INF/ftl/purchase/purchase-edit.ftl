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
                        <h4 class="page-title "><b>编辑采购单</b></h4>
                    </div>
                </div>
            </div>

            <div class="card-box">
                <div class="row">
                    <div class="col-sm-8">
                        <div class="advert_container">
                            <h5 class="page-title" style="padding-top: 20px"><b>基本信息</b></h5>
                            <hr style="height:1px;width:100%;border:none;border-top:1px dashed #ccc;"/>
                            <div class="table-responsive advert_detail_table">
                                <table class="table table-bordered">
                                <#--<#if advert?exists>-->
                                    <tbody>
                                    <tr>
                                        <td style="background-color: #f9f9f9">仓库名称</td>
                                        <td>
                                            <select class="form-control" id="houseSelect">
                                                <option value="-1">请选择仓库</option>
                                                <#if warehouseItem?exists >
                                                    <#list warehouseItem as warehouse>
                                                        <option value="${warehouse.id?c}">${warehouse.warehouseName}</option>
                                                    </#list>
                                                </#if>
                                            </select>
                                        </td>
                                        <td style="background-color: #f9f9f9">供应商名称</td>
                                        <td>
                                            <select class="form-control" id="supplierSelect">
                                                <option value="-1">请选择供应商</option>
                                                <#if supperlierItem?exists >
                                                    <#list supperlierItem as supplier>
                                                        <option value="${supplier.id?c}">${supplier.supplierName}</option>
                                                    </#list>
                                                </#if>
                                            </select>
                                        </td>
                                    </tr>
                                    </tbody>
                                <#--</#if>-->
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="advert_container">
                            <ol class="breadcrumb pull-right">
                                <li><button id="addRow" onclick="addTr2('tab',-1);">添加商品</button></li>
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
                                    <tbody>
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
<script type="text/javascript">
    function addTr2(tab, row){
        var trHtml="<tr>" +
                "<td><select class='form-control'><option value='-1'>饮料</option><option value='0'>零食</option></select></td>" +
                "<td><select class='form-control'><option value='-1'>哇哈哈</option><option value='0'>营养快线</option></select></td>" +
                "<td><input id='endTime' type='text' class='form-control'  placeholder='如：2017-10-10'></td>" +
                "<td><input type='text' class='form-control' id='purchaseCount' placeholder='输入采购数量'></td>" +
                "<td><button onclick='deleTr(this);'>删除</button></td>" +
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
        console.log(nowTr);
        $(nowTr).parent().parent().remove();
    }
    $(document).ready(function () {
        var s1 = $("#houseSelect").val();
        var s2 = $("#supplierSelect").val();
        $("#deleBtn").on('click',function () {
            deleTr(this);
        });
    });
</script>
