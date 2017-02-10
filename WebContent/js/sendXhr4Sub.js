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
function goPage(currentPage) {
	ajax('get','RequestHandler?type=article&page='+currentPage, null, function(pageAttr) {
		var content = "";
		for (var i = 0; i < pageAttr.data.length; i ++) {
			var article = pageAttr.data[i];
			console.info(article);
			content += "<tr>" + 
										"<td>" +article.Title + "</td>" +
										"<td><img src='"+article.PicUrl+"'></td>";
			
			content += "<td><a href='javascript:choose4Sub("+article.article_id+")'><img src='img/button-add.png'></a></td></tr>";
		}
		var tbody = document.getElementById('tbody');
		tbody.innerHTML = content;
		//显示工具条
		var pageNav = document.getElementById('pageBar');
		pageNav.innerHTML = "<li><a href='javascript:goPage(1)'>首页</a></li>" + 
												"<li><a href='javascript:goPage("+(pageAttr.currentPage - 1)+")'>上一页</a></li>" +
												pageAttr.currentPage + " / " + pageAttr.totalPage + 
												"<li><a href='javascript:goPage("+(pageAttr.currentPage + 1)+")'>下一页</a></li>" + 
												"<li><a href='javascript:goPage("+pageAttr.totalPage+")'>尾页</a></li>";
	});
}