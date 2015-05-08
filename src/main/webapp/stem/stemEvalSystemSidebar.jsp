<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="sidebar" id="sidebar">
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
					</script>

					<div class="sidebar-shortcuts" id="sidebar-shortcuts">
						<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
							<button class="btn btn-success">
								<i class="icon-signal"></i>							</button>

							<button class="btn btn-info">
								<i class="icon-pencil"></i>							</button>

							<button class="btn btn-warning">
								<i class="icon-group"></i>							</button>

							<button class="btn btn-danger">
								<i class="icon-cogs"></i>							</button>
						</div>

						<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
							<span class="btn btn-success"></span>

							<span class="btn btn-info"></span>

							<span class="btn btn-warning"></span>

							<span class="btn btn-danger"></span>						</div>
					</div><!-- #sidebar-shortcuts -->

					<ul class="nav nav-list">
						<li>
							<a href="<c:url value='/stem/newexam.html'/>">
								<i class="icon-dashboard"></i>
								<span class="menu-text"> STEM考试 </span></a>
						</li>
								

						<li>
							<a href="<c:url value='/stem/myexam.html'/>">
								<i class="icon-text-width"></i>
								<span class="menu-text"> 我的考试 </span>							</a>						</li>
								
								
						<li>
							<a href="<c:url value='/stem/profile.html'/>">
								<i class="icon-text-width"></i>
								<span class="menu-text"> 我的资料 </span></a></li>
						<li>
							<a href="/">
								<i class="icon-text-width"></i>
								<span class="menu-text"> 回到首页 </span></a></li>
						<li>
							<a href="/logout.html">
								<i class="icon-text-width"></i>
								<span class="menu-text"> 注销 </span></a></li>


					
					</ul><!-- /.nav-list -->

					<!--<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>					</div>-->

					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
				</div>