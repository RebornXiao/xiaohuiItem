<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->

<link href="${res}/assets/plugins/bootstrap-select2/select2.min.css" rel="stylesheet" type="text/css">

<script src="${res}/assets/plugins/bootstrap-select2/select2.min.js"></script>
<script src="${res}/assets/plugins/bootstrap-select2/zh-CN.js"></script>

<div class="modal" tabindex="-1" role="dialog" id="upImgDialog" >
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="mySmallModalLabel">Small modal</h4>
            </div>
            <div class="modal-body">
                <div class="progress">
                    <div id="upImgBar" class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
                        <span class="sr-only"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



<div class="content-page">
    <!-- Start content -->
    <div class="content">
        <div class="container">

            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">商品管理</a></li>
                            <li><a href="#">商品模板库</a></li>
                            <li class="active"><#if item?exists>编辑<#else>添加</#if>商品模板</li>
                        </ol>
                        <h4 class="page-title"><b><#if item?exists>编辑<#else>添加</#if>商品模板</b></h4>
                    </div>
                </div>
            </div>

            <div class="row m-t-30">
                <div class="col-md-8">
                    <form class="form-horizontal" role="form">

                        <div class="form-group">
                            <label class="col-md-4 control-label">商品名称：</label>
                            <div class="col-md-8">
                                <input type="text" id="itemName" class="form-control" <#if item?exists>
                                       value="${item.name}" </#if>>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label" for="example-email">自定义编码：</label>
                            <div class="col-md-8">
                                <input type="text" id="defineCode" class="form-control" <#if item?exists>
                                       value="${item.defineCode}" </#if>>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">标准条形码：</label>
                            <div class="col-md-8">
                                <input type="text" id="barcode" class="form-control" <#if item?exists>
                                       value="${item.barcode}" </#if>>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">分类：</label>
                            <div class="col-md-6">
                                <select class="form-control" id="typeSelect">
                                <#if (itemTypes?size > 0)>
                                    <#list itemTypes as iType>
                                        <option data_id="${iType.id?c}" <#if item?exists && iType.id == item.typeId>
                                                selected </#if>>

                                            <#if iType.parentId == 0>
                                            ${iType.title}
                                            <#else>
                                                &nbsp;&nbsp;&nbsp;&nbsp;${iType.title}
                                            </#if>
                                        </option>
                                    </#list>
                                <#else>
                                    <option data_id="0">无</option>
                                </#if>
                                </select>
                            </div>
                            <button id="addTypeBtn" type="button"
                                    class="btn col-md-2 waves-effect waves-light btn-default">
                                添加商品类型
                            </button>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">单位：</label>
                            <div class="col-md-6">
                                <select class="form-control" id="unitSelect">
                                <#if (itemUnits?size > 0)>
                                    <#list itemUnits as unit>
                                        <option data_id="${unit.id?c}" <#if item?exists && unit.id == item.unitId>
                                                selected </#if>>${unit.title}</option>
                                    </#list>
                                <#else>
                                    <option data_id="0">无</option>
                                </#if>
                                </select>
                            </div>
                            <button id="addUnitBtn" type="button"
                                    class="btn col-md-2 waves-effect waves-light btn-default">
                                添加商品单位
                            </button>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">成本价：</label>
                            <div class="col-md-8">
                                <input type="text" id="costPrice" class="form-control" <#if item?exists>
                                       value="${item.costPrice}" </#if> onkeyup="clearNoNum(this)">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">零售价：</label>
                            <div class="col-md-8">
                                <input type="text" id="defaultPrice" class="form-control" <#if item?exists>
                                       value="${item.defaultPrice}" </#if> onkeyup="clearNoNum(this)">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">规格：</label>
                            <div class="col-md-8">
                                <div class="input-group">
                                    <span class="input-group-addon">长：</span>
                                    <input type="text" id="iLength" class="form-control" <#if item?exists>
                                           value="${item.length?c}" </#if>>
                                    <span class="input-group-addon">宽：</span>
                                    <input type="text" id="iWidth" class="form-control" <#if item?exists>
                                           value="${item.width?c}" </#if>>
                                    <span class="input-group-addon">高：</span>
                                    <input type="text" id="iHeight" class="form-control" <#if item?exists>
                                           value="${item.height?c}" </#if>>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">商品主图：</label>
                            <div class="col-md-4">
                                <div id="itemMainImgDiv" style="background: white; width:150px; height:150px;">
                                    <img id="upImg" <#if item?exists && item.imageUrl??>
                                         src="${item.imageUrl}"
                                         style="width: 150px; height: 150px"</#if>/>
                                </div>
                                <p class="m-t-10">
                                    <button id="selectFileBtn" type="button"
                                            class="btn waves-effect waves-light btn-default">
                                        选择主图
                                    </button>
                                </p>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">商品描述：</label>
                            <div class="col-md-8">
                                <textarea class="form-control" id="itemInfo" rows="5"></textarea>
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

                        <br><br><br><br><br>
                    </form>
                </div>
            </div>

            <!-- end container -->
        </div>

        <script src="${res}/assets/plugins/alioss/plupload-2.1.2/js/plupload.full.min.js"></script>
        <script src="${res}/assets/plugins/alioss/plupload-2.1.2/my_upload.js"></script>

        <script type="text/javascript">
            $(document).ready(function () {

                var itemId = <#if item?exists >${item.id?c};<#else>0;</#if>
                var itemImgBarcode = "";

                moveEnd($("#itemName").get(0));

                //添加类型
                $("#addTypeBtn").on('click', function () {
                    //location.href = "${base}/item/itemTypeEdit.do";
                    open({url:"${base}/item/itemTypeEdit.do"});
                });

                //添加单位
                $("#addUnitBtn").on('click', function () {
                    //location.href = "${base}/item/itemUnitEdit.do";
                    open({url:"${base}/item/itemUnitEdit.do"});
                });

                $("#backBtn").on('click', function () {
                    history.back(-1);
                });

                $("#typeSelect").select2();

                function updateImgUrl(itemId, itemImgUrl) {
                    $.post("${base}/item/itemUpdateImgUrl.do?itemId="+itemId+"&itemImgUrl="+itemImgUrl, function(data) {
                        //重新刷新
                        swal(data.msg);
                    }, "json");
                }

                upImgName = "upImg";
                serverUrl = "${base}/oss/uploadImg.do?targetDir=item";

                function upImgFunc(itemId, itemImgBarcode) {
                    upCallback = function (hType, obj) {
                        if(hType == 0) {
                            //没有图片提交
                            alert("没有图片需要提交");
                        } else if(hType == 1) {
                            //开始提交
                        } else if(hType == 2) {
                            //进度监听
                        } else if(hType == 100) {
                            //完成，更新回对应的图片
                            updateImgUrl(itemId, obj);
                        } else {
                            //有错误
                        }
                    };

                    set_upload_param(uploader, itemImgBarcode);
                }

                //添加/修改 商品
                $("#saveBtn").on('click', function () {

                    $(this).button("loading");

                    var _typeId = $("#typeSelect").find("option:selected").attr("data_id");
                    if(_typeId == 0) {
                        swal("请选择分类");
                        return;
                    }

                    var _unitId = $("#unitSelect").find("option:selected").attr("data_id");
                    if(_unitId == 0) {
                        swal("请选择单位");
                        return;
                    }

                    itemImgBarcode = $("#barcode").val();

                    //先提交
                    var post_data = {
                        itemId:itemId,
                        name: $("#itemName").val(),
                        defineCode:$("#defineCode").val(),
                        barcode:itemImgBarcode,
                        typeId:_typeId,
                        unitId:_unitId,
                        costPrice:$("#costPrice").val(),
                        defaultPrice:$("#defaultPrice").val(),
                        passportId:0,
                        iLength:$("#iLength").val(),
                        iWidth:$("#iWidth").val(),
                        iHeight:$("#iHeight").val(),
                    };

                    $.post("${base}/item/itemEditSave.do", post_data, function(data) {
                        //重新刷新
                        if(data.code == "0") {
                            //提交成功，这里重新包装一个商品ID
                            var item = data.response.datas
                            upImgFunc(item.id, item.barcode);
                        } else {
                            swal(data.msg);
                        }
                    }, "json");
                });
            });
        </script>