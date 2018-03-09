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
        }
    }
})(SYBR)