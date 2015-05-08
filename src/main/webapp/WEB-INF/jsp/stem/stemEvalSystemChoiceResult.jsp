<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="stemEvalSystemTop.jsp" charEncoding="UTF-8" /> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>

	<body>
		<c:import url="stemEvalSystemHeader.jsp" charEncoding="UTF-8" />

		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>				</a>

				<c:import url="stemEvalSystemSidebar.jsp" charEncoding="UTF-8" />

				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="#">个人中心</a>							</li>

							<!--<li>
								<a href="#">Other Pages</a>							</li>-->
							<li class="active">答题结果</li>
						</ul><!-- .breadcrumb -->

						<div class="nav-search" id="nav-search">
							<!-- <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>								</span>
							</form> -->
						</div><!-- #nav-search -->
					</div>

					<div class="page-content">
						<div class="page-header">
							<h1>
								
								<small>
									<i class="icon-double-angle-right"></i>
									考试时间：2014年11月11号——2014年12月18号								</small>							</h1>
						</div><!-- /.page-header -->

						<%-- <div class="row">
							<form class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-md-3">
									姓名：
								</label><span class="control-label col-md-3" style="text-align:left;">${profile.realname}</span>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">
									身份证号：
								</label><span class="control-label col-md-3" style="text-align:left;">${profile.idcard}</span>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">
									考试时间：
								</label><span class="control-label col-md-3" style="text-align:left;">${exam.examBegin}——<br/>${exam.examEnd}</span>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">
									STEM测评分数：
								</label><span class="control-label col-md-3" style="text-align:left;">${result.choicescore}分(满分：${fullmark}分)</span>
							</div>
							</form> --%>
						<%-- <c:if test="${result.status ==1}">
						<div class="alert alert-block alert-success">

									<i class="icon-ok green"></i>

									恭喜您，
									<strong class="green">
										通过
										<small></small>
									</strong>
									选择题考试,考试满分为:<strong class="black">${fullmark}</strong>;您的考试成绩为:<strong class="green">${result.choicescore}</strong>;及格分数为:<strong class="green">${examline}</strong>,您的具体考试成绩如下所示.	
								</div>
								</c:if>
							<c:if test="${result.status ==2}">
						<div class="alert alert-block alert-warning">

									<i class="icon-ok green"></i>

									很遗憾，您
									<strong class="red">
										未通过
										<small></small>
									</strong>
									选择题考试,考试满分为:<strong class="black">${fullmark}</strong>;您的考试成绩为:<strong class="green">${result.choicescore}</strong>;及格分数为:<strong class="green">${examline}</strong>,您的具体考试成绩如下所示.	
								</div>
								</c:if> --%>
					<%-- <div class="col-sm-12">
										<div class="widget-box">
											<div class="widget-header widget-header-flat widget-header-small">
												<h5>
													<i class="icon-signal"></i>
													考试成绩
												</h5>

												<div class="widget-toolbar no-border">
													<!--<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown">
														本周
														<i class="icon-angle-down icon-on-right bigger-110"></i>
													</button>-->

													<ul class="dropdown-menu pull-right dropdown-125 dropdown-lighter dropdown-caret">
														<li class="active">
															<a href="#" class="blue">
																<i class="icon-caret-right bigger-110">&nbsp;</i>
																本周
															</a>
														</li>

														<li>
															<a href="#">
																<i class="icon-caret-right bigger-110 invisible">&nbsp;</i>
																上周
															</a>
														</li>

														<li>
															<a href="#">
																<i class="icon-caret-right bigger-110 invisible">&nbsp;</i>
																本月
															</a>
														</li>

														<li>
															<a href="#">
																<i class="icon-caret-right bigger-110 invisible">&nbsp;</i>
																上月
															</a>
														</li>
													</ul>
												</div>
											</div>

											<div class="widget-body">
												<div class="widget-main">
													<div id="piechart-placeholder"></div>

													<div class="hr hr8 hr-double"></div>

													<div class="clearfix">
														<div class="grid4">
															<span class="grey">
																<!--<i class="icon-facebook-sign icon-2x blue"></i>-->
																&nbsp; S
															</span>
															<h4 class="bigger pull-right">${result.choices}</h4>
														</div>

														<div class="grid4">
															<span class="grey">
																<!--<i class="icon-twitter-sign icon-2x purple"></i>-->
																&nbsp; T
															</span>
															<h4 class="bigger pull-right">${result.choicet}</h4>
														</div>

														<div class="grid4">
															<span class="grey">
																<!--<i class="icon-pinterest-sign icon-2x red"></i>-->
																&nbsp; E
															</span>
															<h4 class="bigger pull-right">${result.choicee}</h4>
														</div>
                                                        <div class="grid4">
															<span class="grey">
																<!--<i class="icon-pinterest-sign icon-2x red"></i>-->
																&nbsp; M
															</span>
															<h4 class="bigger pull-right">${result.choicem}</h4>
														</div>
													</div>
												</div><!-- /widget-main -->
											</div><!-- /widget-body -->
										</div><!-- /widget-box -->
									</div> --%>
		
		
		
									<%-- <c:if test="${result.status ==1}">	
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="button" onClick="location.href='<c:url value='/stem/choosecourse.html'/>?paperId=${paperId}'">
												<i class="icon-ok bigger-110"></i>
												进入开放题答题
											</button>

											&nbsp; &nbsp; &nbsp;
											
										</div>
									</div>
                                	</c:if> --%>
                                
                                
                                
						<!-- </div> --><!-- /.row -->
						<div class="row">
							<div class="col-xs-12">
										<div class="table-responsive">
											<table id="sample-table-1" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th>&nbsp;</th>
														<th>&nbsp;</th>
													</tr>
												</thead>

												<tbody>
													<tr>
														<td>姓名：</td>
														<td>${profile.realname}</td>
													</tr>
													<tr>
														<td>
															身份证号：
														</td>
														<td>${profile.idcard}</td>
													</tr>
													<tr>
														<td>
															考试时间：
														</td>
														<td>${exam.examBegin}——${exam.examEnd}</td>
													</tr>
													<tr>
														<td>
															STEM测评分数：
														</td>
														<td>${result.choicescore}分(满分：${fullmark}分)</td>
														
													</tr>
													<tr>
														<td>
															S
														</td>
														<td>${result.choices}</td>
														
													</tr>
													<tr>
														<td>
															T
														</td>
														<td>${result.choicet}</td>
														
													</tr>
													<tr>
														<td>
															E
														</td>
														<td>${result.choicee}</td>
														
													</tr>
													<tr>
														<td>
															M
														</td>
														<td>${result.choicem}</td>
														
													</tr>
												</tbody>
											</table>
										</div><!-- /.table-responsive -->
									</div>
						</div><!-- /.row -->
						
						
						
						
						
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->

				
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>			</a>		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->

		<script src="<c:url value='/stem/assets/js/jquery-1.10.2.min.js'/>"></script>

		<!-- <![endif]-->

		<!--[if IE]>
<script src="<c:url value='/stem/assets/js/jquery-1.10.2.min.js'/>"></script>
<![endif]-->

		<script src="<c:url value='/stem/js/highcharts.js'/>"></script>
        <script src="<c:url value='/stem/js/modules/exporting.js'/>"></script>
		

		<script type="text/javascript">
$(function () {
    $('#piechart-placeholder').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '考试成绩'
        },
        xAxis: {
            categories: [
                'S',
                'T',
                'E',
                'M'
               // ,'总分'
            ]
        },
        yAxis: {
            min: 0,
            max:100,
            title: {
                text: '分数'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: '考试成绩',
            data: [${result.choices}, ${result.choicet}, ${result.choicee}, ${result.choicem}/*, ${result.choicescore}*/]

        }]
    });
});
		</script>

		


		<!-- inline scripts related to this page -->
	<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>
