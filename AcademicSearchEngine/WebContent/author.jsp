<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Author Searching</title>
</head>
<body>
	<form method=get action="author" >
		<input value="" name="key" id="searchkey" />
		<input style="width: 110px; font-weight: bold; height: 35px;" type=submit
			value="搜索" autocomplete=off action="AuthorAction" />
		<br /> 搜索：
		<input name="type" type="radio" value="name" checked />
		<label for="core0">学者名</label> 
	</form>
	<s:iterator id="rs" value="authorlist">
		<p>${rs.name}</p>
		<p>${rs.workplace}</p>
		<p>${rs.field}</p>
		<p>${rs.homepage}</p>
	</s:iterator>
	
	<!-- 
		<p><a href="<s:url action='index'/>">回到主页</a></p>
		
		<s:url action="hello" var="helloLink">
  			<s:param name="userName">参数值</s:param>
		</s:url>
		<p><a href="${helloLink}">这也是一种控制</a></p>
		
		<s:property value="messageStore.message" />直接把值显示出来
		
		<s:form action="register ">
			这是struts2自己的form
 	  		<s:textfield name="personBean.firstName" label="First name" />
 	  		<s:textfield  name="personBean.lastName" label="Last name" />
 	  		<s:textfield name="personBean.email"  label ="Email"/>  
 	  		<s:textfield name="personBean.age"  label="Age"  />
   	  		<s:submit value="搜索" />
		</s:form>	
	-->
</body>
</html>