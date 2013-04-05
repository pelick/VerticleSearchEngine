<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Academic Sharing Search Engine</title>
  <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" />
  <link href="css/index.css" rel="stylesheet" />
</head>
<body>
  <s:include value="head.jsp"></s:include>

  <div class="container" id="myhead">
	<form class="form-search" action="academic">
      <input type="text" class="input-xlarge control-group info" placeholder="search key input" name="key">
	  <button type="submit" class="btn btn-info">Search</button> <br/>
	  <label class="radio"> <input type="radio" name="core" value="core0" checked> 学者 </label> 
	  <label class="radio"> <input type="radio" name="core" value="core1" > 论文 </label>
	</form>
  </div>
  <div class="container">
    <div class="pagination" id="pages">
      <ul>
        <li><a href="#">Prev</a></li>
        <li><a href="academic?key=&core=&start=&rows=">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li><a href="#">Next</a></li>
      </ul>
    </div>
    <p class="text-error">
      <em>共有<s:property value="total"></s:property>条关于"<s:property value="key"></s:property>"的搜索结果</em>
    </p>
  </div>
  <div class="container-fluid">
	<div class="row-fluid">
	  <div class="span2" id="myother1"></div>
	  <div class="span8" id="mymain">
	    <s:if test="authorlist.size()>0">
		  <s:iterator id="ars" value="authorlist">
		    
		    <s:if test="%{#ars.homepage.length()>5}">
			  <p><a href="${ars.homepage}"><i class="icon-home"></i>${ars.name}</a>
			    <a class="btn btn-small" href="#" id="star"><i class="icon-star"></i>Star</a>
			  </p>
		    </s:if>
		    <s:else>
			  <p>${ars.name}</a>
			    <a class="btn btn-small" href="#" id="star"><i class="icon-star"></i>Star</a>
			  </p>
		    </s:else>
			<p><b>Workplace:</b> ${ars.workplace}</p>
			<p class="text-success"><b>Field:</b> ${ars.field}</p>
			<br />
		  </s:iterator>
		</s:if>

		<s:if test="paperlist.size()>0">
		  <s:iterator id="prs" value="paperlist">
		    <s:if test="%{#prs.view_url.length()>5}">
			  <p><a href="${prs.view_url}">${prs.title}</a></p>
			</s:if>
			<s:else>
			  <p>${prs.title}</a></p>
			</s:else>
			<p><b>Authors:</b> ${prs.author}</p>
			<p><b>Abstract:</b>${prs.pub_abstract}</p>
			<p><i><b>Conference:</b>${prs.conference}</i></p>
			<br />
		  </s:iterator>
		</s:if>
	  </div>
	  <div class="span2" id="myother2"></div>
    </div>
  </div>
  <s:include value="foot.jsp"></s:include>
</body>
</html>