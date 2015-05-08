<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" /> 
    <script type="text/javascript" src="<c:url value='/admin/plugins/fileinput/fileinput.js' />">
    </script>
    <script type="text/javascript">
    var path="<c:url value='/image/' />";
	$(document).ready(function(){
		
		
		
		$(".removeOpen").click(function(b){
			var openid=$(this).attr("openid");
			b.preventDefault();
			bootbox.dialog({
						message:"确认删除此题目吗？",
						title:"删除题目",
						buttons:{success:{label:"确认",className:"btn-success",callback:function(){removeOpen(openid);}},
						cancel:{label:"取消",className:"btn-success",callback:function(){/*alert("已取消")*/}}}
			});
		});
		$("#removeMultiOpen").click(function(b){
			
			var openid="";//=$(this).attr("memid");
			var obj=document.getElementsByName("open");
			var q=0;
			for(var i=0;i<obj.length;i++){
				if(obj[i].checked){
				  	//alert(obj[i].value+",");
				if(q==0)
				{
					openid += obj[i].value;
					q=1;
				}
				else
					openid += ","+obj[i].value;
				}
			}
			//alert(memid);
			
			b.preventDefault();
			if(q==0)
				showInfo("你没选中任何题目！");
			else	
				bootbox.dialog({
						message:"确认删除选中题目吗？",
						title:"删除题目",
						buttons:{success:{label:"确认",className:"btn-success",callback:function(){removeOpen(openid);}},
						cancel:{label:"取消",className:"btn-success",callback:function(){/*alert("已取消")*/}}}
			});
		});
	});
	var removeOpen = function(openlist)	{
		$.ajax({
			data:'openId='+openlist,
			dataType:'text',
			type:'post',
			url:"<c:url value='/ecadmin/removeopen.html' />",
			success:function(data){
				console.log("return success");
				console.log(data);
				bootbox.alert("删除成功",function(){location.reload();});
				
			}
			});  
	  };
	  var showInfo = function(info) {
			bootbox.alert(info,function(){console.log("Alert Callback")})
	  };  
	  function judge()
                  {
				  //习题管理员文件上传的表单判断
				  //用法：onclick=return judge();
                     if ($("[id$=mytext]").val()=='')
				     {
                        console.log("test"); 
                        alert("请选择excel文件");
                  		return false;  
                 	 } 
					var extendtext=mytext.value.substring(mytext.value.lastIndexOf(".")+1); 
					if(extendtext!="xlsx")	
					 {
						 alert("请上传xlsx格式的文件");
						return false;
					 }
					  
				 	 if($("[id$=mypicture]").val()=='')
				 	 {	 	
				 		 var isconfirm=confirm("您还未上传图片，如果继续请点击确认");
				 		 if(isconfirm)
				     		return true;
				 	     else{
						 return false;
							 }
					  }
					  else{
				   		var extendpicture=mypicture.value.substring(mypicture.value.lastIndexOf(".")+1);
						if(extendpicture!="zip")	
					 	{
						 alert("请上传zip格式的图片");
						return false;
						}
					  }
						return true;
				 	 }
                  </script> 
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
  
  <body>

    <div class="modal fade in" id="add_choice_modal" aria-hidden="false" role="dialog">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                  <h4 class="modal-title">
                    上传习题
                  </h4>
            </div>
			<form class="form-horizontal" id="add_choice" action="<c:url value='/ecadmin/upload' />" novalidate="novalidate" method="post" enctype="multipart/form-data">
            <div class="modal-body">
              
                <div class="form-wizard">
                  <div class="form-body">
                      <div class="form-group">
                          <label class="control-label col-md-3">习题文档</label>
                          <div class="col-md-7">
                            <input type="file"  data-style="fileinput" data-inputsize="medium" name="excel" id="mytext"/>
                          </div>
                      </div>
					  <div class="form-group">
                          <label class="control-label col-md-3">图片压缩</label>
                          <div class="col-md-7">
                           <input type="file"  data-style="fileinput" data-inputsize="medium" name="image" id="mypicture"/>
                          </div>
                      </div>
                      <div class="form-group">
					   <div class="col-md-7">
					   
					   </div>
                      </div>
					  
                      <!--<div class="form-group">
                          <label class="control-label col-md-3">办卡日期</label>
                          <div class="col-md-7">
                            <div class="inlinepicker datepicker-fixed">
                            </div>
                          </div>
                      </div>-->
                  </div>
                </div>
              
            </div>
            <div class="modal-footer">
				<input type="submit" class="btn btn-success" value="提交" onclick="return judge();" />
              <!--<button type="button" class="btn btn-success" >确定</button>-->
              <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
            </div>
			</form>
          </div>
        </div>
    </div>
	
	 <div class="modal fade in" id="view_choice_modal" aria-hidden="false" role="dialog">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                  <h4 class="modal-title">
                   查看选择题
                  </h4>
            </div>
			  <div class="modal-body">
              <form class="form-horizontal" id="view_choice_form" action="#" novalidate="novalidate">
              	
                <div class="form-wizard">
                  <div class="form-body">
                     
                      <div class="form-group">
                <div class="w_subjes">
                    <div class="w_subje" id="Content" style="font-size:14px;line-height:2.2;margin:3px 60px"><!-- ATM信元及信头的字节数分别为（ ） --></div>
                    <div class="w_subje" id="Img" align="center"></div>
                </div>
                <!-- <ul class="w_contents" style="font-size:14px;line-height:2.2;margin:3px 60px">
                        
                </ul> -->
                <table cellspacing="0" width="80%" align="center" id="w_contents">
              		<!-- <tr><td>sdf</td><td>sdf</td><td>sdf</td><td>sdf</td><td>sdf</td></tr> -->
              	</table>
                 </div>
			         
					 
                  </div>
                </div>
              </form>
            </div>
            <div class="modal-footer">
              <!-- <button type="button" class="btn btn-success" data-dismiss="modal">提交</button> -->
              <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
            </div>
          </div>
        </div>
    </div>
	<a style="display:none;" data-toggle="modal" href="#view_choice_modal" id="choice_view_show">查看</a>
	
	
    <c:import url="stemEvalSystemHeader.jsp" charEncoding="UTF-8" /> 
    <div id="container">
        <div id="sidebar" class="sidebar-fixed">
        <div id="sidebar-content">
          <ul id="nav">
            
            <li class="current">
              <a href="<c:url value='/ecadmin/questionlist.html' />">
                <i class="icon-dashboard">
                </i>
                习题管理
              </a>
            </li>
            
          </ul>
         
        </div>
        <div id="divider" class="resizeable">
        </div>
      </div>
        <div id="content">
            <div class="container">
                <div class="crumbs">
                    <ul id="breadcrumbs" class="breadcrumb">
                        <li class="current">
                            <i class="icon-home"></i>
                            <a href="#">习题管理</a>
                        </li>
                    </ul>
                </div>
                <div class="row row-bg">
                    <div class="col-md-2">
                        <p class="btn-toolbar">
                            <button class="btn" data-toggle="modal" href="#add_choice_modal">增加</button>
                            <button class="btn" id="removeMultiOpen">删除</button>
                        </p>
                    </div>
                     <div class="col-md-3">
                                    <label>
                            题目类型:
                          </label>
                          <select class="select2" data-minimum-results-for-search="-1" name="qtype" onchange="location.href='<c:url value='/ecadmin/questionlist.html' />'">
                            <option value="O">
                                	开放题
                              </option>
                            <option value="C">
                                	选择题
                              </option>
                              
                          </select>
                    </div>
                    <div class="col-md-3">
                                    <label>
                            学科类型:
                          </label>
                           <select class="select2" data-minimum-results-for-search="-1" name="course" onchange="location.href='<c:url value='/ecadmin/openlist.html' />?course='+this.value">
                            <option value="L" <c:if test='${course == "L" }' >selected</c:if> >
                                	生命科学
                              </option>
                              <option value="M" <c:if test='${course == "M" }' >selected</c:if>>
                                	物质科学
                              </option>
                              <option value="T" <c:if test='${course == "T" }' >selected</c:if>>
                                	技术与设计
                              </option>
                              <option value="E" <c:if test='${course == "E" }' >selected</c:if>>
                                	地球与环境科学
                              </option>
                              <option value="S" <c:if test='${course == "S" }' >selected</c:if>>
                                	社会及行为科学
                              </option>
                          </select>
                         
                    </div>
                   
                    <div class="col-md-4">
                        <div class="widget">
                            <div class="widget-content">
                            	<form action="<c:url value='/ecadmin/openlist.html' />" method="get" >
                                <div class="input-group">
                                	
                                    <input type="text" class="form-control" placeholder="输入标题查找" name="searchtext" value="${searchtext}"/>
                                    <span class="input-group-btn">
                                        <button class="btn btn-default" type="submit" id="textsearch" >
                                            <i class="icon-search"></i>
                                        </button>
                                    </span>
                                    
                                </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-striped table-bordered table-hover table-checkable" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th class="checkbox-column">
                                        <input type="checkbox" class="uniform">
                                    </th>
                                    <th>
                                       序号
                                    </th>
                                    <th>
                                       标题
                                    </th>
                                    <th>
                                       学科
                                    </th>
                                    
                                    <th>
                                       难度
                                    </th>
                                    <th>
                                       出题人
                                    </th>
                                    <th>
                                       审核人
                                    </th>
                                    <th>
                                       操作
                                    </th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="choice" items="${openlist}">
                                <tr>
                                    <td class="checkbox-column">
                                        <input type="checkbox" value="<c:out value='${choice.openId}' />" name="open" class="uniform">
                                    </td>
                                   <td>
                                       ${choice.openId}
                                    </td>
                                    <td>
                                        ${choice.title}
                                    </td>
                                    <td>
                                      ${choice.courseDes}
                                    </td>
                                    <td>
                                       ${choice.difficultyDes} 
                                    </td>
                                    <td>
                                      ${choice.examiner}
                                    </td>
                                    <td>
                                      ${choice.verifier}
                                    </td>
                                   <td>
                                      	<a href="<c:url value='/ecadmin/opendetail.html' />?openId=${choice.openId}"  openid="${choice.openId}">查看</a> 
                                      	<a href="javascript:void(0);" class="removeOpen" openid="${choice.openId}">删除</a> 
                                    </td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                
                
                <div class="row">
                    <div class="table-footer">
                      <div class="col-md-6">
                        <div class="table-actions">
                          
                        </div>
                        <span>总共${questioncount}条记录</span>
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
                        					<!-- <script type="text/javascript">document.write(encodeURI('<c:url value='${page.link}'/>'));</script> -->
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