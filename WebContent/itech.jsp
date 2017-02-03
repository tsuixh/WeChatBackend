<%--
  User: TsuiXh
  Date: 17/1/25
  Time: 16:32
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
    <title>微信公众号管理中心</title>
    <link href="css/lib/bootstrap.min.css" rel="stylesheet">
    <link href="css/index.css" rel="stylesheet">
    <script type="text/javascript" src="js/lib/jquery-3.1.1.js"></script>
	<script type="text/javascript" src="js/lib/carousel.js"></script>    
</head>
<body>

	<!--  导航栏 -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
    	<div class="container">
    		<a class="navbar-brand" href="itech.jsp">武理解析-Wulijiexi</a>
    		<div class="collapse navbar-collapse" id="bs-example-bavbar-collapse-1">
    			<ul class="nav navbar-nav na">
    				<li><a href="keyword_manage.jsp">关键词管理</a></li>
    				<li><a href="addPic_reply.html">添加图文回复</a></li>
    				<li><a href="addText_reply.html">添加文字回复</a></li>
    				<li><a href="addAd.html">广告设置</a></li>
    				<li><a href="defaultTextReply.html">设置默认回复</a>
    				<li><a href="Reply4Sub.jsp">设置关注回复</a>
    				<li><a href="#">查看统计数据</a></li>
    			</ul>
    		<p class="navbar-text navbar-right">Signed in as <a href="#" class="navbar-link">${user }</a></p>
    		</div>
    	</div>
    </nav>
    
    <!-- 内容部分 -->
	    <div class="container" id="scroll_play">
	    
	    <!-- 公众号效果展示部分 -->
	    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
		  <!-- Indicators -->
		  <ol class="carousel-indicators">
		    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
		    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
		    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
		  </ol>
		
		  <!-- Wrapper for slides -->
		  <div class="carousel-inner" role="listbox">
		    <div class="item active">
		      <img src="img/scroll_1.jpg" alt="...">
		      <div class="carousel-caption">
		        <h3>展示图片1</h3>
		        <p>关于武理解析的使用方法</p>
		      </div>
		    </div>
		    <div class="item">
		      <img src="img/scroll_2.jpg" alt="...">
		      <div class="carousel-caption">
		        <h3>展示图片2</h3>
		        <p>关于武理解析的使用方法</p>
		      </div>
		    </div>
		    <div class="item">
		      <img src="img/scroll_3.jpg" alt="...">
		      <div class="carousel-caption">
		        <h3>展示图片3</h3>
		        <p>关于武理解析的使用方法</p>
		      </div>
		    </div>
		  </div>
		
		  <!-- Controls -->
		  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
		    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
		    <span class="sr-only">Previous</span>
		  </a>
		  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
		    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
		    <span class="sr-only">Next</span>
		  </a>
		</div>
    	
    	<!-- 欢迎部分 -->
	    <div class="jumbotron">
	    	<h1>Hello, Wulijiexi !</h1>
	    	<p>武理解析是为在校大学生提供教材习题答案解析的微信公众号，联系我们以获取更多信息。</p>
	    	<p><a class="btn btn-primary btn-lg" href="mailto:smartcyh@hotmail.com" role="button">联系我们</a></p>
	    </div>
	    
	    <!--版权信息-->
	    <hr>
	    <div class="text-info">&copy;武理解析Wulijiexi. Powered by Yunhong. 2017</div>
    </div>
</body>
</html>
