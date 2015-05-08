<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" /> 
    <script>
      $(document).ready(function() {
        App.init();
        Plugins.init();
        FormComponents.init();
        $(".inlinepicker").datepicker({inline:true,showOtherMonths:true});
      });
    </script>
    <script type="text/javascript" src="assets/js/custom.js">
    </script>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
  
  <body>


   
  <c:import url="stemEvalSystemHeader.jsp" charEncoding="UTF-8" /> 
    
    <div id="container">
       <c:import url="stemEvalSystemTeacherSidebar.jsp" charEncoding="UTF-8"></c:import>
        <div id="content">
            <div class="container">
                <div class="crumbs">
                    <ul id="breadcrumbs" class="breadcrumb">
                        <li class="current">
                            <i class="icon-home"></i>
                            <a href="#">试卷</a>
                        </li>
                    </ul>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-striped table-bordered table-hover table-checkable" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    
                                    <th>
                                       考试编号
                                    </th>
                                    <th>
                                      考生账号
                                    </th>
                                    <th>
                                      考生姓名
                                    </th>
                                     <th>
                                      考试时间
                                    </th>
                                     <th>
                                      评阅状态
                                    </th>
                                     <th>
                                       操作
                                    </th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="review" items="${reviewlist}"> 
                            		<tr>
	                                    <td>
	                                        <c:out value="${review.examId}" />
	                                    </td>
                                   		<td>
	                                        <c:out value="${review.userId}" />
	                                    </td>
	                                    <td>
	                                        <c:out value="${review.name}" />
	                                    </td>
                                        <td>
                                            <c:out value="${review.examBegin}" />——<c:out value="${review.examEnd}" />
                                        </td>
                                        <td>
                                            <c:out value="${review.status}" />
                                        </td>
										<td>
											<c:if test="${review.hasResult == 1}">
	                                        <a  reviewid="<c:out value="${review.reviewId}" />" class="viewResult">查看成绩</a>&nbsp;
	                                    	</c:if>
	                                    	<c:if test='${review.status == "未评阅"}'>
	                                    		<a href="<c:url value='/tcadmin/paper.html' />?reviewId=${review.reviewId}">评阅</a>
	                                    	</c:if>
	                                    </td>
	                                </tr> 
     							</c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
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