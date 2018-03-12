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
                                    <button type="button" id="btn_query" class="btn btn-primary btn-sm" style="margin-bottom:5px;">Submit</button>

                                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal-default">新增用户</button>
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
                                <span>查询列表(从服务端一次性获取所有数据,然后在客户端分页处理)</span>
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

        <!--model dialog -->
        <div class="modal fade" id="modal-default">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form id="form_test" action="" class="form-horizontal">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">表单(HTML5自带表单验证)</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-12">
                                        <div class="form-group">
                                            <label for="inputEmail" class="col-sm-2 control-label">用户名称</label>
                                            <div class="col-sm-10">
                                                <!--<input type="text" class="form-control" id="inputEmail" placeholder="请输入用户名称" required pattern="[\u4e00-\u9fa5]{2,4}" oninvalid="validatelt(this,'用户名称不能为空')">-->
                                                <input type="text" class="form-control" id="inputEmail" placeholder="请输入用户名称" required oninvalid="setCustomValidity('用户名称不能为空')" oninput="setCustomValidity('')">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="inputPassword" class="col-sm-2 control-label">用户代码</label>
                                            <div class="col-sm-10">
                                                <input type="password" class="form-control" id="inputPassword" placeholder="Password"  pattern="^[a-zA-Z]\w{5,7}$" title="密码应包含英文字母和数字，且长度为6到8位">
                                            </div>
                                        </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="form-group">
                                        <label for="inputUserDesc" class="col-sm-2 control-label">用户描述</label>
                                        <div class="col-sm-4">
                                            <input type="text" class="form-control" id="inputUserDesc" name="inputUserDesc" placeholder="用户描述" required pattern="[\u4e00-\u9fa5]{2,4}" oninvalid="setCustomValidity('真实姓名必须是中文，且长度不小于2，不大于4')" oninput="setCustomValidity('')">
                                        </div>
                                        <label for="inputUserStatus" class="col-sm-2 control-label">用户状态</label>
                                        <div class="col-sm-4">
                                            <input type="password" class="form-control" id="inputUserStatus" placeholder="Password">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                            <button type="submit" id="btn_save" class="btn btn-primary pull-right">Save changes</button>
                        </div>
                    </form>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>

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
    function validatelt(inputelement,err){
        if(inputelement.validity.patternMismatch){
            inputelement.setCustomValidity(err); }
        else{
            inputelement.setCustomValidity("");
            return true;
        }
    }
    $(document).ready(function() {
//        $('#form_test').bootstrapValidator({
//            live: 'disabled',//验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
//            excluded: [':disabled', ':hidden', ':not(:visible)'],//排除无需验证的控件，比如被禁用的或者被隐藏的
//            submitButtons: '#btn-test',//指定提交按钮，如果验证失败则变成disabled，但我没试成功，反而加了这句话非submit按钮也会提交到action指定页面
//            message: '通用的验证失败消息',//好像从来没出现过
//            feedbackIcons: {//根据验证结果显示的各种图标
//                valid: 'glyphicon glyphicon-ok',
//                invalid: 'glyphicon glyphicon-remove',
//                validating: 'glyphicon glyphicon-refresh'
//            },
//            fields: {
//                inputUserDesc: {
//                    message: '用户描述不合法',
//                    validators: {
//                        notEmpty: {
//                            message: '用户描述不能为空'
//                        },
//                        stringLength: {
//                            min: 3,
//                            max: 30,
//                            message: '请输入3到30个字符'
//                        },
//                        regexp: {
//                            regexp: /^[a-zA-Z0-9_\. \u4e00-\u9fa5 ]+$/,
//                            message: '用户描述只能由字母、数字、点、下划线和汉字组成 '
//                        }
//                    }
//                }
//            }
//
//        });
        SYBR.user.init();
    });
</script>
</body>
</html>