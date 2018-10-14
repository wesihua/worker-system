$(function(){
	// 超时跳到登录
	$(document).ajaxSuccess(function(event, xhr, settings){
		if(xhr.responseJSON.code == 1002){
			top.location.href="/";
		}
	});
	//$(document).bind("ajaxSend", function () {
	//	parent.$("#loading").show();
    //}).bind("ajaxComplete", function () {
    //	parent.$("#loading").hide();
    //});
	//按钮事件绑定
//	$("#add-demand-bottom").click(function(){
//		addDemand();
//	});
	
	//监听公司名称变化
	$('#companyName').bind('input propertychange', function() {
		queryCompany();
	});
	
});

function deleteJob(obj){
	var thisObj=$(obj);
	thisObj.parent().parent().remove();
}

// 查工种
function queryJobType(jobTypeId){
	var name = parent.$("#jobTypeName").val();

	$.ajax({
		url:"/jobType/queryChildJobType",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var dics = data.data;
				var content = "<option value=\"\">---请选择---</option>";
				for(var i=0; i<dics.length; i++){
					var dic = dics[i];
					
					if(jobTypeId != undefined && jobTypeId == dic.id){
						content += "<option value=\""+dic.id+"\" selected=\"selected\">"+dic.name+"</option>";
					} else {
						content += "<option value=\""+dic.id+"\">"+dic.name+"</option>";
					}
				}
				parent.$("#jobType").empty().html(content);
			}
		}
	});
}

// 查公司
function queryCompany(){
	var name = $("#companyName").val();

	$.ajax({
		url:"/company/queryByName",
		type:"get",
		data:{name:name},
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var firmArr = data.data;
				
				var divContent="";
				if(firmArr.length > 0){
				
					for(var i=0; i<firmArr.length; i++){
						var company = firmArr[i];
						divContent+=  "<div class='li' value_id= "+company.id+" value_name= "+company.name+"  onclick='changCompany(this)'>"+company.name +"</div>";
					}
				}
				$("#companyList").show();
				$("#companyList").empty().append(divContent);
			}
		}
	});
}

function changCompany(obj){
	var thisObj = $(obj);
	$("#companyName").val(thisObj.attr("value_name"));
	$("#companyId").val(thisObj.attr("value_id"));
	$("#companyList").hide();
}

function initJob(provinceCode,jobTypeId,areaCode){
	
	
	// 工种选中事件
	parent.$("select#jobType").change(function(){
		// 选中事件
		var jobTypeId = parent.$('select#jobType option:selected').val();
		var jobTypeName = parent.$('select#jobType option:selected').text();
		parent.$('#jobTypeId').val(jobTypeId);
		parent.$('#jobTypeName').val(jobTypeName);
		
    });
	
	// 省份选中事件
	parent.$("#province").change(function(){
		var parentCode = this.value;
		parent.$('#parentCode').val(parentCode);
		queryArea(parentCode,areaCode);
	});
	
	// 地区选中事件
	parent.$("select#workAreaList").change(function(){
		// 选中事件
		var workArea = parent.$('select#workAreaList option:selected').val();
		var workAreaName = parent.$('select#workAreaList option:selected').text();
		parent.$('#workArea').val(workArea);
		parent.$('#workAreaName').val(workAreaName);
		
    });
	
	// 初始化省
	initProvinceSelect(provinceCode);
	
	// 查工种
	queryJobType(jobTypeId);
}

function queryArea(parentCode,areaCode){
	$.ajax({
		url:"/common/queryAreaByParentCode",
		type:"get",
		dataType:"json",
		data:{parentCode:parentCode},
		success:function(data){
			if(data.code == 1){
				var dics = data.data;
				var content = "<option value=\"\">---请选择---</option>";
				for(var i=0; i<dics.length; i++){
					var dic = dics[i];
					if(areaCode != undefined && areaCode == dic.code){
						content += "<option value=\""+dic.code+"\" selected=\"selected\">"+dic.name+"</option>";
					} else {
						content += "<option value=\""+dic.code+"\">"+dic.name+"</option>";
					}
					
				}
				parent.$("#workAreaList").empty().html(content);
			}
		}
	});
}

/**
 * 添加工种
 * @returns
 */
function addJob(){
	// 打开弹窗
	openDialog("add-job-dialog");
	// 时间框格式
	parent.$('.J-yearMonthPicker-single').datePicker({
        format: 'YYYY-MM-DD'
    });
	
	initJob(null,null,null);
	
	parent.$(".add-job-type-content").click(function(){
		top.closeDialog();
		
		var jobTypeName = parent.$("#jobTypeName").val();
		var jobTypeId = parent.$("#jobTypeId").val();
		var workerCount = parent.$("#workerCount").val();
		var salary = parent.$("#salary").val();
		var requireTime = parent.$("#requireTime").val();
		var workAreaName = parent.$("#workAreaName").val();
		var workArea = parent.$("#workAreaList").val();
		var parentCode = parent.$("#parentCode").val();
		var requirement = parent.$("#requirement").val();
		var content = "<tr class=\"tr-body\">"+
					  "  <td id='jobTypeName'>"+jobTypeName+"</td>"+
					  "  <input id='jobTypeId' type=\"hidden\" name=\"jobTypeId\" value="+ jobTypeId +">" +
					  "  <td id='workerCount'>"+workerCount+"</td>"+
					  "  <td id='salary'>"+salary+"</td>"+
					  "  <td id='requireTime'>"+requireTime+"</td>"+
					  "  <td id='workAreaName'>"+workAreaName+"</td>"+
					  "  <input id='workArea' type='hidden' name='workArea' value="+ workArea +">" +
					  "  <input id='parentCode' type='hidden' name='parentCode' value="+ parentCode +">" +
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
	var jobTypeName =  trobj.children("#jobTypeName").html();
	var workerCount =  trobj.children("#workerCount").html();
	var salary =  trobj.children("#salary").html();
	var requireTime =  trobj.children("#requireTime").html();
	var workArea =  trobj.children("#workArea").val();
	var requirement =  trobj.children("#requirement").html();
	var jobTypeId =  trobj.children("#jobTypeId").val();
	var parentCode =  trobj.children("#parentCode").val();
	var workAreaName =  trobj.children("#workAreaName").html();
	
	initJob(parentCode,jobTypeId,workArea)
	queryArea(parentCode,workArea);
	parent.$("#jobTypeName").val(jobTypeName);
	parent.$("#workerCount").val(workerCount);
	parent.$("#salary").val(salary);
	parent.$("#requireTime").val(requireTime);
	parent.$("#workArea").val(workArea);
	parent.$("#requirement").val(requirement);
	parent.$("#jobTypeId").val(jobTypeId);
	parent.$("#parentCode").val(parentCode);
	parent.$("#workAreaName").val(workAreaName);
	parent.$(".add-job-type-content").click(function(){
		
		var jobTypeName_ = parent.$("#jobTypeName").val();
		var jobTypeId_ = parent.$("#jobTypeId").val();
		var workerCount_ = parent.$("#workerCount").val();
		var salary_ = parent.$("#salary").val();
		var requireTime_ = parent.$("#requireTime").val();
		var workArea_ = parent.$("#workArea").val();
		var workAreaName_ = parent.$("#workAreaName").val();
		var parentCode_ =  parent.$("#parentCode").val();
		var requirement_ = parent.$("#requirement").val();
		
		top.closeDialog();
		
		trobj.children("#jobTypeName").html(jobTypeName_);
		trobj.children("#jobTypeId").val(jobTypeId_);
		trobj.children("#workerCount").html(workerCount_);
		trobj.children("#salary").html(salary_);
		trobj.children("#requireTime").html(requireTime_);
		trobj.children("#workArea").val(workArea_);
		trobj.children("#workAreaName").html(workAreaName_);
		trobj.children("#parentCode").val(parentCode_);
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

	var demand = {};
	demand.companyId =$("#companyId").val();
	demand.description =$("#description").val();
	//
	var demandJobList = [];
	$(".tr-body").each(function(){
		var demandJob = {};
		demandJob.jobTypeId =  $(this).children("#jobTypeId").val();
		demandJob.workerCount =  $(this).children("#workerCount").html();
		demandJob.salary =  $(this).children("#salary").html();
		demandJob.requireTime =  $(this).children("#requireTime").html();
		demandJob.workArea =  $(this).children("#workArea").val();
		demandJob.requirement =  $(this).children("#requirement").html();
		demandJobList.push(demandJob);
	});
	demand.demandJobList = demandJobList;
	
	
//	if(worker.name == null || worker.name.length == 0){
//		alert("姓名不能为空！");
//		return false;
//	}
//	if(worker.name.length > 50){
//		alert("姓名长度不能超过50个字！");
//		return false;
//	}
//	if(worker.telephone == null || worker.telephone.length == 0){
//		alert("联系电话不能为空！");
//		return false;
//	}
//	if(worker.telephone.length > 50){
//		alert("电话号码长度不能超过50个字！");
//		return false;
//	}
//	if(worker.idcard == null || worker.idcard.length == 0){
//		alert("身份证号不能为空！");
//		return false;
//	}
//	if(worker.idcard.length > 50){
//		alert("身份证号长度不能超过50个字！");
//		return false;
//	}
//	if(worker.sex == null || worker.sex.length == 0){
//		alert("性别不能为空！");
//		return false;
//	}
	
	$.ajax({
		url:"/demand/saveDemand",
		type:"post",
		dataType:"json",
		contentType:"application/json",
		data:JSON.stringify(demand),
		success:function(data){
			if(data.code == 1){
				alert("新增成功！");
				location.href="/signing/index";
			}
			else{
				alert("新增失败！原因："+data.msg);
			}
		}
	});

}


function initProvinceSelect(provinceCode){
	$.ajax({
		url:"/common/queryAllProvince",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var dics = data.data;
				var content = "<option value=\"\">---请选择---</option>";
				for(var i=0; i<dics.length; i++){
					var dic = dics[i];
					if(provinceCode != undefined && provinceCode == dic.code){
						content += "<option value=\""+dic.code+"\" selected=\"selected\">"+dic.name+"</option>";
					}else{
						content += "<option value=\""+dic.code+"\">"+dic.name+"</option>";
					}
					
				}
				parent.$("#province").empty().html(content);
				
			}
		}
	});
}

//function initProvinceSelect(){
//	$.ajax({
//		url:"/common/queryAllProvince",
//		type:"get",
//		dataType:"json",
//		success:function(data){
//			if(data.code == 1){
//				var dics = data.data;
//				var content = "<option value=\"\">---请选择---</option>";
//				for(var i=0; i<dics.length; i++){
//					var dic = dics[i];
//					content += "<option value=\""+dic.code+"\">"+dic.name+"</option>";
//				}
//				$("#province").empty().html(content);
//			}
//		}
//	});
//}
