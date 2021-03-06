(function(a) {
    a.user = {
        table:{},
        datatables_options:{
            oLanguage:a.DataTableLang,
            // ajax: {
            //     beforeSend:function(){
            //         $(".overlay").show();
            //     },
            //     complete: function(){
            //         $(".overlay").hide();
            //     },
            //     //指定数据源
            //     crossDomain: true,
            //     url: ctx+"/users/query",
            //     type: 'POST',
            //     contentType: "application/json; charset=utf-8",
            //     dataType: 'json',
            //     data: function(param) {
            //         return JSON.stringify({});
            //     },
            //     success:function (result) {
            //         a.user.table.clear();
            //         if(result.data.totalCount >0){
            //             a.user.table.rows.add(result.data.data);
            //         }
            //         a.user.table.draw();
            //     }
            // },
            //每页显示三条数据
            // processing: true,
            lengthChange: false,
            searching: false,
            autoWidth: false,
            pageLength: 3,
            ordering: false,
            columns: [
                {
                    "data": "id"
                },
                {
                    "data": "name"
                },
                {
                    "data": "code"
                }],
            columnDefs: [{
                // "visible": false,
                //"targets": 0
            },
                {
                    "render": function(data, type, row, meta) {
                        //渲染 把数据源中的标题和url组成超链接
                        return '<a href="' + data + '" target="_blank">' + row.name + '</a>';
                    },
                    //指定是第三列
                    "targets": 1
                }]
        },
        init:function () {
            $("#btn_query").on("click", a.user.btnSearchClick);
            $("#form_test").on("submit", a.user.formSubmit);
            a.user.table = $('#usersTable').DataTable(a.user.datatables_options);
            $("#btn_query").trigger("click");
            // t.on('order.dt search.dt',
            //     function() {
            //         t.column(0, {
            //             "search": 'applied',
            //             "order": 'applied'
            //         }).nodes().each(function(cell, i) {
            //             cell.innerHTML = i + 1;
            //         });
            //     }).draw();
        },
        btnSearchClick:function () {
            var params = {};
            // params.queryConditions = {};
            // params.queryConditions.name = $("#query_userName").val();
            // params.queryConditions.code = $("#query_userCode").val();
            var params = {};
            params.queryConditions = $("#queryForm").serializeJSON();
            $.ajax({
                beforeSend:function(){
                    $("#btn_query").attr("disabled","true");
                    $(".overlay").show();
                },
                complete: function(){
                    $(".overlay").hide();
                    $("#btn_query").attr("disabled",false);
                },
                type: "POST",
                // async: true,
                contentType: "application/json;charset=utf-8",
                url: ctx + "/users/query",
                cache: false, //禁用缓存
                //data: '{"name": "uname", "age": 18}',
                data: JSON.stringify(params),
                dataType: "json",
                success: function (result) {
                    a.user.table.clear();
                    if(result.data.totalCount >0){
                        a.user.table.rows.add(result.data.data);
                    }
                    a.user.table.draw();
                }
            });
            // var url = a.user.table.ajax.url(ctx+"/users/query?date="+new Date().getTime());
            // var data = a.user.table.ajax.params();
            // alert( '搜索词是: '+data);
            // a.user.table.ajax.params(function(param) {
            //     param.name="aaaaa";
            //     param.id=1;
            //     return JSON.stringify(param);
            // });
            // url.load(function(result){
            //     alert(result);
            // });
        },
        //一般表单的提交用的都是用button然后用ajax来提交，但是用button就不能触发HTML5的自带表单验证,用submit的话就又会直接提交表单,
        // 用submit来提交表单，然后在js中监听submit方法，用ajax提交表单最后阻止submit的自动提交
        //H5自带的验证只是比较简单的验证，不是很完美，只适合做比较简单的验证。最好是用jQ的Validation插件或者其他表单验证插件。
        formSubmit:function (ev) {
            console.log("formSubmit.....");
            //TODO AJAX提交

            //阻止submit表单提交
            ev.preventDefault();
            //或者return false
            //return false;
        }
    }
    a.user2 = {
        table:{},
        datatables_options:{
            oLanguage:a.DataTableLang,
            lengthChange: false,
            searching: false,
            autoWidth: false,
            iDisplayLength: 10,
            ordering: false,
            // processing: true,
            serverSide: true,
            ajax: function(data, callback, settings) {
                data.queryConditions = $("#queryForm").serializeJSON();
                console.log(JSON.stringify(data));
                $.ajax({
                    beforeSend:function(){
                        $("#btn_query").attr("disabled","true");
                        $(".overlay").show();
                    },
                    complete: function(){
                        $(".overlay").hide();
                        $("#btn_query").attr("disabled",false);
                    },
                    contentType: "application/json; charset=utf-8",
                    type: "POST",
                    url: ctx + "/users/query2",
                    cache : false,  //禁用缓存
                    data: JSON.stringify(data),    //传入已封装的参数
                    // data: function() {
                    //             return JSON.stringify({});
                    //         },
                    dataType: "json",
                    success: function(result) {
                        //异常判断与处理
                        // if (result.errorCode) {
                        //     alert("查询失败");
                        //     return;
                        // }
                        //封装返回数据
                        var returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.data.totalCount;//总记录数
                        returnData.recordsFiltered = result.data.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.data.data;
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        alert("查询失败");
                    }
                });
            },
            columns: [
                { "data": "id", "width":"100px"},
                { "data": "name", "render": function(data, type, row, meta) {
                    //渲染 把数据源中的标题和url组成超链接
                    return '<a href="' + data + '" target="_blank">' + row.name + '</a>';
                }},
                { "data": "code", "width":"200px" },
                {
                    "data": "id",
                    "orderable": false, // 禁用排序
                    "defaultContent": "",
                    "width": "200px"
                    ,"render": function (data, type, row, meta) {
                        return data = '<button style="margin-bottom:-5px;margin-top: -10px;" class="btn btn-info btn-sm" data-id=' + data + '><i class="fa fa-pencil"></i>Edit</button>'
                            + '&nbsp;&nbsp;<button style="margin-bottom:-5px;margin-top: -10px;" class="btn btn-danger btn-sm" data-id=' + data + '><i class="fa fa-trash-o"></i>Delete</button>';

                    }
                }
            ]
        },
        init:function () {
            $("#btn_query").on("click", a.user2.btnSearchClick);
            a.user2.table = $('#usersTable').dataTable(a.user2.datatables_options).api();
        },
        btnSearchClick:function () {
            a.user2.table.draw();
        }

    }
})(SYBR)