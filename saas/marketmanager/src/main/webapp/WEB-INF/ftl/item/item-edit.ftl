<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->

<link href="${res}/assets/plugins/bootstrap-select2/select2.min.css" rel="stylesheet" type="text/css">

<script src="${res}/assets/plugins/bootstrap-select2/select2.min.js"></script>
<script src="${res}/assets/plugins/bootstrap-select2/zh-CN.js"></script>


<script type="text/javascript">
    function clearNoNum(obj) {
//先把非数字的都替换掉，除了数字和.   
        obj.value = obj.value.replace(/[^\d.]/g, "");
//必须保证第一个为数字而不是.   
        obj.value = obj.value.replace(/^\./g, "");
//保证只有出现一个.而没有多个.   
        obj.value = obj.value.replace(/\.{2,}/g, ".");
//保证.只出现一次，而不能出现两次以上   
        obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");

        //最后保证是正确的数字
        var str = obj.value;
        if (str.length > 1) {
            //第一个是否为0
            if(str.charAt(0) == '0' && str.charAt(1) != '.') {
                obj.value = "0";
            }
        }
    }
</script>

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
                                <#--<input type="file" class="btn waves-effect waves-light btn-default"-->
                                <#--onchange="previewImage('itemMainImgDiv', 'itemMainImg', this)"></input>-->
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

                    //如果有图片，先执行上传图片操作
                    $(this).button("loading");

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

            var haveUpImg = false;

            function send_request()
            {
                var xmlhttp = null;
                if (window.XMLHttpRequest)
                {
                    xmlhttp=new XMLHttpRequest();
                }
                else if (window.ActiveXObject)
                {
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }

                if (xmlhttp!=null)
                {
                    serverUrl = './php/get.php';
                    xmlhttp.open( "GET", serverUrl, false );
                    xmlhttp.send( null );
                    return xmlhttp.responseText
                }
                else
                {
                    alert("Your browser does not support XMLHTTP.");
                }
            };

            function get_signature()
            {
                //可以判断当前expire是否超过了当前时间,如果超过了当前时间,就重新取一下.3s 做为缓冲
                now = timestamp = Date.parse(new Date()) / 1000;
                if (expire < now + 3)
                {
                    body = send_request()
                    var obj = eval ("(" + body + ")");
                    host = obj['host'];
                    policyBase64 = obj['policy'];
                    accessid = obj['accessid'];
                    signature = obj['signature'];
                    expire = parseInt(obj['expire']);
                    callbackbody = obj['callback'];
                    key = obj['dir'];
                    return true;
                }
                return false;
            };

            function random_string(len) {
                len = len || 32;
                var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
                var maxPos = chars.length;
                var pwd = '';
                for (i = 0; i < len; i++) {
                    pwd += chars.charAt(Math.floor(Math.random() * maxPos));
                }
                return pwd;
            }

            function get_suffix(filename) {
                pos = filename.lastIndexOf('.')
                suffix = ''
                if (pos != -1) {
                    suffix = filename.substring(pos)
                }
                return suffix;
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

            var uploader = new plupload.Uploader({
                browse_button : 'selectFileBtn',
                url : 'http://oss.aliyuncs.com',
                multi_selection: false,//设置只能单选文件

                filters: {
                    mime_types : [ //只允许上传图片和zip,rar文件
                        { title : "Image files", extensions : "jpg,png" },
                    ],
                    max_file_size : '1mb', //最大只能上传1mb的文件
                    prevent_duplicates : false //不允许选取重复文件
                },

                init: {
                    // PostInit: function() {
                    // document.getElementById('ossfile').innerHTML = '';
                    // document.getElementById('postfiles').onclick = function() {
                    //     set_upload_param(uploader, '', false);
                    //     return false;
                    // };
                    // },

                    FilesAdded: function(up, files) {
                        haveUpImg = true;
                        //保证只上传1张图片，这个方法的意思是删除旧的图片
                        $.each(up.files, function (i, file) {
                            if (up.files.length <= 1) {
                                return;
                            }
                            up.removeFile(file);
                        });
                        var file = files[0];

                        var preloader = new mOxie.Image();
                        preloader.onload = function () {
                            var imgsrc = preloader.type == 'image/jpeg' ? preloader.getAsDataURL('image/jpeg', 80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
                            file.imgsrc = imgsrc;
                            var targetImg = $("#upImg");
                            targetImg.attr("src", imgsrc);
                            var rect = clacImgZoomParam(150, 150, preloader.width, preloader.height);
                            targetImg.css({
                                "width": rect.width + "px",
                                "height": rect.height + "px",
                                "marginTop": rect.top + "px",
                                "marginLeft": rect.left + "px"
                            });
                            preloader.destroy();
                            preloader = null;
                        };
                        preloader.load(file.getSource());
                    },

                    BeforeUpload: function(up, file) {
                        //check_object_radio();
                        //set_upload_param(up, file.name, true);
                    },

                    UploadProgress: function(up, file) {
                        //var d = document.getElementById(file.id);
                        //d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
                        //var prog = d.getElementsByTagName('div')[0];
                        //var progBar = prog.getElementsByTagName('div')[0]
                        //progBar.style.width= 2*file.percent+'px';
                        //progBar.setAttribute('aria-valuenow', file.percent);
                    },

                    FileUploaded: function(up, file, info) {
                        if (info.status == 200)
                        {
                            //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = 'upload to oss success, object name:' + get_uploaded_object_name(file.name);
                        }
                        else
                        {
                            //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
                        }
                    },

                    Error: function(up, err) {
                        if (err.code == -600) {
                            //document.getElementById('console').appendChild(document.createTextNode("\n选择的文件太大了,可以根据应用情况，在upload.js 设置一下上传的最大大小"));
                        }
                        else if (err.code == -601) {
                            //document.getElementById('console').appendChild(document.createTextNode("\n选择的文件后缀不对,可以根据应用情况，在upload.js进行设置可允许的上传文件类型"));
                        }
                        else if (err.code == -602) {
                            //document.getElementById('console').appendChild(document.createTextNode("\n这个文件已经上传过一遍了"));
                        }
                        else
                        {
                            //document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
                        }
                    }
                }
            });

            uploader.init();

            function startUp(callback) {
                //如果没有图片
                if(!haveUpImg) {
                    callback(0);
                    return;
                }
                //开始上传
                callback(1);
                //执行上传操作
                uploader
            }

        </script>