<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Academic Homepage</title>
  <script src="js/jquery-1.7.2.js"></script>
  <script src="js/index.js"></script>
  <script src="bootstrap/js/bootstrap.min.js"></script>
  <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" />
  <link href="css/index.css" rel="stylesheet" />
</head>
<body> 
  <s:include value="smallhead.jsp"></s:include>
  <%
    session.invalidate();  
    response.sendRedirect("http://dcd.academic:8443/cas/logout?service=http://localhost:8080/AcademicSearchEngine/share.jsp");  
  %>
  <p><a href="index.jsp">回到搜索页</a></p>

</body>
</html>