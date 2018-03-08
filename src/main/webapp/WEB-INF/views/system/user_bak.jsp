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
                                <label >用户名：</label>
                                <input type="text" id="query01_username">&nbsp;&nbsp;&nbsp;&nbsp;
                                <label >IMEI码：</label>
                                <input type="text" id="query01_imei">&nbsp;&nbsp;&nbsp;&nbsp;
                                <label >状态：</label>
                                <select id="query01_status">
                                    <option value =""></option>
                                    <option value ="0">激活</option>
                                    <option value ="1">禁用</option>
                                </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button type="submit" id="btn_query" class="btn btn-primary btn-sm" style="margin-bottom:5px;">Submit</button>
                            </div>
                        </div>
                    </div>
                    <!-- /.row -->
                </div>
                <!-- /.box-body -->

            </div>
            <!-- /.box-header -->
            <div class="box box-default">
                <table id="usersTable" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>Rendering engine</th>
                        <th>Browser</th>
                        <th>Platform(s)</th>
                        <th>Engine version</th>
                        <th>CSS grade</th>
                    </tr>
                    </thead>
                    <tbody></tbody>

                </table>
            </div>
            <!-- /.box-body -->
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
<!-- Bootstrap 3.3.7 -->
<script src="${ctx}/adminLTE/components/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/adminLTE/components/datatables/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/adminLTE/components/datatables/js/dataTables.bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="${ctx}/adminLTE/js/adminlte.min.js"></script>
<script>
    function retrieveData(url, aoData, fnCallback) {
        $.ajax({
            url: url,//这个就是请求地址对应sAjaxSource
            data : {
                "aoData" : JSON.stringify(aoData)
            },
            type: 'POST',
            dataType: 'json',
            async: false,
            success: function (result) {
                //var obj=JSON.parse(result);
                console.log(result);
                fnCallback(result);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                alert("status:"+XMLHttpRequest.status+",readyState:"+XMLHttpRequest.readyState+",textStatus:"+textStatus);
            }
        });
    }

    var datatables_options = {
        "iDisplayLength": 3,
        'paging'      : true,
        'lengthChange': false,
        'searching'   : false,
        'ordering'    : false,
        'info'        : true,
        'autoWidth'   : true,
        "oLanguage" : {
            "sProcessing" : "正在加载数据...",
            "sLengthMenu" : "显示_MENU_条 ",
            "sZeroRecords" : "没有您要搜索的内容",
            "sInfo" : "从_START_ 到 _END_ 条记录,总记录数为 _TOTAL_ 条",
            "sInfoEmpty" : "记录数为0",
            "sInfoFiltered" : "(全部记录数 _MAX_  条)",
            "sInfoPostFix" : "",
            "sSearch" : "搜索",
            "sUrl" : "",
            "oPaginate" : {
                "sFirst" : "第一页",
                "sPrevious" : " 上一页 ",
                "sNext" : " 下一页 ",
                "sLast" : " 最后一页 "
            }
        },
        "processing": false,
        "serverSide": true,
        "sAjaxSource": "${ctx}/users/query",
        "fnServerData": retrieveData
    }
    $(document).ready(function() {
        var tableData = $('#usersTable').DataTable(datatables_options);

        $('#btn_query').click(function(){
            var data={"data":{"id":"11111","name":"3s",}};
            var aoData = JSON.stringify(data);

            var table = $('#usersTable').dataTable();
            if(table){
                table.fnClearTable(false);
                table.fnDestroy();
            }
            datatables_options.fnServerData = retrieveData;
            $('#usersTable').DataTable(datatables_options);

        })

    });


</script>
</body>
</html>