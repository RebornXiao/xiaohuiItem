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
                            <li><a href="#">商品管理</a></li>
                            <li class="active"><a href="#">商品模板库</a></li>
                        </ol>
                        <h4 class="page-title"><b>商品模板库</b></h4>
                    </div>
                </div>
            </div>

            <div class="card-box">
                <h4 class="header-title m-t-0"><b>搜索订单</b></h4>
                <form class="form-inline" role="form">

                    <div class="form-group m-l-15">
                        <label for="exampleInputName2">省：</label>
                        <select class="form-control" id="itSelect" style="width:150px">
                            <option data_id="0">无</option>
                        </select>
                    </div>

                    <div class="form-group m-l-15">
                        <label for="exampleInputName2">市：</label>
                        <select class="form-control" id="itSelect" style="width:150px">
                            <option data_id="0">无</option>
                        </select>
                    </div>

                    <div class="form-group m-l-15">
                        <label for="exampleInputName2">区：</label>
                        <select class="form-control" id="itSelect" style="width:150px">
                            <option data_id="0">无</option>
                        </select>
                    </div>

                    <div class="form-group m-l-15">
                        <label for="exampleInputName2">街道：</label>
                        <select class="form-control" id="itSelect" style="width:150px">
                            <option data_id="0">无</option>
                        </select>
                    </div>

                    <div class="input-group m-l-15">
                        <div class="input-group-btn">
                            <button id="searchMenu" type="button" search_name="名称"
                                    class="btn btn-default waves-effect waves-light dropdown-toggle"
                                    data-toggle="dropdown">按名称搜索 <span class="caret"></span></button>
                            <ul class="dropdown-menu" id="searchType">
                                <li><a href="javascript:void(0)" search_name="名称" search_type="name">按名称搜索</a></li>
                                <li><a href="javascript:void(0)" search_name="自定义编码"
                                       search_type="define_code">按自定义编码搜索</a>
                                </li>
                                <li><a href="javascript:void(0)" search_name="条形码" search_type="barcode">按条形码搜索</a>
                                </li>
                            </ul>
                        </div>
                        <input type="text" id="searchKeyTxt"
                               class="form-control" placeholder="按名称查">
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
                            <th>序号</th>
                            <th>订单号</th>
                            <th>商家名称</th>
                            <th>商家帐号</th>
                            <th>商家手机号码</th>
                            <th>供应商/子仓名称</th>
                            <th>收货地址</th>
                            <th>订单金额</th>
                            <th>支付金额</th>
                            <th>支付类型</th>
                            <th>支付状态</th>
                            <th>快递员状态</th>
                            <th>快递员</th>
                            <th>订单状态</th>
                            <th>下单时间</th>
                            <th>剩余时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="itemListTable">

                        <tr>
                            <th>序号</th>
                            <th>订单号</th>
                            <th>商家名称</th>
                            <th>商家帐号</th>
                            <th>商家手机号码</th>
                            <th>供应商/子仓名称</th>
                            <th>收货地址</th>
                            <th>订单金额</th>
                            <th>支付金额</th>
                            <th>支付类型</th>
                            <th>支付状态</th>
                            <th>快递员状态</th>
                            <th>快递员</th>
                            <th>订单状态</th>
                            <th>下单时间</th>
                            <th>剩余时间</th>
                            <th>操作</th>
                        </tr>

                        </tbody>
                    </table>

                </div>
            </div>


            <!-- end container -->
        </div>