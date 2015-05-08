<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="header navbar navbar-fixed-top" role="banner">
      <div class="container">
        <ul class="nav navbar-nav">
          <li class="nav-toggle">
            <a href="javascript:void(0);" title="">
              <i class="icon-reorder">
              </i>
            </a>
          </li>
        </ul>
        <a class="navbar-brand" href="index.html">
        
          <img src="<c:url value='/admin/assets/img/logo.png' />" alt="logo" />
          
             stem后台管理    </a>
        <a href="#" class="toggle-sidebar bs-tooltip" data-placement="bottom"
        data-original-title="Toggle navigation">
          <i class="icon-reorder">          </i>        </a>
        <ul class="nav navbar-nav navbar-left hidden-xs hidden-sm">
          <!-- Nothing in navbar-left. -->
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <li class="dropdown user">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <i class="icon-male">
              </i>
              <span class="username">
                <!-- John Doe -->${adminname}
              </span>
              <i class="icon-caret-down small">
              </i>
            </a>
            <ul class="dropdown-menu">
             <!--  <li>
                <a href="pages_user_profile.html">
                  <i class="icon-user">
                  </i>
                  我的资料
                </a>
              </li>
              <li>
                <a href="#">
                  <i class="icon-tasks">
                  </i>
                  修改密码
                </a>
              </li> -->
              <li class="divider">
              </li>
              <li>
                <a href="<c:url value='/adminlogin/logout.html'/>">
                  <i class="icon-key">
                  </i>
                  注销
                </a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </header>