<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Discover</title>
  <script src="js/jquery-1.7.2.js"></script>
  <script src="bootstrap/js/bootstrap.min.js"></script>
  <script src="bootstrap/js/bootstrap-tooltip.js"></script>
  <script src="http://d3js.org/d3.v3.min.js"></script>
  <script src="js/index.js"></script>
  <script src="js/spin.js"></script>
  <script src="js/fisheye.js"></script>
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
          <li class="active">
            <a href="discover.jsp">Discover</a>
          </li>
          <li>
            <a href="share.jsp">Share</a>
          </li>
        </ul>
      </div>
    </div>
  </div>

  <!-- visualization -->
  <div class="container">
	<div class="row-fluid">
	    <div class="container">
	      <!-- How To Use -->
          <div class="alert alert-block">
  		    <button type="button" class="close" data-dismiss="alert">&times;</button>
  		    <h4>How To Use</h4>
  		    It aims to find <strong>related-authors</strong> and visualize the result for you.<br />
  			There are several search types for you to select different author groups. 
		  </div>
	    
          <!-- search -->
          <div class="tabbable">
            <ul class="nav nav-tabs">
              <li class="active"><a href="#tab1" data-toggle="tab">Reseacher Name</a></li>
              <li><a href="#tab4" data-toggle="tab">Paper Context</a></li>
              <li><a href="#tab2" data-toggle="tab">Study Field</a></li>
              <li><a href="#tab3" data-toggle="tab">Work Place</a></li>
            </ul>
            <div class="tab-content">
              <div class="tab-pane active" id="tab1">
                <form class="form-search">
                  <div class="input-append">
                    <input type="text" class="input-xlarge search-query control-group info" id="dname" placeholder="Type a researcher name">
                    <button class="btn btn-info coauthor_btn" type="button">Discover</button>
                  </div>
                </form>
              </div>
              <div class="tab-pane" id="tab2">
                <form class="form-search">
                  <div class="input-append">
                    <input type="text" class="input-xlarge search-query control-group success" id="dfield" placeholder="Type a study field">
                    <button class="btn btn-success cofield_btn" type="button">Discover</button>
                  </div>
                </form>
              </div>
              <div class="tab-pane" id="tab3">
                <form class="form-search">
                  <div class="input-append">
                    <input type="text" class="input-xlarge search-query control-group warning" id="dplace" placeholder="Type a work place">
                    <button class="btn btn-warning coplace_btn" type="button">Discover</button>
                  </div>
                </form>
              </div>
              <div class="tab-pane" id="tab4">
                <form class="form-search">
                  <div class="input-append">
                    <input type="text" class="input-xlarge search-query control-group error" id="dpaper" placeholder="Type text">
                    <button class="btn btn-danger copaper_btn" type="button">Discover</button>
                  </div>
                </form>
              </div>
            </div>
          </div>

          <!-- history table -->
          <div class="media well" >
            <h2>Discovery History</h2>
            <table class="table table-condensed" id="history">
              <tr>
                <td>Type</td>
                <td>Target</td>
                <td>Status</td>
                <td>Replay</td>
                <td>Remove</td>
              </tr>
            </table>
          </div>
        </div>
        
        <a href="#" class="btn btn-large btn-inverse disabled" id="back_to_top">Top</a>
	      
		<!-- Coauthor -->
        <div class="page-header">
          <h2>Visualization</h2>
        </div>
        <div class="row-fluid">
          <div class="span6" id="coauthorOne">
            <div id="load_one"></div>
            <div id="graph_one"></div>
          </div>
          <div class="span5" id="coauthorTwo">
            <select id="order">
			  <option value="name">by Name</option>
		      <option value="count">by Frequency</option>
			  <option value="group">by Cluster</option>
  		    </select>
  		    <div id="load_two"></div>
  		    <div id="graph_two"></div>
          </div>
        </div>
        
		<!-- Other app -->
        <div class="page-header">
          <h2>FunnyFaces</h2>
        </div>
        To Be Continued
	  </div>
    </div>
 
</body>
</html>