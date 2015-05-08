<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import
url="stemEvalSystemTop.jsp" charEncoding="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>
<script type="text/javascript">
function nameA(){
	alert("您选择STEM综合能力测试，请阅读考试须知并开始考试");
}
function nameB(){
	alert("您选择生物竞赛能力测试，请阅读考试须知并开始考试");
}
</script>
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
							 &nbsp;&nbsp;${profile.realname},欢迎您！您还有${profile.examTimes}次考试机会。“不是该用户？<a href="/login.html">点此切换</a>”
						</h3>
						
					</div>
					<!-- /.page-header -->

					<div class="page-header">
					 <div class="row row-bg">
                    <div class="col-sm-12 col-md-3">
                    <form> <table><tr>
                    <td><button class="btn" id="selectnameA" name=examname value="stemtest" onclick="return nameA();">STEM综合能力测试</button></td>
                    <td><button class="btn" id="selectnameB" name=examname value="biologytest" onclick="return nameB();">生物竞赛能力测试</button></td>
                   <!-- <td><button class="btn" id="selectnameC" name=examname value="C" onclick="return nameC();">点此参加名称C考试</button></td>-->
                    </tr></table></form>
                    </div>
                </div>
						<h1>
							<small> 
								<input type="checkbox" name="examknows" id="examknows"/> &nbsp;我已阅读并认可考试须知 </small> 
								<c:if test="${profile.examTimes > 0}">  &nbsp; &nbsp;
								<button class="btn btn-danger" type="button" id="beginexamsubmit" >点此开始考试</button>
								</c:if>
						</h1>
					</div>
					<!-- /.page-header -->

					<div class="row" style="margin-left:20px;">
					<span style="color:red;font-size:18px;">STEM答题系统友情提醒：推荐您使用火狐浏览器或者谷歌浏览器进行考试以获得最好的体验，我们不推荐使用IE进行考试.</span><br/>
					<span style="color:red;font-size:18px;">浏览器下载地址：<img width="20" height="20" src="<c:url value='/stem/images/firefox.png'/>"/><a href="http://www.firefox.com.cn/">火狐浏览器</a>&nbsp;&nbsp;<img width="20" height="20"  src="<c:url value='/stem/images/chrome.png'/>"/><a href="http://download.pchome.net/internet/browser/browser/detail-141810.html">谷歌浏览器</a><!-- <img width="50" height="50" src="<c:url value='/stem/images/firefox.png'/>"/> <img width="55" height="55"  src="<c:url value='/stem/images/chrome.png'/>"/> --></span><br/>
						<pre>STEM综合素养测评系统考试须知

一、	什么是STEM综合素养测评系统
STEM是科学（Science）、技术（Technology）、工程（Engineering）和数学（Mathematics）的首字母缩写。STEM教育是基于以上四大领域的教育。它注重的不是追其单纯的知识本身，而是一种正确的科学、技术、工程、数学四个领域的探究及运用的方法，同时鼓励学科间的交叉，推进跨学科的融合，旨在培养具有创新能力的复合型人才。
“STEM综合素养测评系统（后称‘测评系统’）”是以物质科学、生命科学、技术与设计、地球与空间四大学科为背景， STEM教育理念为支撑，STEM八大研究步骤“生成研究主题—制定研究计划—进行主题背景研究—撰写研究方案—准备并开展实验—统计分析—解释数据—报告结果”为主线，从科学常识、各学科基础知识的掌握、跨学科知识的运用、科学研究方法及逻辑思维能力等多个方面考察测试者的综合科学研究与创新能力的评估体系。
本测试作为第30届上海青少年科技创新大赛（后称“创赛”）赛前测试，其成绩将作为申报创赛的必要条件，并作为赛事评审参考之一。

二、	考试规则
(一)	考生自报名成功获得5次考试机会，点击“考试”进入正式考试。
(二)	考试时间为90分钟，100道题，共300分。
(三)	考试题型为单项选择题，包括常规型选择题及优选型选择题。常规型选择题为只有一个正确答案的单项选择题，每题3分。优选型选择题为多个选项均为可选选项，考生根据判断选择最优答案的单项选择题，最优答案为3分、次优2分，依次类推，错误0分。
(四)	考试结束即时显示测评分数及STEM素养指数。

三、	备注
(一)	由于该考试为第30届上海青少年科技创新大赛赛前测试，请在测试结束后返回大赛申报页面点击“分数刷新/测评分数刷新”按钮同步分数，进入下一步大赛申报程序。
(二)	因各类原因（包括不可抗力）致使考试中断,考生在自考试开始后90分钟内重新登录可以继续作答，但将扣除中断时间。否则，考生将失去一次考试机会。请在考试前确认网络环境安全通畅。
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
		$("#beginexamsubmit").click(function(){
			var ek = $("#examknows")[0].checked;
			//alert(ek);
			if(!ek)
			{
				alert("请您先阅读考试须知，若认可请勾选下方的复选框，再点击开始考试按钮！");
				return;
			}
			var examname = '${examname}'
			//if($("#examknows").)
			$.ajax({
				data:{examname:examname},
				dataType:'json',
				type:'post',
				url:"<c:url value='/stem/newsignin.html'/>",
				success:function(data){
					var status = data.status;
					if(status == "success")
					{
						alert("报名成功");	
						location.href = "<c:url value='/stem/waitpage.html'/>?paperId="+data.paperId;
					}
					else if(status == "notfound")
					{
						alert("暂时尚无考试！");
					}
					else if(status == "duplicate")
					{
						alert("您已报名！");
						location.href = "<c:url value='/stem/waitpage.html'/>?paperId="+data.paperId;
					}
					else if(status == "notimes")
					{
						alert("考试次数不够！");
					}
					else if(status == "noinfo")
					{
						alert("您尚未完善个人信息，无法开始考试");
						//location.href = "<c:url value='/stem/profile.html'/>";
					}
				
				}
			
			});
		});
	});
	</script>
	<!-- inline scripts related to this page -->
</body>
</html>