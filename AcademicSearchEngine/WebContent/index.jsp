<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="org.jasig.cas.client.validation.Assertion" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Academic Sharing Search Engine</title>
  <script src="js/jquery-1.7.2.js"></script>
  <script src="bootstrap/js/bootstrap.min.js"></script>
  <script src="bootstrap/js/bootstrap-tooltip.js"></script>
  <script src="http://d3js.org/d3.v3.min.js"></script>
  <script src="js/index.js"></script>
  <script src="js/spin.js"></script>
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
              <li><a href="http://dcd.academic:8443/cas/login">login</a></li>
              <li><a href="http://dcd.academic:8443/cas/logout">logout</a></li>
              <li class="divider"></li>
              <li class="nav-header">Menu 2nd</li>
              <li><a href="#" id="register">Register</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </div>

  <!-- 搜索框 -->
  <div class="container">
    <div class="well">
	  <form class="form-search" action="academic">
        <input type="text" class="input-xxlarge control-group info" placeholder="您的搜索关键词" name="key">
	    <button type="submit" class="btn btn-info" id="sbtn">Search</button> <br/>
	    <label class="radio"> <input type="radio" name="core" value="core0" checked> 学者 </label> 
	    <label class="radio"> <input type="radio" name="core" value="core1" > 论文 </label>
	    <label class="radio"> <input type="radio" name="core" value="core2" > 全文 </label>
	  </form>
	</div>
  </div>
  
  <!-- 结果统计，分页 -->
  <div class="container">
    <div class="pagination" id="pages">
      <ul>
        <li><a href=<s:property value="preUrl" />>Prev</a></li>
        <li><a href=<s:property value="nextUrl" />>Next</a></li>
      </ul>
    </div>
    <p class="text-error">
      <em>共有<s:property value="total" />条关于"<s:property value="key" /> <s:property value="field" /> <s:property value="workplace" />" 的搜索结果</em>
                  当前结果为<s:property value="start" /> - <s:property value="curNum" />,第<s:property value="curPage" />页, 共<s:property value="allPage" />页
    </p>
  </div>
  
  <!-- Login -->
  <div id="loginModal" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
  	<div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
      <h3>您还未登录</h3>
    </div>
    <div class="modal-body">
      <p>登录使用的是CAS的单点登录，请跳转到server登录</p>
      <a class="btn btn-inverse btn-large" href="http://localhost:8080/AcademicSearchEngine/home.jsp">去登录</a>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
    </div>
  </div>
  <!-- Register -->
  <div id="registerModal" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
  	<div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
      <h3>注册</h3>
    </div>
    <div class="modal-body">
      <form class="form-horizontal">
        <div class="control-group">
          <label class="control-label" for="inputUsername">Username</label>
          <div class="controls">
            <input type="text" id="inputUsername" placeholder="Username">
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputPassword">Password</label>
          <div class="controls">
            <input type="password" id="inputPassword" placeholder="Password">
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputName">Name</label>
          <div class="controls">
            <input type="text" id="inputName" placeholder="Name">
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputEmail">Email</label>
          <div class="controls">
            <input type="text" id="inputEmail" placeholder="Email">
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputWeibo">Weibo</label>
          <div class="controls">
            <input type="text" id="inputWeibo" placeholder="Weibo">
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputGithub">Github</label>
          <div class="controls">
            <input type="text" id="inputGithub" placeholder="Github">
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputHomepage">Homepage</label>
          <div class="controls">
            <input type="text" id="inputHomepage" placeholder="Homepage">
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="inputInterests">Interests</label>
          <div class="controls">
            <input type="text" id="inputInterests" placeholder="Interests">
          </div>
        </div>
      </form>
    </div>
    <div class="modal-footer">
      <button type="submit" class="btn btn-primary" id="register_btn">Sign In</button>
      <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
    </div>
  </div>
  
  
  <!-- 左中右三份结果 -->
  <div class="container-fluid">
	<div class="row-fluid">
	  <!-- 左侧 -->
	  <div class="span2">
	    <!-- 若干列 -->
	    <div class="accordion" id="stat_left">
          <div class="accordion-group">
            <div class="accordion-heading">
              <a class="accordion-toggle" data-toggle="collapse" data-parent="#stat_left" href="#fieldBar">
                 <b>Fields</b><i class="icon-arrow-down"></i>
              </a>
            </div>
            <div id="fieldBar" class="accordion-body collapse in"></div>
          </div>
          <div class="accordion-group">
            <div class="accordion-heading">
              <a class="accordion-toggle" data-toggle="collapse" data-parent="#stat_left" href="#placeBar">
                 <b>Workplaces</b><i class="icon-arrow-down"></i>
              </a>
            </div>
            <div id="placeBar" class="accordion-body collapse"></div>
          </div>
        </div>
	  </div>
	  <%
	    Object object = request.getSession().getAttribute("_const_cas_assertion_");
	    String loginName = "";
	    if (object != null) {
	      Assertion assertion = (Assertion) object;
	      loginName = assertion.getPrincipal().getName();
	    }
	  %>
	  <!-- 中部搜索结果 -->
	  <div class="span8" id="mymain">
	    <!-- Top -->
	    <a href="#" class="btn btn-large btn-inverse disabled" id="back_to_top">Top</a>
		<!-- save fail -->
		<div class="alert alert-block" id="save_fail">
  		  <h4>Warning</h4>
  		  You`ve already saved it!
		</div>
		<!-- save success -->
		<div class="alert alert-success" id="save_success">
  		  <h4>Success</h4>
  		  You can get your list in Share~
		</div>
		
	    <!-- 学者 -->
	    <s:if test="authorlist.size()>0">
		  <s:iterator id="ars" value="authorlist">
			<p> 
			  <a class="pull-left" href="#">
                <img class="media-object" src="${ars.picurl}" id="head_pic">
              </a>
			  <u><i class="icon-user"></i><a href="researcher?name=${ars.name}">${ars.name}</a></u>
			  <s:if test="%{#ars.homepage.length()>5}">
			    <a href="${ars.homepage}" class="text-error"><i class="icon-share-alt"></i></a>
		      </s:if>
			  <a class="btn btn-small save_author" user="<%=loginName%>" author="${ars.name}" id="star"><i class="icon-star-empty"></i>Star</a>
			</p>
			<p><b>Workplace:</b> ${ars.workplace}</p>
			<p class="text-success"><b>Field:</b> ${ars.field}</p>
			<br />
		  </s:iterator>
		</s:if>

		<!-- 论文 -->
		<s:if test="paperlist.size()>0">
		  <s:iterator id="prs" value="paperlist">
			<p>
			  <u><i class="icon-book"></i>${prs.title}</u>
			  <s:if test="%{#prs.view_url.length()>5}">
			    <a href="${prs.view_url}" class="text-error"><i class="icon-share-alt"></i></a>
			  </s:if>
			  <a class="btn btn-small save_paper" user="<%=loginName%>" title="${prs.title}" key="<s:property value="key" />" id="star"><i class="icon-star-empty"></i>Star</a>
			</p>
			
			<p>
			  <b>Authors:</b>
			  <s:iterator id="tmpauthor" value="%{#prs.authors}">
			    <a class="author_tooltip" data-toggle="tooltip" data-placement="right" href="researcher?name=<s:property value="#tmpauthor.name" />"
				   data-original-title="<s:property value="#tmpauthor.field" />" > <s:property value="#tmpauthor.name" /></a>
				<s:if test="%{#tmpauthor.homepage.length()>5}">
			      <a href="<s:property value="#tmpauthor.homepage" />" class="text-error"><i class="icon-share-alt"></i></a>
			    </s:if>
			  </s:iterator>
			</p>
			<p><b>Abstract:</b>${prs.pub_abstract}</p>
			<p><i>${prs.conference}</i></p>
			<br />
		  </s:iterator>
		</s:if>
		
		<!-- pdf -->
		<s:if test="paperfulllist.size()>0">
		  <s:iterator id="prs" value="paperfulllist">
		    <s:if test="%{#prs.url.length()>5}">
			  <p>
			    <u><a href="${prs.url}"><i class="icon-book"></i>${prs.title}</a></u>
			    <a class="btn btn-small" href="#" id="star"><i class="icon-star-empty"></i>Star</a>
			  </p>
			</s:if>
			<s:else>
			  <p>
			    <u>${prs.title}</u>
			    <a class="btn btn-small" href="#" id="star"><i class="icon-star-empty"></i>Star</a>
			  </p>
			</s:else>
			<br />
		  </s:iterator>
		</s:if>
		
	  </div>
	  
	  <!-- 右侧 -->
	  <div class="span2">
	    <div class="accordion" id="stat_right">
          <div class="accordion-group">
            <div class="accordion-heading">
              <a class="accordion-toggle" data-toggle="collapse" data-parent="#stat_right" href="#hotBar">
                 <b>Hot Search</b><i class="icon-arrow-down"></i>
              </a>
            </div>
            <div id="hotBar" class="accordion-body collapse in"></div>
          </div>
          <div class="accordion-group">
            <div class="accordion-heading">
              <a class="accordion-toggle" data-toggle="collapse" data-parent="#stat_right" href="#newlyBar">
                 <b>The Newly</b><i class="icon-arrow-down"></i>
              </a>
            </div>
            <div id="newlyBar" class="accordion-body collapse"></div>
          </div>
        </div>
	  </div>
    </div>
  </div>

  <input id="skey" type=hidden value="<s:property value="key" />" ></input>
  <input id="stype" type=hidden value="<s:property value="core" />" ></input>
  <input id="sfield" type=hidden value="<s:property value="field" />" ></input>
  <input id="splace" type=hidden value="<s:property value="workplace" />" ></input>
  <input id="suser" type=hidden value=<%=loginName%> ></input>

</body>
</html>