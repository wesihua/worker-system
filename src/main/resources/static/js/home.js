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
	// 设置菜单的高度
	$("#nav").height($(window).height()-110);
	// alert提示框click事件
	$("#alert_close").click(function(){
		closeAlert();
	});
	// confirm提示框click事件
	$("#public-bottom1").click(function(){
		closeConfirm()
	});
	$("#confirm_close").click(function(){
		closeConfirm()
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
				var menuHtml = "<ul><li onClick=\"loadPage('/welcome.html')\"><div class=\"f on\" id=\"sy\">首页</div></li>";
				for(var i=0; i<menuList.length; i++){
					var menu = menuList[i];
					if(menu.children.length > 0){
						menuHtml+="<li><div class=\"f\">"+menu.name+"<span class='fa fa-angle-right'>"+"</span>"+"</div>";
						for(var j=0; j<menu.children.length; j++){
							var subMenu = menu.children[j];
							menuHtml+="<div class=\"s\" style=\"display:none;\" onClick=\"loadPage('"+subMenu.path+"')\">"+
									"	<div class=\"s-n\">"+subMenu.name+"</div>"+
									"</div>";
						}
						menuHtml+="</li>";
					}
					else{
						if(menu.name != "首页"){
							if(menu.path){
								menuHtml+="<ul><li onClick=\"loadPage('"+menu.path+"')\"><div class=\"f\">"+menu.name+"</div></li>";
							}
							else{
								menuHtml+="<ul><li><div class=\"f\">"+menu.name+"</div></li>";
							}
						}
					}
				}
				menuHtml+="</ul>";
				$("#nav").html(menuHtml);
				
				// 绑定菜单的点击事件
				$("#nav").children("ul").find("div[class=s]").click(function(){
					$(this).children("div").addClass("on");
					$(this).siblings().children("div").removeClass("on");
					$("#sy").removeClass("on");
				});
				$("#sy").click(function(){
					$(this).addClass("on");
					$(this).parents("li").siblings().find("div[class=s]").slideUp(300);
					$(this).parents("li").siblings().find("div[class=s]").children("div").removeClass("on");
				});
				$("#nav").children("ul").find("div[class=f]").click(function(){
					$(this).siblings().slideToggle(300);
					if($(this).children('span').hasClass('fa-angle-right')){
						$(this).children('span').removeClass('fa-angle-right');
						$(this).children('span').addClass('fa-angle-down');
					}else{
						$(this).children('span').removeClass('fa-angle-down');
						$(this).children('span').addClass('fa-angle-right');
					}
					$(this).parents("li").siblings().find("div[class=s]").slideUp(300);
					$(this).parents("li").siblings().find("div[class=s]").children("div").removeClass("on");
				});
			}
		}
	});
}
/**
 * 加载页面
 * @param url
 * @param flag 0:父页面，1:子页面
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
				$("#userName").text(data.data.realName);
			}
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
		var height = $(window).height() - 110;
		initIframeHeight(height);
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
	var realHeight = height;// = getIframePageHeight("myframe");
	//alert(realHeight);
	// 谷歌浏览器特殊处理
	/*
	if (userAgent.indexOf("Chrome") > -1) {
		realHeight = $(subdoc).height();
	} else {
		realHeight = $(subbody).height();
	}
	*/
	
	if (realHeight < height) {
		$(myframe).height(height);
	} else {
		$(myframe).height(realHeight);
	}
}
/**
 * alert提示框
 */
function alert(msg,f){
	if(!msg){
		msg = "网络发生异常！";
	}
	$("#alert_msg").text(msg);
	$("#public-box2").show();
	if(f){
		$(".alert_sure").click(f);
	}
	else{
		$(".alert_sure").click(function(){
			$("#public-box2").hide();
		});
	}
}

/**
 * confirm提示框
 */
function confirm(msg,f){
	if(!msg){
		msg = "网络发生异常！";
	}
	$("#confirm_msg").text(msg);
	$("#public-box1").show();
	$(".confirm_sure").click(f);
}
/**
 * 关闭confirm
 * @returns
 */
function closeConfirm(){
	$("#public-box1").hide();
}
function closeAlert(){
	$("#public-box2").hide();
}
/**
 * 关闭弹窗
 * @returns
 */
function closeDialog(){
	$("#dialog").hide();
}