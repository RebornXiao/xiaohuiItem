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
                            <li><a href="#">商品分类</a></li>
                            <li class="active"><#if itemType?exists>编辑<#else>添加</#if>商品分类</li>
                        </ol>
                        <h4 class="page-title"><b><#if itemType?exists>编辑<#else>添加</#if>商品分类</b></h4>
                    </div>
                </div>
            </div>

            <div class="row m-t-30">
                <div class="col-md-8">
                    <form class="form-horizontal" role="form">
                        <div id="sBtn" class="form-group">
                            <div class="col-md-4"></div>
                            <div class="col-md-8">
                                <div class="radio radio-primary radio-inline">
                                    <input id="addBtnA" type="radio" name="radio3" value="option3">
                                    <label for="addBtnA">
                                        添加一级分类
                                    </label>
                                </div>
                                <div class="radio radio-primary radio-inline">
                                    <input id="addBtnB" type="radio" name="radio3" value="option3" <#if pItemType?exists>checked</#if>>
                                    <label for="addBtnB">
                                        添加二级分类
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">商品单位名称：</label>
                            <div class="col-md-8">
                                <input id="itName" type="text" class="form-control" <#if itemType?exists>value="${itemType.title}"</#if>>
                            </div>
                        </div>

                        <div id="typesAlist" class="form-group">
                            <label class="col-md-4 control-label">绑定一级分类：</label>
                            <div class="col-md-8">
                                <select class="form-control" id="itSelect">
                                <#if itemTypes?exists && (itemTypes?size > 0)>
                                    <#list itemTypes as type>
                                        <option data_id="${type.id}"
                                                <#if pItemType?exists && pItemType.id == type.id>
                                                selected
                                                <#else>
                                                <#if itemType?exists && itemType.parentId == type.id>
                                                selected </#if>
                                                </#if>
                                                >${type.title}
                                        </option>
                                    </#list>
                                <#else>
                                    <option data_id="0">无</option>
                                </#if>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">分类图标：</label>
                            <div class="col-md-4">
                                <img id="itImg" <#if itemType?exists && itemType.icon??> src="${itemType.icon}" </#if> width="150px" height="150px" />
                                <p class="m-t-10">
                                    <button id="upBtn" type="button" class="btn waves-effect waves-light btn-default">上传图标</button></p>
                            </div>
                        </div>

                        <br><br>

                        <div class="form-group">
                            <div class="col-md-4"></div>
                            <button id="saveBtn" type="button" class="btn waves-effect waves-light btn-primary col-md-2">确定</button>
                            <button id="backBtn" type="button" class="btn waves-effect waves-light btn-default col-md-2">返回</button>
                        </div>

                        <br><br><br><br><br>
                    </form>
                </div>
            </div>

            <!-- end container -->
        </div>


        <script type="text/javascript">

            $(document).ready(function () {

                var itemTypeId = 0;
                var _typesAlist = $("#typesAlist");

                <#if itemType?exists>
                    itemTypeId = "${itemType.id?c}"
                    //隐藏
                    $("#sBtn").hide();

                    //如果是修改一级，则隐藏2级选项
                    <#if itemType.parentId == 0>
                        _typesAlist.hide();
                    </#if>

                </#if>

                <#if !(itemType?exists) && !(pItemType?exists) >
                    //默认选中一级
                    $("#addBtnA").attr("checked", true);
                    //隐藏二级
                    _typesAlist.hide();
                    //添加点击切换
                    $("#addBtnA").on('click', function () {
                        if($(this).is(':checked')) {
                            //隐藏二级
                            _typesAlist.hide();
                        }
                    });
                    //添加点击切换
                    $("#addBtnB").on('click', function () {
                        if($(this).is(':checked')) {
                            //隐藏二级
                            _typesAlist.show();
                        }
                    });
                </#if>

                <#if pItemType?exists>
                    $("#sBtn").hide();
                </#if>

                moveEnd($("#itName").get(0));

                $("#backBtn").on('click', function () {
                    history.back(-1);
                });

                $("#upBtn").on('click', function () {
                });

                //保存商品单位
                $("#saveBtn").on('click', function () {

                    //检测
                    var title = $("#itName").val();
                    if (title == "") {
                        swal("请输入分类名称!");
                        return;
                    }

                    //保存
                    $.post("${base}/item/itemTypeEditSave.do", function(data) {

                        <#if !(itemType?exists) >
                        $("#itName").val("");
                        </#if>

                        //重新刷新
                        if(data.code == "0") {
                            swal("提示", data.msg, "success");
                        } else {
                            swal(data.msg);
                        }
                    }, "json");

                });
            });
        </script>