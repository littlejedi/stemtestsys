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
						<li class="active">考试列表</li>
					</ul>
					<!-- .breadcrumb -->

					<div class="nav-search" id="nav-search">
					</div>
					<!-- #nav-search -->
				</div>

				<div class="page-content">
					<div class="page-header">
						<h3>
							 &nbsp;&nbsp;STEM测评系统正在为您安排考场与试卷，请稍后。<br/><br/>
							 &nbsp;&nbsp;离考试开始还有 &nbsp;&nbsp;<span style="color:red;" class="remainingTime" data-diff="${remainingtime}"></span>
						</h3>
					</div>
					<!-- /.page-header -->

					

					<div class="row" style="margin-left:20px;">
						<pre>本次考试时间为90分钟，100道题，共300分。
因各类原因（包括不可抗力）致使考试中断,考生在自考试开始后90分钟内重新登录可以继续作答，但将扣除中断时间。否则，考生将失去一次考试机会。请在考试前确认网络环境安全通畅。
						</pre>
						<!-- <img width="500" height="500" /> -->
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
	<script src="<c:url value='/stem/js/jquery-countdown-master/jquery.countdown.js' />"></script>
	<script>
			$(function() {
				
				$('.remainingTime').countdown({
					tmpl : '<span>%{m}</span>分<span>%{s}</span>秒',
					afterEnd: function() { 
						var url="<c:url value='/stem/beginexam.html'/>";//+paperId;
						var paperId = '${paperId}';
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
					location.href="<c:url value='/stem/answer.html'/>?paperId="+paperId;
						/* if(st == "0")
						{
							alert("付款成功!");
							location.reload();
						}
						else if(st == "1")
						{
							location.href="<c:url value='/stem/answer.html'/>?paperId="+paperId;
						} */
					}
				
				}
				});

			}
				});
				/* 
				$('.J_countdown3').countdown({
					tmpl : '<span>%{d}</span>天, <span>%{h}</span>小时, <span>%{m}</span>分, <span>%{s}</span>秒'
				}); */
			});
		</script>
	<!-- inline scripts related to this page -->
</body>
</html>
