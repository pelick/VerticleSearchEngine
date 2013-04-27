<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="org.jasig.cas.client.validation.Assertion" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Share</title>
  <script src="js/jquery-1.7.2.js"></script>
  <script src="bootstrap/js/bootstrap.min.js"></script>
  <script src="bootstrap/js/bootstrap-tooltip.js"></script>
  <script src="js/index.js"></script>
  <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" />
  <link href="css/index.css" rel="stylesheet" />
</head>
<body>
  <!-- smallhead -->
  <div class="navbar navbar-inverse">
    <div class="navbar-inner">
      <a class="brand" href="#">Academic Search</a>
      <div class="nav-collapse collapse">
        <ul class="nav">
          <li>
            <a href="home.jsp">Home</a>
          </li>
          <li>
            <a href="index.jsp">Search</a>
          </li>
          <li>
            <a href="discover.jsp">Discover</a>
          </li>
          <li class="active">
            <a href="share.jsp">Share</a>
          </li>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
               Menu<b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
              <li><a href="http://dcd.academic:8443/cas/login">login</a></li>
              <li><a href="http://dcd.academic:8443/cas/logout">logout</a></li>
              <li class="divider"></li>
              <li class="nav-header">Menu 2nd</li>
              <li><a href="#">haha</a></li>
              <li><a href="#">hoho</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </div>
  <div class="container">

  <%
	Object object = request.getSession().getAttribute("_const_cas_assertion_");
	Assertion assertion = (Assertion) object;
	String loginName = "";
	String name = "";
	String email = "";
	String weibo_url = "";
	String github_url = "";
	String interests = "";
	String homepage = "";
	if (object != null) {
	  loginName = assertion.getPrincipal().getName();
	  Map<String, Object> map = assertion.getPrincipal().getAttributes();
	  name = (String) map.get("name");
	  email = (String) map.get("email");
	  weibo_url = (String) map.get("weibo_url");
	  github_url = (String) map.get("github_url");
	  interests = (String) map.get("interests");
	  homepage = (String) map.get("homepage");
	}
  %>
  <div class="jumbotron">
    <h1 class="muted"><%=name%></h1>
    <h2><small><%=email%></small></h2>
    <p class="lead">I`m interested in <strong><%=interests%></strong>. You can <i class="icon-envelope"></i>
    <em><%=email%></em>. </p>
    <a class="btn btn-large btn-danger" href="<%=weibo_url%>">Follow Me on Weibo</a>
    <a class="btn btn-large btn-inverse" href="<%=github_url%>">Follow Me on Github</a>
  </div>
  
  <div class="page-header"></div>
  
  <div class="row-fluid">
    <div class="span4">
      <h2>History</h2>
      <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
      <p><a class="btn" href="#">View details &raquo;</a></p>
    </div>
    <div class="span4">
      <h2>Share</h2>
      <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
      <p><a class="btn" href="#">View details &raquo;</a></p>
    </div>
    <div class="span4">
      <h2>Favor</h2>
      <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa.</p>
      <p><a class="btn" href="#">View details &raquo;</a></p>
    </div>
  </div>
  
  <div class="page-header"></div>
  
  
  </div>
</body>
</html>