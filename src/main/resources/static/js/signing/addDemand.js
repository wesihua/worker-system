$(function(){
	// 超时跳到登录
	$(document).ajaxSuccess(function(event, xhr, settings){
		if(xhr.responseJSON.code == 1002){
			top.location.href="/";
		}
	});
	//按钮事件绑定
	$("#public-bottom2").click(function(){
		addDemand();
	});
});


function addDemand(){
	alert(1);
}

function deleteJob(obj){
	var thisObj=$(obj);
	thisObj.parent().parent().remove();
}

function addJob(){
	openDialog("add-job-dialog");
	parent.$('.J-yearMonthPicker-single').datePicker({
        format: 'YYYY-MM-DD'
    });
	
	parent.$(".add-job-type-content").click(function(){
		top.closeDialog();
		
		var jobTypeId = parent.$("#jobTypeId").val();
		var workerCount = parent.$("#workerCount").val();
		var salary = parent.$("#salary").val();
		var requireTime = parent.$("#requireTime").val();
		var workArea = parent.$("#workArea").val();
		var requirement = parent.$("#requirement").val();
		var content = "<tr>"+
					  "  <td id='jobTypeId'>"+jobTypeId+"</td>"+
					  "  <td id='workerCount'>"+workerCount+"</td>"+
					  "  <td id='salary'>"+salary+"</td>"+
					  "  <td id='requireTime'>"+requireTime+"</td>"+
					  "  <td id='workArea'>"+workArea+"</td>"+
					  "  <td id='requirement'>"+requirement+"</td>"+
					  "  <td><span class=\"des\" onclick=\"editJob(this)\">编辑</span><span class=\"delete\" onclick=\"deleteJob(this)\">移除</span></td>"+
					  "</tr>";
		$("table").append(content);
	});
}

function editJob(obj){
	openDialog("add-job-dialog");
	parent.$('.J-yearMonthPicker-single').datePicker({
        format: 'YYYY-MM-DD'
    });
	
	var trobj = $(obj).parent().parent();
	var jobTypeId =  trobj.children("#jobTypeId").html();
	var workerCount =  trobj.children("#workerCount").html();
	var salary =  trobj.children("#salary").html();
	var requireTime =  trobj.children("#requireTime").html();
	var workArea =  trobj.children("#workArea").html();
	var requirement =  trobj.children("#requirement").html();
	parent.$("#jobTypeId").val(jobTypeId);
	parent.$("#workerCount").val(workerCount);
	parent.$("#salary").val(salary);
	parent.$("#requireTime").val(requireTime);
	parent.$("#workArea").val(workArea);
	parent.$("#requirement").val(requirement);
	
	parent.$(".add-job-type-content").click(function(){
		
		var jobTypeId_ = parent.$("#jobTypeId").val();
		var workerCount_ = parent.$("#workerCount").val();
		var salary_ = parent.$("#salary").val();
		var requireTime_ = parent.$("#requireTime").val();
		var workArea_ = parent.$("#workArea").val();
		var requirement_ = parent.$("#requirement").val();
		top.closeDialog();
		
		trobj.children("#jobTypeId").html(jobTypeId_);
		trobj.children("#workerCount").html(workerCount_);
		trobj.children("#salary").html(salary_);
		trobj.children("#requireTime").html(requireTime_);
		trobj.children("#workArea").html(workArea_);
		trobj.children("#requirement").html(requirement_);
		
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

function addDemand(){

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