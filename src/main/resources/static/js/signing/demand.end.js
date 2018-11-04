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

});

/**
 * 查询
 * @returns
 */
function query(currentPage) {
	var quertJson = {};
	quertJson.companyName = $("#companyName").val();
	quertJson.demandNumber = $("#demandNo").val();
	quertJson.closeBeginTime = $("#beginTime").val();
	quertJson.closeEndTime = $("#endTime").val();
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

					tableContent+= "<tr>"+
									"	<th>企业客户</th>"+
									"	<th>招聘编号</th>"+
									"	<th>状态</th>"+
									"	<th>接单人</th>"+
									"	<th>接单时间</th>"+
									"	<th>关单人</th>"+
									"	<th>关单时间</th>"+
									"	<th>关单原因</th>"+
									"	<th>创建人</th>"+
									"	<th>创建时间</th>"+
									"	<th width='80'>操作</th>"+
									"</tr>";
					for(var i=0; i<firmArr.length; i++){
						var firm = firmArr[i];
						tableContent+=  "<tr>"+
										"	<td>"+firm.companyName+"</td>"+
										"	<td>"+firm.demandNumber+"</td>"+
										"	<td>"+firm.stateName+"</td>"+
										"	<td>"+firm.undertakeUserName+"</td>"+
										"	<td>"+(firm.undertakeTime == null ? "" : firm.undertakeTime)+"</td>"+
										"	<td>"+firm.closeUserName+"</td>"+
										"	<td>"+firm.closeTime+"</td>"+
										"	<td>"+firm.closeReason+"</td>"+
										"	<td>"+firm.createUserName+"</td>"+
										"	<td>"+firm.createTime+"</td>"+
										"   <td><span class=\"des\" onClick=\"demandDetail("+firm.id+")\">详情</span></td>"+
										"</tr>";
					}
				
				}

				$("table").empty().append(tableContent);
				
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
	window.location.href="/demand/detailWithOrder?demandId="+demandId;
}


