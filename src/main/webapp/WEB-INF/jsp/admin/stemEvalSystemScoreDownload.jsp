<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" /> 
    <script>
      $(document).ready(function() {
        $(".datepicker").datepicker({defaultDate:+0,showOtherMonths:true,autoSize:true,dateFormat:"yy-mm-dd"});

      });
    </script>
  <script >
	  var showInfo = function(info) {
			bootbox.alert(info,function(){console.log("Alert Callback")})
	  };
	  function examSet()
		{
		    //获取复选框society的值
		    //考试设置的表单判断
		    //用法：onclick=return examSet();
		    if($("[name='examSociety']").val()=='')
			{
				alert("请填写考试所属学会信息");
				$("[name='examSociety']").val()=null;
			}
			if($("[name='examSubject']").val()=='')
			{
				alert("请填写考试所属学科信息");
				$("[name='examSubject']").val()=null
			}
			if($("[name='examName']").val()=='')
			{
				alert("请填写考试开始时间");
				return false;
			}
			return true;
			
		}
	  function doExport(){
	         document.all.exportForm.action="D:\java\workspace\testsys\WebRoot\admin\export.jsp";
	         var str = document.getElementById("table1").outerHtml;
	         document.all.excelText.value=str;
	         document.all.exportForm.submit();
	}
	</script>
  <body>
      <c:import url="stemEvalSystemHeader.jsp" charEncoding="UTF-8" /> 
    <div id="container">
        <c:import url="stemEvalSystemSuperSidebar.jsp" charEncoding="UTF-8"></c:import>
        <div id="content">
            <div class="container">
                <div class="crumbs">
                    <ul id="breadcrumbs" class="breadcrumb">
                        <li class="current">
                            <i class="icon-home"></i>
                            <a href="#">成绩下载</a>
                        </li>
                    </ul>
                </div>
                            <form class="form-horizontal" id="add_admin" novalidate="novalidate">
            <div class="modal-body">
              

                  <div class="form-group">
                          <label class="control-label col-md-3">学会信息</label>
                          <div class="col-md-7">
                            <input type="text" name="examSociety" class="form-control"  placeholder="请填写学会信息">
                          </div>
                      </div>
                       <div class="form-group">
                          <label class="control-label col-md-3">学科信息</label>
                          <div class="col-md-7">
                            <input type="text" name="examSubject" class="form-control"  placeholder="请填写学科信息">
                          </div>
                      </div>
					  
     <div class="form-group">
                          <label class="control-label col-md-3">考试名称</label>
                          <div class="col-md-3">
                           <input type="text" name="examName" class="form-control"  placeholder="请填写考试名称">
                           </div>
                      </div>
                  </div>
              <input type="submit" class="btn btn-success" id="addExam" value="提交" onclick="return examSet();"/>
              <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
            </form>
                <div class="row">
                    <div class="col-md-12">
					
                       <table class="table table-striped table-bordered table-hover table-checkable" id="table1" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>
                                        序号
                                    </th>
                                    
                                    <th>
                                        选择题成绩
                                    </th>
                                    <th>
                                  开放题成绩
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                            
                            <c:forEach var="result" items="${result}"> 
                            		<tr>      
                            		<td>
                            		<c:out value="${result.resultId}" />
                            		</td>                             
	                                    <td>
	                                        <c:out value="${result.choicescore}" />
	                                    </td>
                                   
	                                   <td>
	                                        	<c:out value="${result.openscore}" />
	                                    </td>
	                                </tr> 
     							</c:forEach>
                            
                                
                            </tbody>
                        </table>
                    </div>
                </div>
                
                <form name="exportForm" method="post" action="">
                <input type="hidden" name="excelText" id="excelText">
                <input name="exportBtn" type="button" onclick="doExport()" class="button" value="下载成绩列表">
                </form>
                <div class="row">
                    <div class="table-footer">
                      <div class="col-md-6">
                        <div class="table-actions">
                          
                        </div>
                      </div>
                      <div class="col-md-6">
                      
                        <ul class="pagination">
                       <%--  <c:if test="${page.isCur}">class="active"</c:if> --%>
                        		<c:forEach var="page" items="${pagelist}">
                        			<c:if test="${page.isDisabled == 1}">
                        				<li class="disabled"><a href="#">${page.name}</a></li>
                        			</c:if>
                        			<c:if test="${page.isDisabled == 0	}">
                        				<li  <c:if test="${page.isCur == 1}">class="active"</c:if> >
                        					<a href="<c:url value='${page.link}'/>">${page.name}</a>
                        				</li>
                        			</c:if>
                         		 
								</c:forEach>  
								               
                        </ul>
                      </div>
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