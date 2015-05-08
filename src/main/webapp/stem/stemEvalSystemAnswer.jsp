<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<c:url value='/admin/assets/js/libs/jquery-1.10.2.min.js' />">
    </script>
<script type="text/javascript">
				$(document).ready(function(){
					//$("#NextQuestion").click(function(){
						
				
				});
				var showInfo = function(info) {
			bootbox.alert(info,function(){console.log("Alert Callback")})
	  };
	</script>

</head>

<body>
	<c:import url="stemEvalSystemHeader.jsp" charEncoding="UTF-8" />

	<div class="main-container" id="main-container">
		<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span> </a>

			<c:import url="stemEvalSystemSidebar.jsp" charEncoding="UTF-8" />

			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i> <a href="#">个人中心</a>
						</li>

						<!--<li>
								<a href="#">Other Pages</a>							</li>-->
						<li class="active">我的考试</li>
					</ul>
					<!-- .breadcrumb -->

					<div class="nav-search" id="nav-search">
						<form class="form-search">
							<span class="input-icon"> <input type="text"
								placeholder="Search ..." class="nav-search-input"
								id="nav-search-input" autocomplete="off" /> <i
								class="icon-search nav-search-icon"></i> </span>
						</form>
					</div>
					<!-- #nav-search -->
				</div>

				<div class="page-content">
					<!--<div class="page-header">
							<h1>
								Grid
								<small>
									<i class="icon-double-angle-right"></i>
									bootstrap grid sizing								</small>							</h1>
						</div>-->
					<!-- /.page-header -->

					<div class="row">





						<div class="exam-cl">
							<div class="exam-grad">
								<h2 class="txt">
									<span id="QuestionType">选择题</span><span class="stars"
										id="stars"></span><span id="QuestionScore"> </span>
								</h2>
							</div>
							<div class="exam-timel">
								<h2 class="her_ans_time">
									剩余时间&nbsp;:&nbsp;<span class="remainingTime" data-diff="${remainingtime}"></span><span>答题进度：<span id="curChoice">1</span>/<span id="totalChoice">10</span></span>
								</h2>
							</div>
						</div>

						<div class="exam-cl">
							<div class="exam-grad">
								<button id="prechoice" >上一题</button>
							</div>
							<div class="exam-timel">
								<button id="nextchoice" >下一题</button>
							</div>
						</div>




						<div class="w_topic">
							


							<div style="display: none">
								<span id="limittime">59</span><span id="questionmode">10</span><span
									id="ExamTime">1200</span><span id="ExamStartTime">1411177272342</span><span
									id="IsShowRightAnswer">1</span><span id="IsShowQuestionTime">1</span>
								<input type="hidden" id="examNum" value="1" />
							</div>
							<div class="w_subjes">
								<div class="w_subje" id="Content">${choice.title}</div>
								<div class="w_subje" id="Img" align="center"></div>
							</div>
							<ul class="w_contents">
								<c:forEach var="option" items="${optionlist}">
									<li><input type="radio" name="choice"
										value="${option.optionId}" /><span class="w_vation">${option.identifier}.&nbsp;&nbsp;&nbsp;</span>

										&nbsp;&nbsp;&nbsp;${option.optionContent} 
										<span style="color: Red"></span>
									</li>
								</c:forEach>

							</ul>
							<span id="result"></span> <a href="javascript:void(0);" style="display:none;"
								class="w_answers" id="NextQuestion" paper_id=${paperId}>提交答案</a>
						</div>






					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
			<!-- /.main-content -->

			
		</div>
		<!-- /.main-container-inner -->

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="icon-double-angle-up icon-only bigger-110"></i> </a>
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->

	<!--[if !IE]> -->

		<script src="<c:url value='/stem/assets/js/jquery-1.10.2.min.js'/>"></script>

		<!-- <![endif]-->

		<!--[if IE]>
			<script src="<c:url value='/stem/assets/js/jquery-1.10.2.min.js'/>"></script>
		<![endif]-->

	<!--[if !IE]> -->

	<script type="text/javascript">
		
		</script>

	<!-- <![endif]-->

	<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

	<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
	<script src="<c:url value='/stem/assets/js/bootstrap.min.js' />"></script>
	<script src="<c:url value='/stem/assets/js/typeahead-bs2.min.js' />"></script>

	<!-- page specific plugin scripts -->

	<!-- ace scripts -->

	<script src="<c:url value='/stem/assets/js/ace-elements.min.js' />"></script>
	<script src="<c:url value='/stem/assets/js/ace.min.js' />"></script>
	<script src="<c:url value='/stem/js/jquery-countdown-master/jquery.countdown.js' />"></script>
	<script>
		var choicelist=function()
		{
			this.choicelist = new Array();
			this.curChoiceId =0;
			this.choiceNumber = 0;
			this.path="http://www.stemcloud.cn/uploads/testsys/image/";//"<c:url value='/image/' />";
			var pos = this.path.lastIndexOf(";jsessionid");
			if(pos != -1)
				this.path=this.path.substr(0,pos);
			
			this.addlist=function(value)
			{
				this.choicelist.push(value);
				//alert(this.choicelist[this.curChoiceId].title);
				this.choiceNumber++;
			}
			this.nextChoice = function()
			{
				if(this.curChoiceId >= this.choiceNumber - 1)
					return false;
				else
				{
					this.curChoiceId ++;
					this.show();
					return true;
					//return this.choicelist[this.curChoiceId + 1];
				}
			};
			this.preChoice= function()
			{
				if(this.curChoiceId <= 0)
					return false;
				else
				{
					this.curChoiceId -- ;
					this.show();
					return true;
					//return this.choicelist[this.curChoiceId - 1];
				}
			};
			this.show = function()
			{
				if(this.curChoiceId == this.choiceNumber - 1)
					$("#NextQuestion").show();
				else
					$("#NextQuestion").hide();
				var data = this.choicelist[this.curChoiceId];
				$("#Content").html((data.sequens+1)+"."+data.title);
				$("#curChoice").html(data.sequens+1);
				var path = this.path
				if(data.img == undefined)
					$("#Img").html("");
				else
					$("#Img").html('<img src="'+this.path+data.img+'" width="500"  />');
				$("#totalChoice").html(this.choiceNumber);
				var options = '';
				if (typeof (data.options) == "object") {
				    $(data.options).each(function(ind) {
						options += '<li>'+data.options[ind].identifier+'.&nbsp;&nbsp;&nbsp;<input type="radio" name="choice" value="'+data.options[ind].optionid+'" '+(data.options[ind].optionid == data.soptionid ? "checked":"")+'/>';
						options += '<span class="w_vation"></span>&nbsp;&nbsp;&nbsp;'+data.options[ind].content+'</li> ';
				        if(data.options[ind].img != undefined)
				        	options += '<div align="left" style="margin-left:60px;"><img src="'+path+data.options[ind].img+'" width="500"  /></div>';
				    });
				    //console.log(options);
				}
				$(".w_contents").html(options);
				$(".w_contents li").click(function(){
					// console.log("click!");
					//  $("input[name=choice]").removeAttr("checked");
					 /* var optionradio = $(this).parent().find("input");
					 $(optionradio).each(function(i){
					 	optionradio.eq(i).removeAttr("checked");
					 });  */
					 var value =  $(this).find("input").eq(0).prop("checked",true);//.attr("value");
					 setTimeout(function(){
					 	//if(this.curChoiceId < this.choiceNumber - 1)
					 		$("#NextQuestion").click();
					 	},300);
					 //$("#NextQuestion").click();
					// $("[name='choice']").val(value);
					 //$("input[name=choice][value="+value+"]").prop("checked",true);
					 //$(this).find("input").eq(0).attr("checked","checked");
				});
			}
			this.curChoice= function()
			{
				if(this.curChoiceId < 0 || this.curChoiceId >= this.choiceNumber)
					return false;
				else
					this.show();//return this.choicelist[this.curChoiceId];
				return true;
			};
		};
		
		$(document).ready(function(){
		
		
		});
		function submitToServer(type)
		{
			var resultId="";
			for(var i = 0;i < instance.choiceNumber; i++)
			{
				if(i == 0)
					resultId+= instance.choicelist[i].soptionid;
				else
					resultId+= ","+instance.choicelist[i].soptionid;
							
			}
			var paperId = $("#NextQuestion").attr("paper_id");
			$.ajax({
				data:{paperId:paperId,resultIdList:resultId,type:type},
				dataType:'json',
				type:'post',
				url:"<c:url value='/stem/submitchoice.html' />",
				success:function(data){
					//console.log("get returned!");
					if(data.status== "success")
					{
						if(type == 1)
						{
							alert("答题结束");
							location.href = "<c:url value='/stem/choiceresult.html' />"+"?paperId="+paperId;
						}
					}
					else
					{
						if(data.status== "noauth")
							alert("访问出错！");
						else if(data.status == "notbegin")
							alert("还未开始，无法提交");
						else if(data.status == "expired")
							alert("提交超时!");
						location.href = "<c:url value='/stem/myexam.html' />";
					}
						
					
				}
			});
		}
			var instance=new choicelist();
			$(function() {
				console.log('sdf');
			$("#prechoice").click(function(){
			console.log('sdf');
				instance.preChoice();
			});
			$("#nextchoice").click(function(){
				instance.nextChoice();
			});
			$("#NextQuestion").click(function(){
				var optionId="";//=$(this).attr("memid");
				var obj=document.getElementsByName("choice");
				var q=0;
				for(var i=0;i<obj.length;i++){
					if(obj[i].checked){
						//alert(obj[i].value+",");
						if(q==0)
						{
							optionId += obj[i].value;
							q=1;
						}
						else
							optionId += ","+obj[i].value;
					}
				}
				//alert(memid);
				//b.preventDefault();
				if(q==0)
				{
					alert("请选择选项然后提交！");
					return false;
				}
				else
				{
					instance.choicelist[instance.curChoiceId].soptionid = optionId;
					if(!instance.nextChoice())
					{
						if(confirm("是否确认提交？"))
							submitToServer(1);
						/* var submitList = "";
						for(var i = 0;i < instance.choiceNumber; i++)
						{
							if(i == 0)
								submitList+= instance.choicelist[i].soptionid;
							else
								submitList+= ","+instance.choicelist[i].soptionid;
							
						}
						console.log(submitList); */
					}
					else
						submitToServer(0);
				}
			
			});
			var paperId = $("#NextQuestion").attr("paper_id");
			$.ajax({
				data:{paperId:paperId},
				dataType:'json',
				type:'post',
				url:"<c:url value='/stem/allchoice.html' />",
				success:function(data){
					console.log("get returned!");
					//alert(data.status);
					if(data.status == "success")
					{
						if (typeof (data.choices) == "object") 
						{
							var sequens=0;
					        $(data.choices).each(function(ind) 
					        {
					        	var choice = {};
					        	choice['title'] = data.choices[ind].title;
					        	//console.log(choice['title'])
					        	choice['img'] = data.choices[ind].img;
					        	choice['sequens'] = sequens;
					        	choice['soptionid'] = data.choices[ind].answer;//0;
					        	choice['options'] = new Array();
					        	//choice['answer'] = data.choices[ind].answer;
					        	var options = data.choices[ind].options;
					        	//console.log(options);
					        	if(typeof(options) == "object")
					        	{
					        		$(options).each(function(j){
					        			var option = {};
					        			option['identifier'] = options[j].identifier;
					        			option['optionid'] = options[j].optionid;
					        			option['content'] = options[j].content;
					        			//console.log(option['content']);
					        			option['img'] = options[j].img;
					        			choice['options'].push(option);
					        		});
					        	}
					        	sequens++;
					        	instance.addlist(choice);
					        	
								//options += '<li><input type="radio" name="choice" value="'+data.options[ind].optionid+'" /><span class="w_vation">'+data.options[ind].optionidentifier+'.&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;'+data.options[ind].optioncontent+'</li> ';
					            //alert(val.Title + " " + val.Content + " " + val.summary[ind].sum0);
					        });
					        //console.log(options);
					     }
					     $(instance.choicelist).each(function(int,item){
					     	if(item.soptionid != 0)
					     	{
					     		instance.nextChoice();
					     	}
					     	else
					     	{
					     		return false;
					     	}
					     });
					     instance.curChoice();
					    // $(".w_contents").html(options);
				    }
					else if(data.status == "end")
					{
						
						//最后应跳到成绩页面
						//location.href = "<c:url value='/stem/choiceresult.html' />"+"?paperId="+paperId;
					}
					//$(data.options).each(function(){});
				}
			}); 
				$('.remainingTime').countdown({
					tmpl : '<span>%{h}</span>小时<span>%{m}</span>分<span>%{s}</span>秒',
					afterEnd: function() { submitToServer(1); }
				});
				/* 
				$('.J_countdown3').countdown({
					tmpl : '<span>%{d}</span>天, <span>%{h}</span>小时, <span>%{m}</span>分, <span>%{s}</span>秒'
				}); */
			});
		</script>
	<!-- inline scripts related to this page -->
	<div style="display:none">
		<script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540'
			language='JavaScript' charset='gb2312'></script>
	</div>
</body>
</html>
