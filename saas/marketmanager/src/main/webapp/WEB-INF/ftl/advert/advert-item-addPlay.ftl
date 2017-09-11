<div class="content-page" style="background-color: #fff">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">广告端管理</a></li>
                            <li class="active"><a href="#">广告播放</a></li>
                        </ol>
                        <h4 class="page-title"><b>添加广告播放</b></h4>
                    </div>
                </div>
            </div>

            <div class="card-box">
                <form class="form-inline" role="form">
                    <div class="form-group m-l-15">
                        <label>门店信息：</label>
                        <select id="storeSelect" class="form-control">
                            <option value="">选择门店</option>
                            <#if markets?exists >
                                <#list markets as market>
                                    <option value="${market.id?c}">${market.name}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <label>屏幕编号：</label>
                        <select id="screenNumSelect" class="form-control">
                            <option value="">选择屏幕编号</option>
                            <#if screens?exists >
                                <#list screens as screen>
                                    <option value="${screen.screenID?c}">${screen.code}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="form-group pull-right">
                        <button type="button" id="selectBtn" class="btn btn-primary" data-toggle="modal"><i class="fa fa-check"></i> 选择广告</button>
                    </div>
                </form>
            </div>

            <div class="row" id="toggle">
                <div class="col-sm-12">
                    <table class="table table-striped table-bordered">
                        <thead class="table_head">
                            <tr>
                                <th>序号</th>
                                <th>广告标题</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>播放顺序</th>
                                <th>播放备注</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody id="screenInfoTable">
                            <tr>
                                <td style="text-align: center">1</td>
                                <td style="text-align: center">xxx的广告</td>
                                <td style="text-align: center"><input type="text" class="form-control" id="stime"></td>
                                <td style="text-align: center"><input type="text" class="form-control" id="stime"></td>
                                <td style="text-align: center"><input type="text" class="form-control" id="stime"></td>
                                <td style="text-align: center"><input type="text" class="form-control" id="stime"></td>
                                <td style="text-align: center">
                                    <button id="deleBtn" type="button" data-target="#deleModel"
                                            class="btn btn-danger btn-sm" data-toggle="model">删除
                                    </button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <button type="button" class="btn btn-primary btn-lg pull-right">确定</button>
                </div>
            </div>

            <!--分页-->
            <div class="row small_page">
                <div class="col-sm-12">
                <#--<#include "../common/paginate.ftl">-->
                <#--<@paginate nowPage=pageIndex itemCount=count action="${base}/item/itemList.do?searchType=${searchType}&searchKey=${searchKey}" />-->
                </div>
            </div>
            <!--/分页-->
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $("#toggle").hide();//每次刷新时先隐藏
          //鼠标经过效果
        $("tr[id^='tr_']").hover(
            function(){ // onmouseover
                $(this).css("background-color", "#FFFFBF") // 设置背景颜色
                        //.css("cursor", "pointer"); // 设置鼠标光标为手状
            },
            function(){ // onmouseout
                if (!$(this.id.replace("tr_", "")).attr("checked")){
                    $(this).css("background-color", "#FFFFFF"); // 还原背景颜色
                }
            }
        );
        //选择广告
        $("#selectBtn").on('click', function () {
            $("#checkModel").modal('show');
//            $("#checkAll").click(function(){
//                $("input[type='checkbox']").attr("checked", this.checked);
//            });
            $('#checkAll').click(function(){
                $("#checkTable tr td").each(function(){
                    $(this).find("input[type=checkbox]").prop("checked", "checked");
                });
            });
            $("#checkYesBtn").on('click',function(){//选择确定
                $("#checkModel").modal('hide');
                $("#toggle").show();
            })
        });
        //删除
        $("#screenInfoTable").find('button[id=deleBtn]').each(function () {
            var that = this;
            $(this).on('click', function () {
                console.log($(that).attr("a_id"));
                $("#deleModel").modal('show');
                $("#deleNoBtn").on('click',function () {
                    $("#deleModel").modal('hide');
                });
                $("#deleYesBtn").on('click',function () {
                    /*$.post("${base}/advert/delScreenAdvert.do?advertID=" +$(that).attr("a_id")+ "&screenID=" +$(that).attr("s_id"), function(data) {
                        //重新刷新
                        if(data.code == "0") {
                            $("#deleModel").modal('hide');
                            swal("提示", "删除成功", "success");
                            setTimeout(function(){location.reload();},5000);
                        } else {
                            swal(data.msg);
                        }
                    }, "json");*/
                });
            });
        });

    });
</script>
<!--选择广告弹窗-->
<div class="modal fade" id="checkModel" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">选择广告</h4>
            </div>
            <div class="modal-body" style="height: 650px;overflow-y:auto;">
                <div class="modalAdvertStyle">
                    <div class="row">
                        <div class="col-sm-12">
                            <table class="table table-striped table-bordered" id="checkTable">
                                <thead class="table_head">
                                    <tr>
                                        <th>
                                            <input id="checkAll" type="checkbox" value=""/>全选
                                        </th>
                                        <th>广告标题</th>
                                        <th>广告时长</th>
                                        <th>备注</th>
                                    </tr>
                                </thead>
                                <tbody id="advertInfoTable">
                                <#if (adverts?size > 0)>
                                    <#list adverts as advert>
                                    <tr id="tr_${advert_index}">
                                        <td style="text-align: center"><input type="checkbox" value="${advert_index}"/></td>
                                        <td style="text-align: center">${advert.title}</td>
                                        <td style="text-align: center">${advert.timeSize}</td>
                                        <td style="text-align: center">${advert.remark}</td>
                                    </tr>
                                    </#list>
                                <#else>
                                    <tr>
                                        <td colSpan="11" height="200px">
                                            <p class="text-center" style="line-height: 200px">暂无任何广告</p>
                                        </td>
                                    </tr>
                                </#if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer" style="text-align: center">
                <button type="button" id="checkNoBtn" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button type="button" id="checkYesBtn" class="btn btn-primary" style="padding:10px 80px">确定</button>
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
                <h4 class="modal-title" id="myModalLabel">删除广告播放</h4>
            </div>
            <div class="modal-body">
                <p style="text-align: center">确定要删除该广告播放吗？</p>
            </div>
            <div class="modal-footer" style="text-align: center">
                <button type="button" id="deleNoBtn" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button type="button" id="deleYesBtn" class="btn btn-primary" style="padding:10px 80px">确定</button>
            </div>
        </div>
    </div>
</div>