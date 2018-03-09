<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ include file="../common/vars.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>AdminLTE 2 | Starter</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${ctx}/adminLTE/components/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/adminLTE/components/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/adminLTE/components/datatables/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/adminLTE/css/AdminLTE.min.css">
    <link rel="stylesheet" href="${ctx}/adminLTE/css/skins/skin-blue.min.css">
    <!-- Google Font -->
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <jsp:include page="../common/header.jsp"/>

    <!-- Left side column. contains the logo and sidebar -->
    <jsp:include page="../common/left.jsp">
        <jsp:param name="moudleId" value="10"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" id="content_wrapper">
        <section class="content-header">
            <span style="font-size:16px;font-weight: bold">
                用户管理
            </span>
            <small>包含用户的增删改查等功能</small>
            <%--<ol class="breadcrumb">--%>
            <%--<li><a href="#"><i class="fa fa-dashboard"></i> 系统管理</a></li>--%>
            <%--<li class="active">用户管理</li>--%>
            <%--</ol>--%>
        </section>
        <section class="content container-fluid">
            <div class="box box-default">
                <div class="box-header with-border">
                    <span>查询条件</span>
                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                        <%--<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>--%>
                    </div>
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <div class="row">
                        <div class="col-lg-16">
                            <div class="box-header with-border" style="padding:0px 0px 0px 15px;">
                                <form id="queryForm">
                                <label >用户名：</label>
                                <input type="text" name="name" id="query_userName">&nbsp;&nbsp;&nbsp;&nbsp;
                                <label >用户代码：</label>
                                <input type="text" name="code"  id="query_userCode">&nbsp;&nbsp;&nbsp;&nbsp;
                                <label >状态：</label>
                                <select name="userDesc" id="query_userStatus">
                                    <option value =""></option>
                                    <option value ="0">激活</option>
                                    <option value ="1">禁用</option>
                                </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button type="submit" id="btn_query" class="btn btn-primary btn-sm" style="margin-bottom:5px;">Submit</button>
                                </form>
                            </div>
                        </div>
                    </div>
                    <!-- /.row -->
                </div>
                <!-- /.box-body -->
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <%--<h3 class="box-title">Hover Data Table</h3>--%>
                                <span>查询列表</span>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="usersTable" class="table table-bordered table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>标题</th>
                                    <th>连接</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                        <div class="overlay" style="display: none">
                            <i class="fa fa-refresh fa-spin"></i>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <jsp:include page="../common/footer.jsp"/>


    <!-- Control Sidebar -->

    <!-- /.control-sidebar -->
    <!-- Add the sidebar's background. This div must be placed
    immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 3 -->
<script src="${ctx}/adminLTE/components/jquery/jquery.min.js"></script>
<script src="${ctx}/adminLTE/components/jquery/jquery.serializejson.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="${ctx}/adminLTE/components/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/adminLTE/components/datatables/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/adminLTE/components/datatables/js/dataTables.bootstrap.min.js"></script>
<script src="${ctx}/js/global.js"></script>
<script src="${ctx}/js/system/user.js?v=20110203"></script>
<!-- AdminLTE App -->
<script src="${ctx}/adminLTE/js/adminlte.min.js"></script>
<script>
    $(document).ready(function() {
        SYBR.user.init();
    });
</script>
</body>
</html>