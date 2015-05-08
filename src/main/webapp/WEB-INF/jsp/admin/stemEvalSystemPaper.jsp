
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" />
<script>
$(document).ready(function() { App.init(); Plugins.init();
FormComponents.init();
$(".inlinepicker").datepicker({inline:true,showOtherMonths:true}); });
</script>
<script type="text/javascript" src="assets/js/custom.js">
    </script>
<link rel="stylesheet" href="<c:url value='/stem/css/gaoxiao_v2.css'/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<script
	src="<c:url value='/stem/js/jquery-countdown-master/jquery.countdown.js' />"></script>
<script>
	<c:if test="${remainingtime > 0 }">
			var submitcheck = false;
			</c:if>
			<c:if test="${remainingtime <= 0 }">
			var submitcheck = true;
			</c:if>
			$(function() {
				<c:if test="${remainingtime > 0 && remainingtime <=180}">
				$('.remaining').countdown({
					tmpl : '<span>%{m}</span>分<span>%{s}</span>秒',
					afterEnd: function() 
					{ 
						submitcheck = true;
					}
				});
				</c:if>
				$("#download").click(function(){
					//alert("dsf");
					var reviewId = $(this).attr("reviewid");
					$.ajax({
						data:{reviewId:reviewId},
						dataType:'json',
						type:'post',
						url:"<c:url value='/tcadmin/setdownloadtime.html' />",
						success:function(data){
							if(data.status == "empty")
							{
								submitcheck = false;
								$('.remaining').attr("data-diff",180);
							    $('.remaining').countdown({
									tmpl : '<span>%{m}</span>分<span>%{s}</span>秒',
									afterEnd: function() 
									{ 
										submitcheck = true;
									}
								}); 
							}
							else if(data.status == "error")
								alert("出现错误！");
						
						
						}
					});
					
					
				});
				/* 
				$('.J_countdown3').countdown({
					tmpl : '<span>%{d}</span>天, <span>%{h}</span>小时, <span>%{m}</span>分, <span>%{s}</span>秒'
				}); */
			});
		
		function Submit()
		{
			var s=$("#s").val();
			var t=$("#t").val();
			var e=$("#e").val();
			var m=$("#m").val();
			var review_result=$("#review_result").val();
			var isNum=  /^\+?[1-9][0-9]*$/;
 
 			if(s==''||t==''||e==''||m==''||review_result=='')			
			{
				console.log(s+t+e+m);
				alert("请输入分数");
				return false;
			}
			
 			else if(!isNum.test(s)||!isNum.test(t)||!isNum.test(e)||!isNum.test(m)||!isNum.test(review_result))
			{
 				alert("所有的分数都应为正整数");
				return false;
			
			}
			else if(!submitcheck)
			{
				alert("至少三分钟后才能提交分数！");
				return false;
			}
			return true;

		}
	</script>


<body>
	<c:import url="stemEvalSystemHeader.jsp" charEncoding="UTF-8" />
	<div id="container">
		<c:import url="stemEvalSystemTeacherSidebar.jsp" charEncoding="UTF-8"></c:import>
		<div id="content">
			<div class="container">
				<div class="crumbs">
					<ul id="breadcrumbs" class="breadcrumb">
						<li class="current"><i class="icon-home"></i> <a href="#">试卷下载</a>
						</li>
					</ul>
				</div>

				<div class="row">
					<div class="item">
						<div class="heading">开放题评阅：</div>
						<div class="bd">

							<div class="content clearfix">
								<c:if test="${open.img != null }">
									<img src="<c:url value='/image/'/>${open.img}"
										class="pull-left k_language" alt="" width="200">
								</c:if>
								<cite><strong>题目：</strong>
									<p>&nbsp;&nbsp;&nbsp;&nbsp;${open.title}</p> </cite><br /> <cite><strong>答案模板：</strong>
									<p>&nbsp;&nbsp;&nbsp;&nbsp;${open.as_example}</p> </cite><br /> <cite><strong>考点：</strong>
									<p>&nbsp;&nbsp;&nbsp;&nbsp;${open.test_point}</p> </cite><br /> <cite><strong>题目说明：</strong>
									<p>&nbsp;&nbsp;&nbsp;&nbsp;${open.description}</p> </cite><br />
							</div>

							<form class="form-horizontal" role="form" method="post"
								action="<c:url value='/tcadmin/reviewpaper.html' />">
								<input name="reviewId" value="${reviewId}" type="hidden" />

								<div class="form-group"></div>

								<!-- <div class="space-4"></div>
                                    -->



								<div class="clearfix form-actions">
									<div class="col-md-offset-3 col-md-9">


										<div class="form-group">

											<label class="control-label col-md-7"></label>
											<div class="col-md-7">
												<a target="_blank" reviewid="${reviewId}" id="download"
													href="<c:url value='/tcadmin/download.html' />?reviewId=${reviewId}"><p>点击下载试卷</p>
												</a> <span class="remaining" data-diff="${remainingtime}"></span>
												<%-- <c:if test="${remainingtime > 0 }">
                            <span class="remaining" data-diff="${remainingtime}"></span>
                            </c:if> --%>
											</div>
										</div>
										<div class="form-group">


											<div class="col-md-5">
												<input type="text" class="form-control" placeholder="S值"
													name="s" id="s">
											</div>
										</div>
										<div class="form-group">


											<div class="col-md-5">
												<input type="text" class="form-control" placeholder="T值"
													name="t" id="t">
											</div>
										</div>
										<div class="form-group">


											<div class="col-md-5">
												<input type="text" class="form-control" placeholder="E值"
													name="e" id="e">
											</div>
										</div>
										<div class="form-group">


											<div class="col-md-5">
												<input type="text" class="form-control" placeholder="M值"
													name="m" id="m">
											</div>
										</div>
										<div class="form-group">


											<div class="col-md-5">
												<input type="text" class="form-control" placeholder="请输入分数"
													name="review_result" id="review_result">
											</div>
										</div>


										<input type="submit" class="btn btn-success" value="提交"
											onClick="return Submit();" /> &nbsp; &nbsp; &nbsp;

									</div>
								</div>
							</form>
						</div>




					</div>

				</div>
				<!-- /.row -->

			</div>
		</div>
	</div>
	<script type="text/javascript">
          if (location.host == "envato.stammtec.de" || location.host == "themes.stammtec.de") {
            var _paq = _paq || [];
            _paq.push(["trackPageView"]);
            _paq.push(["enableLinkTracking"]); (function() {
              var a = (("https:" == document.location.protocol) ? "https": "http") + "://analytics.stammtec.de/";
              _paq.push(["setTrackerUrl", a + "piwik.php"]);
              _paq.push(["setSiteId", "17"]);
              var e = document,
              c = e.createElement("script"),
              b = e.getElementsByTagName("script")[0];
              c.type = "text/javascript";
              c.defer = true;
              c.async = true;
              c.src = a + "piwik.js";
              b.parentNode.insertBefore(c, b)
            })()
          };

    </script>
</body>
</html>