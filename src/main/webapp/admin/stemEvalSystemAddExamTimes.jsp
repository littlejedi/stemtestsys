<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" /> 
<script >
	$(document).ready(function() {
		$("#addExamTimesSubmit").click(function(){
			var idcard = $("[name='idcard']").val();
			var num=$("[name='num']").val();
			$.ajax({
				data:{idcard:idcard,num:num},
				dataType:'json',
				type:'post',
				url:"<c:url value='/superadmin/addexamtimes.html'/>",
				success:function(data){
					var status = data.status;
					
					if(status == "success")
					{
						$("#warninginfo").hide();
						$("#successinfo").show();
					}
					else if(status == "unmatch")
					{
						$("#successinfo").hide();
						$("#warninginfo").show();
					}
					else 
					{
						$("#warninginfo").hide();
						$("#successinfo").hide();
					    alert("发生未知错误！");
					}
				
				}
			
			});
		});
	});
	</script>
</head>
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
                            <a href="#">考生增加考试次数</a>
                        </li>
                    </ul>
                </div>
                <div class="space-4"></div>
                <div class="row" style="margin-top:20px;">
                    <div class="col-xs-12">
                   
                   <div class="alert alert-warning fade in" id="warninginfo" style="display:none;">
                    <i class="icon-remove close" data-dismiss="alert">
                    </i>
                    <strong>
                
                    </strong>
                    未找到此身份证号对应的考生，请核对后输入
                  </div>
                  
                  <div class="alert alert-success fade in" id="successinfo" style="display:none;">
                    <i class="icon-remove close" data-dismiss="alert">
                    </i>
                      该考生的考试次数添加
                    <strong>
                     成功！
                    </strong>
                 
                  </div>
                  
                    <form class="form-horizontal" role="form">
                    
                    
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 身份证号 </label>

										<div class="col-sm-3">
											<input id="form-field-1" placeholder="身份证号" name="idcard" class="col-xs-10 col-sm-5 form-control" type="text" value="">
										</div>
									</div>
                                    
                                    <div class="space-4"></div>
                                    <div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 考试次数</label>

										<div class="col-sm-3">
											<input id="form-field-2" placeholder="考试次数" name="num" class="col-xs-10 col-sm-5 form-control" type="text" value="">
											<span class="help-inline col-xs-12 col-sm-7">
												<span class="middle"></span>
											</span>
										</div>
									</div>
                                    
                                    
                                    
                                    <div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="button" id="addExamTimesSubmit">
												<i class="icon-ok bigger-110"></i>
												确认
											</button>

											&nbsp; &nbsp; &nbsp;
											
										</div>
									</div>
                                    </form>
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