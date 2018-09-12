$(function(){
	query();
	$("#public-bottom2").click(function(){
		query();
	});
	$("#add-role").click(function(){
		openDialog();
	});
});

function query(){
	var roleName = $("#roleName").val();
	var currentPage = $("#currentPage").text();
	$.ajax({
		url:"/role/list",
		type:"get",
		data:{name:roleName,pageNumber:currentPage},
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var roleArr = data.data.data;
				var tableContent="";
				for(var i=0; i<roleArr.length; i++){
					var role = roleArr[i];
					tableContent+=  "<tr>"+
									"	<td>"+role.name+"</td>"+
									"	<td>"+role.userCount+"</td>"+
									"	<td>"+role.createTime+"</td>"+
									"	<td>"+
									"		<span class=\"des\">编辑名称</span> <span class=\"des\">成员管理</span>"+
									"		<span class=\"des\">权限管理</span> <span class=\"des\">移除</span>"+
									"	</td>"+
									"</tr>";
				}
				$("tbody").empty().append(tableContent);
				$("#totalCount").text(data.data.totalCount+"个结果");
				$("#pagination1").pagination({
					currentPage: data.data.pageNumber,
					totalPage: data.data.pageCount,
					callback: function(current) {
						$("#currentPage").text(current);
						query();
					}
				});
			}
		}
	});
}

function openDialog(){
	var content = $("#dialog-content").html();
	parent.$("#dialog").html(content);
	parent.$("#dialog").show();
	// 因为弹窗页面是重新渲染到parent页面的。所以事件绑定只能在渲染之后。否则不起作用！
	parent.$(".cancel-dialog").click(function(){
		parent.$("#dialog").hide();
	});
	parent.$("#close-dialog").click(function(){
		parent.$("#dialog").hide();
	});
}