$(function(){
	$(".submit").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		if(username == "" || username.length == 0){
			alert("请输入用户名！");
			return false;
		}
		if(password == "" || password.length == 0){
			alert("请输入密码！");
			return false;
		}
		$.ajax({
			url:"/account/login",
			type:"get",
			dataType:"json",
			data:{userName:username,password:password,flag:0},
			success:function(data){
				if(data.code == 0){
					alert(data.msg);
				}
				else{
					location.href="./home.html"
				}
			},
			error: function(data){
				alert(data.msg);
			}
		});
	});
	$("#public-bottom2").click(function(){
		$("#public-box2").hide();
	});
	$("#alert_close").click(function(){
		$("#public-box2").hide();
	});
});
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