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
								<a href="#">Other Pages</a>							</li>-->
							<li class="active">科目选择页面</li>
						</ul><!-- .breadcrumb -->

						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>								</span>
							</form>
						</div><!-- #nav-search -->
					</div>

					<div class="page-content">
						<div class="page-header">
							<h1>开放题科目选择						</h1>
						</div><!-- /.page-header -->

						<div class="row">
						
						
						<div class="alert alert-block alert-warning">

									<i class="icon-ok red"></i>

									请选择考试科目：
									 <select class="select2" data-minimum-results-for-search="-1" name="course" >
                            <option value="L">
                                	生命科学
                              </option>
                              <option value="M">
                                	物质科学
                              </option>
                              <option value="T">
                                	技术与设计
                              </option>
                              <option value="E">
                                	地球与环境科学
                              </option>
                              <option value="S">
                                	社会及行为科学
                              </option>
                          </select>
								</div>
                                <button class="btn btn-info" type="button" id="choosecoursesubmit" pid="${paperId}">
												<i class="icon-ok bigger-110"></i>
												提交
											</button>
						</div><!-- /.row -->
						
						
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->

				
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>			</a>		</div><!-- /.main-container -->

		<!-- basic scripts -->

<!--[if !IE]> -->

		<script src="<c:url value='/stem/assets/js/jquery-1.10.2.min.js'/>"></script>

		<!-- <![endif]-->

		<!--[if IE]>
			<script src="<c:url value='/stem/assets/js/jquery-1.10.2.min.js'/>"></script>
		<![endif]-->		



<script type="text/javascript">

	$(document).ready(function(){
		$("#choosecoursesubmit").click(function(){
			var paperId=$(this).attr("pid");
			var course = $("[name='course']").val();
				$.ajax({
					data:{paperId:paperId,course:course},
					dataType:'json',
					type:"post",
					url:"<c:url value='/stem/beginopen.html'/>",
					success:function(data){
					if(data.status == "noauth")
						alert("无法访问！");
					else if(data.status == "expired")
					{
						alert("已过期！");
						location.reload();
					}
					else if(data.status == "notbegin")
						alert("考试尚未开始！");
					else if(data.status == "success")
					{
						alert("成功");
						location.href = "<c:url value='/stem/open.html' />?paperId="+paperId;
					}
				
				}
				});
		});
	});
	</script>
		<!-- <![endif]-->

		<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

		
</body>
</html>
