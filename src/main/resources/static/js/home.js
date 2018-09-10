$(function(){
	$(document).ajaxSuccess(function(event, xhr, settings){
		if(xhr.responseJSON.code == 1002){
			location.href="/";
		}
	});
	// 先加载用户菜单
	loadMenu();
	// 加载用户信息
	userInfo();
	
	// alert提示框click事件
	$("#public-bottom2").click(function(){
		$("#public-box2").hide();
	});
	$("#alert_close").click(function(){
		$("#public-box2").hide();
	});
	// confirm提示框click事件
	$("#public-bottom1").click(function(){
		$("#public-box1").hide();
	});
	$("#public-bottom2").click(function(){
		$("#public-box1").hide();
	});
	$("#confirm_close").click(function(){
		$("#public-box1").hide();
	});
	// 退出登录
	$("#logout").click(function(){
		logout();
	});
});

/**
 * 加载当前登录用户的菜单
 * @returns
 */
function loadMenu(){
	$.ajax({
		url:"/user/queryUserMenu",
		type:"get",
		dataType:"json",
		//headers:{Authorization:token},
		success:function(data){
			if(data.code == 1){
				var menuList = data.data;
				var menuHtml = "<ul><li onClick=\"loadPage('/home')\"><div class=\"f on\">首页</div></li>";
				for(var i=0; i<menuList.length; i++){
					var menu = menuList[i];
					if(menu.children.length > 0){
						menuHtml+="<li><div class=\"f\">"+menu.name+"</div>";
						for(var j=0; j<menu.children.length; j++){
							var subMenu = menu.children[j];
							menuHtml+="<div class=\"s\" onClick=\"loadPage('"+subMenu.path+"')\">"+
									"	<div class=\"s-n\">"+subMenu.name+"</div>"+
									"</div>";
						}
						menuHtml+="</li>";
					}
					else{
						if(menu.name != "首页"){
							menuHtml+="<ul><li onClick=\"loadPage('"+menu.path+"')\"><div class=\"f\">"+menu.name+"</div></li>";
						}
					}
				}
				menuHtml+="</ul>";
				$("#nav").html(menuHtml);
			}
		},
		error: function(data){
			alert(data.msg);
		}
	});
}
/**
 * 加载页面
 * @param url
 * @returns
 */
function loadPage(url){
	$("#myframe").attr("src",url);
}

/**
 * 加载登录用户信息
 * @returns
 */
function userInfo(){
	$.ajax({
		url:"/user/queryUserInfo",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				$("#userName").text(data.data.userName);
			}
		},
		error: function(data){
			alert(data.msg);
		}
	});
}
/**
 * 退出登录
 * @returns
 */
function logout(){
	$.ajax({
		url:"/account/logout",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				location.href="/";
			}
			else{
				alert("退出登录失败！");
			}
		},
		error: function(data){
			alert(data.msg);
		}
	});
}
function handleFrame(){
	var userAgent = navigator.userAgent;
	var myframe = parent.document.getElementById("myframe");
	var subdoc = myframe.contentDocument || myframe.contentWindow.document;
	var subbody = subdoc.body;
	if(subbody.innerText.indexOf("1002") > 0 && subbody.innerText.indexOf("token已过期，请重新登录") > 0){
		location.href="/";
	}
	else{
		initIframeHeight(450);
	}
}
/**
 * iframe高度自适应
 * @param height
 * @returns
 */
function initIframeHeight(height) {
	var userAgent = navigator.userAgent;
	var myframe = parent.document.getElementById("myframe");
	var subdoc = myframe.contentDocument || myframe.contentWindow.document;
	var subbody = subdoc.body;
	var realHeight = subbody.clientHeight+20;// = getIframePageHeight("myframe");
	// 谷歌浏览器特殊处理
	/*if (userAgent.indexOf("Chrome") > -1) {
		realHeight = subdoc.documentElement.scrollHeight;
	} else {
		realHeight = subbody.scrollHeight;
	}*/
	if (realHeight < height) {
		$(myframe).height(height);
	} else {
		$(myframe).height(realHeight);
	}
}
/**
 * alert提示框
 */
function alert(msg){
	if(!msg){
		msg = "请加入提示信息！";
	}
	$("#alert_msg").text(msg);
	$("#public-box2").show();
}

/**
 * confirm提示框
 */
function confirm(msg,func){
	if(!msg){
		msg = "请加入提示信息！";
	}
	$("#confirm_msg").text(msg);
	$("#public-box1").show();
	$(".confirm_sure").click(func);
}
/**
 * 关闭confirm
 * @returns
 */
function closeConfirm(){
	$("#public-box1").hide();
}
