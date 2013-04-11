<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Academic Sharing Search Engine</title>
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

  <!-- 搜索框 -->
  <div class="container">
    <div class="hero-unit">
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
      <em>共有<s:property value="total" />条关于"<s:property value="key" />"的搜索结果</em>
                  当前结果为<s:property value="start" /> - <s:property value="curNum" />,第<s:property value="curPage" />页, 共<s:property value="allPage" />页
    </p>
  </div>
  
  <!-- 左中右三份结果 -->
  <div class="container-fluid">
	<div class="row-fluid">
	  <!-- 左侧 -->
	  <div class="span2" id="myother1">
	    <!-- 若干列 -->
	    <div class="accordion" id="stat_left">
          <div class="accordion-group">
            <div class="accordion-heading">
              <a class="accordion-toggle" data-toggle="collapse" data-parent="#stat_left" href="#fieldBar">
                 Fields
              </a>
            </div>
            <div id="fieldBar" class="accordion-body collapse in"></div>
          </div>
          <div class="accordion-group">
            <div class="accordion-heading">
              <a class="accordion-toggle" data-toggle="collapse" data-parent="#stat_left" href="#placeBar">
                 Workplaces
              </a>
            </div>
            <div id="placeBar" class="accordion-body collapse"></div>
          </div>
        </div>
	  </div>
	  
	  <!-- 中部搜索结果 -->
	  <div class="span8" id="mymain">
	    <a href="#" class="btn btn-large btn-inverse disabled" id="back_to_top">Top</a>
	    
	    <!-- 学者 -->
	    <s:if test="authorlist.size()>0">
		  <s:iterator id="ars" value="authorlist">
		    <s:if test="%{#ars.homepage.length()>5}">
			  <p>
			    <u><a href="${ars.homepage}"><i class="icon-home"></i>${ars.name}</a></u>
			    <a class="btn btn-small" href="#" id="star"><i class="icon-star-empty"></i>Star</a>
			  </p>
		    </s:if>
		    <s:else>
			  <p>
			    <u><i class="icon-user"></i>${ars.name}</a></u>
			    <a class="btn btn-small" href="#" id="star"><i class="icon-star-empty"></i>Star</a>
			  </p>
		    </s:else>
			<p><b>Workplace:</b> ${ars.workplace}</p>
			<p class="text-success"><b>Field:</b> ${ars.field}</p>
			<br />
		  </s:iterator>
		</s:if>

		<!-- 论文 -->
		<s:if test="paperlist.size()>0">
		  <s:iterator id="prs" value="paperlist">
		    <s:if test="%{#prs.view_url.length()>5}">
			  <p>
			    <u><a href="${prs.view_url}"><i class="icon-book"></i>${prs.title}</a></u>
			    <a class="btn btn-small" href="#" id="star"><i class="icon-star-empty"></i>Star</a>
			  </p>
			</s:if>
			<s:else>
			  <p>
			    <u>${prs.title}</u>
			    <a class="btn btn-small" href="#" id="star"><i class="icon-star-empty"></i>Star</a>
			  </p>
			</s:else>
			<p>
			  <b>Authors:</b>
			  <s:iterator id="tmpauthor" value="%{#prs.authors}">
			    <s:if test="%{#tmpauthor.homepage.length()>5}">
			      <a href="<s:property value="#tmpauthor.homepage" />" class="author_tooltip" data-toggle="tooltip" 
			         data-placement="right" data-original-title="<s:property value="#tmpauthor.field" />" ><i class="icon-home"></i><s:property value="#tmpauthor.name" /></a>
			    </s:if>
			    <s:else>
			      <a class="author_tooltip" data-toggle="tooltip" data-placement="right" 
					 data-original-title="<s:property value="#tmpauthor.field" />" > <s:property value="#tmpauthor.name" /></a>
			    </s:else>
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
	  <div class="span2" id="myother2">
	  </div>
    </div>
  </div>

  <input id="skey" type=hidden value="<s:property value="key" />" ></input>
  <input id="stype" type=hidden value="<s:property value="core" />" ></input>
  	<!-- 
		<s:url action="hello" var="helloLink">
  			<s:param name="userName">参数值</s:param>
		</s:url>
		<p><a href="${helloLink}">这也是一种控制</a></p>
	-->
</body>
</html>