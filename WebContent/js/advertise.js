function queryAd() {
	ajax('get','RequestHandler?type=queryAd', null, function(ad){
		//对返回的广告进行处理
	});
}