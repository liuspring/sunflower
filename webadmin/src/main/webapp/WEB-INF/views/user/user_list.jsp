<%--
  Created by IntelliJ IDEA.
  User: liuJian
  Date: 2016/8/17
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- libraries -->

<link type="text/css" rel="stylesheet" href="asset/css/lib/font-awesome.css"/>
<link type="text/css" rel="stylesheet" href="asset/css/lib/jquery.dataTables.css"/>
<!-- this page specific styles -->
<link type="text/css" rel="stylesheet" href="asset/css/compiled/datatables.css" media="screen"/>

<div id="pad-wrapper" class="datatables-page">
    <div class="row">
        <div class="col-md-12">
            <table class="table table-bordered table-hover dataTable" id="table_userList">
                <thead>
                <tr role="row">
                    <th>序号</th>
                    <th>用户名</th>
                    <th>密码</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript" src="asset/js/dataTables/jquery.dataTables.js"></script>
<script type="text/javascript" src="asset/js/dataTables/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="asset/js/dataTables/dataTables.js"></script>
<script type="text/javascript">
    $(function () {
        var grid = new initDataTables();
        grid.init({
            src: $("#table_userList"),
            dataTable: {
                "sAjaxSource": "user/getList.do",
                //配置列要显示的数据
                columns: [
                    //对应上面thead里面的序列 ;字段名字和返回的json序列的key对应
                    {"data": "id"},
                    {"data": "loginname"},
                    {"data": "password"},
                    {"data": "id"}
                ],//按钮列
                "columnDefs": [
                    {
                     "render": function (data, type, row, me) {
                     return me.settings._iDisplayStart + me.row + 1;
                     },
                     "targets": 0
                     }
                ],
            }
        });
        //搜索
        $("#btnSearch").click(function () {
            grid.submitFilter();
        });


    });
</script>

