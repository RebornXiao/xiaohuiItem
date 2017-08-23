<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->

<link href="${res}/assets/plugins/bootstrap-select2/select2.min.css" rel="stylesheet" type="text/css">

<script src="${res}/assets/plugins/bootstrap-select2/select2.min.js"></script>
<script src="${res}/assets/plugins/bootstrap-select2/zh-CN.js"></script>

<!-- 商品更改库存界面 -->
<div class="modal fade" id="upItemDialog" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="lItemTitle">上架商品</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label class="col-md-4 control-label">弹夹编码：</label>
                                <div class="col-md-6">
                                    <input type="text" id="ipItemCode" class="form-control" disabled>
                                </div>
                            </div>
                            <div class="form-group" id="dItemName">
                                <label class="col-md-4 control-label" id="lItemName">商品名称：</label>
                                <div class="col-md-6">
                                    <input type="text" id="ipItemName" class="form-control" disabled>
                                </div>
                            </div>
                            <div class="form-group" id="dItemStock">
                                <label class="col-md-4 control-label" id="lItemStock">库存：</label>
                                <div class="col-md-6">
                                    <input type="text" id="ipItemStock" class="form-control" disabled>
                                </div>
                            </div>
                            <div class="form-group" id="dItemTypes">
                                <label class="col-md-4 control-label" id="lItemTypes">选择替换的商品：</label>
                                <div class="col-md-8">
                                    <select id="sItemTypes" style="width:120px;">
                                    <#if itemTypes?exists && (itemTypes?size > 0)>
                                        <option data_id="0">选择分类</option>
                                        <#list itemTypes as iType>
                                            <option data_id="${iType.id?c}">
                                            <#if iType.parentId == 0>
                                                ${iType.title}
                                            <#else>
                                                &nbsp;&nbsp;&nbsp;&nbsp;${iType.title}
                                            </#if>
                                            </option>
                                        </#list>
                                    <#else>
                                        <option data_id="0">选择分类</option>
                                    </#if>
                                    </select>
                                    <select id="sItem" style="width:120px;"></select>
                                </div>
                            </div>
                            <div class="form-group" id="dNewItemStock">
                                <label class="col-md-4 control-label" id="lNewItemStock">新增库存：</label>
                                <div class="col-md-6">
                                    <input type="text" id="ipNewItemStock" class="form-control">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="row">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-6">
                        <button type="button" class="btn btn-default"
                                data-dismiss="modal">取消
                        </button>
                        <button id="upItemOkBtn" type="button"
                                class="btn btn-primary" data-dismiss="modal">确定
                        </button>
                    </div>
                    <div class="col-sm-2"></div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- 任务列表 -->
<div class="modal fade" id="taskDialog" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="upItemTitle">任务详情</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-10">

                        <form class="form-horizontal" role="form">


                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="row">
                    <div class="col-sm-4"></div>
                    <div class="col-sm-4">
                        <button type="button" class="btn btn-default"
                                data-dismiss="modal">取消
                        </button>
                        <button id="locationBtn" type="button"
                                class="btn btn-primary" data-dismiss="modal">确定
                        </button>
                    </div>
                    <div class="col-sm-4"></div>
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
                            <li><a href="#">店铺管理</a></li>
                            <li class="active"><a href="#">店铺列表</a></li>
                        </ol>
                        <h4 class="page-title"><b>店铺列表</b></h4>
                    </div>
                </div>
            </div>

            <div class="card-box">
                <div class="row">
                    <div class="col-sm-12">
                        <form class="form-inline" role="form">

                            <div class="form-group">
                                <label>选择店铺：</label>
                                <select id="sMarket" style="width:120px;">
                                <#if markets?exists && (markets?size > 0)>
                                    <#list markets as market>
                                        <option data_id="${market.id?c}" <#if marketId == market.id>
                                                selected </#if>>
                                        ${market.name}
                                        </option>
                                    </#list>
                                <#else>
                                    <option data_id="0" selected>选择店铺</option>
                                </#if>
                                </select>
                            </div>

                            <div class="form-group m-l-15">
                                <label>组：</label>
                                <select id="m_group" style="width:120px;">
                                    <option data_id="0">选择组</option>
                                <#if groups?exists && (groups?size > 0)>
                                    <#list groups as group>
                                        <option value="${group}">${group}</option>
                                    </#list>
                                </#if>
                                </select>
                            </div>

                            <div class="form-group m-l-15">
                                <label>单元：</label>
                                <select id="m_unit" style="width:120px;"></select>
                            </div>

                            <div class="form-group m-l-15">
                                <label>层：</label>
                                <select id="m_layer" style="width:120px;"></select>
                            </div>

                            <div class="form-group pull-right">
                                <button id="taskListBtn" type="button"
                                        class="btn waves-effect waves-light btn-default">任务列表
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-12">

                    <table class="table table-striped table-bordered">
                        <thead class="table_head">
                        <tr>
                            <th>弹夹编码</th>
                            <th>商品条码</th>
                            <th>商品名称</th>
                            <th>商品单位</th>
                            <th>商品库存</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="clipListTable">
                        <tr id="emptyTr">
                            <td colSpan="7" height="200px">
                                <p class="text-center" id="errorInfo">暂无任何数据</p>
                            </td>
                        </tr>
                        </tbody>
                    </table>


                </div>
            </div>

        <#if clips?exists && (clips?size > 0)>
            <div class="row small_page">
                <div class="col-sm-12">
                    <#include "../common/paginate.ftl">
                    <@paginate nowPage=pageIndex itemCount=count action="${base}/market/markets.do" />
                </div>
            </div>
        </#if>
            <!-- end container -->
        </div>


        <script type="text/javascript">


            //以弹夹为key的任务列表
            var task_list = {};
            var task_count = 0;
            task_list[1] = {id: 1, name: "你好1"};
            task_list[2] = {id: 2, name: "你好2"};
            task_list[3] = {id: 3, name: "你好3"};
            task_list[4] = {id: 4, name: "你好4"};

            var unit_list = {};
            var level_list = {};

            //选中的商铺ID
            var s_MarketId = ${marketId?c};
            var s_Group = "";
            var s_Unit = "";
            var s_Layer = "";

            var title = ['选择单元', '选择楼层'];
            $.each(title, function (k, v) {
                title[k] = '<option data_id=\"0\">' + v + '</option>';
            });

            $(document).ready(function () {

                //---------------------------------------上架库存相关内容

                var _lItemTitle = $("#lItemTitle");
                var _ipItemCode = $("#ipItemCode");

                var _divItemName = $("#dItemName");
                var _labelItemName = $("#lItemName");
                var _ipItemName = $("#ipItemName");

                var _divItemStock = $("#dItemStock");
                var _labelItemStock = $("#lItemStock");
                var _ipItemStock = $("#ipItemStock");

                var _sItem = $("#sItem");

                var _divItemTypes = $("#dItemTypes");
                var _labelItemTypes = $("#lItemTypes");
                var _sItemTypes = $("#sItemTypes");

                var _divNewItemStock = $("#dNewItemStock");
                var _labelNewItemStock = $("#lNewItemStock");
                var _ipNewItemStock = $("#ipNewItemStock");

                var _clipListTable = $("#clipListTable");//单元表
                var _upItemOkBtn = $("#upItemOkBtn");
                var _upItemDialog = $("#upItemDialog");

                //商品内容
                var items = {};//以分类ID储存

                _sItemTypes.select2();
                _sItemTypes.change(function () {
                    //获取商品
                    var select_obj = _sItemTypes.find("option:selected");
                    var data_id = select_obj.attr("data_id");
                    _sItem.empty();
                    _sItem.append("<option data_id=\"0\" >选择商品</option>");

                    //如果有商品，则不理会，否则，弹出选择
                    if(items[data_id] != null) {
                        //添加
                        $.each(items[data_id], function (k, v) {
                            var option = '<option data_id="' + k + '">' + v + '</option>';
                            _sItem.append(option);
                        });
                    } else {
                        //从网络获取
                        $.get("${base}/item/idNameItems.do?itemTypeId=" + data_id, function (json) {
                            if (json.code != 0) {
                                swal(json.msg);
                            } else {
                                items[data_id] = json.response.datas;
                                $.each(items[data_id], function (k, v) {
                                    var option = '<option data_id="' + k + '">' + v + '</option>';
                                    _sItem.append(option);
                                });
                            }
                        }, "json");
                    }
                });

                _sItem.append("<option data_id=\"0\" >选择商品</option>");
                _sItem.select2();

                //上架，提交任务
                _upItemOkBtn.on('click', function () {
                    var data_id = $(this).attr("data_id");
                    var data_code = $(this).attr("data_code");

                });

                _clipListTable.on("click", "button[id=taskCancelBtn]", function () {
                    var tr_obj = $(this);
                    swal({
                        title: "取消任务",
                        text: "你确定要取消这个任务吗？",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        closeOnConfirm: false
                    }, function () {
                        //通知取消任务
                        swal("Deleted!", "Your imaginary file has been deleted.", "success");
                    });
                });

                //上架商品
                _clipListTable.on("click", "button[id=upItemBtn]", function () {

                    //如果原来有商品
                    var data_id = $(this).attr("data_id");
                    var data_code = $(this).attr("data_code");

                    _upItemOkBtn.attr("data_id", data_id);
                    _upItemOkBtn.attr("data_code", data_code);

                    var tr_ui = $(this).parent().parent();

                    _lItemTitle.text("上架商品");
                    _ipItemCode.val(tr_ui.find("td[id=iCode]").text());//编码

                    if(data_id == 0) {
                        //原来没有，上架商品
                        _divItemName.hide();
                        _divItemStock.hide();
                        _divItemTypes.show();
                        _labelItemTypes.text("选择上架商品：");
                        _labelNewItemStock.text("上架库存：");

                        _upItemOkBtn.attr("data_id", "0");
                    } else {
                        _divItemName.show();
                        _divItemStock.show();
                        _divItemTypes.hide();
                        _labelNewItemStock.text("新增库存：");
                        _ipItemName.val(tr_ui.find("td[id=iName]").text());//名称
                        _ipItemStock.val(tr_ui.find("td[id=iStock]").text());//原库存
                    }

                    _upItemDialog.modal('show');
                });

                //原来有商品，现在替换
                _clipListTable.on("click", "button[id=changeItemBtn]", function () {
                    //切换商品
                    var tr_ui = $(this).parent().parent();

                    _lItemTitle.text("切换商品");
                    _ipItemCode.val(tr_ui.find("td[id=iCode]").text());//编码

                    _divItemName.show();
                    _divItemStock.show();
                    _divItemTypes.show();

                    _labelItemTypes.text("选择切换商品：");
                    _labelNewItemStock.text("切换库存：");
                    _ipItemName.val(tr_ui.find("td[id=iName]").text());//名称
                    _ipItemStock.val(tr_ui.find("td[id=iStock]").text());//原库存

                    var data_id = $(this).attr("data_id");
                    var data_code = $(this).attr("data_code");

                    _upItemOkBtn.attr("data_id", data_id);
                    _upItemOkBtn.attr("data_code", data_code);

                    _upItemDialog.modal('show');
                });

                //-------------------------------------------------------

                var _emptyTr = $("#emptyTr");
                var _errorInfo = $("#errorInfo");

                function setSelectUi(ui_id, ui_title) {
                    var v = $("#" + ui_id + "");
                    v.append(ui_title);
                    v.select2();
                    return v;
                }

                function setDatas(ui, list) {
                    $.each(list, function (k, v) {
                        var option = '<option value="' + v + '" >' + v + '</option>';
                        ui.append(option);
                    });
                }

                function hibeDatas(msg) {
                    //删除所有tr
                    _clipListTable.find('tr[id=dataTr]').each(function () {
                        $(this).remove();
                    });
                    _emptyTr.show();
                    _errorInfo.text(msg);
                }

                function addBtns(addType, id, locationCode) {
                    if (addType == 0) {
                        return "<button id=\"seeTaskBtn\" type=\"button\""
                                + "class=\"btn waves-effect waves-light btn-danger btn-sm\""
                                + "data_id=\"" + id + "\">查看任务</button>";
                    } else if (addType == 1) {
                        return "<button id=\"upItemBtn\" type=\"button\""
                                + "class=\"btn waves-effect waves-light btn-success btn-sm\""
                                + "data_id=\"" + id + "\" data_code=\"" + locationCode + "\">上架商品</button>"
                                + "<button id=\"changeItemBtn\" type=\"button\""
                                + "class=\"m-l-10 btn waves-effect waves-light btn-purple btn-sm\""
                                + "data_id=\"" + id + "\" data_code=\"" + locationCode + "\">切换商品</button>";
                    } else {
                        return "<button id=\"upItemBtn\" type=\"button\""
                                + "class=\"btn waves-effect waves-light btn-success btn-sm\""
                                + "data_id=\"" + id + "\" data_code=\"" + locationCode + "\">上架商品</button>";
                    }
                }

                function showDatas(json) {
                    //先删除
                    _clipListTable.find('tr[id=dataTr]').each(function () {
                        $(this).remove();
                    });
                    //隐藏
                    _emptyTr.hide();
                    //生成 tr
                    $.each(json, function (n, value) {
                        var txt = "<tr id=\"dataTr\"><td id=\"iCode\">" + value.locationCode + "</td>"
                                + "<td>" + value.barcode + "</td>"
                                + "<td id=\"iName\">" + value.itemName + "</td>"
                                + "<td>" + value.unitName + "</td>"
                                + "<td id=\"iStock\">" + value.itemQuantity + "</td>";
                        //如果已有任务，则什么也不做
                        if (task_list[value.locationCode] == null) {
                            if (value.taskId > 0) {
                                txt = txt + "<td>" + addBtns(0, value.taskId, value.locationCode) + "</td>";
                            } else if (value.itemTemplateId != 0) {
                                txt = txt + "<td>" + addBtns(1, value.itemTemplateId, value.locationCode) + "</td>";
                            } else {
                                txt = txt + "<td>" + addBtns(2, 0, value.locationCode) + "</td>";
                            }
                        } else {
                            txt = txt + "<td></td>";
                        }
                        _clipListTable.append(txt);
                    });
                }

                var _sMarket = $("#sMarket");
                _sMarket.select2();

                var _m_group_ui = $("#m_group");
                _m_group_ui.select2();

                var _m_unit_ui = setSelectUi("m_unit", title[0]);
                var _m_layer_ui = setSelectUi("m_layer", title[1]);

                _sMarket.change(function () {

                    var select_obj = _sMarket.find("option:selected");
                    s_MarketId = select_obj.attr("data_id");
                    if (s_MarketId == 0) {
                        return;
                    }

                    //如果有任务，则提醒，否则再切换，否则 直接切换
                    if (task_count > 0) {
                        swal({
                            title: "提醒",
                            text: "当前店铺有上架任务未提交，确定丢弃切换 店铺？",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "丢弃并切换店铺",
                            cancelButtonText: "查看任务列表",
                            closeOnConfirm: false,
                            closeOnCancel: false
                        }, function (isConfirm) {
                            if (isConfirm) {
                                swal("Deleted!", "Your imaginary file has been deleted.", "success");
                            } else {
                                swal("Cancelled", "Your imaginary file is safe :)", "error");
                            }
                        });
                    } else {
                        //直接切换
                        location.href = "${base}/market/marketItems.do?id=" + s_MarketId;
                    }
                });

                _m_group_ui.change(function () {
                    _m_unit_ui.empty();
                    _m_unit_ui.append(title[0]);

                    var select_obj = _m_group_ui.find("option:selected");
                    var data_id = select_obj.attr("data_id");

                    if (data_id == "0") {
                        return;
                    }

                    s_Group = select_obj.val();

                    if (unit_list[s_Group] == null) {
                        $.get("${base}/market/getShelvesMarks.do?marketId=" + s_MarketId + "&shelvesType=2&groupCode=" + s_Group, function (json) {
                            if (json.code != 0) {
                                swal("无法找到该店铺的 单元 信息");
                            } else {
                                unit_list[s_Group] = json.response.datas;
                                setDatas(_m_unit_ui, unit_list[s_Group]);
                                _m_unit_ui.change();
                            }
                        }, "json");
                    } else {
                        //更改
                        setDatas(_m_unit_ui, unit_list[s_Group]);
                        _m_unit_ui.change();
                    }
                });

                _m_unit_ui.change(function () {
                    _m_layer_ui.empty();
                    _m_layer_ui.append(title[1]);

                    var select_obj = _m_unit_ui.find("option:selected");
                    var data_id = select_obj.attr("data_id");

                    if (data_id == "0") {
                        return;
                    }

                    s_Unit = select_obj.val();

                    if (level_list[s_Unit] == null) {
                        $.get("${base}/market/getShelvesMarks.do?marketId=" + s_MarketId + "&shelvesType=3&groupCode=" + s_Group + "&unitCode=" + s_Unit, function (json) {
                            if (json.code != 0) {
                                swal("无法找到该店铺的 层 信息");
                            } else {
                                level_list[s_Unit] = json.response.datas;
                                setDatas(_m_layer_ui, level_list[s_Unit]);
                                _m_layer_ui.change();
                            }
                        }, "json");
                    } else {
                        //更改
                        setDatas(_m_layer_ui, level_list[s_Unit]);
                        _m_layer_ui.change();
                    }

                });

                _m_layer_ui.change(function () {
                    var select_obj = _m_layer_ui.find("option:selected");
                    var data_id = select_obj.attr("data_id");

                    if (data_id == "0") {
                        hibeDatas("暂无数据");
                        return;
                    }
                    s_Layer = select_obj.val();

                    $.get("${base}/market/loaderClipDatas.do?marketId=" + s_MarketId + "&groupCode=" + s_Group + "&unitCode=" + s_Unit + "&floorCode=" + s_Layer, function (json) {

                        if (json.code != 0) {
                            hibeDatas(json.msg);
                        } else {
                            var data = json.response.datas;
                            showDatas(data);
                        }
                    }, "json");

                });

            });
        </script>