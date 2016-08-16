<%--
  Created by IntelliJ IDEA.
  User: liuJian
  Date: 2016/8/16
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <%-- 静态引入全局样式 --%>
    <%@include file="includes/global_styles.html" %>
    <%-- 静态引入全局Js脚本 --%>
    <%@include file="includes/global_scripts.html" %>
    <title>
        <sitemesh:write property='title'/>
    </title>
    <sitemesh:write property="head"/>
</head>
<body>
<jsp:include page="includes/_header.jsp" flush="true"/>
<jsp:include page="includes/_sidebar.jsp" flush="true"/>
<!-- main container -->
<div class="content">

    <!-- settings changer -->
    <div class="skins-nav">
        <a href="#" class="skin first_nav selected">
            <span class="icon"></span><span class="text">默认皮肤</span>
        </a>
        <a href="#" class="skin second_nav" data-file="css/compiled/skins/dark.css">
            <span class="icon"></span><span class="text">深色皮肤</span>
        </a>
    </div>
    <!-- upper main stats -->
    <div id="main-stats">
        <sitemesh:write property='body' />
    </div>
</div>
<%-- 动态引入footer --%>
<jsp:include page="includes/_footer.jsp" flush="true" />
</body>
</html>
