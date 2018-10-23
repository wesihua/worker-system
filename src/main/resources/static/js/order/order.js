$(function(){
	// 超时跳到登录
	$(document).ajaxSuccess(function(event, xhr, settings){
		if(xhr.responseJSON.code == 1002){
			top.location.href="/";
		}
	});
	$(document).bind("ajaxSend", function () {
		parent.$("#loading").show();
    }).bind("ajaxComplete", function () {
    	parent.$("#loading").hide();
    });
	// 进入页面自动查询
	query(1);
	//按钮事件绑定
	$("#query-bottom").click(function(){
		query(1);
	});
	$("#download").click(function(){
		var companyName = $("#companyName").val();
		window.open("/company/export?companyName="+companyName);
	});
});

/**
 * 查询
 * @returns
 */
function query(currentPage){
	var companyName = $("#companyName").val();
	var orderNumber = $("#orderNumber").val();
	var createUserName = $("#createUserName").val();
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();;
	$.ajax({
		url:"/order/list",
		type:"get",
		data:{companyName:companyName,orderNumber:orderNumber,
			createUserName:createUserName,beginTime:beginTime,
			endTime:endTime,
			pageNumber:currentPage},
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var orderArr = data.data.data;
				var tableContent="";
				for(var i=0; i<orderArr.length; i++){
					var order = orderArr[i];
					tableContent+=  "<tr>"+
									"	<td>"+order.companyName+"</td>"+
									"	<td>"+(order.orderNumber == null ? "" : order.orderNumber)+"</td>"+
									"	<td>"+order.workerCount+"</td>"+
									"	<td>"+order.totalIncome+"</td>"+
									"	<td>"+(order.customer == null ? "" : order.customer)+"</td>"+
									"	<td>"+order.createUserName+"</td>"+
									"	<td>"+(order.description == null ? "" : order.description)+"</td>"+
									"	<td>"+order.createTime+"</td>"+
									"	<td><span class=\"des\" onClick=\"openDialog("+order.id+")\">签订人员</span></td>"+
									"</tr>";
				}
				$("tbody").empty().append(tableContent);
				$("#totalCount").text(data.data.totalCount+"个结果");
				$("#pagination1").pagination({
					currentPage: data.data.pageNumber,
					totalPage: data.data.pageCount,
					callback: function(current) {
						query(current);
					}
				});
			}
		}
	});
}

/**
 * 打开弹窗
 * @returns
 */
function openDialog(id){
	// 先查询信息
	$.ajax({
		url:"/order/queryByOrderId",
		type:"get",
		data:{orderId:id},
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var orderArr = data.data;
				var tableContent="";
				for(var i=0; i<orderArr.length; i++){
					var order = orderArr[i];
					tableContent+=  "<tr>"+
									"	<td>"+(order.name == null ? "" : order.name)+"</td>"+
									"	<td>"+(order.idcard == null ? "" : order.idcard)+"</td>"+
									"	<td>"+(order.jobTypeName == null ? "" : order.jobTypeName)+"</td>"+
									"	<td>"+(order.businessIncome == null ? "0.0" : order.businessIncome)+"</td>"+
									"	<td>"+(order.signSalary == null ? "" : order.signSalary)+"</td>"+
									"	<td>"+(order.createUserName == null ? "" : order.createUserName)+"</td>"+
									"	<td>"+(order.description == null ? "" : order.description)+"</td>"+
									"	<td>"+order.createTime+"</td>"+
									"</tr>";
				}
				$("#detail-order-dialog").find("tbody").empty().append(tableContent);
			}
		}
	});
	
	var content = $("#detail-order-dialog").html();
	top.$("#dialog").html(content);
	top.$("#dialog").show();
	// 因为弹窗页面是重新渲染到top页面的。所以事件绑定只能在渲染之后。否则不起作用！
	top.$(".cancel-dialog").click(function(){
		top.closeDialog();
	});
	top.$("#close-dialog").click(function(){
		top.closeDialog();
	});
}

