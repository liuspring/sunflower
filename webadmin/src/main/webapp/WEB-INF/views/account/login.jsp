<%--
  Created by IntelliJ IDEA.
  User: liuJian
  Date: 2016/8/15
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html class="login-bg">
<head>
    <title>登录页面</title>
    <meta name="keywords" content="后台管理系统"/>
    <meta name="description" content="后台管理系统"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- bootstrap -->
    <link href="<%=basePath%>/asset/css/bootstrap/bootstrap.css" rel="stylesheet">
    <!-- this page specific styles -->
    <link rel="stylesheet" href="<%=basePath%>/asset/css/compiled/signin.css" type="text/css" media="screen"/>
</head>
<div class="login-wrapper">
    <div class="box">
        <div class="content-wrap">
            <h6>登录</h6>
            <input class="form-control" id="loginname" type="text" placeholder="输入邮箱地址">
            <input class="form-control" id="password" type="password" placeholder="输入密码">
            <input class="form-control col-md-3" id="vcode" type="text" placeholder="验证码" style="width:220px;">
            <img style="cursor: pointer;" title="点击刷新验证码" id="vcode_img" src="../verifycode.do" width="100" height="40"
                 alt="点击获取验证码">
            <a href="javascript:void(0)" class="forgot">忘记密码?</a>
            <div class="remember">
                <input id="remember-me" type="checkbox">
                <label for="remember-me">记住密码</label>
            </div>
            <span id="errormsg" class="text-danger hide text-left"></span><br/>
            <input type="button" class="btn btn-primary login" id="btn_login" value="登 录">
        </div>
    </div>

    <div class="no-account">
        <p>还没账号?</p>
        <a href="javascript:void(0)">注册</a>
    </div>
</div>
<!-- scripts -->
<script src="<%=basePath%>/asset/js/jquery-1.11.1.min.js"></script>
<!-- pre load bg imgs -->
<script type="text/javascript">
    $(function () {


        $("#vcode_img").click(function () {
            changeVcode();
        })
        function changeVcode(){
            var imgSrc = $("#vcode_img");
            var src = imgSrc.attr("src");
            imgSrc.attr("src", chgUrl(src));
        }
        //时间戳
        //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
        function chgUrl(url) {
            var timestamp = (new Date()).valueOf();
            url = url.substring(0, 17);
            if ((url.indexOf("&") >= 0)) {
                url = url + "×tamp=" + timestamp;
            } else {
                url = url + "?timestamp=" + timestamp;
            }
            return url;
        }

        function setInfo(msg) {
            $("#errormsg").addClass("show").html(msg);
            $("#btn_login").val("登录").removeClass("disabled").removeAttr("disabled");
        }

        $("#btn_login").click(function () {
            var $this = $(this);
            var loginname = $("#loginname").val();
            if (loginname == "") {
                setInfo("输入邮箱地址");
                return;
            }
            var password=$("#password").val();
            if(password==0){
                setInfo("请输入密码");
                return;
            }
            var vcode=$("#vcode").val();
            if(vcode==""){
                setInfo("请输入验证码");
                return;
            }
            $("#btn_login").val("验证中...").addClass("disabled").attr("disabled","disabled");

            $.ajax({
                type:"post",
                url:"../account/loginSubmit.do",
                dataType:"json",
                data:{
                    "loginname":loginname,
                    "password":password,
                    "vcode":vcode
                },
                success:function(data){
                    if (json.state) {
                        $("#btn_login").val("跳转中...");
                        window.location = json.data;
                    }
                    else {
                        setInfo(json.msg);
                        changeVcode();
                    }
                },
                error: function (xMLHttpRequest, textStatus, errorThrown) {
                    setInfo("访问出错，请稍后再试");
                }

            });
        })

    });
</script>
</body>
</html>
