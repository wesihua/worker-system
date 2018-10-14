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
				
				if(state == 1){
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
										"	<td><span class=\"des\" onClick=\"workerList("+firm.id+")\">分配用工</span></td>"+
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
										"	<td><span class=\"des\" onClick=\"demandDetail("+firm.id+")\">查看签约列表</span></td>"+
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
										"	<td><span class=\"des\" onClick=\"demandDetail("+firm.id+")\">查看签约列表</span></td>"+
										"</tr>";
					}
				}
				$("table").empty().append(tableContent);
//				$("#totalCount").text(data.data.totalCount+"个结果");
//				$("#pagination1").pagination({
//					currentPage: data.data.pageNumber,
//					totalPage: data.data.pageCount,
//					callback: function(current) {
//						query(current);
//					}
//				});
			}
		}
	});
}

/**
 * 需求单详情
 * @param demandId
 * @returns
 */
function workerList(jobTypeId){
	window.location.href = "/signing/workerList?jobTypeId=" + jobTypeId;
}

/**
 * 接单
 * @param demandId
 * @returns
 */
function undertakeDemand(demandId){
	alert("undertakeDemand" + demandId);
}

/**
 * 关单
 * @param demandId
 * @returns
 */
function closeDemand(demandId){
	alert("closeDemand" + demandId);
}


/**
 * 签约
 * @param demandId
 * @returns
 */
function signings(demandId){
	alert("signings" + demandId);
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

function addCompany(){
	openDialog("add-company-dialog");
	top.$(".add-company").click(function(){
		var companyName = top.$("#companyName").val();
		var industry = parent.$("#industry").val();
		var contactName = parent.$("#contactName").val();
		var contactPhone = parent.$("#contactPhone").val();
		var address = parent.$("#address").val();
		var description = parent.$("#description").val();
		
		if(companyName == null || companyName.length == 0){
			alert("企业名称不能为空！");
			return false;
		}
		if(companyName.length > 100){
			alert("企业名称长度不能超过100个字！");
			return false;
		}
		if(!contactName){
			alert("联系人不能为空！");
			return false;
		}
		if(contactName.length > 30){
			alert("联系人长度不能超过30个字！");
			return false;
		}
		if(!contactPhone){
			alert("联系电话不能为空！");
			return false;
		}
		if(contactPhone.length > 20){
			alert("联系电话长度不能超过20个字！");
			return false;
		}
		$.ajax({
			url:"/company/saveCompany",
			type:"get",
			dataType:"json",
			data:{name:companyName,industry:industry,address:address,contactName:contactName,
				contactPhone:contactPhone,description:description},
			success:function(data){
				if(data.code == 1){
					top.closeDialog();
					alert("新增企业成功！");
					query(1);
				}
				else{
					alert("新增企业失败！原因："+data.msg);
				}
			}
		});
	});
}

function updateCompany(companyId){
	$.ajax({
		url:"/company/queryDetail",
		type:"get",
		data:{companyId:companyId},
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var firm = data.data;
				// 打开页面
				openDialog("add-company-dialog");
				parent.$("#companyName").val(firm.name);
				parent.$("#industry").val(firm.industry);
				parent.$("#address").val(firm.address);
				parent.$("#contactName").val(firm.contactName);
				parent.$("#contactPhone").val(firm.contactPhone);
				parent.$("#description").val(firm.description);
				
				top.$(".add-company").click(function(){
					var companyId = firm.id;
					var companyName = top.$("#companyName").val();
					var industry = parent.$("#industry").val();
					var contactName = parent.$("#contactName").val();
					var contactPhone = parent.$("#contactPhone").val();
					var address = parent.$("#address").val();
					var description = parent.$("#description").val();
					
					if(companyName == null || companyName.length == 0){
						alert("企业名称不能为空！");
						return false;
					}
					if(companyName.length > 100){
						alert("企业名称长度不能超过100个字！");
						return false;
					}
					if(!contactName){
						alert("联系人不能为空！");
						return false;
					}
					if(contactName.length > 30){
						alert("联系人长度不能超过30个字！");
						return false;
					}
					if(!contactPhone){
						alert("联系电话不能为空！");
						return false;
					}
					if(contactPhone.length > 20){
						alert("联系电话长度不能超过20个字！");
						return false;
					}
					$.ajax({
						url:"/company/saveCompany",
						type:"get",
						dataType:"json",
						data:{id:companyId,name:companyName,industry:industry,address:address,contactName:contactName,
							contactPhone:contactPhone,description:description},
						success:function(data){
							if(data.code == 1){
								top.closeDialog();
								alert("更新企业成功！");
								query(1);
							}
							else{
								alert("更新企业失败！原因："+data.msg);
							}
						}
					});
				});
				
			}
		}
	});
	
}

function deleteCompany(companyId){
	var b = confirm("是否删除该企业？");
	if(b){
		$.ajax({
			url:"/company/deleteCompany",
			type:"get",
			dataType:"json",
			data:{companyId:companyId},
			success:function(data){
				if(data.code == 1){
					alert("删除企业成功！");
					query(1);
				}
				else{
					alert("删除企业失败！原因："+data.msg);
				}
			}
		});
	}
}