<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body>
	<div class="navbar navbar-default" id="navbar">
		<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

		<div class="navbar-container" id="navbar-container">
			<div class="navbar-header pull-left">
				<a href="#" class="navbar-brand"> <small> <i
						class="icon-leaf"></i> STEM答题系统 </small> </a>
				<!-- /.brand -->
			</div>
			<!-- /.navbar-header -->


		</div>
		<!-- /.container -->
	</div>

	<div class="main-container" id="main-container">
		<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span> </a>

			<c:import url="stemEvalSystemSidebar.jsp" charEncoding="UTF-8" />

			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i> <a href="#">个人中心</a>
						</li>

						<!--<li>
								<a href="#">Other Pages</a>							</li>-->
						<li class="active">错误页面</li>
					</ul>
					<!-- .breadcrumb -->

					<div class="nav-search" id="nav-search"></div>
					<!-- #nav-search -->
				</div>

				<div class="page-content">


					<div class="row" style="text-align: center; padding-top:50px;">
						<!--  class="error" id="error" -->
						<!-- <div style="margin-left:auto;margin-right:auto;"> -->
						<img id="logo"
							src="<c:url value='/admin/assets/img/explosion.jpg' />" />
						<p>啊欧，发生了异常错误，我们已经派出了专业的科学家研究这起事故。</p>
						<p>
							<!-- <a href="/" src="/">点击这里返回主页</a> -->
						</p>
						<!-- </div> -->
					</div>


				</div>
				<!-- /.page-content -->
			</div>
			<!-- /.main-content -->


		</div>
		<!-- /.main-container-inner -->

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="icon-double-angle-up icon-only bigger-110"></i> </a>
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->




</body>
</html>
