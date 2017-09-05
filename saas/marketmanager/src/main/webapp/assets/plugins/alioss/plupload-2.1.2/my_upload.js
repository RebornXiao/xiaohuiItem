

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




//                var changeFile = {};
//
//                //图片上传预览    IE是用了滤镜。
//                function previewImage(divname, imgname, file) {
//
////                    //如果原来存在，则先删除
////                    var old_file = changeFile[imgname];
////                    if (old_file != null) {
////                        //先删除
////                        uploader.removeFile(file);
////                    }
////
////                    changeFile[imgname] = file;
////                    //添加回去
////                    uploader.addFile(file);
//
//                    var MAXWIDTH = 260;
//                    var MAXHEIGHT = 180;
//                    var div = document.getElementById(divname);
//                    if (file.files && file.files[0]) {
//                        div.innerHTML = "<img id=\"" + imgname + "\">";
//                        var img = document.getElementById(imgname);
//                        img.onload = function () {
//                            var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
//                            img.width = rect.width;
//                            img.height = rect.height;
//                            img.style.marginTop = rect.top + 'px';
//                        }
//                        var reader = new FileReader();
//                        reader.onload = function (evt) {
//                            img.src = evt.target.result;
//                        }
//                        reader.readAsDataURL(file.files[0]);
//                    }
//                    else //兼容IE
//                    {
//                        var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
//                        //file.select();
//                        var src = document.selection.createRange().text;
//                        div.innerHTML = "<img id=\"" + imgname + "\">";
//                        var img = document.getElementById(imgname);
//                        img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
//                        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
//                        status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width + ',' + rect.height);
//                        div.innerHTML = "<div id=divhead style='width:" + rect.width + "px;height:" + rect.height + "px;margin-top:" + rect.top + "px;" + sFilter + src + "\"'></div>";
//                    }
//                    //添加到 puload
//                }
//
//                function clacImgZoomParam(maxWidth, maxHeight, width, height) {
//                    var param = {top: 0, left: 0, width: width, height: height};
//                    if (width > maxWidth || height > maxHeight) {
//                        rateWidth = width / maxWidth;
//                        rateHeight = height / maxHeight;
//
//                        if (rateWidth > rateHeight) {
//                            param.width = maxWidth;
//                            param.height = Math.round(height / rateWidth);
//                        } else {
//                            param.width = Math.round(width / rateHeight);
//                            param.height = maxHeight;
//                        }
//                    }
//
//                    param.left = Math.round((maxWidth - param.width) / 2);
//                    param.top = Math.round((maxHeight - param.height) / 2);
//                    return param;
//                }
