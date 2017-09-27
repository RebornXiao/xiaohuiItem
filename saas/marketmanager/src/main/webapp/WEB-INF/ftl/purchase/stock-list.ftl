<div class="content-page">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title-box">
                        <ol class="breadcrumb pull-right">
                            <li><a href="#">首页</a></li>
                            <li><a href="#">采购端管理</a></li>
                            <li class="active"><a href="#">商品库存列表</a></li>
                        </ol>
                        <h4 class="page-title"><b>商品库存列表</b></h4>
                    </div>
                </div>
            </div>

            <div class="card-box">
                <form class="form-inline" role="form" action="${base}/purchase/commodityStoresPage.do">
                    <div class="form-group m-l-15">
                        <label>仓库名称：</label>
                        <select id="houseSelect" class="form-control" name="warehouseCode">
                            <option value="">选择仓库</option>
                        <#if warehouseItem?exists >
                            <#list warehouseItem as warehouse>
                                <option value="${warehouse.warehouseCode}">${warehouse.warehouseName}</option>
                            </#list>
                        </#if>
                        </select>
                    </div>
                    <div class="form-group m-l-15">
                        <label>商品名称：</label>
                        <input type="text" class="form-control" id="goodsName" placeholder="输入商品名称" name="itemName">
                    </div>
                    <div class="form-group m-l-15">
                        <label>条形码：</label>
                        <input type="text" class="form-control" id="goodsCode" placeholder="输入商品条形码" name="barcode">
                    </div>
                    <div class="form-group m-l-15">
                        <button type="submit" class="btn btn-primary" id="searchBtn"><i class="fa fa-search"></i> 搜索</button>
                    </div>
                </form>
            </div>

            <div class="row">
                <div class="col-sm-12">
                    <table class="table table-striped table-bordered">
                        <thead class="table_head">
                        <tr>
                        	<th>ID</th>
                            <th>仓库名称</th>
                            <th>商品分类</th>
                            <th>商品名称</th>
                            <th>库存数量</th>
                            <th>库存预警</th>
                            <th>更新时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="stockInfoTable">
                        <#if (stocks?size > 0)>
                            <#list stocks as stock>
                            <tr id="tr_${stock_index}">
                                <td>${stock.id}</td>
                                <td>${stock.warehouseName}</td>
                                <td>${stock.itemTypeName}</td>
                                <td>${stock.itemName}</td>
                                <td>${stock.storesNumber}</td>
                                <td>${stock.warnNumber}</td>
                                <td>${stock.updateTime}</td>
                                <td>
                                    <button id="editBtn" type="button" class="btn btn-primary btn-sm">编辑</button>
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

	//编辑
    <#--<#if (stockList?size > 0)>-->
        <#--$("#stockInfoTable").find('button[id=editBtn]').each(function () {-->
            <#--var that = this;-->
            <#--$(this).on('click', function () {-->
                <#--location.href = "${base}"+$(that).attr("data_id");-->
            <#--});-->
        <#--});-->
    <#--</#if>-->
    });
</script>
