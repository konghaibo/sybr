<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<aside class="main-sidebar" id="main_menu">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

        <!-- Sidebar user panel (optional) -->
        <%--<div class="user-panel">--%>
            <%--<div class="pull-left image">--%>
                <%--<img src="/adminLTE/img/user2-160x160.jpg" class="img-circle" alt="User Image">--%>
            <%--</div>--%>
            <%--<div class="pull-left info">--%>
                <%--<p>Alexander Pierce</p>--%>
                <%--<!-- Status -->--%>
                <%--<a href="#"><i class="fa fa-circle text-success"></i> Online</a>--%>
            <%--</div>--%>
        <%--</div>--%>

        <!-- search form (Optional) -->
        <%--<form action="#" method="get" class="sidebar-form">--%>
            <%--<div class="input-group">--%>
                <%--<input type="text" name="q" class="form-control" placeholder="Search...">--%>
                <%--<span class="input-group-btn">--%>
              <%--<button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>--%>
              <%--</button>--%>
            <%--</span>--%>
            <%--</div>--%>
        <%--</form>--%>
        <!-- /.search form -->

        <!-- Sidebar Menu -->
        <ul class="sidebar-menu" data-widget="tree">
            <li class="header">菜单导航栏</li>
            <!-- value is the moudleId, see jsp:include tag -->
            <li value="0">
                <a href="${pageContext.servletContext.contextPath}/users/list" style="cursor: pointer;">
                    <i class="fa fa-th"></i><span>Widgets</span>
                </a>
            </li>

            <li value="1">
                <a href="${pageContext.servletContext.contextPath}/users/list" style="cursor: pointer;">
                    <i class="fa fa-th"></i><span>Widgets222</span>
                </a>
            </li>

            <li class="treeview">
                <a href="#"><i class="fa fa-cogs"></i> <span>系统管理</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li value="10"><a href="${pageContext.servletContext.contextPath}/users/list" style="cursor: pointer;">&nbsp;&nbsp;&nbsp;<i class="fa fa-user"></i>用户管理</a></li>
                    <li value="11"><a href="${pageContext.servletContext.contextPath}/users/list2" style="cursor: pointer;">&nbsp;&nbsp;&nbsp;<i class="fa fa-user"></i>用户管理2</a></li>
                    <li><a href="${pageContext.servletContext.contextPath}/users/list" style="cursor: pointer;">&nbsp;&nbsp;&nbsp;<i class="fa fa-link"></i>角色权限</a></li>
                </ul>
            </li>
        </ul>
        <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
</aside>
<script src="${pageContext.servletContext.contextPath}/adminLTE/components/jquery/jquery.min.js"></script>
<script>
      <%
        String moudleId = request.getParameter("moudleId");
      %>
        $(document).ready(function () {
            var $link = $("li[value=<%=moudleId%>]");
            $link.addClass("active");
            if(10==<%=moudleId%> || 11==<%=moudleId%>){
                $link.parents('.treeview').addClass("active");
//                $link.parents(".treeview").addClass("menu-open");
                $link.parents(".treeview-menu").css("display","block");
            }
//        $('#main_menu').click(function (evt) {
//            var target = evt.target;
////            alert(target.nodeName);
//            if (target.nodeName == 'A' || target.nodeName == 'I' || target.nodeName == 'SPAN') {
//                var $link;
//                if(target.nodeName=='A'){
//                    $link = $(target);
//                }else if(target.nodeName=='I' || target.nodeName == 'SPAN'){
//                    $link = $(target).parent();
//                }
//                if ($link.attr('url') == undefined) {
//                    return;
//                }
//
//                $("li").removeClass("active");
//
//                $(".treeview-menu").each(function(){
//                    $(this).css("display","none");
//                    $(this).parents(".treeview").removeClass("menu-open");
//                });
//
//                if($link.parents().hasClass("treeview")){
//                    $link.parents('.treeview').addClass("active");
//                }
//                $link.parent().addClass("active");
//                $link.parents(".treeview").addClass("menu-open");
//                $link.parents(".treeview-menu").css("display","block");
//                $.ajax({
//                    url: $link.attr('url'),
//                    type: 'get',
//                    dataType: 'html',
//                    success: function (html) {
//                        $('#content_wrapper').html(html);
//                    }
//                });
//            }else{
//                return;
//            }
//        });
    });
</script>