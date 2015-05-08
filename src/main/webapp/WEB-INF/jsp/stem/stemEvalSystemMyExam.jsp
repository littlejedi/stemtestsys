<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import
url="stemEvalSystemTop.jsp" charEncoding="UTF-8" /> 
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
							<li class="active">我的考试</li>
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
									我参加过的考试								</small>							</h1>
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
															考试进程
														</th>
														<th class="hidden-480">操作</th>

														
													</tr>
												</thead>

												<tbody>
												<c:forEach var="myexam" items="${myexamlist}">
													<tr>
														<!--<td class="center">
															<label>
																<input type="checkbox" class="ace">
																<span class="lbl"></span>
															</label>
														</td>-->
														
														<td>
															<a href="#">${myexam.paperId}</a>
														</td>
														<td>${myexam.examBegin}——${myexam.examEnd}</td>
														<td class="hidden-480"><c:if test="${myexam.status != 3 && myexam.status != 4}">${myexam.statusDes}</c:if><c:if test="${myexam.status == 3 || myexam.status == 4}">选择题考试已结束</c:if></td>
														<td>
															<c:if test="${myexam.status == 0 || myexam.status == 1}"><a href="javascript:void(0);" class="nextstep" st="${myexam.status}" pid="${myexam.paperId}">${myexam.nextStep}</a></c:if>
															<c:if test="${myexam.status == 2}"><a href="<c:url value='/stem/answer.html?paperId=${myexam.paperId}'/>">${myexam.nextStep}</a></c:if>
															<c:if test="${myexam.status == 3 ||  myexam.status == 10}"><a href="<c:url value='/stem/choiceresult.html?paperId=${myexam.paperId}'/>">${myexam.nextStep}</a></c:if>
															<c:if test="${myexam.status == 4}"><a href="<c:url value='/stem/choiceresult.html?paperId=${myexam.paperId}'/>">查看选择题成绩</a></c:if>
															<c:if test="${myexam.status == 5}">${myexam.nextStep}</c:if>
															<c:if test="${myexam.status == 6}"><a href="<c:url value='/stem/choiceresult.html?paperId=${myexam.paperId}'/>">查看选择题成绩</a></c:if>
															<c:if test="${myexam.status == 7}"><a href="<c:url value='/stem/choiceresult.html?paperId=${myexam.paperId}'/>">查看选择题成绩</a></c:if>
															<c:if test="${myexam.status == 8 ||  myexam.status == 9}"><a href="<c:url value='/stem/choiceresult.html?paperId=${myexam.paperId}'/>">查看选择题成绩</a></c:if>
														</td>
							
														
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
	function signUp()
	{
		alert("支付成功!");
		location.href="stemEvalSystemMyExam1.html";	
	}
	$(document).ready(function(){
		$(".nextstep").click(function(){
			var st = $(this).attr("st");
			var paperId=$(this).attr("pid");
			var url="";
			if(st == "0")
				url="<c:url value='/stem/pay.html'/>";//+paperId;
			else if(st == "1")
				url="<c:url value='/stem/beginexam.html'/>";//+paperId;
				$.ajax({
					data:{paperId:paperId},
					dataType:'json',
					type:"post",
					url:url,
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
						if(st == "0")
						{
							alert("付款成功!");
							location.reload();
						}
						else if(st == "1")
						{
							location.href="<c:url value='/stem/answer.html'/>?paperId="+paperId;
						}
					}
				
				}
				});
		});
	});
	</script>
		<!-- inline scripts related to this page -->
</body>
</html>
