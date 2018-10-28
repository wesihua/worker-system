$(function(){
	// 超时跳到登录
	$(document).ajaxSuccess(function(event, xhr, settings){
		if(xhr.responseJSON.code == 1002){
			top.location.href="/";
		}
	});
	
	// 统计信息
	//statisticsByState();
	
//    $(document).bind("ajaxSend", function () {
//		parent.$("#loading").show();
//    }).bind("ajaxComplete", function () {
//    	parent.$("#loading").hide();
//    });
    
	// 进入页面自动查询
	query(1);
	//按钮事件绑定
	$("#query-bottom").click(function(){
		query(1);
	});
	

});

/**
 * 查询
 * @returns
 */
function query(currentPage) {
	var quertJson = {};
	quertJson.companyName = $("#companyName").val();
	quertJson.demandNumber = $("#demandNo").val();
	quertJson.createBeginTime = $("#beginTime").val();
	quertJson.createEndTime = $("#endTime").val();
	quertJson.state = $("input:hidden[name='state']").val();

	parent.$("#loading").show();
	
			$.ajax({
				url : "/demand/queryDemand",
				type : "post",
				data : {
					demandQueryJson : JSON.stringify(quertJson),
					pageNumber : currentPage
				},
				dataType : "json",
				success : function(data) {

					parent.$("#loading").hide();

					if (data.code == 1) {
						var firmArr = data.data.data;
						var tableContent = "";

						if (firmArr.length > 0) {

							tableContent += "<tr>"
								+ "	<th>单号</th>"
								+ "	<th>创建日期</th>"
								+ "	<th>企业客户</th>"
								+ "	<th>状态</th>" 
								+ "	<th>创建人员</th>"
								+ "	<th width='120'>备注说明</th>"
								+ "	<th width='150'>操作</th>" + "</tr>";

							for (var i = 0; i < firmArr.length; i++) {
								var firm = firmArr[i];
								tableContent += "<tr>" 
									+ "	<td>" + firm.demandNumber + "</td>"
									+ "	<td>" + firm.createTime + "</td>"
									+ "	<td>" + firm.companyName	+ "</td>"
									+ "	<td>" + firm.stateName + "</td>"
									+ "	<td>" + firm.createUserName + "</td>"
									+ "	<td width='120'>" + firm.description+ "</td>"
									+ "   <td><span class=\"des\" onClick=\"updateDemand("+ firm.id+ ")\">编辑</span><span class=\"des\" onClick=\"demandDetail("+ firm.id+ ")\">详情</span><span class=\"jiedan\" onClick=\"undertakeDemand("+ firm.id + ")\">接单</span></td>"
								+ "</tr>";
							}
						}

						$("table").empty().append(tableContent);
						// 初始化时间控件
//						$('.J-yearMonthPicker-single').datePicker({
//							format : 'YYYY-MM-DD'
//						});
						
						$('.J-datepicker-range').datePicker({
							format : 'YYYY-MM-DD HH:mm',
					        hasShortcut: true,
					        isRange: true,
					        shortcutOptions: [{
					          name: '昨天',
					          day: '-1,-1',
					          time: '00:00,23:59'
					        },{
					          name: '最近一周',
					          day: '-7,0',
					          time:'00:00,'
					        }, {
					          name: '最近一个月',
					          day: '-30,0',
					          time: '00:00,'
					        }, {
					          name: '最近三个月',
					          day: '-90, 0',
					          time: '00:00,'
					        }]
					      });
						$("#totalCount").text(data.data.totalCount + "个结果");
						$("#pagination1").pagination({
							currentPage : data.data.pageNumber,
							totalPage : data.data.pageCount,
							callback : function(current) {
								query(current);
							}
						});
					}
				},
				error : function() {
					parent.$("#loading").hide();
				}
			});
}

/**
 * 需求单详情
 * @param demandId
 * @returns
 */
function demandDetail(demandId){
	window.location.href= "/signing/demandDetail?source=0&demandId=" + demandId ;
}

/**
 * 编辑
 */
function updateDemand(demandId){
	window.location.href= "/signing/addDemand?demandId=" + demandId ;
}

function addDemand(){
	window.location.href= "/signing/addDemand";
}

/**
 * 接单
 * @param demandId
 * @returns
 */
function undertakeDemand(demandId){
	var b = confirm("确认接单？");
	if(b){
		comfirmUndertake(demandId)
	}
}

/**
 * 接单
 * @param demandId
 * @returns
 */
function comfirmUndertake(demandId){
	$.ajax({
		url : "/demand/undertake",
		type : "get",
		dataType : "json",
		data:{demandId:demandId},
		success : function(data) {
			if (data.code == 1) {
				statisticsByState();
				query(1);
				alert("接单成功！");
			}
		},
		error: function(data){
			alert(data.msg);
		}
	});
}


/**
 * 打开弹窗
 * @returns
 */
function openDialog(id){
	var content = $("#"+id).html();
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



