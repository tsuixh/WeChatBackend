/*
 * @Author：Cui Yunhong
 */

/**
 * 删除关键词
 * @param key_id
 * @param currentPage
 * @returns
 */
function deleteKey(key_id, currentPage) {
	ajax('get','RequestHandler?type=deleteKey&key_id='+key_id, null, function() {
		goPage(currentPage, 'keyword');
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
			var keyword = pageAttr.data[i];
			content += "<tr>" + 
										"<td>" + keyword.key_id + "</td>" +
										"<td>" + keyword.keyword + "</td>";
			if (keyword.reply_type == "article") {
				content += "<td>图文回复</td>";
			} else {
				content += "<td>文本回复</td>";
			}
			content += "<td><a href='javascript:deleteKey("+keyword.key_id+", "+currentPage+")'><img src='img/delete.png'></a></td></tr>";
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