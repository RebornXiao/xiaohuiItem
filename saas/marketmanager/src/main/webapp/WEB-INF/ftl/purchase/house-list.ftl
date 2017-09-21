<div class="content-page">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">采购端管理</a></li>
                            <li class="active"><a href="#">仓库列表</a></li>
                        </ol>
                        <h4 class="page-title"><b>仓库列表</b></h4>
                    </div>
                </div>
            </div>

            <div class="card-box">
                <form class="form-inline" role="form" action="${base}">
                    <div class="form-group m-l-15">
                        <label>仓库名称：</label>
                        <select id="houseSelect" class="form-control" name="timeType">
                            <option value="-1">选择仓库</option>
                            <option value="0">淘金仓</option>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <label>状态：</label>
                        <select id="status" class="form-control" name="timeType">
                            <option value="-1">全部</option>
                            <option value="0">停用</option>
                            <option value="1">正常</option>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <button type="submit" class="btn btn-primary" id="searchBtn"><i class="fa fa-search"></i> 搜索</button>
                    </div>
                </form>
            </div>

            <button type="button" class="btn btn-primary pull-right" style="padding-left: 30px;padding-right: 30px;margin-right: 30px;margin-bottom: 20px"><i class="fa fa-plus"></i> 新建仓库</button>
            <div class="row">
                <div class="col-sm-12">
                    <table class="table table-striped table-bordered">
                        <thead class="table_head">
                        <tr>
                            <th>仓库编码</th>
                            <th>名称</th>
                            <th>地址</th>
                            <th>仓管员/位</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="houseInfoTable">
                        <#--<#if (advertList?size > 0)>-->
                            <#--<#list advertList as advert>-->
                            <#--<tr id="tr_${advert_index}">-->
                                <#--<td>${advert_index +1}</td>-->
                                <#--<td>${advert.title}</td>-->
                                <#--<td>${advert.timeSize}</td>-->
                                <#--<td>${advert.timeSize}</td>-->
                                <#--<td>${advert.createTime}</td>-->
                                <#--<td>-->
                                    <#--<button id="lookBtn" type="button" class="btn btn-primary btn-sm"-->
                                            <#--data_title="${advert.title}" data_remark="${advert.remark}"-->
                                             <#--data_time="${advert.timeSize}" data_id="${advert.advertID?c}">查看-->
                                    <#--</button>-->
                                    <#--<button id="editBtn" type="button" data-target="#editModel" class="btn btn-primary btn-sm"-->
                                            <#--data_title="${advert.title}" data_remark="${advert.remark}"-->
                                            <#--data_time="${advert.timeSize}" data_id="${advert.advertID?c}">编辑-->
                                    <#--</button>-->
                                    <#--<button id="deleBtn" type="button" data-target="#deleModel" class="btn btn-danger btn-sm"-->
                                            <#--data_title="${advert.title}" data_remark="${advert.remark}"-->
                                            <#--data_time="${advert.timeSize}" data_id="${advert.advertID?c}">启用-->
                                    <#--</button>-->
                                <#--</td>-->
                            <#--</tr>-->
                            <#--</#list>-->
                        <#--<#else>-->
                        <#--<tr>-->
                            <#--<td colSpan="11" height="200px">-->
                                <#--<p class="text-center" style="line-height: 200px">暂无任何数据</p>-->
                            <#--</td>-->
                        <#--</tr>-->
                        <#--</#if>-->
                        </tbody>
                    </table>
                </div>
            </div>

            <!--分页-->
            <div class="row small_page">
                <div class="col-sm-12">
                <#include "../common/paginate.ftl">
                    <@paginate nowPage=pageIndex itemCount=count action="${base}"/>
                </div>
            </div>
            <!--/分页-->
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
         //取url参数给表单赋值
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)return  unescape(r[2]); return null;
        }

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

        //查看,编辑
    <#--<#if (advertList?size > 0)>-->
        <#--$("#supplierInfoTable").find('button[id=lookBtn]').each(function () {-->
            <#--var that = this;-->
            <#--$(this).on('click', function () {-->
                <#--location.href = "${base}"+$(that).attr("data_id");-->
            <#--});-->
        <#--});-->
        <#--$("#supplierInfoTable").find('button[id=editBtn]').each(function () {-->
            <#--var that = this;-->
            <#--$(this).on('click', function () {-->
                <#--location.href = "${base}"+$(that).attr("data_id");-->
            <#--});-->
        <#--});-->
    <#--</#if>-->
    });
</script>

<!--停用弹窗-->
<div class="modal fade" id="deleModel" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">提示</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                	<div class="form-group">
                        <label class="col-md-4 control-label">请说明停用仓库的原因：</label>                    
                    </div>
                    <div class="form-group">
                        <div class="col-md-6">
                            <textarea class="form-control" rows="4" id="stopRemark"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer" style="text-align: center">
                <button id="stopNoBtn" type="button" class="btn btn-primary" style="padding:10px 80px" data-dismiss="modal">取消</button>
                <button id="stopOkBtn" type="button" class="btn btn-primary" style="padding:10px 80px">确定</button>
            </div>
        </div>
    </div>
</div>