<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="org.jasig.cas.client.validation.Assertion" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Home</title>
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
          <li>
            <a href="about.jsp">About</a>
          </li>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
               Menu<b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
              <li><a href="http://dcd.academic:8443/cas/login">Login</a></li>
              <li><a href="logout.jsp">Logout</a></li>
              <li class="divider"></li>
              <li class="nav-header">Menu 2nd</li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </div>
  
  <div class="container">

  <div class="jumbotron media well">
    <h2 class="muted"><s:property value="user.name" /></h2>
    <h3><i class="icon-envelope"></i><small><em><s:property value="user.email" /></em></small></h3>
    <p class="lead">I`m interested in <strong><s:property value="user.interests" /></strong>.</p>
    <a class="btn btn-large btn-danger" href="<s:property value="user.weibo_url" />">Follow Me on Weibo</a>
    <a class="btn btn-large btn-inverse" href="<s:property value="user.github_url" />">Follow Me on Github</a>
  </div>
  
  <div class="container">
    <div class="tabbable tabs-left"> 
      <ul class="nav nav-tabs">
        <li class="active"><a href="#tab1" data-toggle="tab">我的收藏</a></li>
        <li><a href="#tab2" data-toggle="tab">推荐给我</a></li>
        <li><a href="#tab3" data-toggle="tab">我的分享</a></li>
        <li><a href="#tab4" data-toggle="tab">我的历史</a></li>
      </ul>
      <div class="tab-content">
        <div class="tab-pane active" id="tab1">
          <div class="tabbable">
            <ul class="nav nav-pills">
              <li class="active"><a href="#tab11" data-toggle="tab">我收藏的学者</a></li>
              <li><a href="#tab12" data-toggle="tab">我收藏的论文</a></li>
            </ul>
            <div class="tab-content">
              <div class="tab-pane active" id="tab11">
                <button class="btn" type="button" id="user_author_btn" user="<s:property value="user.username" />">Click</button>
                <div id="user_author"></div>
              </div>
              <div class="tab-pane" id="tab12">
                <button class="btn" type="button" id="user_paper_btn" user="<s:property value="user.username" />">Click</button>
                <div id="user_paper"></div>
              </div>
            </div>
          </div>
        </div>
        <div class="tab-pane" id="tab2">
          <div class="alert alert-error">
            <strong>论文</strong> 收藏了XXX的用户，还收藏了。。。
          </div>
          <div class="alert alert-warning">
            <strong>论文</strong> 收藏了XXX的用户，还收藏了。。。
          </div>
          <div class="alert alert-success">
            <strong>论文</strong> 收藏了XXX的用户，还收藏了。。。
          </div>
          <div class="alert alert-info">
            <strong>论文</strong> 收藏了XXX的用户，还收藏了。。。
          </div>
        </div>
        <div class="tab-pane" id="tab3">
          <div class="tabbable">
            <ul class="nav nav-pills">
              <li class="active"><a href="#tab31" data-toggle="tab">分享历史</a></li>
              <li><a href="#tab32" data-toggle="tab">新的分享</a></li>
            </ul>
            <div class="tab-content">
              <div class="tab-pane active" id="tab31">
                <button class="btn" type="button" id="share_history_btn" user="<s:property value="user.username" />">Click</button>
                <div id="share_history"></div>
              </div>
              <div class="tab-pane" id="tab32">
                <form class="form-horizontal">
                  <div class="control-group">
                    <label class="control-label" for="inputContent">Content</label>
                    <div class="controls">
                      <input type="text" id="inputContent" placeholder="Content">
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label" for="inputUrl">Url</label>
                    <div class="controls">
                      <input type="text" id="inputUrl" placeholder="url">
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label" for="inputTag">Tags</label>
                    <div class="controls">
                      <input type="text" id="inputTag" placeholder="key words">
                    </div>
                  </div>
                  <div class="control-group">
                    <div class="controls">
                      <input type="radio" name="type" value="article" checked> 博文 
	                  <input type="radio" name="type" value="paper" > 论文 
	                  <input type="radio" name="type" value="page" > 主页 
	                </div>
	              </div>
	              <button class="btn btn-primary" id="share_btn">Share</button>
                </form>
              </div>
            </div>
          </div>
        </div>
        <div class="tab-pane" id="tab4">
          <div class="tabbable">
            <ul class="nav nav-pills">
              <li class="active"><a href="#tab41" data-toggle="tab">搜索历史</a></li>
              <li><a href="#tab42" data-toggle="tab">收藏历史</a></li>
              <li><a href="#tab43" data-toggle="tab">发现历史</a></li>
            </ul>
            <div class="tab-content">
              <div class="tab-pane active" id="tab41">
                <button class="btn" type="button" id="search_history_btn" user="<s:property value="user.username" />">Click</button>
                <div id="search_history"></div>
              </div>
              <div class="tab-pane" id="tab42">
                <button class="btn" type="button" id="save_history_btn" user="<s:property value="user.username" />">Click</button>
                <div id="save_history"></div>
              </div>
              <div class="tab-pane" id="tab43">
                <button class="btn" type="button" id="discover_history_btn" user="<s:property value="user.username" />">Click</button>
                <div id="discover_history"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  

  <div class="page-header"></div>
  
  </div>
</body>
</html>