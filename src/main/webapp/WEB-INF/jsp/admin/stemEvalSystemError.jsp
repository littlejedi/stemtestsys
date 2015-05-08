<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" />
<script>
	$(document).ready(function() {
		$(".datepicker").datepicker({
			defaultDate : +0,
			showOtherMonths : true,
			autoSize : true,
			dateFormat : "yy/mm/dd"
		});

	});
</script>
<script>
	
</script>
<body>
	<c:import url="stemEvalSystemHeader.jsp" charEncoding="UTF-8" />
	<div id="container">
		<div class="row" style="text-align: center; padding-top:50px;">
		<!--  class="error" id="error" -->
			<!-- <div style="margin-left:auto;margin-right:auto;"> -->
    			<img id="logo" src="<c:url value='/admin/assets/img/explosion.jpg' />" />
    			<p>啊欧，发生了异常错误，我们已经派出了专业的科学家研究这起事故。</p>
    			<p><!-- <a href="/" src="/">点击这里返回主页</a> --></p>
			<!-- </div> -->
		</div>
	</div>
</body>
</html>