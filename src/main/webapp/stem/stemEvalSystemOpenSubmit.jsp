<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body>
	<c:import url="stemEvalSystemHeader.jsp" charEncoding="UTF-8" />

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
						<li class="active">开放题答题</li>
					</ul>
					<!-- .breadcrumb -->

					<div class="nav-search" id="nav-search">
						<form class="form-search">
							<span class="input-icon"> <input type="text"
								placeholder="Search ..." class="nav-search-input"
								id="nav-search-input" autocomplete="off" /> <i
								class="icon-search nav-search-icon"></i> </span>
						</form>
					</div>
					<!-- #nav-search -->
				</div>

				<div class="page-content">
					<div class="page-header">
						<h1>

							<small> <i class="icon-double-angle-right"></i>
								上传答题结果（rar或zip格式） </small>
						</h1>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="item">
							<div class="heading">开放题：</div>
							<div class="bd">

								<div class="content clearfix">
									<img width="200" src="<c:url value='/image/' />${open.img}"
										class="pull-left k_language" alt=""> <cite>${open.title}</cite>
								</div>

								<form class="form-horizontal" id="add_choice"
									action="<c:url value='/stem/openupload.html' />"
									novalidate="novalidate" method="post"
									enctype="multipart/form-data">

									<input name="paperId" value="${paperId}" type="hidden" />
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"
											for="form-field-1"> 提交答案 </label>

										<div class="col-sm-6">
											<input type="file" name="answer" id="id-input-file-2" />
										</div>
									</div>

									<div class="space-4"></div>




									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="submit">
												<i class="icon-ok bigger-110"></i> 提交
											</button>

											&nbsp; &nbsp; &nbsp;

										</div>
									</div>
								</form>
							</div>
						</div>
						<!-- /.row -->
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
	</div>
	<!-- basic scripts -->

	<!--[if !IE]> -->
	<script src="<c:url value='/stem/assets/js/jquery-1.10.2.min.js'/>"></script>

	<!-- <![endif]-->

	<!--[if IE]>
			<script src="<c:url value='/stem/assets/js/jquery-1.10.2.min.js'/>"></script>
		<![endif]-->

	<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<c:url value='/stem/assets/js/jquery.mobile.custom.min.js'/>'>"+"<"+"/script>");
		</script>
	<script src="<c:url value='/stem/assets/js/bootstrap.min.js'/>"></script>
	<script src="<c:url value='/stem/assets/js/typeahead-bs2.min.js'/>"></script>

	<!-- page specific plugin scripts -->

	<!-- ace scripts -->

	<script src="<c:url value='/stem/assets/js/ace-elements.min.js'/>"></script>
	<script src="<c:url value='/stem/assets/js/ace.min.js'/>"></script>
	<script type="text/javascript">
jQuery(function($) {
$('#id-input-file-1 , #id-input-file-2').ace_file_input({
					no_file:'No File ...',
					btn_choose:'Choose',
					btn_change:'Change',
					droppable:false,
					onchange:null,
					thumbnail:false //| true | large
					//whitelist:'gif|png|jpg|jpeg'
					//blacklist:'exe|php'
					//onchange:''
					//
				});
	});</script>
	<!-- inline scripts related to this page -->
	<div style="display:none">
		<script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540'
			language='JavaScript' charset='gb2312'></script>
	</div>
</body>
</html>
