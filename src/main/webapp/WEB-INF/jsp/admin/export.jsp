<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<title>Insert title here</title>
</head>
<body>
<%
String fileName="成绩单.xls";
response.setContentType("application/msexcel;charset=utf_8");
response.setHeader("Content-disposition","inline;filename="+fileName);
request.setCharacterEncoding("utf-8");
String html=request.getParameter("excelText");
%>
</body>
</html>