



function goPage(currentPage, requestType) {
	ajax('get','DataRequestHandler?type'+requestType+'&page='+currentPage, null, function(pageAttr) {
		var content = "";
		for (var i = 0; i < pageAttr.data.length; i ++) {
			var keyword = pa.data[i];
			content += "<tr>" + 
										"<td>" + keyword.key_id + "</td>" +
										"<td>" + keyword.keyword + "</td>";
			if (keyword.reply_type == "article") {
				content += "<td>图文回复</td></tr>";
			} else {
				content += "<td>文本回复</td></tr>";
			}
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