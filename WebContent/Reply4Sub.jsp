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
	<script type="text/javascript" src="js/sendXhr4Sub.js"></script>
</head>
<body onload="javascript:goPage(1,'keyword')" style="padding-top: 70px">
	<div class="container">
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
	    				<li class="active"><a href="Reply4Sub.jsp">设置关注回复</a>
	    				<li><a href="#">查看统计数据</a></li>
	    			</ul>
	    		<p class="navbar-text navbar-right">Signed in as <a href="#" class="navbar-link">${user }</a></p>
	    		</div>
	    	</div>
	    </nav>
	    
	    	<div class="well">
		    	<!-- 内容展示：表格展示当前所有关键词（分页） -->
		    	<table class="table table-hover">
		    		<thead>
		    			<tr>
		    				<td>图文标题</td>
		    				<td>封面图片</td>
		    				<td>选择</td>
		    			</tr>
		    		</thead>
					<tbody id="tbody"></tbody>
				</table>
			    <!-- 分页 -->
			    <nav aria-label="...">
				  <ul class="pager" id="pageBar">
				  </ul>
				</nav>
			</div>
			 <!--版权信息-->
		    <hr>
		    <div class="text-info">&copy;武理解析Wulijiexi. Powered by Yunhong. 2017</div>
		</div>
</body>
</html>
