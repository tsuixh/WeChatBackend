/*
 * @Author：Cui Yunhong
 */

/**
 * 删除关键词
 * @param key_id
 * @param currentPage
 * @returns
 */
function choose4Sub(article_id) {
	ajax('get','RequestHandler?type=choose4Sub&article_id='+article_id, null, function() {
		//do nothing
	});
}

/**
 * 分页查询
 * @param currentPage
 * @param requestType
 * @returns
 */
function goPage(currentPage, requestType) {
	ajax('get','RequestHandler?type='+requestType+'&page='+currentPage, null, function(pageAttr) {
		var content = "";
		for (var i = 0; i < pageAttr.data.length; i ++) {
			var article = pageAttr.data[i];
			content += "<tr>" + 
										"<td>" +article.title + "</td>" +
										"<td><img src='"+article.pic_url+"'></td>";
			
			content += "<td><a href='javascript:choose4Sub("+article.article_id+")'><img src='img/button-add.png'></a></td></tr>";
		}
		var tbody = document.getElementById('tbody');
		tbody.innerHTML = content;
		//显示工具条
		var pageNav = document.getElementById('pageBar');
		pageNav.innerHTML = "<li><a href='javascript:goPage(1, 'article')'>首页</a></li>" + 
												"<li><a href='javascript:goPage("+(pageAttr.currentPage - 1)+", 'article' )'>上一页</a></li>" +
												pageAttr.currentPage + " / " + pageAttr.totalPage + 
												"<li><a href='javascript:goPage("+(pageAttr.currentPage + 1)+", 'article')'>下一页</a></li>" + 
												"<li><a href='javascript:goPage("+pageAttr.totalPage+", 'article')'>尾页</a></li>";
	});
}