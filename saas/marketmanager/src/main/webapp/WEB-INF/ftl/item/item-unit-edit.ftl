<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->
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
                            <li><a href="#">商品单位</a></li>
                            <li class="active"><#if itemUnit?exists>编辑<#else>添加</#if>商品单位</li>
                        </ol>
                        <h4 class="page-title"><b><#if itemUnit?exists>编辑<#else>添加</#if>商品单位</b></h4>
                    </div>
                </div>
            </div>

            <div class="row m-t-30">
                <div class="col-md-8">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-md-4 control-label">商品单位名称：</label>
                            <div class="col-md-8">
                                <input id="itemUnitTitle" type="text" class="form-control" <#if itemUnit?exists>
                                       value="${itemUnit.title}" </#if>>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-4"></div>
                            <div class="col-md-8">
                                <div class="checkbox checkbox-primary">
                                    <input id="itemUnitStatus" type="checkbox"
                                    <#if itemUnit?exists && itemUnit.status == 1>
                                           checked="true" </#if>>
                                    <label for="itemUnitStatus">马上启用该单位？</label>
                                </div>
                            </div>
                        </div>

                        <br><br>

                        <div class="form-group">
                            <div class="col-md-4"></div>
                            <button id="saveBtn" type="button"
                                    class="btn waves-effect waves-light btn-primary col-md-2">确定
                            </button>
                            <div class="col-md-6"></div>
                        </div>

                        <br><br><br><br><br>
                    </form>
                </div>
            </div>

            <!-- end container -->
        </div>


        <script type="text/javascript">

            $(document).ready(function () {

                var itemUnitId = 0;
                <#if itemUnit?exists>
                    itemUnitId = "${itemUnit.id?c}"
                </#if>

                $("#itemUnitTitle").focus();

                //保存商品单位
                $("#saveBtn").on('click', function () {

                    //检测
                    var title = $("#itemUnitTitle").val();
                    var status = 0;
                    if (title == "") {
                        swal("请输入商品单位名称!");
                        return;
                    }

                    if($("#itemUnitStatus").is(':checked')) {
                        status = 1;
                    }

                    $.post("${base}/market/manager/item/itemUnitEditSave.do?id="+itemUnitId+"&title="+title+"&status="+status, function(data) {
                        //重新刷新
                        if(data.code == "0") {
                            swal("提示", "操作成功", "success");
                            location.reload();
                        } else {
                            swal(data.msg);
                        }
                    }, "json");
                });
            });
        </script>