<!-- ============================================================== -->
<!-- Start right Content here -->
<!-- ============================================================== -->


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
                <h4 class="header-title m-t-0"><b>搜索订单</b></h4>
                <form class="form-inline" role="form">

                    <div class="form-group m-l-15">
                        <label for="exampleInputName2">地区选择：</label>
                        <select class="form-control" id="itSelect" style="width:150px">
                            <option data_id="0">省</option>
                        </select>
                        <select class="form-control" id="itSelect" style="width:150px">
                            <option data_id="0">市</option>
                        </select>
                        <select class="form-control" id="itSelect" style="width:150px">
                            <option data_id="0">区</option>
                        </select>
                        <select class="form-control" id="itSelect" style="width:150px">
                            <option data_id="0">镇</option>
                        </select>
                        <select class="form-control" id="itSelect" style="width:150px">
                            <option data_id="0">街道</option>
                        </select>
                    </div>

                    <div class="form-group m-l-15">
                        <label for="exampleInputName2">店铺类型：</label>
                        <select class="form-control" id="itSelect" style="width:150px">
                            <option data_id="0">自动化</option>
                        </select>
                    </div>

                    <div class="form-group m-l-15">
                        <label for="exampleInputName2">店铺状态：</label>
                        <select class="form-control" id="itSelect" style="width:150px">
                            <option data_id="0">正常</option>
                        </select>
                    </div>

                    <div class="form-group m-l-15">
                        <label for="exampleInputName2">店铺配送方式：</label>
                        <select class="form-control" id="itSelect" style="width:150px">
                            <option data_id="0">仅自提</option>
                        </select>
                    </div>

                    <div class="input-group m-l-15">
                        <input type="text" id="searchKeyTxt"
                               class="form-control" placeholder="输入店铺名称搜索">
                            <span class="input-group-btn">
                                    <button id="searchBtn" type="button"
                                            class="btn waves-effect waves-light btn-primary"><i
                                            class="fa fa-search"></i></button>
                            </span>
                    </div>

                </form>
            </div>


            <div class="row">
                <div class="col-sm-12">

                    <table class="table table-striped table-bordered">
                        <thead class="table_head">
                        <tr>
                            <th>ID</th>
                            <th>商店名字</th>
                            <th>商店类型</th>
                            <th>当前状态</th>
                            <th>所在地区</th>
                            <th>具体地址</th>
                            <th>配送方式</th>
                            <th>配送距离</th>
                            <th>配送费</th>
                            <th>建店时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="marketListTable">

                        <#if (markets?size > 0)>
                            <#list markets as market>
                            <tr>
                                <td>序号</td>
                                <td>序号</td>
                                <td>序号</td>
                                <td>序号</td>
                                <td>序号</td>
                                <td>序号</td>
                                <td>序号</td>
                                <td>序号</td>
                                <td>序号</td>
                                <td>序号</td>
                                <td>序号</td>
                            </tr>

                            </#list>
                        <#else>
                        <tr>
                            <td colSpan="11" height="200px">
                                <p class="text-center">暂无任何数据</p>
                            </td>
                        </tr>
                        </#if>
                        </tbody>
                    </table>


                </div>
            </div>

        <#if (markets?size > 0)>
            <div class="row small_page">
                <div class="col-sm-12">
                    <#include "../common/paginate.ftl">
                    <@paginate nowPage=pageIndex itemCount=count action="${base}/market/manager/market/markets.do" />
                </div>
            </div>
        </#if>
            <!-- end container -->
        </div>