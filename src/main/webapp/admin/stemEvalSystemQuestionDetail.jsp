<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" /> 
    <script type="text/javascript" src="<c:url value='/admin/plugins/fileinput/fileinput.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/ajaxfileupload/ajaxfileupload.js' />">
    </script>
    
    <script type="text/javascript">
	$(document).ready(function(){
                	$(".optionModified").click(function(){
                		var optionId = $(this).attr("optionid");
                		console.log(optionId);
                		var content = $("[name='content"+optionId+"']").val();
                		var point = $("[name='point"+optionId+"']").val();
                		console.log(content+point);
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
				
		
	});

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
function ajaxFileUpload(obj)
	{
		var elementid=obj.id;
		//var id=imagelist.length+1;
		var suffix = obj.value.substring(obj.value.lastIndexOf(".")+1);
		if(suffix != "jpg" && suffix !="png" && suffix != "gif")
		{
			alert("not allowed!");
			return ;
			
		}
		var type = "";
		var id = $("#"+elementid).attr("valueid");
		console.log(id);
		if(elementid == "choiceimg")
		{
			type = "choice";
		}
		else
		{
			type = "option";
		}
		
		//alert(elementid);
		$.ajaxFileUpload
		(
			{
				url:"<c:url value='/ecadmin/imageupload.html' />",
				secureuri:false,
				fileElementId:elementid,
				dataType: 'json',
				data:{id:id,type:type},
				success: function (data, status)
				{
					console.log(JSON.stringify(data));
					if(data.status != undefined)
						alert("上传失败！");
					else
					{
						var path="http://www.stemcloud.cn/uploads/testsys/image/";
						$("#show"+elementid).attr('src',path+data.src);
					}
				},
				error: function (data, status, e)
				{
					alert('上传失败！');
					alert(e);
				}
			}
		)		
		return false;
	}
                  </script> 
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
  
  <body>	
	
    <c:import url="stemEvalSystemHeader.jsp" charEncoding="UTF-8" /> 
    <div id="container">
        <div id="sidebar" class="sidebar-fixed">
        <div id="sidebar-content">
          <ul id="nav">
            
            <li class="current">
              <a href="<c:url value='/ecadmin/questionlist.html' />">
                <i class="icon-dashboard">
                </i>
                习题详情
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
                </div>
                <div class="row">
                	 
                	<form class="form-horizontal" id="view_choice_form" action="#" novalidate="novalidate">
              	
                		<div class="form-wizard">
                  			<div class="form-body">
                      			<div class="form-group">
                					<div class="w_subjes">
                    					<div class="w_subje" id="Content" style="font-size:14px;line-height:2.2;margin:3px 60px">${choice.choiceId}.${choice.title}</div>
                    					<div class="w_subje" id="Img" align="center"><c:if test="${choice.img != null}"><img src="http://www.stemcloud.cn/uploads/testsys/image/${choice.img}" width="400" id="showchoiceimg"  /><div class="col-md-7"><input type="file" valueid="${choice.choiceId}"  data-style="fileinput" onChange="return ajaxFileUpload(this);" data-inputsize="medium" name="choiceimg" id="choiceimg"/></div></c:if></div>
                					</div>
                					<table cellspacing="0" width="80%" align="center" id="w_contents">
	                					<c:forEach var="option" items="${optionlist}">
					                	<tr>
					                		<td width="10%"><span class="w_vation">${option.identifier}.</span></td>
											<td width="50%"><input name="content${option.optionId}" value="${option.optionContent}" class="form-control"/></td>
											<td width="10%" width="10%">分值:</td>
											<td width="12%"><input name="point${option.optionId}" value="${option.optionWeight}" class="form-control"/></td>
											<td width="18%"><button type="button" class="btn optionModified" optionid = "${option.optionId}">修改</button></td>
										</tr>
										<c:if test="${option.optionImg != null}">
									    <tr>
									    <td></td>
									    	<td >
									    		<div align="left" style="margin-left:10px;">
									    			<img src="http://www.stemcloud.cn/uploads/testsys/image/${option.optionImg}" id="showoptionimg${option.optionId}" width="100%"  />
									    		</div>
									    	</td>
									    	<td colspan="3"><input type="file" valueid="${option.optionId}" data-style="fileinput" data-inputsize="medium" onChange="return ajaxFileUpload(this);" name="choiceimg" id="optionimg${option.optionId}"/></td>
									    	
									    	<!-- optionimg${option.optionId} -->
									    </tr>
									    </c:if>
								    </c:forEach>
              						</table>
                 				</div>
			         		</div>
                		</div>
              		</form> 
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