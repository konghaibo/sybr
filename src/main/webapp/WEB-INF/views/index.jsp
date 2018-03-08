<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>AdminLTE 2 | Starter</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${ctx}/adminLTE/components/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/adminLTE/components/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/adminLTE/css/AdminLTE.min.css">
    <link rel="stylesheet" href="${ctx}/adminLTE/css/skins/skin-blue.min.css">
    <!-- Google Font -->
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <jsp:include page="common/header.jsp"/>

    <!-- Left side column. contains the logo and sidebar -->
    <jsp:include page="common/left.jsp">
        <jsp:param name="moudleId" value="0"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" id="content_wrapper">
        <div class="row">
            <div class="col-md-3">
                <div class="box box-warning">
                    <div class="box-header with-border">
                        <h3 class="box-title">Collapsable</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                            </button>
                        </div>
                        <!-- /.box-tools -->
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        The body of the box
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
        </div>
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <jsp:include page="common/footer.jsp"/>


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
<!-- Bootstrap 3.3.7 -->
<script src="${ctx}/adminLTE/components/bootstrap/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="${ctx}/adminLTE/js/adminlte.min.js"></script>

<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. -->
</body>
</html>