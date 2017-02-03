/**
 * 简单的工具集
 * @author: Cui Yunhong
 */

/**
 * @param method	请求方式
 * @param url	请求地址
 * @param data	post的数据(问号后面的内容，如："user=jack&flag=login")
 * @returns
 */
function ajax(method, url, data, callback) {
	
	var xhr;
	if (window.XMLHttpRequest()) {
		xhr = new XMLHttpRequest();
	} else {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xhr.open(method, url);
	if (method.toLowerCase() == "post") {
		xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
	}
	xhr.send(data);
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && shr.status == 200) {
			var data = xhr.responseText;
			callback(data);
		}
	}
}

function $(idName) {
	return document.getElementById(idName);
}