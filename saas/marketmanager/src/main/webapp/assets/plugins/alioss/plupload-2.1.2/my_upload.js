

var uploader = new plupload.Uploader({
    runtimes : 'html5,flash,silverlight,html4',
    browse_button : 'selectFileBtn',
    //multi_selection: false,
    //container: document.getElementById('container'),
    // flash_swf_url : 'js/Moxie.swf',
    // silverlight_xap_url : 'js/Moxie.xap',
    url : 'http://oss.aliyuncs.com',
    //设置只能单选文件
    multi_selection: false,

    filters: {
        mime_types : [ //只允许上传图片和zip,rar文件
            { title : "Image files", extensions : "jpg,gif,png,bmp" },
            // { title : "Zip files", extensions : "zip,rar" }
        ],
        max_file_size : '1mb', //最大只能上传10mb的文件
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
            // plupload.each(files, function(file) {
            //     document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
            //         +'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
            //         +'</div>';
            // });

            //$.each(up.files, function (i, file) {
            //    if (up.files.length <= 1) {
            //        return;
            //    }
            //    up.removeFile(file);
            //});
            alert("原有：" + up.files.length + ", 新增：" + files.length);
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