<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->

<link href="${res}/assets/plugins/bootstrap-select2/select2.min.css" rel="stylesheet" type="text/css">

<script src="${res}/assets/plugins/address/address.js"></script>

<script src="${res}/assets/plugins/bootstrap-select2/select2.min.js"></script>
<script src="${res}/assets/plugins/bootstrap-select2/zh-CN.js"></script>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=lbVUzs9wHA7D61mfQjvrr7CxV97fbML7"></script>


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
                            <li class="active"><#if market?exists>编辑<#else>添加</#if>店铺</li>
                        </ol>
                        <h4 class="page-title"><b><#if market?exists>编辑<#else>添加</#if>店铺</b></h4>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-12">
                    <div class="row m-t-30">
                        <div class="col-md-8">
                            <form class="form-horizontal" role="form" onkeypress="if(event.keyCode==13) {$('#saveBtn').click();return false;}" method="POST" action="${base}/marketmanager/" id="formDefault">
                                <div class="form-group">
                                    <label class="col-md-4 control-label">店铺名称：</label>
                                    <div class="col-md-8">
                                        <input type="text" id="mName" class="form-control" <#if market?exists>
                                               value="${market.name}" </#if>>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="example-email">所在地区：</label>
                                    <div class="col-md-8">
                                        <select id="loc_province" style="width:120px;"></select>
                                        <select id="loc_city" style="width:120px; margin-left: 10px"></select>
                                        <select id="loc_district" style="width:120px;margin-left: 10px"></select>
                                        <select id="loc_street" style="width:120px;margin-left: 10px"></select>
                                        <button id="addStreeBtn" type="button"
                                                class="btn waves-effect waves-light btn-primary">
                                            添加街道
                                        </button>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-4 control-label">门牌号：</label>
                                    <div class="col-md-8">
                                        <input type="text" id="mAddress" class="form-control" <#if market?exists>
                                               value="${market.address}" </#if>>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-4 control-label">经纬度：</label>
                                    <div class="col-md-4">
                                        <input type="text" id="mLocation" class="form-control" <#if market?exists>
                                               value="${market.location}" </#if> disabled="true">
                                    </div>
                                    <div class="col-md-4">

                                        <!-- Button trigger modal -->
                                        <button type="button" class="btn waves-effect waves-light btn-primary"
                                                data-toggle="modal" data-target="#myModal">
                                            设置经纬度
                                        </button>

                                        <!-- Modal -->
                                        <div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1"
                                             role="dialog">
                                            <div class="modal-dialog modal-lg" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal"
                                                                aria-label="Close"><span
                                                                aria-hidden="true">&times;</span></button>
                                                        <h4 class="modal-title">点击地图选择经纬度</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div id="allmap" style="width: 830px; height: 520px;"></div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="col-sm-8"><p id="baiduTxt"></p></div>
                                                        <div class="col-sm-4">
                                                            <button type="button" class="btn btn-default"
                                                                    data-dismiss="modal">取消
                                                            </button>
                                                            <button id="locationBtn" type="button"
                                                                    class="btn btn-primary" data-dismiss="modal">确定
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-4 control-label">配送方式：</label>
                                    <div class="col-md-8">
                                        <select class="form-control" id="mDeliveryMode" style="width:150px">
                                            <option data_id="1">仅自提</option>
                                            <option data_id="2">仅送货</option>
                                            <option data_id="3">可自提也可送货</option>
                                            <option data_id="4">AI(智能)</option>
                                            <option data_id="5">可自提也可AI配送</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-4 control-label">覆盖的配送距离(米)：</label>
                                    <div class="col-md-8">
                                        <input type="number" id="mDistance" class="form-control" <#if market?exists>
                                               value="${market.coveringDistance?c}" </#if>>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-4 control-label">配送费(分)：</label>
                                    <div class="col-md-8">
                                        <input type="number" id="mDeliveryCost" class="form-control" <#if market?exists>
                                               value="${market.deliveryCost?c}" </#if>>
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
                </div>
            </div>

            <!-- end container -->
        </div>


        <script type="text/javascript">
            $(document).ready(function () {

                //临时内容
                var _marketId = <#if market?exists> ${market.id?c};<#else> 0;</#if>

                // 百度地图API功能
                var map = new BMap.Map("allmap");
                var old_marker = null;
                var map_lng = 0;
                var map_lat = 0;
                var geoc = new BMap.Geocoder();

                map.centerAndZoom("广州", 12);

                function setBaiduPoint(lng, lat) {
                    if (old_marker != null) {
                        map.removeOverlay(old_marker);
                    }
                    map_lng = lng;
                    map_lat = lat;
                    var point = new BMap.Point(map_lng, map_lat);
                    var marker = new BMap.Marker(point);  // 创建标注
                    map.addOverlay(marker);               // 将标注添加到地图中
                    marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
                    old_marker = marker;
                }

                //单击获取点击的经纬度
                map.addEventListener("click", function (e) {
                    setBaiduPoint(e.point.lng, e.point.lat);
                    var pt = e.point;
                    geoc.getLocation(pt, function (rs) {
                        var addComp = rs.addressComponents;
                        $("#baiduTxt").text(addComp.province + " " + addComp.city + " " + addComp.district + " " + addComp.street + " " + addComp.streetNumber + " 经纬度：(" + e.point.lng + ", " + e.point.lat + ")");
                    });
                });

                map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
                map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

                //添加
                $("#addStreeBtn").on('click', function () {
                    location.href = "${base}/market/streetEdit.do";
                });

                var _streets = {};
            <#if streets?exists && (streets?size > 0) >
                <#list streets as street>
                    _streets[${street.id?c}] = "${street.name}";
                </#list>
            </#if>


                showLocation(
                        <#if provinceId?exists >${provinceId?c}<#else>0</#if>,
                        <#if cityId?exists >${cityId?c}<#else>0</#if> ,
                        <#if areaId?exists >${areaId?c}<#else>0</#if> ,
                        <#if streetId?exists >${streetId?c}<#else>0</#if> ,
                        _streets, function (id, func) {
                            $.get("${base}/market/getStreets.do?districtId=" + id, function (data) {
                                func(id, data);
                            }, "json");
                        });

                moveEnd($("#mName").get(0));

                $("#locationBtn").on('click', function () {
                    $("#mLocation").val(map_lng + ", " + map_lat);
                });

                $("#backBtn").on('click', function () {
                    history.back(-1);
                });




//                var valid = $('#formDefault').Validform({
//                    tiptype:4,
//                    label:".help-block",
//                    showAllError:true,
//                    ajaxPost:true,
//                    postonce:true,
//                    beforeSubmit:function(curform){
//                        var con = getContent();
//                        $('#detail_text').val(encodeURIComponent(con));
//                    },
//                    callback:function(data){
//
//                        if (data.success == true) {
//                            //alert(data.msg);
//                            location.reload(true);
//                            //window.location.href="/order/api.php?s=/home/menu/lists&merchant_id="+data.merchant_id+"";
//                            show_notify(data.msg);
//                        } else {
//                            if (data.msg == null) {
//                                data.msg = '系统错误，请重试';
//                            }
//                            $('#btnDefault').button('reset');
//                            alert(data.msg);
//                            // show_notify(data.msg);
//                        }
//
//                    }
//                }); // end validform
//
//                valid.addRule(
//                        [{
//                            ele:'#name',
//                            datatype:'*',
//                            nullmsg:'不能为空',
//                            errormsg:'格式错误',
//                            sucmsg:'√'
//                        },
//                            {
//                                ele:'#price',
//                                datatype:/^\d+(\.\d{1,2})?$/,
//                                nullmsg:'不能为空',
//                                errormsg:'价格为数字，且最多保留两位小数',
//                                sucmsg:'√'
//                            },
//                            {
//                                ele:'#repertory',
//                                datatype:'n',
//                                nullmsg:'x',
//                                errormsg:'请输入正确的数字',
//                                sucmsg:'√'
//                            },
//                            {
//                                ele:'#today_repertory',
//                                datatype:'n',
//                                nullmsg:'x',
//                                errormsg:'请输入正确的数字',
//                                sucmsg:'√'
//                            },
//                            {
//                                ele:'#menu_sort',
//                                datatype:'n',
//                                nullmsg:'x',
//                                errormsg:'请输入正确的数字',
//                                sucmsg:'√'
//                            },
//                            {
//                                ele:'#menu_img',
//                                datatype:'url',
//                                nullmsg:'请上传菜单图片',
//                                errormsg:'格式错误',
//                                sucmsg:'√'
//                            },
//                            {
//                                ele:'#barcode',
//                                datatype:'*',
//                                nullmsg:'不能为空',
//                                errormsg:'格式错误',
//                                sucmsg:'√',
//                                ignore:'ignore'
//                            },
//                            {
//                                ele:'#stock_price',
//                                datatype:/^\d+(\.\d{1,2})?$/,
//                                nullmsg:'不能为空',
//                                errormsg:'格式错误',
//                                sucmsg:'√',
//                                ignore:'ignore'
//                            }
//                        ]
//
//                );

                function getV(ui_name, info) {
                    var obj = $("#"+ui_name).find("option:selected");
                    var id = obj.attr("data_id");
                    if(id == 0) {
                        swal(info);
                        return null;
                    }
                    return obj.attr("data_v");
                }

                function getVs(ui_name, info) {
                    var obj = $("#"+ui_name).find("option:selected");
                    var id = obj.attr("data_id");
                    if(id == 0) {
                        swal(info);
                        return null;
                    }
                    return {id:id,name:obj.attr("data_v")};
                }

                $("#saveBtn").on('click', function () {

                    var _province = getV("loc_province", "请选择省份");
                    if(_province == null) return;

                    var _city = getV("loc_city", "请选择城市");
                    if(_city == null) return;

                    var _district = getV("loc_district", "请选择区");
                    if(_district == null) return;

                    var _street = getVs("loc_street", "请选择镇");
                    if(_street == null) return;

                    var post_data = {
                        marketId:_marketId,
                        name: $("#mName").val(),
                        province:_province,
                        city:_city,
                        district:_district,
                        streetId:_street.id,
                        streetName:_street.name,
                        streetNumber:$("#mStreetNumber").val(),
                        address:$("#mStreetNumber").val(),
                        location:$("#mLocation").val(),
                        deliveryMode:$("#mDeliveryMode").find("option:selected").attr("data_id"),
                        distance:$("#mDistance").val(),
                        deliveryCost:$("#mDeliveryCost").val(),
                    };

                    $.post("${base}/market/marketEditSave.do", post_data, function (data) {

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