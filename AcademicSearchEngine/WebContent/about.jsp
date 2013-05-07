<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>About Academic Search Engine</title>
  <script src="js/jquery-1.7.2.js"></script>
  <script src="js/index.js"></script>
  <script src="bootstrap/js/bootstrap.min.js"></script>
  <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" />
  <link href="css/index.css" rel="stylesheet" />
</head>
<body> 
  <s:include value="smallhead.jsp"></s:include>
 
  <!-- 搜索框 -->
  <div class="container">
    <div class="tabbable"> <!-- Only required for left/right tabs -->
      <ul class="nav nav-tabs">
        <li class="active"><a href="#tab1" data-toggle="tab">Reseacher</a></li>
        <li><a href="#tab2" data-toggle="tab">Publication</a></li>
      </ul>
      <div class="tab-content">
        <div class="tab-pane active" id="tab1">
          <p>Howdy, I'm in Section 1.</p>
        </div>
        <div class="tab-pane" id="tab2">
          <p>Howdy, I'm in Section 2.</p>
        </div>
      </div>
    </div>
  </div>
  
  
  <div class="container" id="home_container">
    <div id="myCarousel" class="carousel slide">
        <ol class="carousel-indicators">
          <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
          <li data-target="#myCarousel" data-slide-to="1"></li>
          <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>
        <!-- Carousel items -->
        <div class="carousel-inner">
          <div class="active item">
            <img src="images/logo/mongodb.png" class="img-rounded">
            <div class="carousel-caption">
              <h4>Mongodb</h4>
              <p>
                MongoDB is a NoSQL...
              </p>
            </div>
          </div>
          <div class="item">
            <img src="images/logo/jquery.png" class="img-rounded">
            <div class="carousel-caption">
              <h4>jQuery</h4>
              <p>
                jQuery controls the DOM elements... 
              </p>
            </div>
          </div>
          <div class="item">
            <img src="images/logo/bootstrap.png" class="img-rounded">
            <div class="carousel-caption">
              <h4>Bootstrap</h4>
              <p>
                Boostrap is a tool to design the website...
              </p>
            </div>
          </div>
        </div>
        <!-- Carousel nav -->
        <a class="left carousel-control" href="#myCarousel" data-slide="prev">&lsaquo;</a>
        <a class="right carousel-control" href="#myCarousel" data-slide="next">&rsaquo;</a>
      </div>
    
  </div>

  <div class="container">
    <div class="hero-unit">
      <h2>Thanks To</h2>
      <p>All The Lovely OpenSource Project</p>
      <div id="logos">
      <img src="images/logo/mongodb.png" class="img-polaroid">
      <img src="images/logo/scrapy.jpg" class="img-polaroid">
  	  <img src="images/logo/solr.png" class="img-polaroid">
  	  <img src="images/logo/lucene.jpg" class="img-polaroid">
      <br />
      <img src="images/logo/tika.png" class="img-polaroid">
  	  <img src="images/logo/bootstrap.png" class="img-polaroid">
  	  <img src="images/logo/jquery.png" class="img-polaroid">
      <img src="images/logo/struts2.jpg" class="img-polaroid">
  	  <br />
  	  <img src="images/logo/cas.JPG" class="img-polaroid">
  	  <br />
  	  <img src="images/logo/tomcat.jpg" class="img-circle">
  	  <img src="images/logo/github.jpg" class="img-circle">
  	  </div>     
    </div>
  </div>
</body>
</html>