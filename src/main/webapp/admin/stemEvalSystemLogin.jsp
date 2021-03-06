<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">  
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0"
    />
   	 <title>测评系统管理后台登陆</title>
    <link href="<c:url value='/admin/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css"
    />
    <link href="<c:url value='/admin/assets/css/main.css'/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/admin/assets/css/plugins.css'/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/admin/assets/css/responsive.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/admin/assets/css/icons.css'/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/admin/assets/css/login.css'/>" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="<c:url value='/admin/assets/css/fontawesome/font-awesome.min.css'/>">
    <!--[if IE 7]>
      <link rel="stylesheet" href="<c:url value='/admin/assets/css/fontawesome/font-awesome-ie7.min.css'/>">
    <![endif]-->
    <!--[if IE 8]>
      <link href="<c:url value='/admin/assets/css/ie8.css'/>" rel="stylesheet" type="text/css" />
    <![endif]-->
    <script type="text/javascript" src="<c:url value='/admin/assets/js/libs/jquery-1.10.2.min.js'/>">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/bootstrap/js/bootstrap.min.js'/>">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/assets/js/libs/lodash.compat.min.js'/>">
    </script>
    <!--[if lt IE 9]>
      <script src="<c:url value='/admin/assets/js/libs/html5shiv.js'/>">
      </script>
    <![endif]-->
    <script type="text/javascript" src="<c:url value='/admin/plugins/uniform/jquery.uniform.min.js'/>">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/validation/jquery.validate.min.js'/>">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/nprogress/nprogress.js'/>">
    </script>
    <%-- <script type="text/javascript" src="<c:url value='/admin/assets/js/login.js'/>">
    </script> --%>
    <script>
      $(document).ready(function() {
        Login.init()
      });
    </script>
  </head>
  <body class="login">
    <div class="logo">
      <img src="<c:url value='/admin/assets/img/logo.png'/>" alt="logo" />
      <strong>
        STEM
      </strong>测评系统管理后台</div>
    <div class="box">
      <div class="content">
        <form class="form-vertical login-form" action="<c:url value='/adminlogin/loginCheck.html' />" method="post">
          <h3 class="form-title">
            登录
          </h3>
          <div class="alert fade in alert-danger" style="display: none;">
            <i class="icon-remove close" data-dismiss="alert">
            </i>
           输入任何用户名密码进入.
          </div>
          <div class="form-group">
            <div class="input-icon">
              <i class="icon-user">
              </i>
              <input type="text" name="account" class="form-control" placeholder="用户名"
              autofocus="autofocus" data-rule-required="true" data-msg-required="请输入用户名."
              />
            </div>
          </div>
          <div class="form-group">
            <div class="input-icon">
              <i class="icon-lock">
              </i>
              <input type="password" name="password" class="form-control" placeholder="密码"
              data-rule-required="true" data-msg-required="请输入密码."
              />
            </div>
          </div>
          <div class="form-actions">
            <label class="checkbox pull-left">
              <!-- <input type="checkbox" class="uniform" name="remember">
              记住密码 -->
            </label>
             <input type="submit" class="submit btn btn-primary pull-right" value="登录"/> 
            
          </div>
        </form>
        
      </div>
      <div class="inner-box">
        <div class="content">
          <i class="icon-remove close hide-default">
          </i>
          
          <!-- <div class="forgot-password-done hide-default">
            <i class="icon-ok success-icon">
            </i>
            <span>
              Great. We have sent you an email.
            </span>
          </div> -->
        </div>
      </div>
    </div>
    <!-- <div class="single-sign-on">
      <span>
        or
      </span>
      <button class="btn btn-facebook btn-block">
        <i class="icon-facebook">
        </i>
        Sign in with Facebook
      </button>
      <button class="btn btn-twitter btn-block">
        <i class="icon-twitter">
        </i>
        Sign in with Twitter
      </button>
      <button class="btn btn-google-plus btn-block">
        <i class="icon-google-plus">
        </i>
        Sign in with Google
      </button>
    </div> -->
    <div class="footer">
      <!-- <a href="#" class="sign-up">
        Don't have an account yet?
        <strong>
          Sign Up
        </strong>
      </a> -->
    </div>
    <script type="text/javascript">
      if (location.host == "envato.stammtec.de" || location.host == "themes.stammtec.de") {
        var _paq = _paq || [];
        _paq.push(["trackPageView"]);
        _paq.push(["enableLinkTracking"]); (function() {
          var a = (("https:" == document.location.protocol) ? "https": "http") + "://analytics.stammtec.de/";
          _paq.push(["setTrackerUrl", a + "piwik.php"]);
          _paq.push(["setSiteId", "17"]);
          var e = document,
          c = e.createElement("script"),
          b = e.getElementsByTagName("script")[0];
          c.type = "text/javascript";
          c.defer = true;
          c.async = true;
          c.src = a + "piwik.js";
          b.parentNode.insertBefore(c, b)
        })()
      };
    </script>
  </body>

</html>