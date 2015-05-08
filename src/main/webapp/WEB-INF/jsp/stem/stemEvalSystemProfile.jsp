<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" /> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>

	<body>
		<c:import url="stemEvalSystemHeader.jsp" charEncoding="UTF-8" />

		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>				</a>

				<c:import url="stemEvalSystemSidebar.jsp" charEncoding="UTF-8" />

				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="#">个人中心</a>							</li>

							<!--<li>
								<a href="#"></a>							</li>-->
							<li class="active">考试信息</li>
						</ul><!-- .breadcrumb -->

						<div class="nav-search" id="nav-search">
							<!-- <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>								</span>
							</form> -->
						</div><!-- #nav-search -->
					</div>

					<div class="page-content">
						<div class="page-header">
							<h1>
								我的资料
								<small>
									<i class="icon-double-angle-right"></i>
									完善/更新考试信息								</small>							</h1>
						</div><!-- /.page-header -->

						<div class="row">
						
						
						
					<div class="col-xs-12">
                    
                    <form class="form-horizontal" role="form">
                    
                    				<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 姓名 </label>

										<div class="col-sm-9">
											<input id="form-field-1" placeholder="姓名" name="realname" disabled="true" class="col-xs-10 col-sm-5" type="text" value="${profile.realname}">
										</div>
									</div>
                                    
                                    <div class="space-4"></div>
                    		
                    				
                    				
										<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 身份证号 </label>

										<div class="col-sm-9">
											<input id="form-field-1" placeholder="身份证号" name="idcard" disabled="true" class="col-xs-10 col-sm-5" type="text" value="${profile.idcard}">
										</div>
									</div>
                                    
                                    <%-- <div class="space-4"></div>
                                    <div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 电话号码 </label>

										<div class="col-sm-9">
											<input id="form-field-2" placeholder="电话号码" name="telephone" disabled="true" class="col-xs-10 col-sm-5" type="text" value="${profile.telephonenumber}">
											<span class="help-inline col-xs-12 col-sm-7">
												<span class="middle"></span>
											</span>
										</div>
									</div> --%>
                                    
                                    
                                    
                                    <div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<!-- <button class="btn btn-info" type="button" id="updateProfileSubmit">
												<i class="icon-ok bigger-110"></i>
												更新
											</button> -->

											&nbsp; &nbsp; &nbsp;
											
										</div>
									</div>
                                    </form>
									</div>
		
		
		
		
						</div><!-- /.row -->
						
						
						
						
						
						
						
						
						
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->

				
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>			</a>		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->

		<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="assets/js/bootstrap.min.js"></script>
		<script src="assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->

		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#updateProfileSubmit").click(function(){
			var idcard = $("[name='idcard']").val();
			var telephone=$("[name='telephone']").val();
			var realname = $("[name='realname']").val();
			$.ajax({
				data:{idcard:idcard,telephone:telephone,realname:realname},
				dataType:'json',
				type:'post',
				url:"<c:url value='/stem/updateprofile.html'/>",
				success:function(data){
					var status = data.status;
					if(status == "success")
					{
						alert("更新成功");	
						location.href = "<c:url value='/stem/profile.html'/>";
					}
				
				}
			
			});
		});
	});
	</script>
		<!-- inline scripts related to this page -->
	<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>
