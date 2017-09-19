<div class="content-page" style="background-color: #fff">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12 ">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">广告端管理</a></li>
                            <li class="active"><a href="#">投放广告</a></li>
                        </ol>
                        <h4 class="page-title"><b>投放管理</b></h4>
                    </div>
                </div>
            </div>

            <div class="" style="padding:0px 20px 20px 20px">
                <button id="adPlayBtn" type="button" class="btn btn-primary btn-lg"><i class="fa fa-play-circle"></i> 广告播放</button>
                <button id="adScreenEditBtn" type="button" class="btn btn-default btn-lg"><i class="fa fa-edit"></i> 屏幕配置</button>
            </div>
            <div class="card-box">
                <form class="form-inline" role="form" action="${base}/advert/advertScreens.do">
                    <div class="form-group m-l-35">
                        <label>门店信息:</label>
                        <select class="form-control" id="marketSelect" name="marketID">
                            <option value="-1">全部店铺</option>
                            <#if markets?exists >
                                <#list markets as market>
                                    <option value="${market.id?c}">
                                    ${market.name}
                                    </option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="form-group m-l-35">
                        <label>屏幕编号:</label>
                        <input type="text" class="form-control" id="screenNum" placeholder="输入编号" name="code">
                    </div>
                    <div class="form-group m-l-35">
                        <label>广告标题:</label>
                        <input type="text" class="form-control" id="advertNavTitle" placeholder="输入广告标题关键字" name="title">
                    </div>
                    <div class="form-group m-l-35">
                        <label>日期:</label>
                        <div class="input-group">
                            <input id="startTime" type="text" class="form-control" name="beginTime" readonly>
                            <span class="input-group-addon bg-default"
                                  onClick="jeDate({dateCell:'#startTime',isTime:true,format:'YYYY-MM-DD 00:00:00'})"><i
                                    class="fa fa-calendar"></i></span>
                        </div>
                        <label> &nbsp;至&nbsp; </label>
                        <div class="input-group">
                            <input id="endTime" type="text" class="form-control" name="endTime" readonly>
                            <span class="input-group-addon bg-default"
                                  onClick="jeDate({dateCell:'#endTime',isTime:true,format:'YYYY-MM-DD 00:00:00'})"><i
                                    class="fa fa-calendar"></i></span>
                        </div>
                    </div>
                    <div class="form-group m-l-35">
                        <label>是否下载:</label>
                        <select class="form-control" id="isdownSelect" name="isDown">
                            <option value="-1"<#if isDown == -1>selected</#if>>全部</option>
                            <option value="0"<#if isDown == 0>selected</#if>>是</option>
                            <option value="1"<#if isDown == 1>selected</#if>>否</option>
                        </select>
                    </div>
                    <div class="form-group m-l-35">
                        <label>播放状态:</label>
                        <select class="form-control" id="statusSelect" name="playStatus">
                            <option value="-1"<#if playStatus == -1>selected</#if>>全部</option>
                            <option value="0"<#if playStatus == 0>selected</#if>>待播放</option>
                            <option value="1"<#if playStatus == 1>selected</#if>>播放中</option>
                            <option value="2"<#if playStatus == 2>selected</#if>>已停止</option>
                            <option value="3"<#if playStatus == 3>selected</#if>>已移除</option>
                        </select>
                    </div>
                    <#--<div class="form-group pull-right m-l-15" id="isdownBtnGroup">-->
                        <#--<label>是否下载：</label>-->
                        <#--<button type="button" class="btn btn-default btn-primary focus" value="-1">全部</button>-->
                        <#--<button type="button" class="btn btn-default" value="0">是</button>-->
                        <#--<button type="button" class="btn btn-default" value="1">否</button>-->
                    <#--</div>-->
                    <#--<div class="form-group m-l-15 m-t-20" id="isplayBtnGroup">-->
                        <#--<label>播放状态：</label>-->
                        <#--<button type="button" class="btn btn-default btn-primary action">全部</button>-->
                        <#--<button type="button" class="btn btn-default">待播放</button>-->
                        <#--<button type="button" class="btn btn-default">播放中</button>-->
                        <#--<button type="button" class="btn btn-default">已停止</button>-->
                        <#--<button type="button" class="btn btn-default">已移除</button>-->
                    <#--</div>-->
                    <div class="form-group  m-l-25">
                        <button type="submit" class="btn btn-primary" id="searchBtn"><i class="fa fa-search"></i> 搜索 </button>
                    </div>
                </form>
            </div>

            <button type="button" class="btn btn-primary pull-right m-b-15" data-toggle="modal" id="addPlayButton" style="padding-left: 30px;padding-right: 30px;margin-right: 30px;margin-bottom: 20px"><i class="fa fa-plus"></i> 添加广告播放</button>
            <div class="row">
                <div class="col-sm-12">
                    <table class="table table-striped table-bordered">
                        <thead class="table_head">
                        <tr>
                            <th>序号</th>
                            <th>门店信息</th>
                            <th>屏幕编号</th>
                            <th>广告标题</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>是否下载</th>
                            <th>播放状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="storeInfoListTable">
                        <#if (advertScreens?size > 0)>
                            <#list advertScreens as advert>
                            <tr id="tr_${advert_index}">
                                <td>${advert_index +1}</td>
                                <td>${advert.marketName}</td>
                                <td>${advert.sCode}</td>
                                <td>${advert.title}</td>
                                <td>${advert.beginTime}</td>
                                <td>${advert.endTime}</td>
                                <#if advert.isDown=0><td><b>是</b></td>
                                <#else><td class="text-danger"><b>否</b></td></#if>
                                <td>
                                    <#if advert.playStatus=0><span class="label label-primary">待播放</span>
                                    <#elseif advert.playOrder=1><span class="label label-danger">播放中</span>
                                    <#elseif advert.playOrder=2><span class="label label-warning">已停止</span>
                                    <#elseif advert.playOrder=3><span class="label label-default">已移除</span></#if>
                                </td>
                                <td>
                                    <button id="lookBtn" type="button"
                                            class="btn btn-primary btn-sm"
                                            a_id="${advert.advertID?c}" m_id="${advert.marketID}" s_id="${advert.screenID?c}">查看
                                    </button>
                                    <button id="editBtn" type="button" data-target="#editModel"
                                        class="btn btn-primary btn-sm" data-toggle="model"
                                        a_id="${advert.advertID?c}" m_id="${advert.marketID}" s_id="${advert.screenID?c}">编辑
                                    </button>
                                    <button id="deleBtn" type="button" data-target="#deleModel"
                                            class="btn btn-danger btn-sm" data-toggle="model"
                                            a_id="${advert.advertID?c}" m_id="${advert.marketID}" s_id="${advert.screenID?c}">删除
                                    </button>
                                </td>
                            </tr>
                            </#list>
                        <#else>
                            <tr>
                                <td colSpan="11" height="200px">
                                    <p class="text-center" style="line-height: 200px">暂无任何数据</p>
                                </td>
                            </tr>
                        </#if>
                        </tbody>
                    </table>
                </div>
            </div>

            <!--分页-->
            <div class="row small_page">
                <div class="col-sm-12">
                <#include "../common/paginate.ftl">
                <@paginate nowPage=pageIndex itemCount=count action="${base}/advert/advertScreens.do?marketID=${marketID}&code=${code}&title=${title}&beginTime=${beginTime}&endTime=${endTime}&isDown=${isDown}&playStatus=${playStatus}"/>
                </div>
            </div>
            <!--/分页-->

        </div>
    </div>
</div>
<script type="text/javascript" src="${res}/assets/plugins/jedate/jedate.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //取url参数给表单赋值
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)return  unescape(r[2]); return null;
        }
        var add_title = GetQueryString("title");
        var add_code = GetQueryString("code");
        document.getElementById("advertNavTitle").value=add_title;
        document.getElementById("screenNum").value=add_code;
        //鼠标经过效果
        $("tr[id^='tr_']").hover(
            function(){ // onmouseover
                $(this).css("background-color", "#FFFFBF"); // 设置背景颜色
            },
            function(){ // onmouseout
                // 代表当前行对应的checkbox没有选中
                if (!$(this.id.replace("tr_", "")).attr("checked")){
                    $(this).css("background-color", "#FFFFFF"); // 还原背景颜色
                }
            }
        );
        //单选按钮组处理
        /*$("#isdownBtnGroup").find('button').each(function () {
            $(this).on('click',function () {
                console.log(this);
                if(!$(this).hasClass('btn-primary')){
                    $("#isdownBtnGroup button").removeClass("btn-primary focus");
                    $(this).addClass("btn-primary focus");
                }
            })
        });
        $("#isplayBtnGroup").find('button').each(function () {
            $(this).on('click',function () {
                if(!$(this).hasClass('btn-primary')){
                    $("#isplayBtnGroup button").removeClass("btn-primary action");
                    $(this).addClass("btn-primary action");
                }
            });
        });*/
        //搜索功能
        $("#searchBtn").on('click', function () {
            var s_store = $("#marketSelect option:selected").val();//门店值
            var s_number = $("#screenNum").val();//屏幕编号
            var s_title = $("#advertNavTitle").val();//广告标题
            var s_time = $("#startTime").val();//开始时间
            var s_etime = $("#endTime").val();//结束时间
            var s_isDown = $("#isdownSelect option:selected").val();//下载状态
            var s_playStatus = $("#statusSelect option:selected").val();//播放状态
            var url ="${base}/advert/advertScreens.do?marketID="+s_store+"&code="+s_number+"&title="+s_title+
                    "&beginTime="+s_time+"&endTime="+s_etime+"&isDown="+s_isDown+"&playStatus="+s_playStatus;
                console.log(url);
            $.get(url, function(data) {
                if(data.code == "0") {

                } else {
                    swal(data.msg);
                }
            }, "json");
        });
        //编辑
        <#if (advertScreens?size > 0)>
            $("#storeInfoListTable").find('button[id=editBtn]').each(function () {
                var that = this;
                $(this).on('click', function () {
                    $("#editModel").modal('show');
                    $.get("${base}/advert/getAdvertScreen.do?advertID=" +$(that).attr("a_id")+ "&screenID=" +$(that).attr("s_id")+ "&marketID=" +$(that).attr("m_id"),function(object){//取该行列表全部信息
                        $("#editNoBtn").on('click',function () {
                            $("#editModel").modal('hide');
                        });
                        console.log(object);
                        $("#editTitle").val(object.title);
                        $("#editTime").val(object.timeSize);
                        $("#editRemark").val(object.advertRemark);
                        $("#editStoreInfo option:selected").val(object.marketID);
                        $("#editStoreInfo option:selected").text(object.marketName);
                        $("#editScreenNum option:selected").val(object.sCode);
                        $("#editScreenNum option:selected").text(object.sCode);
                        $("#editStartTime").val(object.beginTime);
                        $("#editEndTime").val(object.endTime);
                        $("#editPlaySort").val(object.playOrder);
                        $("#editPlayRemark").val(object.remark);
                        $("#editYesBtn").on('click',function () {
                            var arr ={
                                "title":$("#editTitle").val(),//标题
                                "time":$("#editTime").val(),//时长
                                "remark":$("#editRemark").val(),//广告备注
                                "storeInfo":$("#editStoreInfo").val(),//门店
                                "storeIndex":$("#editStoreInfo").get(0).selectedIndex,//门店index
                                "editScreenNum":$("#editScreenNum").val(),//屏幕编号
                                "screenNumIndex":$("#editScreenNum").get(0).selectedIndex,//屏幕编号index
                                "e_stime":$("#editStartTime").val(),//开始时间
                                "e_etime":$("#editEndTime").val(),//结束时间
                                "e_sort":$("#editPlaySort").val(),//播放排序
                                "e_pRemark":$("#editPlayRemark").val(),//播放备注
                            };
                            var input1 = checkInput(arr.e_stime);
                            var input2 = checkInput(arr.e_etime);
                            var input3 = checkInput(arr.e_sort);
                            var input4 = checkInput(arr.e_pRemark);
                            function checkInput (obj){//检查表单是否有空项，空格验证方法待加
                                if(obj == "") {return false;} else {return true;}
                            }
                            if(input1 && input2 && input3 && input4) {
                                var url="${base}/advert/updateScreenAdvert.do?advertID=" +$(that).attr("a_id")+ "&screenID="
                                        +$(that).attr("s_id")+ "&marketID=" +$(that).attr("m_id")+ "&beginTime="+arr.e_stime+"&endTime="
                                        +arr.e_etime+"&playOrder="+arr.e_sort+"&remark="+arr.e_pRemark+"&sCode="+arr.editScreenNum;
                                console.log(url);
                                $.get(url, function(data) {
                                    //重新刷新
                                    console.log(data);
                                    if(data.code == "0") {
                                        $("#editBtn").modal('hide');
                                        swal("提示", "更新成功", "success");
                                        setTimeout(function(){location.reload();},1000);
                                    } else {
                                        swal("提示", "更新失败", "error");
                                    }
                                }, "json");
                            } else {
                                swal("提示", "请检查表单是否有漏填项！", "info");
                            }
                        });
                    },"json");
                });
            });
        </#if>
        //删除
        <#if (advertScreens?size > 0)>
            $("#storeInfoListTable").find('button[id=deleBtn]').each(function () {
                var that = this;
                $(this).on('click', function () {
                    console.log($(that).attr("a_id"));
                    $("#deleModel").modal('show');
                    $("#deleNoBtn").on('click',function () {
                        $("#deleModel").modal('hide');
                    });
                    $("#deleYesBtn").on('click',function () {
                        $.post("${base}/advert/delScreenAdvert.do?advertID=" +$(that).attr("a_id")+ "&screenID=" +$(that).attr("s_id")+ "&marketID=" +$(that).attr("m_id"), function(data) {
                            //重新刷新
                            if(data.code == "0") {
                                $("#deleModel").modal('hide');
                                swal("提示", "删除成功", "success");
                                setTimeout(function(){location.reload();},1000);
                            } else {
                                swal("提示", "删除失败", "error");
                            }
                        }, "json");
                    });
                });
            });
        </#if>
        //查看
        <#if (advertScreens?size > 0)>
            $("#storeInfoListTable").find('button[id=lookBtn]').each(function () {
                var that = this;
                $(this).on('click', function () {
                    location.href = "${base}/advert/goAdvertScreen.do?advertID="+$(that).attr("a_id")+"&screenID="+$(that).attr("s_id")+"&marketID="+$(that).attr("m_id");
                });
            });
        </#if>
        $("#adScreenEditBtn").on('click', function () {//屏幕配置
            location.href = "${base}/advert/screens.do";
        });
        $("#addPlayButton").on('click', function () {//添加广告播放
            location.href = "${base}/advert/goAddAdvertScreen.do";
        });
    });
</script>
<!--编辑弹窗-->
<div class="modal fade" id="editModel" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">编辑广告播放</h4>
            </div>
            <div class="modal-body">
                <div class="modalAdvertStyle">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <fieldset disabled>
                            <label class="col-md-4 control-label">广告标题：</label>
                            <div class="col-md-6">
                                <input type="text" class="form-control" id="editTitle"/>
                            </div>
                            </fieldset>
                        </div>
                        <div class="form-group">
                            <fieldset disabled>
                            <label class="col-md-4 control-label">广告时长：</label>
                            <div class="col-md-3">
                                <input type="text" class="form-control" id="editTime"/>
                            </div>
                            </fieldset>
                        </div>
                        <div class="form-group">
                            <fieldset disabled>
                            <label class="col-md-4 control-label">广告备注：</label>
                            <div class="col-md-6">
                                <input type="text" class="form-control" id="editRemark"/>
                            </div>
                            </fieldset>
                        </div>
                        <div class="form-group">
                            <fieldset disabled>
                            <label class="col-md-4 control-label">门店信息：</label>
                            <div class="col-md-6">
                                <select class="form-control" id="editStoreInfo">
                                    <option value=""></option>
                                <#--<#if markets?exists >-->
                                    <#--<#list markets as merket>-->
                                        <#--<option value="${merket.id?c}">${merket.name}</option>-->
                                    <#--</#list>-->
                                <#--</#if>-->
                                </select>
                            </div>
                            </fieldset>
                        </div>
                        <div class="form-group">
                            <fieldset disabled>
                            <label class="col-md-4 control-label">屏幕编号：</label>
                            <div class="col-md-6">
                                <select id="editScreenNum" class="form-control">
                                    <option value=""></option>
                                <#--<#if screens?exists >-->
                                    <#--<#list screens as screen>-->
                                        <#--<option value="${screen.screenID?c}">${screen.code}</option>-->
                                    <#--</#list>-->
                                <#--</#if>-->
                                </select>
                            </div>
                            </fieldset>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">开始时间：</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="editStartTime">
                            </div>
                            <div class="col-md-1">
                                <span class="input-group-addon bg-default"
                                      onClick="jeDate({dateCell:'#editStartTime',isTime:true,format:'YYYY-MM-DD 00:00:00'})">
                                    <i class="fa fa-calendar"></i>
                                </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">结束时间：</label>
                            <div class="col-md-5">
                                <input type="text" class="form-control" id="editEndTime">
                            </div>
                            <div class="col-md-1">
                                <span class="input-group-addon bg-default"
                                      onClick="jeDate({dateCell:'#editEndTime',isTime:true,format:'YYYY-MM-DD 00:00:00'})">
                                    <i class="fa fa-calendar"></i>
                                </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">播放排序：</label>
                            <div class="col-md-3">
                                <input type="text" class="form-control" id="editPlaySort"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">播放备注：</label>
                            <div class="col-md-6">
                                <textarea class="form-control" rows="4" id="editPlayRemark"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer" style="text-align: center">
                <button id="editNoBtn" type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button id="editYesBtn" type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
            </div>
        </div>
    </div>
</div>

<!--删除弹窗-->
<div class="modal fade" id="deleModel" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">删除广告</h4>
            </div>
            <div class="modal-body">
                <p style="text-align: center">确定要删除该广告吗？</p>
            </div>
            <div class="modal-footer" style="text-align: center">
                <button id="deleNoBtn" type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button id="deleYesBtn" type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
            </div>
        </div>
    </div>
</div>