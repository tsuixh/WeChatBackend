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
	<!-- Custom JavaScript Frame -->  
	<script type="text/javascript" src="js/lib/myframe.js"></script>
	<!-- Custom JavaScript -->
	<script type="text/javascript" src="js/sendXhr.js"></script>
</head>
<body>
	<div class="container">
		<!--  导航栏 -->
	    <nav class="navbar navbar-inverse navbar-fixed-top">
	    	<div class="container">
	    		<a class="navbar-brand" href="itech.jsp">武理解析-Wulijiexi</a>
	    		<div class="collapse navbar-collapse" id="bs-example-bavbar-collapse-1">
	    			<ul class="nav navbar-nav na">
	    				<li class="active"><a href="#">关键词管理</a></li>
	    				<li><a href="addPic_reply.html">添加图文回复</a></li>
	    				<li><a href="addText_reply.html">添加文字回复</a></li>
	    				<li><a href="addAd.html">广告设置</a></li>
	    				<li><a href="defaultTextReply.html">设置默认回复</a>
	    				<li><a href="#">设置关注回复</a>
	    				<li><a href="#">查看统计数据</a></li>
	    			</ul>
	    		<p class="navbar-text navbar-right">Signed in as <a href="#" class="navbar-link">${user }</a></p>
	    		</div>
	    	</div>
	    </nav>
    	<!-- 内容展示：表格展示当前所有关键词（分页） -->
    	
	    <!-- 分页 -->
	    <nav aria-label="Page navigation">
		  <ul class="pagination">
		    <li class="disabled">
		      <a href="#" aria-label="Previous">
		        <span aria-hidden="true">&laquo;</span>
		      </a>
		    </li>
		    <li><a href="#">1</a></li>
		    <li><a href="#">2</a></li>
		    <li><a href="#">3</a></li>
		    <li><a href="#">4</a></li>
		    <li><a href="#">5</a></li>
		    <li>
		      <a href="#" aria-label="Next">
		        <span aria-hidden="true">&raquo;</span>
		      </a>
		    </li>
		  </ul>
		</nav>
	</div>
</body>
</html>
