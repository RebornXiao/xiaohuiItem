<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->

<link href="${res}/assets/plugins/bootstrap-select2/select2.min.css" rel="stylesheet" type="text/css">

<script src="${res}/assets/plugins/bootstrap-select2/select2.min.js"></script>
<script src="${res}/assets/plugins/bootstrap-select2/zh-CN.js"></script>


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
                                    <option data_id="0">选择店铺</option>
                                <#if (markets?size > 0)>
                                    <#list markets as market>
                                        <option data_id="${market.id?c}" <#if marketId == market.id>
                                                selected </#if>>
                                        ${market.name}
                                        </option>
                                    </#list>
                                </#if>
                                </select>
                            </div>

                            <div class="form-group m-l-15">
                                <label>组：</label>
                                <select id="m_group" style="width:120px;"></select>
                            </div>

                            <div class="form-group m-l-15">
                                <label>单元：</label>
                                <select id="m_unit" style="width:120px;"></select>
                            </div>

                            <div class="form-group m-l-15">
                                <label>层：</label>
                                <select id="m_layer" style="width:120px;"></select>
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
                            <th>等待任务</th>
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


            var group_list = {};
            var unit_list = {};
            var level_list = {};

            //选中的商铺ID
            var s_MarketId = ${marketId};
            var s_Group = "";
            var s_Unit = "";
            var s_Layer = "";

            var title = ['选择走道', '选择单元', '选择楼层'];
            $.each(title, function (k, v) {
                title[k] = '<option data_id=\"0\">' + v + '</option>';
            });

            $(document).ready(function () {

                var _emptyTr = $("#emptyTr");
                var _errorInfo = $("#errorInfo");
                var _clipListTable = $("#clipListTable");

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
//                *              <b>locationCode</b> - String 弹夹的完整编码，如：01010101
//                *              <b>barcode</b> - String 当前存放的商品条码
//                *              <b>name</b> - String 当前存放的商品名称
//                *              <b>unitName</b> - String 当前存放的商品单位
//                *              <b>stock</b> - int 当前的商品库存
//                *              <b>taskId</b> - long 任务ID；当没有存在预操作任务时为0，否则为对应的任务ID
//                               >0存在任务，编辑颜色改变
//                               =0
                function showDatas(json) {
                    //先删除
                    _clipListTable.find('tr[id=dataTr]').each(function () {
                        $(this).remove();
                    });
                    //隐藏
                    _emptyTr.hide();
                    //生成 tr
                    $.each(json, function (n, value) {
                        alert(value.locationCode);
                        var txt = "<tr><td>"+value.locationCode+"</td><td>"+value.barcode+"</td><td>"
                                +"</td><td>"+value.itemName+"</td><td>"
                                +"</td><td>"+value.unitName+"</td><td>"
                                +"</td><td>"+value.itemQuantity+"</td>";
                        if(value.taskId > 0) {
                            txt = txt + "<td>"+value.taskId+"</td>"
                        } else {
                            txt = txt + "<td>0</td>"
                        }

                        txt = txt + "<td>0</td>";
                        _clipListTable.append(txt);

//                        <tr>
//                        <th>弹夹编码</th>
//                        <th>商品条码</th>
//                        <th>商品名称</th>
//                        <th>商品单位</th>
//                        <th>商品库存</th>
//                        <th>等待任务</th>
//                        <th>操作</th>
//                        </tr>
                    });
                }

                var _sMarket = $("#sMarket");
                _sMarket.select2();

                var _m_group_ui = setSelectUi("m_group", title[0]);
                var _m_unit_ui = setSelectUi("m_unit", title[1]);
                var _m_layer_ui = setSelectUi("m_layer", title[2]);

                _sMarket.change(function () {
                    _m_group_ui.empty();
                    _m_group_ui.append(title[0]);

                    var select_obj = _sMarket.find("option:selected");
                    s_MarketId = select_obj.attr("data_id");

                    if (group_list[s_MarketId] == null) {
                        $.get("${base}/market/getShelvesMarks.do?marketId=" + s_MarketId + "&shelvesType=1", function (json) {
                            if (json.code != 0) {
                                alert("无法找到该店铺的 走道 信息")
                            } else {
                                group_list[s_MarketId] = json.response.datas;
                                setDatas(_m_group_ui, group_list[s_MarketId]);
                                _m_group_ui.change();
                            }
                        }, "json");
                    } else {
                        //更改
                        setDatas(_m_group_ui, group_list[data_id]);
                        _m_group_ui.change();
                    }

                });

                _m_group_ui.change(function () {
                    _m_unit_ui.empty();
                    _m_unit_ui.append(title[1]);

                    var select_obj = _m_group_ui.find("option:selected");
                    var data_id = select_obj.attr("data_id");

                    if (data_id == "0") {
                        return;
                    }

                    s_Group = select_obj.val();

                    if (unit_list[s_Group] == null) {
                        $.get("${base}/market/getShelvesMarks.do?marketId=" + s_MarketId + "&shelvesType=2&groupCode=" + s_Group, function (json) {
                            if (json.code != 0) {
                                alert("无法找到该店铺的 走道 信息")
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
                    _m_layer_ui.append(title[2]);

                    var select_obj = _m_unit_ui.find("option:selected");
                    var data_id = select_obj.attr("data_id");

                    if (data_id == "0") {
                        return;
                    }

                    s_Unit = select_obj.val();

                    if (level_list[s_Unit] == null) {
                        $.get("${base}/market/getShelvesMarks.do?marketId=" + s_MarketId + "&shelvesType=3&groupCode=" + s_Group + "&unitCode=" + s_Unit, function (json) {
                            if (json.code != 0) {
                                alert("无法找到该店铺的 走道 信息")
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

                    alert("data_id=" + s_Layer);

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