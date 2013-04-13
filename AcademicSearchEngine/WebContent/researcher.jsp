<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Researcher</title>
  <script src="js/jquery-1.7.2.js"></script>
  <script src="bootstrap/js/bootstrap.min.js"></script>
  <script src="bootstrap/js/bootstrap-tooltip.js"></script>
  <script src="http://d3js.org/d3.v3.min.js"></script>
  <script src="js/index.js"></script>
  <script src="js/cloud.js"></script>
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
          <li class="active">
            <a href="index.jsp">Search</a>
          </li>
          <li>
            <a href="#">Discover</a>
          </li>
          <li>
            <a href="#">Share</a>
          </li>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
               Menu<b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
              <li><a href="#">haha</a></li>
              <li><a href="#">hehe</a></li>
              <li><a href="#">heihei</a></li>
              <li class="divider"></li>
              <li class="nav-header">Menu 2nd</li>
              <li><a href="#">enen</a></li>
              <li><a href="#">hoho</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </div>

  <!-- 正文 -->
  <div class="container-fluid">
	<div class="row-fluid">
	  <!-- 左侧 -->
	  <div class="span8" id="myother1">
	    <div class="media well" >
          <a class="pull-left" href="#">
            <img class="media-object" src="images/github.JPG" id="author_pic">
          </a>
          <div class="media-body">
            <h3 class="media-heading"><s:property value="info.name" /><i class="icon-user"></i></h3>
			<dl class="dl-horizontal">
			  <dt>Homepage</dt>
              <dd><s:property value="info.homepage" /></dd>
            </dl>
			<s:property value="info.workplace" /> <br/>
			<s:property value="info.field" /><br/>
          </div>
        </div>
		
		<a href="#" class="btn btn-large btn-inverse disabled" id="back_to_top">Top</a>
		
		<!-- visualization -->
        <div class="page-header">
          <h2>Coauthors</h2>
          <button class="btn" type="button" id="coauthorOne_btn">Click</button>
          <div id="coauthorOne"></div>
          <button class="btn" type="button" id="coauthorTwo_btn">Click</button>
          <select id="order">
			<option value="name">by Name</option>
			<option value="count">by Frequency</option>
			<option value="group">by Cluster</option>
  		  </select>
          <div id="coauthorTwo"></div>
        </div>
		
		<div class="page-header">
          <h2>Keywords</h2>
          <button class="btn" type="button" id="cloudword_btn">Click</button>
          <div id="wordcloud"></div>
        </div>
		
		
		<!-- publications -->
       
        <div class="page-header">
          <h2>Publications</h2>
          <!-- 论文 -->
		  <s:if test="paperlist.size()>0">
		    <s:iterator id="prs" value="paperlist">
		    <blockquote>
			  <p>
			    ${prs.title}
			    <s:if test="%{#prs.view_url.length()>5}">
			      <a href="${prs.view_url}" class="text-error"><i class="icon-share-alt"></i></a>
			    </s:if>
			  </p>
			  <input id="abstract" type=hidden value="${prs.pub_abstract}" ></input>
			  <p class="muted"><small>${prs.author}
			  <s:if test="%{#prs.conference.length()>5}">
			    <i>@${prs.conference}</i>		 
			  </s:if>
			  </small></p>
			</blockquote>
		    </s:iterator>
		  </s:if>
        </div>
        
        
        
	  </div>
	  
	  <!-- 右侧 -->
	  <div class="span2" id="myother2">
	  
	  </div>
    </div>
  </div>

</body>
</html>