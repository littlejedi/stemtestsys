<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>STEM答题系统</title>
<meta name="keywords" content="stem,测评系统" />
<meta name="description" content="好用的测评系统" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!-- basic styles -->

<link href="<c:url value='/stem/assets/css/bootstrap.min.css'/>"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/stem/assets/css/font-awesome.min.css'/>" />


<link type="text/css" href="<c:url value='/stem/css/commen.css'/>"
	rel="stylesheet" />
<link type="text/css" href="<c:url value='/stem/css/xixi.css'/>"
	rel="stylesheet" />
<!--[if IE 7]>
		  <link rel="stylesheet" href="<c:url value='/stem/assets/css/font-awesome-ie7.min.css'/>" />
		<![endif]-->

<!-- page specific plugin styles -->

<!-- fonts -->


<!-- ace styles -->

<link rel="stylesheet"
	href="<c:url value='/stem/assets/css/ace.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/stem/assets/css/ace-rtl.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/stem/assets/css/ace-skins.min.css'/>" />

<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<c:url value='/stem/assets/css/ace-ie.min.css'/>" />
		<![endif]-->

<!-- inline styles related to this page -->

<link rel="stylesheet" href="<c:url value='/stem/css/nav.css'/>">
<link rel="stylesheet" href="<c:url value='/stem/css/csdn-prod-footer.css'/>">
<link rel="stylesheet" href="<c:url value='/stem/css/panels.css'/>">
<link rel="stylesheet" href="<c:url value='/stem/css/pagination.css'/>">
<link rel="stylesheet" href="<c:url value='/stem/css/tag.css'/>">

<link rel="stylesheet" href="<c:url value='/stem/css/popover.css'/>">
<link rel="stylesheet" href="<c:url value='/stem/css/index.css'/>">
<link rel="stylesheet" href="<c:url value='/stem/css/gaoxiao_v2.css'/>" />

<style>
.center {
	text-align: center;
}

.center [class *="col-"] {
	margin-top: 2px;
	margin-bottom: 2px;
	padding-top: 4px;
	padding-bottom: 4px;
	position: relative;
	text-overflow: ellipsis;
}

.center [class *="col-"]  span {
	position: relative;
	z-index: 2;
	display: inline-block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	width: 100%;
}

.center [class *="col-"]:before {
	position: absolute;
	top: 0;
	bottom: 0;
	left: 2px;
	right: 2px;
	content: "";
	display: block;
	border: 1px solid #DDD;
	z-index: 1;
}

.center [class *="col-"]:hover:before {
	background-color: #FCE6A6;
	border-color: #EFD27A;
}
</style>

<!-- ace settings handler -->

<script src="<c:url value='/stem/assets/js/ace-extra.min.js'/>"></script>
<script type="text/javascript">
        var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
        document.write(unescape("%3Cscript async src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F936b618945c24c410f0d390f3a09aa1d' type='text/javascript'%3E%3C/script%3E"));
</script>
<!-- HTML5 shim and Respond.js'/> IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
		<script src="<c:url value='/stem/assets/js/html5shiv.js'/>"></script>
		<script src="<c:url value='/stem/assets/js/respond.min.js'/>"></script>
		<![endif]-->