<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.fctx.model.User"%>
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

		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript"
			src="<%=basePath%>/asset/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">


   		</script>
	</head>

	<body>
		<a href="<%=basePath%>/user/getDetail_0.do"> 添加 </a>
		<table border="1">
			<tr>
				<th>
					编号
				</th>
				<th>
					账号
				</th>
				<th>
					密码
				</th>
			</tr>
			<%
				List<Object> list = new ArrayList<Object>();
				if (request.getAttribute("list") != null) {
					list = (List<Object>) request.getAttribute("list");
				}
				for (Object obj : list) {
					User user = (User) obj;
			%>
			<tr>
				<td><%=user.getId()%></td>
				<td><%=user.getLoginname()%></td>
				<td><%=user.getPassword()%></td>
			</tr>
			<%
				}
			%>
		</table>
	</body>
</html>
