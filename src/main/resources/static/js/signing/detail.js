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
	queryDetail();
	
});

/**
 * 查询
 * @returns
 */
function queryDetail(){
	
	var demandId = $("input:hidden[name='demandId']").val();
	var source = $("input:hidden[name='source']").val();
	$.ajax({
		url:"/demand/demandDetail",
		type:"get",
		data:{demandId:demandId},
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var state = data.data.state;
				var tableContent="";
				var firmArr = data.data.demandJobList;
				$(".undertokeTime").text(data.data.undertakeTime);
				$(".totalIncome").text(data.data.totalIncome);
				$(".undertokeUserName").text(data.data.undertakeUserName);
				$(".companyName").text(data.data.companyName);
				$(".description").text(data.data.description);
				
				if(state == 2 || state == 3){
					$(".undertokeTime").parent().show();
					$(".totalIncome").parent().show();
					$(".undertokeUserName").parent().show();
				}
				
				if(state == 1 && source == 1){
					$(".signing-botton").show();
				}
				
				if(state == 0){
					tableContent+= "<tr>"+
									"	<th>用工工种</th>"+
									"	<th>用工人数</th>"+
									"	<th>到岗日期</th>"+
									"	<th>月工资（元）</th>"+
									"	<th>工作地区</th>"+
									"	<th>用工要求</th>"+
									"</tr>";
					for(var i=0; i<firmArr.length; i++){
						var firm = firmArr[i];
						tableContent+=  "<tr>"+
										"	<td>"+firm.jobTypeName+"</td>"+
										"	<td>"+firm.workerCount+"</td>"+
										"	<td>"+firm.requireTime+"</td>"+
										"	<td>"+firm.salary+"</td>"+
										"	<td>"+firm.workAreaName +"</td>"+
										"	<td>"+firm.requirement+"</td>"+
										"</tr>";
					}
				}
				
				if(state == 1 && source == 0){
					tableContent+= "<tr>"+
									"	<th>用工工种</th>"+
									"	<th>到岗日期</th>"+
									"	<th>月工资（元）</th>"+
									"	<th>工作地区</th>"+
									"	<th>用工要求</th>"+
									"	<th>用工人数</th>"+
									"	<th>已分配人数</th>"+
									"</tr>";
					for(var i=0; i<firmArr.length; i++){
						var firm = firmArr[i];
						tableContent+=  "<tr>"+
										"	<td>"+firm.jobTypeName+"</td>"+
										"	<td>"+firm.requireTime+"</td>"+
										"	<td>"+firm.salary+"</td>"+
										"	<td>"+firm.workAreaName +"</td>"+
										"	<td>"+firm.requirement+"</td>"+
										"	<td>"+firm.workerCount+"</td>"+
										"	<td>"+firm.assignCount+"</td>"+
										"</tr>";
					}
				}
				
				if(state == 1 && source == 1){
					tableContent+= "<tr>"+
									"	<th>用工工种</th>"+
									"	<th>到岗日期</th>"+
									"	<th>月工资（元）</th>"+
									"	<th>工作地区</th>"+
									"	<th>用工要求</th>"+
									"	<th>用工人数</th>"+
									"	<th>已分配人数</th>"+
									"	<th>操作</th>"+
									"</tr>";
					for(var i=0; i<firmArr.length; i++){
						var firm = firmArr[i];
						tableContent+=  "<tr>"+
										"	<td>"+firm.jobTypeName+"</td>"+
										"	<td>"+firm.requireTime+"</td>"+
										"	<td>"+firm.salary+"</td>"+
										"	<td>"+firm.workAreaName +"</td>"+
										"	<td>"+firm.requirement+"</td>"+
										"	<td>"+firm.workerCount+"</td>"+
										"	<td>"+firm.assignCount+"</td>"+
										"	<td><span class=\"des\" onClick=\"addWorker("+firm.id+")\">分配用工</span></td>"+
										"</tr>";
					}
				}
				
				if(state == 2){
					tableContent+= "<tr>"+
									"	<th>用工工种</th>"+
									"	<th>到岗日期</th>"+
									"	<th>工作地区</th>"+
									"	<th>用工要求</th>"+
									"	<th>用工人数</th>"+
									"	<th>签约人数</th>"+
									"	<th>操作</th>"+
									"</tr>";
					for(var i=0; i<firmArr.length; i++){
						var firm = firmArr[i];
						tableContent+=  "<tr>"+
										"	<td>"+firm.jobTypeName+"</td>"+
										"	<td>"+firm.requireTime+"</td>"+
										"	<td>"+firm.workAreaName +"</td>"+
										"	<td>"+firm.requirement+"</td>"+
										"	<td>"+firm.workerCount+"</td>"+
										"	<td>"+firm.signingCount+"</td>"+
										"	<td><span class=\"des\" onClick=\"workerList("+firm.id+")\">查看签约列表</span></td>"+
										"</tr>";
					}
				}
				
				if(state == 3){
					tableContent+= "<tr>"+
									"	<th>用工工种</th>"+
									"	<th>到岗日期</th>"+
									"	<th>月工资（元）</th>"+
									"	<th>工作地区</th>"+
									"	<th>用工要求</th>"+
									"	<th>用工人数</th>"+
									"	<th>签约人数</th>"+
									"	<th>操作</th>"+
									"</tr>";
					for(var i=0; i<firmArr.length; i++){
						var firm = firmArr[i];
						tableContent+=  "<tr>"+
										"	<td>"+firm.jobTypeName+"</td>"+
										"	<td>"+firm.requireTime+"</td>"+
										"	<td>"+firm.salary+"</td>"+
										"	<td>"+firm.workAreaName +"</td>"+
										"	<td>"+firm.requirement+"</td>"+
										"	<td>"+firm.workerCount+"</td>"+
										"	<td>"+firm.signingCount+"</td>"+
										"	<td><span class=\"des\" onClick=\"workerList("+firm.id+")\">查看签约列表</span></td>"+
										"</tr>";
					}
				}
				$("table").empty().append(tableContent);
			}
		}
	});
}

/**
 * 需求单详情
 * @param demandId
 * @returns
 */
function addWorker(jobTypeId){
	window.location.href = "/signing/workerList?source=1&jobTypeId=" + jobTypeId;
}

/**
 * 需求单详情
 * @param demandId
 * @returns
 */
function workerList(jobTypeId){
	window.location.href = "/signing/workerList?source=0&jobTypeId=" + jobTypeId;
}

/**
 * 签约
 * @param demandId
 * @returns
 */
function signing(){
	var demandId = $("input[name=demandId]").val();
	var b = confirm("确认签约？");
	if(b){
		$.ajax({
			url:"/demand/signing",
			type:"get",
			dataType:"json",
			data:{demandId:demandId},
			success:function(data){
				if(data.code == 1){
					alert("签约成功！");
					queryDetail();
				}
				else{
					alert("签约失败！原因："+data.msg);
				}
			}
		});
	}

}

/**
 * 状态栏点击事件
 * @param obj
 * @returns
 */
function stateChange(obj){
	// 修改样式
	var thisObj=$(obj);
	thisObj.addClass("on");
	thisObj.siblings().each(function(){
	    $(this).removeClass("on");
	  });
	// 修改state值
    var state=thisObj.attr("state"); 
	$("input:hidden[name='state']").val(state);
	query(1);
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


