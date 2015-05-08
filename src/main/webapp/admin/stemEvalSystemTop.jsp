<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">  
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0"
    />
    <title>stem后台管理</title>
    <link href="<c:url value='/admin/bootstrap/css/bootstrap.min.css' />" rel="stylesheet" type="text/css"
    />
    <!--[if lt IE 9]>
      <link rel="stylesheet" type="text/css" href="<c:url value='/admin/plugins/jquery-ui/jquery.ui.1.10.2.ie.css' />"
      />
    <![endif]-->
    <link href="<c:url value='/admin/assets/css/main.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/admin/assets/css/plugins.css' />" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/admin/assets/css/responsive.css' />" rel="stylesheet" type="text/css"
    />
    <link href="<c:url value='/admin/assets/css/icons.css' />" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="<c:url value='/admin/assets/css/fontawesome/font-awesome.min.css' />">
    <!--[if IE 7]>
      <link rel="stylesheet" href="<c:url value='/admin/assets/css/fontawesome/font-awesome-ie7.min.css' />">
    <![endif]-->
    <!--[if IE 8]>
      <link href="<c:url value='/admin/assets/css/ie8.css' />" rel="stylesheet" type="text/css" />
    <![endif]-->
    
    
    <script type="text/javascript" src="<c:url value='/admin/assets/js/libs/jquery-1.10.2.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/jquery-ui/jquery-ui-1.10.2.custom.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/bootstrap/js/bootstrap.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/assets/js/libs/lodash.compat.min.js' />">
    </script>
    <!--[if lt IE 9]>
      <script src="<c:url value='/admin/assets/js/libs/html5shiv.js' />">
      </script>
    <![endif]-->
    <script type="text/javascript" src="<c:url value='/admin/plugins/touchpunch/jquery.ui.touch-punch.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/event.swipe/jquery.event.move.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/event.swipe/jquery.event.swipe.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/assets/js/libs/breakpoints.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/respond/respond.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/cookie/jquery.cookie.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/slimscroll/jquery.slimscroll.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/slimscroll/jquery.slimscroll.horizontal.min.js' />">
    </script>
    <!--[if lt IE 9]>
      <script type="text/javascript" src="<c:url value='/admin/plugins/flot/excanvas.min.js' />">
      </script>
    <![endif]-->
    <script type="text/javascript" src="<c:url value='/admin/plugins/sparkline/jquery.sparkline.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/flot/jquery.flot.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/flot/jquery.flot.tooltip.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/flot/jquery.flot.resize.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/flot/jquery.flot.time.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/flot/jquery.flot.growraf.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/easy-pie-chart/jquery.easy-pie-chart.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/daterangepicker/moment.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/daterangepicker/daterangepicker.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/blockui/jquery.blockUI.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/fullcalendar/fullcalendar.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/pickadate/picker.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/pickadate/picker.date.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/noty/jquery.noty.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/noty/layouts/top.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/noty/themes/default.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/uniform/jquery.uniform.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/select2/select2.min.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/assets/js/app.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/assets/js/plugins.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/assets/js/plugins.form-components.js' />">
    </script>
    <script type="text/javascript" src="<c:url value='/admin/plugins/bootbox/bootbox.min.js' />">
    </script>
    <script>
      $(document).ready(function() {
        App.init();
        Plugins.init();
        FormComponents.init();
        $(".inlinepicker").datepicker({inline:true,showOtherMonths:true});
      });
    </script>
    <script type="text/javascript" src="<c:url value='/admin/assets/js/custom.js' />">
    </script>