<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
							<li class="active">考试列表</li>
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
								
								<small>
									<i class="icon-double-angle-right"></i>
									考试列表								</small>							</h1>
						</div><!-- /.page-header -->

						<div class="row">
						
						
						
					<div class="col-xs-12">
										<div class="table-responsive">
											<table id="sample-table-1" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<!-- <th class="center">
															<label>
																<input type="checkbox" class="ace">
																<span class="lbl"></span>
															</label>
														</th> -->
														<th>序号</th>
														<th>考试时间</th>
														

														<th>
															<i class="icon-time bigger-110 hidden-480"></i>
															状态
														</th>
														<th class="hidden-480">操作</th>

														
													</tr>
												</thead>

												<tbody>
												<c:forEach var="exam" items="${examlist}">
													<tr>
														<!-- <td class="center">
															<label>
																<input type="checkbox" class="ace">
																<span class="lbl"></span>
															</label>
														</td> -->
														
														<td>
															<a href="#">${exam.examId}</a>
														</td>
														<td>${exam.examBegin}——${exam.examEnd}</td>
														<td class="hidden-480">${exam.statusDes}</td>
														<td><c:if test="${exam.status == 0}"><a href="javascript:void(0);" class="signIn" examId="${exam.examId}">报名</a></c:if><c:if test="${exam.status != 0}">无</c:if></td>
							
														
													</tr>
													</c:forEach>

													
												</tbody>
											</table>
										</div><!-- /.table-responsive -->
									</div>
		
		
						</div><!-- /.row -->
						
						
						<!-- 
						<div class="row"><div class="col-sm-6"><div class="dataTables_info" id="sample-table-2_info">Showing 1 to 10 of 23 entries</div></div><div class="col-sm-6"><div class="dataTables_paginate paging_bootstrap"><ul class="pagination"><li class="prev disabled"><a href="#"><i class="icon-double-angle-left"></i></a></li><li class="active"><a href="#">1</a></li><li><a href="#">2</a></li><li><a href="#">3</a></li><li class="next">m</li></ul></div></div></div>
						 -->
						
						
						
						
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
			if("ontouchend" in document) document.write("<script src='<c:url value='/stem/assets/js/jquery.mobile.custom.min.js'/>'>"+"<"+"/script>");
		</script>
		<script src="<c:url value='/stem/assets/js/bootstrap.min.js'/>"></script>
		<script src="<c:url value='/stem/assets/js/typeahead-bs2.min.js'/>"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->

		<script src="<c:url value='/stem/assets/js/ace-elements.min.js'/>"></script>
		<script src="<c:url value='/stem/assets/js/ace.min.js'/>"></script>
		<script type="text/javascript">
	$(document).ready(function(){
		$(".signIn").click(function(){
			var examId = $(this).attr("examId");
			$.ajax({
				data:{examId:examId},
				dataType:'json',
				type:'post',
				url:"<c:url value='/stem/signin.html'/>",
				success:function(data){
					var status = data.status;
					if(status == "success")
					{
						alert("报名成功");	
						location.href = "<c:url value='/stem/myexam.html'/>";
					}
					else if(status == "expired")
					{
						alert("您报名的考试已过期！");
					}
					else if(status == "duplicate")
					{
						alert("请勿重复报名！");
					}
					else if(status == "noinfo")
					{
						alert("您尚未完善个人信息，无法报名");
						location.href = "<c:url value='/stem/profile.html'/>";
					}
				
				}
			
			});
		});
	});
	</script>
		<!-- inline scripts related to this page -->
</body>
</html>
