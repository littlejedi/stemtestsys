<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="sidebar" class="sidebar-fixed">
        <div id="sidebar-content">
          <ul id="nav">
          
          
          	<li>
              <a href="<c:url value='/superadmin/ecadminlist.html' />">
                <i class="icon-dashboard">
                </i>
                习题管理员
              </a>
			  
            </li>
            <li>
              <a href="<c:url value='/superadmin/tcadminlist.html' />">
                <i class="icon-dashboard">
                </i>
                阅卷老师
              </a>
			  
            </li>
            <li>
              <a href="<c:url value='/superadmin/examlist.html' />">
                <i class="icon-dashboard">
                </i>
                考试设置
              </a>
            </li>
            <li>
              <a href="<c:url value='/superadmin/scoredownload.html' />">
                <i class="icon-dashboard">
                </i>
                成绩下载
              </a>
            </li>
            <li>
              <a href="<c:url value='/superadmin/addexamtimespage.html' />">
                <i class="icon-dashboard">
                </i>
                增加免费考试机会
              </a>
            </li>
            <li>
              <a href="<c:url value='/superadmin/timeoutlist.html' />">
                <i class="icon-dashboard">
                </i>
                超时成绩计算
              </a>
            </li>
            
          </ul>
         
          
        </div><!-- end of sidebar-content -->
        <div id="divider" class="resizeable">
        </div>
      </div>