<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>详情界面</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript"
            src="<%=basePath%>/asset/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#save").click(function(){
                var url = "<%=basePath%>/user/saveUser.do";
                var param = {};
                $('#form1').find("input").each(function (index, obj) {
                    param[obj.name] = obj.value;
                });
                $.post(url, param, function (data) {
                    data = data.replace(/\"/g, "");
                    if (data == "success") {
                        alert('操作成功');
                    } else {
                        alert('操作失败');
                    }
                }).error(function (data) {
                    alert("系统出现异常");
                });
            });
        })
    </script>
</head>

<body>
<form id="form1" method="post" action="<%=basePath%>/user/saveUser.do">
    <p>
        <label>
            账号
        </label>
        <input type="text" id="loginname" name="loginname">
    </p>
    <p>
        <label>
            密码
        </label>
        <input type="password" id="password" name="password">
    </p>
    <p>
        <input type="hidden" id="id" name="id" value="0">
        <input type="submit" value="Save">
        <button id="save" type="button" onclick="save();">
            保存
        </button>
        <a href="<%=basePath%>/user/getAllList.do"> 去列表 </a>
    </p>
</form>
</body>
</html>
