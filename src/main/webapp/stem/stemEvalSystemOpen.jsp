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
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span> </a>

			<c:import url="stemEvalSystemSidebar.jsp" charEncoding="UTF-8" />

			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
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
								请从以下三道题中任选一道作答（点击开始测试选择） </small>
						</h1>
					</div>
					<!-- /.page-header -->
					<!-- 
					<div class="row">





						<div class="item">
							<div class="heading">开放题一：iOS测试</div>
							<div class="bd">

								<div class="content clearfix">
									<img src="http://exam.csdn.net/Avatar/Get?FileID=331"
										class="pull-left k_language" alt="" height="150" width="200">
									<cite>本试卷为IOS基础综合测试，考察字符串、数组、指针、析构函数、多态、继承等基本常识。每名学生3次作答机会。
										下载分赢取规则：得分70-79分，送5个下载分；得分80-89分，送10个下载分；得分90-100分，送15个下载分；多次作答成绩
										<a class="k_down_more" hidefocus="true" href="javascript:;">展开更多介绍</a>
									</cite> <cite style="display:none;">本试卷为IOS基础综合测试，考察字符串、数组、指针、析构函数、多态、继承等基本常识。每名学生3次作答机会。
										下载分赢取规则：得分70-79分，送5个下载分；得分80-89分，送10个下载分；得分90-100分，送15个下载分；多次作答成绩均可参与下载分赢取活动。活动时间：2014.09.24-2014.10.15
										24:00.活动过期将不再赠送下载分。 <a href="javascript:;" class="k_up_more"
										hidefocus="true" title="点击收起更多介绍">收起更多介绍</a> </cite>
								</div>

								<a href="stemEvalSystemOpenSubmit.html" class="enter">开始测试</a>
							</div>
						</div>






					</div>
					


					<div class="row">




						<div class="item">
							<div class="heading">开放题二：辽宁工业大学2014年程序设计竞赛素质测评（C语言组）</div>
							<div class="bd">

								<div class="content clearfix">
									<img width="200" height="150"
										src="http://exam.csdn.net/Avatar/Get?FileID=284"
										class="pull-left k_language" alt=""> <cite>本套试卷属于辽宁工业大学2014年程序设计竞赛C语言组素质测评必答试卷。试卷满分100分，包含算法以及数据结构的基础知识，10道单选和10道多选题。每名同学一次作答机会，请准备好后开始答题。
										<a class="k_down_more" hidefocus="true" href="javascript:;">展开更多介绍</a>
									</cite> <cite style="display:none;">本套试卷属于辽宁工业大学2014年程序设计竞赛C语言组素质测评必答试卷。试卷满分100分，包含算法以及数据结构的基础知识，10道单选和10道多选题。每名同学一次作答机会，请准备好后开始答题。
										<a href="javascript:;" class="k_up_more" hidefocus="true"
										title="点击收起更多介绍">收起更多介绍</a> </cite>
								</div>

								<a href="stemEvalSystemOpenSubmit.html" class="enter">开始测试</a>
							</div>
						</div>




					</div>
					-->


					<c:forEach var="open" items="${openlist}" varStatus="status">
					<div class="row">
						<div class="item">
							<div class="heading">开放题<c:if test="${status.index == 0}" >一</c:if><c:if test="${status.index == 1}" >二</c:if><c:if test="${status.index == 2}" >三</c:if>：</div>
							<div class="bd">

								<div class="content clearfix">
									<img width="200" 
										src="<c:url value='/image/' />${open.img}"
										class="pull-left k_language" alt=""> <cite>${open.title}
									</cite> 
								</div>

								<a href="javascript:void(0);" openId="${open.openId}" paperId="${paperId}" class="enter">开始测试</a>
							</div>
						</div>
					</div>
					</c:forEach>





				</div>
				<!-- /.page-content -->
			</div>
			<!-- /.main-content -->

			<div class="ace-settings-container" id="ace-settings-container">
				<div class="btn btn-app btn-xs btn-warning ace-settings-btn"
					id="ace-settings-btn">
					<i class="icon-cog bigger-150"></i>
				</div>

				<div class="ace-settings-box" id="ace-settings-box">
					<div>
						<div class="pull-left">
							<select id="skin-colorpicker" class="hide">
								<option data-skin="default" value="#438EB9">#438EB9</option>
								<option data-skin="skin-1" value="#222A2D">#222A2D</option>
								<option data-skin="skin-2" value="#C6487E">#C6487E</option>
								<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
							</select>
						</div>
						<span>&nbsp; Choose Skin</span>
					</div>

					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-navbar" /> <label class="lbl"
							for="ace-settings-navbar"> Fixed Navbar</label>
					</div>

					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-sidebar" /> <label class="lbl"
							for="ace-settings-sidebar"> Fixed Sidebar</label>
					</div>

					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-breadcrumbs" /> <label class="lbl"
							for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
					</div>

					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-rtl" /> <label class="lbl"
							for="ace-settings-rtl"> Right To Left (rtl)</label>
					</div>

					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-add-container" /> <label class="lbl"
							for="ace-settings-add-container"> Inside <b>.container</b>
						</label>
					</div>
				</div>
			</div>
			<!-- /#ace-settings-container -->
		</div>
		<!-- /.main-container-inner -->

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="icon-double-angle-up icon-only bigger-110"></i> </a>
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->

	<!--[if !IE]> -->

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

	<!-- <![endif]-->

	<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

	<!--[if !IE]> -->
<script type="text/javascript">

	$(document).ready(function(){
		$(".enter").click(function(){
			var paperId=$(this).attr("paperId");
			var openId = $(this).attr("openId");
				$.ajax({
					data:{paperId:paperId,openId:openId},
					dataType:'json',
					type:"post",
					url:"<c:url value='/stem/opensubmitprocess.html' />",
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
						location.href="<c:url value='/stem/opensubmit.html' />?paperId="+paperId;//alert("成功");
					}
				
				}
				});
		});
	});
	</script>




	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='assets/js/jquery-2.0.3.min.js'>"
								+ "<"+"/script>");
	</script>

	<!-- <![endif]-->

	<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

	<script type="text/javascript">
		if ("ontouchend" in document)
			document
					.write("<script src='assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/typeahead-bs2.min.js"></script>

	<!-- page specific plugin scripts -->

	<!-- ace scripts -->

	<script src="assets/js/ace-elements.min.js"></script>
	<script src="assets/js/ace.min.js"></script>

	<!-- inline scripts related to this page -->
	<div style="display:none">
		<script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540'
			language='JavaScript' charset='gb2312'></script>
	</div>
</body>
</html>
