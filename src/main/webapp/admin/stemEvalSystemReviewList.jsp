<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" /> 
<script >
	$(document).ready(function() {
		$(".changePassword").click(function(){
			//alert("pasword has changed");
			var adminid=$(this).attr('adminid');
			$("[name='change_password_admin_id']").val(adminid);
			$("#change_pass_show").click();
		});
		$(".removeAdmin").click(function(b){
			var adminid=$(this).attr("adminid");
			b.preventDefault();
			bootbox.dialog({
						message:"确认删除此管理员吗？",
						title:"删除管理员",
						buttons:{success:{label:"确认",className:"btn-success",callback:function(){removeAdmin(adminid);}},
						cancel:{label:"取消",className:"btn-success",callback:function(){/*alert("已取消")*/}}}
			});
		});
		$("#removeMultiAdmin").click(function(b){
			
			var adminid="";//=$(this).attr("memid");
			var obj=document.getElementsByName("admin");
			var q=0;
			for(var i=0;i<obj.length;i++){
				if(obj[i].checked){
				  	//alert(obj[i].value+",");
				if(q==0)
				{
					adminid += obj[i].value;
					q=1;
				}
				else
					adminid += ","+obj[i].value;
				}
			}
			//alert(memid);
			
			b.preventDefault();
			if(q==0)
				showInfo("你没选中任何管理员！");
			else	
				bootbox.dialog({
						message:"确认删除选中管理员吗？",
						title:"删除管理员",
						buttons:{success:{label:"确认",className:"btn-success",callback:function(){removeAdmin(adminid);}},
						cancel:{label:"取消",className:"btn-success",callback:function(){/*alert("已取消")*/}}}
			});
		});
	});
	function changePassword()
	{
		var newpass=$("#change_pass_form [name='newpass']").val();
		var passconfirm=$("#change_pass_form [name='passconfirm']").val();
		if(newpass==''||newpass!=passconfirm)
			alert('两次输入不一致');
		else
		{
			$.ajax({
				data:new FormData($("#change_pass_form")[0]),
				dataType:'text',
				type:'post',
				url:"<c:url value='/superadmin/changePassword.html' />",
				processData : false,
				contentType : false,
				success:function(data){
					
					if (data == "success")
						;///refreshREDS();
					else
						alert("错误啦");
				}
			});
		}
	}
	var removeAdmin = function(adminlist)	{
		$.ajax({
			data:'adminId='+adminlist,
			dataType:'text',
			type:'post',
			url:"<c:url value='/superadmin/removeAdmin.html' />",
			success:function(data){
				console.log("return success");
				console.log(data);
				bootbox.alert("删除成功",function(){location.reload();});
				/*if(data=='success')
				{
					bootbox.alert("删除成功",function(){location.reload();});
					
				}
				else
					showInfo("删除失败");*/
			}
			});  
	  };
	  var showInfo = function(info) {
			bootbox.alert(info,function(){console.log("Alert Callback")})
	  };
	</script>
</head>
<body>
    <div class="modal fade in" id="add_admin_modal" aria-hidden="false" role="dialog">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                  <h4 class="modal-title">
                    新增习题管理员
                  </h4>
            </div>
            <form class="form-horizontal" id="add_admin" action="<c:url value='/superadmin/addAdmin.html' />" novalidate="novalidate" method="post">
            <div class="modal-body">
              
                <div class="form-wizard">
                  <div class="form-body">
                      <!--<div class="form-group">
                          <label class="control-label col-md-3">经办人</label>
                          <div class="col-md-7">
                            <p class="form-control-static">万科</p>
                          </div>
                      </div>-->
                      <input type="hidden" name="role" value="${role}" />
                      <div class="form-group">
                          <label class="control-label col-md-3">姓名</label>
                          <div class="col-md-7">
                            <input type="text" class="form-control" name="name" placeholder="姓名">
                          </div>
                      </div>
					  <div class="form-group">
                          <label class="control-label col-md-3">账号</label>
                          <div class="col-md-7">
                            <input type="text" class="form-control" name="account" placeholder="账号">
                          </div>
                      </div>
					  <div class="form-group">
                          <label class="control-label col-md-3">密码</label>
                          <div class="col-md-7">
                            <input type="password" class="form-control" name="password" placeholder="密码">
                          </div>
                      </div>
                      <!-- <div class="form-group">
                          <label class="control-label col-md-3">科目</label>
                          <div class="col-md-7">
                          	<select>
                          	<option></option></select>
                            <input type="text" class="form-control" name="course" placeholder="科目">
                          </div>
                      </div> -->
                       <c:if test="${role == 3}">
                      <div class="form-group">
                      <label class="col-md-3 control-label">科目</label>
                          <div class="col-md-5">
                            <select class="form-control" name="course">
                              <option value="L">
                                	生命科学
                              </option>
                              <option value="M">
                                	物质科学
                              </option>
                              <option value="T">
                                	技术与设计
                              </option>
                              <option value="E">
                                	地球与环境科学
                              </option>
                              <option value="S">
                                	社会及行为科学
                              </option>
                            </select>
                          </div>
                        </div>
                        </c:if> 
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
            	<input type="submit" class="btn btn-success" value="提交" />
              <!-- <button type="button" class="btn btn-success" data-dismiss="modal">提交</button> -->
              <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
            </div>
            </form>
          </div>
        </div>
    </div>
    
    <div class="modal fade in" id="change_pass_modal" aria-hidden="false" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">
                        修改密码
                    </h4>
                </div>
                <div class="modal-body">
					<form class="form-horizontal" id="change_pass_form" novalidate="novalidate">
                    	<input name="change_password_admin_id" type="hidden" >
						<div class="form-wizard">
							<div class="form-body">
								<div class="form-group">
									<label class="control-label col-md-3">
										新密码
									</label>
									<div class="col-md-7">
										<input type="text" name="newpass" class="form-control" placeholder="新密码">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">
										确认密码
									</label>
									<div class="col-md-7">
										<input type="text" name="passconfirm" class="form-control" placeholder="确认密码">
                                        
									</div>
								</div>
								
							</div>
						</div>
					</form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-dismiss="modal" onClick="javascript:changePassword();">提交</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
    <button style="display:none;"  data-toggle="modal" href="#change_pass_modal" id="change_pass_show"></button>

    <c:import url="stemEvalSystemHeader.jsp" charEncoding="UTF-8" /> 
    <div id="container">
        <c:import url="stemEvalSystemSuperSidebar.jsp" charEncoding="UTF-8"></c:import>
        <div id="content">
            <div class="container">
                <div class="crumbs">
                    <ul id="breadcrumbs" class="breadcrumb">
                        <li class="current">
                            <i class="icon-home"></i>
                            <a href="#">评阅列表</a>&gt;阅卷老师:<c:out value="${admin.name}" />
                        </li>
                    </ul>
                </div>
                <div class="row row-bg">
                    <div class="col-sm-12 col-md-3">
                        <p class="btn-toolbar">
                           <!-- <button class="btn" data-toggle="modal" href="#add_admin_modal">增加</button>
                            <button class="btn" id="removeMultiAdmin">删除选中</button>-->
                        </p>
                    </div>
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
                                        考生姓名
                                    </th>
                                    
                                    <th>
                                        考试时间
                                    </th>
                                    <th>
                                        评阅状态
                                    </th>
                                    <th>
                                        操作
                                    </th>
                                </tr>
                                <!-- select e.exam_id,u.user_id,u.name,e.exam_begin,e.exam_end,o.status,o.review_id from es_exam as e,es_user as u,es_open_review as o,es_exam_paper as ep where e.exam_id=ep.exam_id and u.user_id=ep.user_id and ep.open_allo_id=o.open_allo_id and o.admin_id=12 -->
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
                                            <c:out value="${review.examBegin}" />——<c:out value="${review.examEnd}" />
                                        </td>
                                        <td>
                                            <c:out value="${review.status}" />
                                        </td>
										<td>
											<c:if test="${review.hasResult == 1}">
	                                        <a href="#" reviewid="<c:out value="${review.reviewId}" />" class="viewResult">查看成绩</a>&nbsp;
	                                    	</c:if>
	                                    	<c:if test="${review.hasResult == 0}">
	                                    		无
	                                    	</c:if>
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
                        <span>总共${reviewcount}条记录</span>
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