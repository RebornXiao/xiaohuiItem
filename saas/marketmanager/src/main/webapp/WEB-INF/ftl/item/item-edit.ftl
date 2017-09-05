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
                                        <option data_id="${iType.id}" <#if item?exists && iType.id == item.typeId>
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
                                        <option data_id="${unit.id}" <#if item?exists && unit.id == item.unitId>
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
                                       value="${item.costPrice}" </#if>>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">零售价：</label>
                            <div class="col-md-8">
                                <input type="text" id="defaultPrice" class="form-control" <#if item?exists>
                                       value="${item.defaultPrice}" </#if>>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">商品描述：</label>
                            <div class="col-md-8">
                                <textarea class="form-control" id="itemInfo" rows="5"></textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">商品主图：</label>
                            <div class="col-md-4">
                                <div id="itemMainImgDiv">
                                    <img id="itemMainImg" <#if item?exists && item.imageUrl??>
                                         src="${item.imageUrl}" </#if>
                                         width="150px" height="150px"/>
                                </div>
                                <p class="m-t-10">
                                <#--<button id="selectFileBtn" type="button" class="btn waves-effect waves-light btn-default">-->
                                <#--上传主图-->
                                <#--</button>-->
                                    <input type="file" class="btn waves-effect waves-light btn-default"
                                           onchange="previewImage('itemMainImgDiv', 'itemMainImg', this)"></input>
                                </p>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label">商品banner图：</label>
                            <div class="col-md-4">

                                <div id="itemBannerImgDiv">
                                    <img id="itemBannerImg" <#if item?exists && item.bannerUrl??>
                                         src="${item.bannerUrl}" </#if>
                                         width="150px" height="150px"/>
                                </div>
                                <p class="m-t-10">
                                <#--<button id="selectFileBtn" type="button" class="btn waves-effect waves-light btn-default">-->
                                <#--上传主图-->
                                <#--</button>-->
                                    <input type="file" class="btn waves-effect waves-light btn-default"
                                           onchange="previewImage('itemBannerImgDiv', 'itemBannerImg', this)"></input>
                                </p>


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

        <script src="${res}/assets/plugins/alioss/plupload-2.1.2/js/plupload.dev.js"></script>

        <script type="text/javascript">
            $(document).ready(function () {

                moveEnd($("#itemName").get(0));

                //添加类型
                $("#addTypeBtn").on('click', function () {
                    location.href = "${base}/item/itemTypeEdit.do";
                });

                //添加单位
                $("#addUnitBtn").on('click', function () {
                    location.href = "${base}/item/itemUnitEdit.do";
                });

                $("#backBtn").on('click', function () {
                    history.back(-1);
                });

                $("#typeSelect").select2();

                //添加商品
                $("#saveBtn").on('click', function () {

                    //检测所有项

                <#--$.post("${base}/item/itemUnitEditSave.do?id="+itemUnitId+"&title="+title+"&status="+status, function(data) {-->
                <#--//重新刷新-->
                <#--if(data.code == "0") {-->
                <#--swal("提示", "操作成功", "success");-->
                <#--location.reload();-->
                <#--} else {-->
                <#--swal(data.msg);-->
                <#--}-->
                <#--}, "json");-->
                });
            });
        </script>

        <script type="text/javascript">


                var uploader = new plupload.Uploader({
                    url: "http://oss.aliyun.com",//服务器端的上传页面地址
                    max_file_size: '2mb',//限制为2MB
                    filters: [{title: "Image files", extensions: "jpg,gif,png"}]//图片限制
                });
                //在实例对象上调用init()方法进行初始化
                uploader.init();
                //绑定各种事件，并在事件监听函数中做你想做的事
                uploader.bind('FilesAdded', function (uploader, files) {
                    //uploader.start();
                    alert("原有：" + uploader.files.length + ", 新增：" + files.length);
                });
                uploader.bind('FileUploaded', function (uploader, files, data) {
//                    var imgUrl = "http://cdn.sojson.com/";
//                    //这里得到图片的id
//                    var id = self.search("-img") == -1 ? self + "-img" : self;
//                    console.log("现在在上传的身份证是：", self.search('cardzmbtn') == 0 ? '正' : '反', "面");
//                    //成功判断
//                    if (data.status == 200) {
//                        data = $.parseJSON(data.response);
//                        var imagePath = imgUrl + data.file
//                        //图片赋值
//                        document.getElementById(id).src = imagePath;
//                        //正面
//                        if (self.search('cardzmbtn') === 0) {
//                            $("#cardzmbtn-input").val(imagePath).attr('src-data', data.file);
//                        } else {//反面
//                            $("#cardbmbtn-input").val(imagePath).attr('src-data', data.file);
//                        }
//                    }
                });

                var changeFile = {};

                //图片上传预览    IE是用了滤镜。
                function previewImage(divname, imgname, file) {

                    //如果原来存在，则先删除
                    var old_file = changeFile[imgname];
                    if (old_file != null) {
                        //先删除
                        uploader.removeFile(file);
                    }

                    changeFile[imgname] = file;
                    //添加回去
                    uploader.addFile(file);

                    var MAXWIDTH = 260;
                    var MAXHEIGHT = 180;
                    var div = document.getElementById(divname);
                    if (file.files && file.files[0]) {
                        div.innerHTML = "<img id=\"" + imgname + "\">";
                        var img = document.getElementById(imgname);
                        img.onload = function () {
                            var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                            img.width = rect.width;
                            img.height = rect.height;
                            img.style.marginTop = rect.top + 'px';
                        }
                        var reader = new FileReader();
                        reader.onload = function (evt) {
                            img.src = evt.target.result;
                        }
                        reader.readAsDataURL(file.files[0]);
                    }
                    else //兼容IE
                    {
                        var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
                        file.select();
                        var src = document.selection.createRange().text;
                        div.innerHTML = "<img id=\"" + imgname + "\">";
                        var img = document.getElementById(imgname);
                        img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
                        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                        status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width + ',' + rect.height);
                        div.innerHTML = "<div id=divhead style='width:" + rect.width + "px;height:" + rect.height + "px;margin-top:" + rect.top + "px;" + sFilter + src + "\"'></div>";
                    }
                    //添加到 puload
                }

                function clacImgZoomParam(maxWidth, maxHeight, width, height) {
                    var param = {top: 0, left: 0, width: width, height: height};
                    if (width > maxWidth || height > maxHeight) {
                        rateWidth = width / maxWidth;
                        rateHeight = height / maxHeight;

                        if (rateWidth > rateHeight) {
                            param.width = maxWidth;
                            param.height = Math.round(height / rateWidth);
                        } else {
                            param.width = Math.round(width / rateHeight);
                            param.height = maxHeight;
                        }
                    }

                    param.left = Math.round((maxWidth - param.width) / 2);
                    param.top = Math.round((maxHeight - param.height) / 2);
                    return param;
                }


        </script>