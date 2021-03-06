<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" /> 
    <script type="text/javascript" src="<c:url value='/admin/plugins/fileinput/fileinput.js' />">
    </script>
    <script type="text/javascript">
    var path="<c:url value='/image/' />";
	$(document).ready(function(){
		
		/* $("#textsearch").click(function(){
			location.href="<c:url value='/ecadmin/questionlist.html' />?searchtext="+escape('生命');//+escape($("[name='search']").val());
			
		}); */
		/* $("[name='qtype']").change(function(){
			location.href="<c:url value='/ecadmin/openlist.html' />";
		}); */
		$(".choiceView").click(function(){
			console.log("choice_view click!");
			var choiceId = $(this).attr("choiceId");
			 $.ajax({
				data:{choiceId:choiceId},
				dataType:'json',
				type:'post',
				url:"<c:url value='/ecadmin/viewchoiceinfo.html' />",
				success:function(data){
					console.log("get returned!");
					console.log(JSON.stringify(data));
					$("#Content").html(data.choiceId+"."+data.title);
					if(data.img == undefined)
						$("#Img").html("");
					else
						$("#Img").html('<img src="'+this.path+data.img+'" width="400"  /><div class="col-md-7"><input type="file"  data-style="fileinput" data-inputsize="medium" name="excel" id="mytext"/></div>');
					var options = '';
					if (typeof (data.options) == "object") {
                    	$(data.options).each(function(ind) {
                    	var identifier=data.options[ind].identifier;
						var content = data.options[ind].content;
						var point = data.options[ind].point;
						var optionId = data.options[ind].optionId;
						var img = data.options[ind].img;
						
						options += '<tr>';
						options += '<td width="10%"><span class="w_vation">'+identifier+'.</span></td>';
						options += '<td width="50%"><input name="content'+optionId+'" value="'+content+'" class="form-control"/></td>';
						options += '<td width="10%" width="10%">分值:</td>';
						options += '<td width="12%"><input name="point'+optionId+'" value="'+point+'" class="form-control"/></td>';
						options += '<td width="18%"><button type="button" class="btn optionModified" optionid = "'+optionId+'">修改</button></td>';
						options += '</tr>';
						if(data.options[ind].img != undefined)
				        	options += '<tr><td colspan="4"><div align="left" style="margin-left:10px;"><img src="'+path+data.options[ind].img+'" width="100%"  /></div></td></tr>';
						//options += '<li><span class="w_vation">'+identifier+'.</span>'+content+'<span style="color: Red">&nbsp;&nbsp;&nbsp;分值:'+point+'分</span></li> ';
                        	//alert(val.Title + " " + val.Content + " " + val.summary[ind].sum0);
                    	});
                	}
                	$("#w_contents").html(options);
                	$(".optionModified").click(function(){
                		var optionId = $(this).attr("optionid");
                		console.log(optionId);
                		var content = $("[name='content"+optionId+"']").val();
                		var point = $("[name='point"+optionId+"']").val();
                		console.log(content+point)
                		 $.ajax({
                			data:{content:$("[name='content"+optionId+"']").val(),point:$("[name='point"+optionId+"']").val(),optionId:optionId},
                			dataType:'json',
                			type:'post',
                			url:"<c:url value='/ecadmin/optionmodified.html' />",
                			success:function(data){
                				//console.log(data.status);
                				if(data.status == "success")
                					alert("修改成功");
                			}	
                		}); 
                	});
                	$("#choice_view_show").click();
					//$(data.options).each(function(){});
				}
			}); 
		});
		$(".removeChoice").click(function(b){
			var choiceid=$(this).attr("choiceid");
			b.preventDefault();
			bootbox.dialog({
						message:"确认删除此题目吗？",
						title:"删除题目",
						buttons:{success:{label:"确认",className:"btn-success",callback:function(){removeChoice(choiceid);}},
						cancel:{label:"取消",className:"btn-success",callback:function(){/*alert("已取消")*/}}}
			});
		});
		$("#removeMultiChoice").click(function(b){
			
			var choiceid="";//=$(this).attr("memid");
			var obj=document.getElementsByName("choice");
			var q=0;
			for(var i=0;i<obj.length;i++){
				if(obj[i].checked){
				  	//alert(obj[i].value+",");
				if(q==0)
				{
					choiceid += obj[i].value;
					q=1;
				}
				else
					choiceid += ","+obj[i].value;
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
						buttons:{success:{label:"确认",className:"btn-success",callback:function(){removeChoice(choiceid);}},
						cancel:{label:"取消",className:"btn-success",callback:function(){/*alert("已取消")*/}}}
			});
		});
	});
	var removeChoice = function(choicelist)	{
		$.ajax({
			data:'choiceId='+choicelist,
			dataType:'text',
			type:'post',
			url:"<c:url value='/ecadmin/removechoice.html' />",
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
        <c:import url="stemEvalSystemSuperSidebar.jsp" charEncoding="UTF-8"></c:import>
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
                           <!--  <button class="btn" data-toggle="modal" href="#add_choice_modal">增加</button>
                            <button class="btn" id="removeMultiChoice">删除</button> -->
                        </p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-striped table-bordered table-hover table-checkable" cellspacing="0" width="100%">
                            <thead>
                                <tr>
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
                                   <td>无
                                      	<%-- <a href="<c:url value='/ecadmin/questiondetail.html' />?choiceId=${choice.openId}"  choiceId="${choice.openId}">查看</a> 
                                      	<a href="javascript:void(0);" class="removeChoice" choiceid="${choice.openId}">删除</a>  --%>
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