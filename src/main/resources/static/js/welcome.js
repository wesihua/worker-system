initIframeHeight(50);
function initIframeHeight(height) {
	var userAgent = navigator.userAgent;
	var myframe = parent.document.getElementById("myframe");
	var subdoc = myframe.contentDocument || myframe.contentWindow.document;
	var subbody = subdoc.body;
	var realHeight;
	// 谷歌浏览器特殊处理
	if (userAgent.indexOf("Chrome") > -1) {
		realHeight = subdoc.documentElement.scrollHeight;
	} else {
		realHeight = subbody.scrollHeight;
	}
	if (realHeight < height) {
		$(myframe).height(height);
	} else {
		$(myframe).height(realHeight);
	}
}