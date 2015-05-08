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
	$(document).ready(function() {
		$(".removeExam").click(function(b){
			var examid=$(this).attr("examid");
			b.preventDefault();
			bootbox.dialog({
						message:"确认删除此考试时间吗？",
						title:"删除考试时间",
						buttons:{success:{label:"确认",className:"btn-success",callback:function(){removeExam(examid);}},
						cancel:{label:"取消",className:"btn-success",callback:function(){/*alert("已取消")*/}}}
			});
		});
		$("#removeMultiExam").click(function(b){			
			var examid="";//=$(this).attr("memid");
			var obj=document.getElementsByName("exam");
			var q=0;
			for(var i=0;i<obj.length;i++){
				if(obj[i].checked){
				  	//alert(obj[i].value+",");
				if(q==0)
				{
					examid += obj[i].value;
					q=1;
				}
				else
					examid += ","+obj[i].value;
				}
			}
			//alert(memid);
			
			b.preventDefault();
			if(q==0)
				showInfo("你没选中任何考试时间！");
			else	
				bootbox.dialog({
						message:"确认删除选中考试时间吗？",
						title:"删除考试时间",
						buttons:{success:{label:"确认",className:"btn-success",callback:function(){removeExam(examid);}},
						cancel:{label:"取消",className:"btn-success",callback:function(){/*alert("已取消")*/}}}
			});
		});
	});

	var removeExam = function(examlist)	{
		$.ajax({
			data:'examId='+examlist,
			dataType:'text',
			type:'post',
			url:"<c:url value='/superadmin/removeExam.html' />",
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
	  function examSet()
		{
		    //获取复选框society的值
	        var society=document.getElementsByName("exam_society");
		    var q=0;
		    for(var i=0;i<society.length;i++){
				if(society[i].checked){
				    if(q==0){
				    	society[0].value +=society[i].value;
				    	q=1;
				    }
				    else if(q==1){
				    	society[0].value += society[i].value+",";
				    }
				}
			}
			//获取复选框subject的值
			var subject=document.getElementsByName("exam_subject");
			var p=0;
			for(var j=0;j<subject.length;j++){
				if(subject[j].checked){
					if(p==0){
						subject[0].value +=subject[j].value;
				    	p=1;
				    }
				    else if(q==1){
				    	subject[0].value += subject[j].value+",";
				    }
				}
			}
		    //考试设置的表单判断
		    //用法：onclick=return examSet();
		    if($("[name='exam_name']").val()=='')
			{
				alert("请填写考试名称");
				return false;
			}
			if($("[name='begin_ymd']").val()==''||$("[name='end_ymd']").val()=='')
			{
				alert("请选择时间");
				return false;
			}
			if($("[name='exam_line']").val()=='')
			{
				alert("请填写分数线");
				return false;
			}
			
			//判断开始时间及结束时间的大小
			var b_time=$("[name='begin_ymd']").val();
			console.log(b_time);
			
			var e_time=$("[name='end_ymd']").val();
			console.log(e_time);
			
			if(b_time > e_time)
			{
				alert("考试开始时间不能大于结束时间");
				return false;
			}
			
			else if(b_time < e_time)
			{
				//return true;
				console.log("ok");
			}
			
			else
			{
				var b_hour=parseInt($("[name='begin_h']").val());
				//var bhour=b_hour.substring(0,2);
				
				var e_hour=parseInt($("[name='end_h']").val());
				//var ehour=e_hour.substring(0,2);
				
				if(b_hour > e_hour)
				{
					alert("考试开始时间不能大于结束时间");
					return false;
				}
				
				else if(b_hour < e_hour)
				{
					//return true;
				}
				
				else
				{
					
					var b_minute=parseInt($("[name='begin_m']").val());
					//var bminute=b_minute.substring(0,2);
				
					var e_minute=parseInt($("[name='end_m']").val());
					//var eminute=e_minute.substring(0,2);					
					
					if(b_minute >= e_minute)
					{
						alert("考试开始时间不能大于结束时间");
						return false;
					}					
					
					//return true;
				}
			}
			return true;
			
		}
	</script>
  <body>

    <div class="modal fade in" id="add_admin_modal" aria-hidden="false" role="dialog">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                  <h4 class="modal-title">
                    新增考试
                  </h4>
            </div>
            <form class="form-horizontal" id="add_admin" action="<c:url value='/superadmin/addExam.html'/>"  novalidate="novalidate">
            <div class="modal-body">
              
                <div class="form-wizard">
                  <div class="form-body">
					  
					  
					   <div class="form-group">
                          <label class="control-label col-md-3">考试名称</label>
                          <div class="col-md-7">
                            <input type="text" name="exam_name" class="form-control"  placeholder="请填写考试名称">
                          </div>
                      </div>
    <div class="row">
	<div class="col-md-12">
	<table class="table table-striped table-bordered table-hover table-checkable" cellspacing="0" width="100%">
	<thead>
	<tr>
	<th></th>
	<th>学会</th>
	<th></th>
	<th>学科</th>
	</tr>
	</thead>
	<tbody>
	<tr>
	<td class="checkbox-column"><input type="checkbox" name="exam_society" class="uniform" value=></td>
	<td>需要选择学会信息</td>
	<td class="checkbox-column"><input type="checkbox" name="exam_subject" class="uniform" value=></td>
	<td>需要选择学科信息</td>
	</tr>
	<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海动物学会" name="exam_society" class="uniform"></td>
	<td>上海动物学会</td>
	<td class="checkbox-column"><input type="checkbox" value="生命科学" name="exam_subject" class="uniform"></td>
	<td>生命科学</td>
	</tr>
	<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海植物学会" name="exam_society" class="uniform"></td>
	<td>上海植物学会</td>
	<td class="checkbox-column"><input type="checkbox" value="物质科学" name="exam_subject" class="uniform"></td>
	<td>物质科学</td>
	</tr>
	<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市生物化学与分子生物学学会" name="exam_society" class="uniform"></td>
	<td>上海市生物化学与分子生物学学会</td>
	<td class="checkbox-column"><input type="checkbox" value="技术与设计" name="exam_subject" class="uniform"></td>
	<td>技术与设计</td>
	</tr>
	<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市医学会" name="exam_society" class="uniform"></td>
	<td>上海市医学会</td>
	<td class="checkbox-column"><input type="checkbox" value="地球与环境科学" name="exam_subject" class="uniform"></td>
	<td>地球与环境科学</td>
	</tr>
	<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市环境科学学会" name="exam_society" class="uniform"></td>
	<td>上海市环境科学学会</td>
	<td class="checkbox-column"><input type="checkbox" value="社会及行为科学" name="exam_subject" class="uniform"></td>
	<td>社会及行为科学</td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市数学学会" name="exam_society" class="uniform"></td>
	<td>上海市数学学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市物理学会" name="exam_society" class="uniform"></td>
	<td>上海市物理学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市化学化工学会" name="exam_society" class="uniform"></td>
	<td>上海市化学化工学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市计算机学会" name="exam_society" class="uniform"></td>
	<td>上海市计算机学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市机器人学会" name="exam_society" class="uniform"></td>
	<td>上海市机器人学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市地理学会" name="exam_society" class="uniform"></td>
	<td>上海市地理学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市电气工程设计研究会" name="exam_society" class="uniform"></td>
	<td>上海市电气工程设计研究会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市机械工程学会" name="exam_society" class="uniform"></td>
	<td>上海市机械工程学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市热处理学会" name="exam_society" class="uniform"></td>
	<td>上海市热处理学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市气象学会" name="exam_society" class="uniform"></td>
	<td>上海市气象学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市土木工程学会" name="exam_society" class="uniform"></td>
	<td>上海市土木工程学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市宇航学会" name="exam_society" class="uniform"></td>
	<td>上海市宇航学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市金属学会" name="exam_society" class="uniform"></td>
	<td>上海市金属学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市药学会" name="exam_society" class="uniform"></td>
	<td>上海市药学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市工业与运用数学学会" name="exam_society" class="uniform"></td>
	<td>上海市工业与运用数学学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市人工智能学会" name="exam_society" class="uniform"></td>
	<td>上海市人工智能学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市天文学学会" name="exam_society" class="uniform"></td>
	<td>上海市天文学学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市城市科学研究会" name="exam_society" class="uniform"></td>
	<td>上海市城市科学研究会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
		<tr>
	<td class="checkbox-column"><input type="checkbox" value="上海市传播与海洋工程学会" name="exam_society" class="uniform"></td>
	<td>上海市传播与海洋工程学会</td>
	<td class="checkbox-column"></td>
	<td></td>
	</tr>
	
	</tbody>
	</table>
	</div>
	</div>
					  
     <div class="form-group">
                          <label class="control-label col-md-3">开始时间</label>
                          <div class="col-md-3">
                           <input type="text" name="begin_ymd" class="form-control datepicker"  placeholder="年/月/日">
                           </div>
                           <div class="col-md-2">
							<select class="form-control" name="begin_h">
                            <option value='00' >0时</option>
<option value='01' >1时</option>
<option value='02' >2时</option>
<option value='03' >3时</option>
<option value='04' >4时</option>
<option value='05' >5时</option>
<option value='06' >6时</option>
<option value='07' >7时</option>
<option value='08' >8时</option>
<option value='09' >9时</option>
<option value='10' >10时</option>
<option value='11' >11时</option>
<option value='12' >12时</option>
<option value='13' >13时</option>
<option value='14' >14时</option>
<option value='15' >15时</option>
<option value='16' >16时</option>
<option value='17' >17时</option>
<option value='18' >18时</option>
<option value='19' >19时</option>
<option value='20' >20时</option>
<option value='21' >21时</option>
<option value='22' >22时</option>
<option value='23' >23时</option>

                            </select>
                            
                          </div>
                          <div class="col-md-2">
							<select class="form-control" name="begin_m">
 <option value='00' >0分</option>
<option value='01' >1分</option>
<option value='02' >2分</option>
<option value='03' >3分</option>
<option value='04' >4分</option>
<option value='05' >5分</option>
<option value='06' >6分</option>
<option value='07' >7分</option>
<option value='08' >8分</option>
<option value='09' >9分</option>
<option value='10' >10分</option>
<option value='11' >11分</option>
<option value='12' >12分</option>
<option value='13' >13分</option>
<option value='14' >14分</option>
<option value='15' >15分</option>
<option value='16' >16分</option>
<option value='17' >17分</option>
<option value='18' >18分</option>
<option value='19' >19分</option>
<option value='20' >20分</option>
<option value='21' >21分</option>
<option value='22' >22分</option>
<option value='23' >23分</option>
<option value='24' >24分</option>
<option value='25' >25分</option>
<option value='26' >26分</option>
<option value='27' >27分</option>
<option value='28' >28分</option>
<option value='29' >29分</option>
<option value='30' >30分</option>
<option value='31' >31分</option>
<option value='32' >32分</option>
<option value='33' >33分</option>
<option value='34' >34分</option>
<option value='35' >35分</option>
<option value='36' >36分</option>
<option value='37' >37分</option>
<option value='38' >38分</option>
<option value='39' >39分</option>
<option value='40' >40分</option>
<option value='41' >41分</option>
<option value='42' >42分</option>
<option value='43' >43分</option>
<option value='44' >44分</option>
<option value='45' >45分</option>
<option value='46' >46分</option>
<option value='47' >47分</option>
<option value='48' >48分</option>
<option value='49' >49分</option>
<option value='50' >50分</option>
<option value='51' >51分</option>
<option value='52' >52分</option>
<option value='53' >53分</option>
<option value='54' >54分</option>
<option value='55' >55分</option>
<option value='56' >56分</option>
<option value='57' >57分</option>
<option value='58' >58分</option>
<option value='59' >59分</option>


                            </select>
                          </div>
                      </div>
					  
					  <div class="form-group">
                          <label class="control-label col-md-3">结束时间</label>
                          <!--<div class="col-md-7">
                            <input type="text" class="form-control" placeholder="时间">-->
							<div class="col-md-3">
                           <input type="text" name="end_ymd" class="form-control datepicker"  placeholder="年/月/日">
                           </div>
                           <div class="col-md-2">
							<select class="form-control" name="end_h">
                            <option value='00' >0时</option>
<option value='01' >1时</option>
<option value='02' >2时</option>
<option value='03' >3时</option>
<option value='04' >4时</option>
<option value='05' >5时</option>
<option value='06' >6时</option>
<option value='07' >7时</option>
<option value='08' >8时</option>
<option value='09' >9时</option>
<option value='10' >10时</option>
<option value='11' >11时</option>
<option value='12' >12时</option>
<option value='13' >13时</option>
<option value='14' >14时</option>
<option value='15' >15时</option>
<option value='16' >16时</option>
<option value='17' >17时</option>
<option value='18' >18时</option>
<option value='19' >19时</option>
<option value='20' >20时</option>
<option value='21' >21时</option>
<option value='22' >22时</option>
<option value='23' >23时</option>

                            </select>
                            
                          </div>
                          <div class="col-md-2">
							<select class="form-control" name="end_m">
                            <option value='00' >0分</option>
<option value='01' >1分</option>
<option value='02' >2分</option>
<option value='03' >3分</option>
<option value='04' >4分</option>
<option value='05' >5分</option>
<option value='06' >6分</option>
<option value='07' >7分</option>
<option value='08' >8分</option>
<option value='09' >9分</option>
<option value='10' >10分</option>
<option value='11' >11分</option>
<option value='12' >12分</option>
<option value='13' >13分</option>
<option value='14' >14分</option>
<option value='15' >15分</option>
<option value='16' >16分</option>
<option value='17' >17分</option>
<option value='18' >18分</option>
<option value='19' >19分</option>
<option value='20' >20分</option>
<option value='21' >21分</option>
<option value='22' >22分</option>
<option value='23' >23分</option>
<option value='24' >24分</option>
<option value='25' >25分</option>
<option value='26' >26分</option>
<option value='27' >27分</option>
<option value='28' >28分</option>
<option value='29' >29分</option>
<option value='30' >30分</option>
<option value='31' >31分</option>
<option value='32' >32分</option>
<option value='33' >33分</option>
<option value='34' >34分</option>
<option value='35' >35分</option>
<option value='36' >36分</option>
<option value='37' >37分</option>
<option value='38' >38分</option>
<option value='39' >39分</option>
<option value='40' >40分</option>
<option value='41' >41分</option>
<option value='42' >42分</option>
<option value='43' >43分</option>
<option value='44' >44分</option>
<option value='45' >45分</option>
<option value='46' >46分</option>
<option value='47' >47分</option>
<option value='48' >48分</option>
<option value='49' >49分</option>
<option value='50' >50分</option>
<option value='51' >51分</option>
<option value='52' >52分</option>
<option value='53' >53分</option>
<option value='54' >54分</option>
<option value='55' >55分</option>
<option value='56' >56分</option>
<option value='57' >57分</option>
<option value='58' >58分</option>
<option value='59' >59分</option>

                            </select>
                          </div>
           

							      </div>
					  <div class="form-group">
                          <label class="control-label col-md-3">选择题分数线</label>
                          <div class="col-md-7">
                            <input type="text" name="exam_line" class="form-control"  placeholder="**分">
                          </div>
                      </div>
                      <div class="form-group">
                          <label class="control-label col-md-3">选择题个数</label>
                          <div class="col-md-7">
                            <input type="text" name="choice_number" class="form-control" value="100" />
                          </div>
                      </div>
                      <div class="form-group">
                          <label class="control-label col-md-3">选择题考试时长</label>
                          <div class="col-md-7">
                            <input type="text" name="choice_time" class="form-control" value="120" />
                          </div>
                      </div>
							
							
							
							
							
							
							
                          
                
					  
					  
					  
                      <div class="form-group">
                          <label class="control-label col-md-3">相关通知</label>
                          <div class="col-md-7">
                            <textarea class="form-control" name="notice"></textarea>
                          </div>
                      </div>
                  </div>
                </div>
              
            </div>
            <div class="modal-footer">
              <input type="submit" class="btn btn-success" id="addExam" value="提交" onclick="return examSet();"/>
              <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
            </div>
            </form>
          </div>
        </div>
    </div>

    <c:import url="stemEvalSystemHeader.jsp" charEncoding="UTF-8" /> 
    <div id="container">
        <c:import url="stemEvalSystemSuperSidebar.jsp" charEncoding="UTF-8"></c:import>
        <div id="content">
            <div class="container">
                <div class="crumbs">
                    <ul id="breadcrumbs" class="breadcrumb">
                        <li class="current">
                            <i class="icon-home"></i>
                            <a href="#">考试设置</a>
                        </li>
                    </ul>
                </div>
                <div class="row row-bg">
                    <div class="col-sm-12 col-md-3">
                        <p class="btn-toolbar">
                            <button class="btn" data-toggle="modal" href="#add_admin_modal">增加</button>
                            <button class="btn" id="removeMultiExam">删除选中</button>
                        </p>
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
                                        考试时间
                                    </th>
                                    <th>
                                       报名人数
                                    </th>
                                    <th>
                                    	操作
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                            
                            <c:forEach var="exam" items="${examlist}"> 
                            		<tr>
                            		 <td class="checkbox-column">
                            		 <c:if test="${exam.signInNumbers == 0}">
                                       <input type="checkbox" value="<c:out value='${exam.examId}' />" name="exam" class="uniform">
                                       </c:if>
                                    </td>
                                   
	                                    <td>
	                                        <c:out value="${exam.examId}" />
	                                    </td>
                                   
	                                   <td>
	                                        	<c:out value="${exam.examBegin}" />——<c:out value="${exam.examEnd}" />
	                                    </td>
	                                    <td>
	                                        	<a href="<c:url value='/superadmin/signInList.html' />?examId=<c:out value='${exam.examId}' />" ><c:out value="${exam.signInNumbers}" /></a>
	                                    </td>
	                                   
										<td>
											<c:if test="${exam.signInNumbers == 0}">
	                                        	<a href="javascript:void(0);" examid="<c:out value='${exam.examId}' />" class="removeExam">删除</a>
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
                        <span>总共${examcount}条记录</span>
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
       /*   if (location.host == "envato.stammtec.de" || location.host == "themes.stammtec.de") {
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
          };*/

    </script>
	
	
	
	
	
	
  </body>
   </html>