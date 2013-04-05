<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>foot</title>

<style>
	
	HTML {
	FONT-FAMILY: "宋体"; FONT-SIZE: 10px
	}
	BODY {
		BACKGROUND-COLOR: #f5f2f1; FONT-SIZE: 1.2em; _height: 100%; margin: 0px;
	}
	* HTML {
		BACKGROUND-IMAGE: url(about:blank); BACKGROUND-ATTACHMENT: fixed
	}
	* HTML BODY {
		BACKGROUND-IMAGE: url(about:blank); BACKGROUND-ATTACHMENT: fixed
	}
	.cf:before {
		DISPLAY: table; CONTENT: ""
	}
	.cf:after {
		DISPLAY: table; CONTENT: ""
	}
	.cf:after {
		CLEAR: both
	}
	.cf {
		ZOOM: 1
	}
	.clearfix {
		CLEAR: both
	}
	A {
		TEXT-DECORATION: none
	}
	
	.footer {
		BACKGROUND-COLOR: #333
	}
	.footer_L2 {
		MARGIN: auto; WIDTH: 960px; COLOR: #898989; PADDING-TOP: 32px
	}
	.footer_L2 A {
		COLOR: #898989
	}
	.footer_L2 A:hover {
		COLOR: #fff
	}
	.footer_about {
		MARGIN-BOTTOM: 10px; margin-top: 0px;
	}
	.footer_about DL {
		WIDTH: 180px; MARGIN-RIGHT: 8px;
	}
	.footer_about .dlLeft {
		FLOAT: left
	}
	.footer_about .dlRight {
		FLOAT: right
	}
	.footer_about DT {
		MARGIN-BOTTOM: 12px; FONT-SIZE: 1.2em; FONT-WEIGHT: bold
	}
	.footer_about DD {
		LINE-HEIGHT: 20px
	}
	
	.footer_about .userService {
		WIDTH: 188px
	}
	.footer_about .userService A.online {
		COLOR: #ffde00; TEXT-DECORATION: underline
	}
	.footer_about .userService DD {
		LINE-HEIGHT: 24px
	}
	
	
	.footer_links {
		BORDER-BOTTOM: #555 1px solid; POSITION: relative; PADDING-BOTTOM: 10px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; ZOOM: 1; BORDER-TOP: #555 1px solid; PADDING-TOP: 10px
	}
	.footer_links_title {
		POSITION: absolute; PADDING-BOTTOM: 10px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; TOP: 0px; PADDING-TOP: 10px; LEFT: 0px; _top: 2px
	}
	.footer_links_list {
		WIDTH: 910px; MARGIN-LEFT: 64px; diplay: block
	}
	.footer_links A {
		MARGIN: 0px 5px
	}
	.footer_copy {
		POSITION: relative; PADDING-BOTTOM: 14px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; ZOOM: 1; PADDING-TOP: 14px
	}
	.footer_copy_left {
		POSITION: absolute; TOP: 14px; LEFT: 0px
	}
	.footer_copy_right {
		
	}
	.footer_copy_right P {
		TEXT-ALIGN: right; LINE-HEIGHT: 21px; margin: 0px; padding: 0px;
	}
	.footer_copy .join A {
		LINE-HEIGHT: 32px; BACKGROUND-COLOR: #444; PADDING-LEFT: 40px; PADDING-RIGHT: 12px; DISPLAY: inline-block; HEIGHT: 32px; VERTICAL-ALIGN: middle; MARGIN-RIGHT: 16px
	}
	.footer_copy .join A:hover {
		BACKGROUND-COLOR: #5a5a5a
	}
	.footer_copy .join A.sina {
		BACKGROUND-POSITION: 0px -703px
	}
	.footer_copy .join A.qqun {
		BACKGROUND-POSITION: 0px -746px
	}
	dd{
		display: block; margin: 0px; padding: 0px;
	}
	
	</style>

  </head>

  <body>
    <div class=footer>
	<div class=footer_L2>
	<div class="footer_about cf">
	<dl class=dlLeft>
	  <dt>工程科教知识服务系统 </dt>
	  <dd><a href="" rel=nofollow 
	  target=_blank>关于我们</a></dd>
	  <dd><a href="" rel=nofollow 
	  target=_blank>加入我们</a> </dd>
	  <dd><a href="" target=_blank>网站地图</a> </dd></dl>
	<dl class=dlLeft>
	  <dt>服务与支持 </dt>
	  <dd><a href="" rel=nofollow 
	  target=_blank>注册服务条款</a> </dd>
	  <dd><a href="" rel=nofollow 
	  target=_blank>意见反馈</a> </dd>
	  <dd><a href="" rel=nofollow 
	  target=_blank>版权申明</a> </dd></dl>
	<dl class=dlLeft>
	  <dt><a href="" rel=nofollow 
	  target=_blank>用户中心</a> </dt>
	  <dd><a href="" rel=nofollow 
	  target=_blank>常见问题</a> </dd>
	  <dd><a href="" 
	  rel=nofollow target=_blank>登录注册</a> </dd>
	  </dl>
	<dl class="dlLeft">
	  <dt>联系我们 </dt>
	  <dd>邮政信箱：北京8068信箱</dd>
	  <dd>邮编：100088</dd>
	  <dd>服务热线：010-0000000</dd>
	  </dl>
	</div>
	<div class="footer_links"><span class=footer_links_title>友情链接：</span> <span 
	class=footer_links_list><a 
	href="http://www.baidu.com/s?ie=utf-8&amp;bs=site%3A9yue.com+%E7%94%B5%E5%AD%90%E4%B9%A6&amp;f=8&amp;rsv_bp=1&amp;wd=site%3A9yue.com+%E7%94%B5%E5%AD%90%E4%B9%A6&amp;inputT=0" 
	target=_blank>免费电子书</a><a href="http://book.163.com/" target=_blank>网易读书</a><a 
	href="http://www.txtbook.com.cn/" 
	target=_blank>电子书下载</a><a 
	href="http://www.ibook8.com/" target=_blank>爱电子书吧</a><a 
	href="http://www.feiku.com/" target=_blank>电子书</a><a 
	href="http://www.eshuba.com/" target=_blank>E书吧</a><a 
	href="http://www.docin.net/" target=_blank>豆丁中文网</a><a 
	href="http://www.bookdao.com/" target=_blank>百道网</a> </span></div>
	<div class=footer_copy>
	
	<div class="footer_copy_right">
	<p>地址：北京市西城区冰窖口胡同2号</p>
	<p>Copyright © 2008 中国工程院 ICP备案号:京ICP备05023557号</p>
	</div></div></div></div><!-- 底部版权信息 end -->

</body>
</html>
