<#macro menu tab="index" tab_child="z">

</head>


<body class="fixed-left">

<!-- Begin page -->
<div id="wrapper">

    <!-- Top Bar Start -->
    <div class="topbar" style="height: 58px">

        <!-- LOGO -->
        <div class="topbar-left">
            <div class="text-center">
                <a href="index.html" class="logo"><i class="fa fa-windows"></i> <span>智能超市后台管理系统</span> </a>
            </div>
        </div>

        <!-- Navbar -->
        <div class="navbar navbar-default" role="navigation">
            <div class="container">
                <div class="">
                    <div class="pull-left">
                        <button class="button-menu-mobile open-left waves-effect">
                            <i class="fa fa-bars"></i>
                        </button>
                        <span class="clearfix"></span>
                    </div>

                    <ul class="nav navbar-nav navbar-right pull-right">
                        <li><a href=""/>当前用户：</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle waves-effect" data-toggle="dropdown"
                               role="button" aria-haspopup="true" aria-expanded="false"> ${userName} <span
                                    class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#"><i class="fa fa-user"></i>&nbsp;帐户信息</a></li>
                                <li><a href="#"><i class="fa fa-sign-out"></i>&nbsp;退出</a></li>
                            </ul>
                        </li>
                    </ul>

                </div>
                <!--/.nav-collapse -->
            </div>
        </div>
    </div>
    <!-- Top Bar End -->


    <!-- ========== Left Sidebar Start ========== -->
    <div class="left side-menu">
        <div class="sidebar-inner slimscrollleft">

            <div id="sidebar-menu">
                <ul>

                    <li>
                        <a href="index.html" class="waves-effect waves-primary <#if tab=="index"> active subdrop </#if> "><i
                                class="fa fa-home"></i><span> 首  页 </span></a>
                    </li>


                    <li class="has_sub">
                        <a href="javascript:void(0);" class="waves-effect waves-primary <#if tab=="order"> active subdrop </#if> "><i class="fa fa-th-list"></i>
                            <span> 订单管理 </span>
                            <span class="menu-arrow"><i class="fa fa-angle-down"></i></span></a>
                        <ul class="list-unstyled">
                            <li <#if tab_child=="all"> class="active" </#if> ><a href="/market/manager/order/orderlist.do">订单列表</a></li>
                        </ul>
                    </li>

                    <li class="has_sub">
                        <a href="javascript:void(0);" class="waves-effect waves-primary <#if tab=="item"> active subdrop </#if>"><i
                                class="fa fa-cubes"></i><span> 商品管理 </span>
                            <span class="menu-arrow"><i class="fa fa-angle-down"></i></span></a>
                        <ul class="list-unstyled">
                            <li <#if tab_child=="template"> class="active" </#if> ><a href="/market/manager/item/itemlist.do">商品模板库</a></li>
                            <li <#if tab_child=="types"> class="active" </#if> ><a href="/market/manager/item/itemtypes.do">商品分类</a></li>
                            <li <#if tab_child=="unit"> class="active" </#if> ><a href="/market/manager/item/itemunits.do">商品单位</a></li>
                        </ul>
                    </li>

                    <li class="has_sub">
                        <a href="javascript:void(0);" class="waves-effect waves-primary <#if tab=="store"> active subdrop </#if>"><i class="fa fa-sitemap"></i>
                            <span> 店铺管理 </span>
                            <span class="menu-arrow"><i class="fa fa-angle-down"></i></span></a>
                        <ul class="list-unstyled">
                            <li><a href="/market/manager/item/storeadd.do">新增店铺</a></li>
                            <li><a href="/market/manager/item/storelist.do">店铺列表</a></li>
                            <li><a href="/market/manager/item/storelist.do">店铺格子</a></li>
                            <li><a href="/market/manager/item/storelist.do">店铺商品</a></li>
                        </ul>
                    </li>

                    <li class="has_sub">
                        <a href="javascript:void(0);" class="waves-effect waves-primary <#if tab=="user"> active subdrop </#if>"><i
                                class="fa fa-users"></i><span> 用户管理 </span>
                            <span class="menu-arrow"><i class="fa fa-angle-down"></i></span></a>
                        <ul class="list-unstyled">
                            <li><a href="/market/manager/passport/userlist.do">用户列表</a></li>
                            <li><a href="form-advanced.html">活跃用户</a></li>
                            <li><a href="form-validation.html">沉默用户</a></li>
                        </ul>
                    </li>

                    <li class="has_sub">
                        <a href="javascript:void(0);" class="waves-effect waves-primary <#if tab=="table"> active subdrop </#if>"><i
                                class="fa fa-line-chart"></i><span> 统计报表 </span>
                            <span class="menu-arrow"><i class="fa fa-angle-down"></i></span></a>
                        <ul class="list-unstyled">
                            <li><a href="tables-basic.html">订单数据统计</a></li>
                            <li><a href="tables-datatable.html">店铺数据统计</a></li>
                            <li><a href="tables-editable.html">用户数据统计</a></li>
                        </ul>
                    </li>

                    <li class="has_sub">
                        <a href="javascript:void(0);" class="waves-effect waves-primary <#if tab=="sys"> active subdrop </#if>"><i
                                class="fa fa-cog"></i><span> 系统设置 </span>
                            <span class="menu-arrow"><i class="fa fa-angle-down"></i></span></a>
                        <ul class="list-unstyled">
                            <li><a href="chart-flot.html">个人信息</a></li>
                        </ul>
                    </li>

                </ul>
            </div>
        </div>
    </div>
    <!-- Left Sidebar End -->

</#macro>