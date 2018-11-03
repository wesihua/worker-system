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
	var demandId = $("input:hidden[name='demandId']").val();
	if(demandId > 0){
		$("#title-name").text("编辑需求单");
	}
	
	//监听公司名称变化
	$('#companyName').bind('input propertychange', function() {
		queryCompany();
	});
	
	queryDetail();
	
});

/**
 * 查询
 * @returns
 */
function queryDetail(){
	
	var demandId = $("input:hidden[name='demandId']").val();
	if(demandId > 0){
		$.ajax({
			url:"/demand/waitingDemand",
			type:"get",
			data:{demandId:demandId},
			dataType:"json",
			success:function(data){
				if(data.code == 1){
					var state = data.data.state;
					var tableContent="";
					var firmArr = data.data.demandJobList;
					$("#companyId").val(data.data.companyId);
					$("#companyName").val(data.data.companyName);
					$("#description").text(data.data.description);
					if(firmArr.length > 0){
						tableContent+= "<tr>"+
										"	<th>用工工种</th>"+
										"	<th>用工人数</th>"+
										"	<th>到岗日期</th>"+
										"	<th>月工资（元）</th>"+
										"	<th>工作地区</th>"+
										"	<th>用工要求</th>"+
										"	<th>操作</th>"+
										"</tr>";
						for(var i=0; i<firmArr.length; i++){
							var firm = firmArr[i];
							tableContent+=  "<tr class=\"tr-body\">"+
											"  <td id='jobTypeName'>"+firm.jobTypeName+"</td>"+
											"  <td id='workerCount'>"+firm.workerCount+"</td>"+
											"  <td id='requireTime'>"+firm.requireTime+"</td>"+
											"  <td id='salary'>"+firm.salary+"</td>"+
											"  <td id='workAreaName'>"+firm.workAreaName+"</td>"+
											"  <td id='requirement'>"+firm.requirement+"</td>"+
											"   <input id='id' type=\"hidden\" name=\"id\" value="+ firm.id +">" +
											"   <input id='jobTypeId' type=\"hidden\" name=\"jobTypeId\" value="+ firm.jobTypeId +">" +
											"   <input id='parentJobTypeId' type=\"hidden\" name=\"parentJobTypeId\" value="+ firm.parentJobTypeId +">" +
											"   <input id='workArea' type='hidden' name='workArea' value="+ firm.workArea +">" +
											"   <input id='parentCode' type='hidden' name='parentCode' value="+ firm.parentCode +">" +
											"   <td><span class=\"des\" onclick=\"editJob(this)\">编辑</span><span class=\"delete\" onclick=\"deleteJob(this)\">移除</span></td>"+
											"</tr>";
						}
					}
					$("table").empty().append(tableContent);
				}
			}
		});

	}
	
}

function deleteJob(obj){
	var thisObj=$(obj);
	thisObj.parent().parent().remove();
}

// 查子级工种
function queryJobType(parentJobTypeId,jobTypeId){
	

	$.ajax({
		url:"/jobType/queryByParentId",
		type:"get",
		dataType:"json",
		data:{parentId : parentJobTypeId},
		async:true,
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
				parent.$("#jobTypeList").empty().html(content);
			}
		}
	});
}


// 查父级工种
function queryParentJobType(parentJobTypeId){
	$.ajax({
		url:"/jobType/queryRootJobType",
		type:"get",
		dataType:"json",
		async:true,
		success:function(data){
			if(data.code == 1){
				var dics = data.data;
				var content = "<option value=\"\">---请选择---</option>";
				for(var i=0; i<dics.length; i++){
					var dic = dics[i];
					
					if(parentJobTypeId != undefined && parentJobTypeId == dic.id){
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

function initJob(provinceCode,areaCode,parentJobTypeId,jobTypeId){
	
	
	// 二级工种选中事件
	parent.$("select#jobTypeList").change(function(){
		// 选中事件
		var jobTypeId = parent.$('select#jobTypeList option:selected').val();
		var jobTypeName = parent.$('select#jobTypeList option:selected').text();
		parent.$('#jobTypeId').val(jobTypeId);
		parent.$('#jobTypeName').val(jobTypeName);
		
    });
	
	// 一级工种选中事件
	parent.$("select#jobType").change(function(){
		var parentJobTypeId = this.value;
		parent.$('#parentJobTypeId').val(parentJobTypeId);
		queryJobType(parentJobTypeId,jobTypeId);
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
	
	// 初始化父级工种
	queryParentJobType(parentJobTypeId);
	
	// 查工种
	//queryJobType(parentJobTypeId,jobTypeId);
}

function queryArea(parentCode,areaCode){
	$.ajax({
		url:"/common/queryAreaByParentCode",
		type:"get",
		dataType:"json",
		async:true,
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
	
	initJob(null,null,null,null);
	
	parent.$(".add-job-type-content").click(function(){
		
		var workAreaName ='';
		var parentCode = 0;
		var jobTypeName = parent.$("#jobTypeName").val();
		var jobTypeId = parent.$("#jobTypeId").val();
		var parentJobTypeId = parent.$("#parentJobTypeId").val();
		var workerCount = parent.$("#workerCount").val();
		var salary = parent.$("#salary").val();
		var requireTime = parent.$("#requireTime").val();
		var workArea = parent.$("#workAreaList").val();
		var requirement = parent.$("#requirement").val();
		
		// var 
		if(workArea > 0){
			workAreaName = parent.$("#workAreaName").val();
			parentCode = parent.$("#parentCode").val();
		}
		
		
		var p_check = checkParameter();	
		
		// 判断工种是否已存在
		$(".tr-body").each(function(){
			
			var this_jobTypeId =  $(this).children("#jobTypeId").val();
			if(this_jobTypeId == jobTypeId){
				p_check = false;
				alert("此工种在列表种已存在！");
				return;
			}
			
		});
		
		
		if(p_check){
			top.closeDialog();
			
			var content = "<tr class=\"tr-body\">"+
						  "  <td id='jobTypeName'>"+jobTypeName+"</td>"+
						  "  <input id='jobTypeId' type=\"hidden\" name=\"jobTypeId\" value="+ jobTypeId +">" +
						  "  <input id='parentJobTypeId' type=\"hidden\" name=\"parentJobTypeId\" value="+ parentJobTypeId +">" +
						  "  <td id='workerCount'>"+workerCount+"</td>"+
						  "  <td id='requireTime'>"+requireTime+"</td>"+
						  "  <td id='salary'>"+salary+"</td>"+
						  "  <td id='workAreaName'>"+workAreaName+"</td>"+
						  "  <input id='workArea' type='hidden' name='workArea' value="+ workArea +">" +
						  "  <input id='parentCode' type='hidden' name='parentCode' value="+ parentCode +">" +
						  "  <td id='requirement'>"+requirement+"</td>"+
						  "  <td><span class=\"des\" onclick=\"editJob(this)\">编辑</span><span class=\"delete\" onclick=\"deleteJob(this)\">移除</span></td>"+
						  "</tr>";
			$("table").append(content);
		}
		
	});
}

function checkParameter() {

	var jobTypeId = parent.$("#jobTypeId").val();
	var workerCount = parent.$("#workerCount").val();
	var salary = parent.$("#salary").val();
	var requireTime = parent.$("#requireTime").val();
	var workArea = parent.$("#workAreaList").val();
	var requirement = parent.$("#requirement").val();
	if (!jobTypeId) {
		alert("用工工种不能为空！");
		return false;
	}
	if (!workerCount) {
		alert("用工人数不能为空！");
		return false;
	}
	if (isNaN(workerCount)) {
		alert("请输入正确的用工人数！");
		return false;
	}
	if (workerCount<=0) {
		alert("用工人数不少于0！");
		return false;
	}
	/*
	if (!requireTime) {
		alert("到岗日期不能为空！");
		return false;
	}
	if (!salary) {
		alert("月工资不能为空！");
		return false;
	}
	if (salary <= 0) {
		alert("月工资不能少于0！");
		return false;
	}
	
	if (!workArea) {
		alert("工作地区不能为空！");
		return false;
	}
	if (!requirement) {
		alert("用工要求不能为空！");
		return false;
	}
	*/
	return true;

}

function editJob(obj) {
	openDialog("add-job-dialog");
	parent.$('.J-yearMonthPicker-single').datePicker({
		format : 'YYYY-MM-DD'
	});

	var trobj = $(obj).parent().parent();
	var jobTypeName = trobj.children("#jobTypeName").html();
	var workerCount = trobj.children("#workerCount").html();
	var salary = trobj.children("#salary").html();
	var requireTime = trobj.children("#requireTime").html();
	var workArea = trobj.children("#workArea").val();
	var requirement = trobj.children("#requirement").html();
	var jobTypeId = trobj.children("#jobTypeId").val();
	var parentCode = trobj.children("#parentCode").val();
	var workAreaName = trobj.children("#workAreaName").html();
	var parentJobTypeId = trobj.children("#parentJobTypeId").val();
    // provinceCode,areaCode,parentJobTypeId,jobTypeId
	initJob(parentCode,workArea,parentJobTypeId, jobTypeId)
	queryArea(parentCode, workArea);
	queryJobType(parentJobTypeId,jobTypeId);
	parent.$("#jobTypeName").val(jobTypeName);
	parent.$("#workerCount").val(workerCount);
	parent.$("#salary").val(salary);
	parent.$("#requireTime").val(requireTime);
	parent.$("#workArea").val(workArea);
	parent.$("#requirement").val(requirement);
	parent.$("#jobTypeId").val(jobTypeId);
	parent.$("#parentJobTypeId").val(parentJobTypeId);
	parent.$("#parentCode").val(parentCode);
	parent.$("#workAreaName").val(workAreaName);
	parent.$(".add-job-type-content").click(function() {
		
		var workAreaName_ = "";
		var parentCode_ = 0;

		var jobTypeName_ = parent.$("#jobTypeName").val();
		var jobTypeId_ = parent.$("#jobTypeId").val();
		var parentJobTypeId_ = parent.$("#parentJobTypeId").val();
		var workerCount_ = parent.$("#workerCount").val();
		var salary_ = parent.$("#salary").val();
		var requireTime_ = parent.$("#requireTime").val();
		var workArea_ = parent.$("#workArea").val();
		var requirement_ = parent.$("#requirement").val();
		
		if(workArea_ > 0){
			workAreaName_ = parent.$("#workAreaName").val();
			parentCode_ = parent.$("#parentCode").val();
		}

		var p_check = checkParameter();
		
		var jobIdCount = 0;
        $(".tr-body").each(function(){
			
			var this_jobTypeId =  $(this).children("#jobTypeId").val();
			if(this_jobTypeId == jobTypeId_){
				jobIdCount= jobIdCount+1;
			}
			
		});
        
        // 如果换了
        if(jobTypeId!=jobTypeId_ && jobIdCount>=1){
        	alert("此工种在列表种已存在！");
        	p_check = false;
        	return;
        }

		if (p_check) {

			top.closeDialog();

			trobj.children("#jobTypeName").html(jobTypeName_);
			trobj.children("#jobTypeId").val(jobTypeId_);
			trobj.children("#parentJobTypeId").val(parentJobTypeId_);
			trobj.children("#workerCount").html(workerCount_);
			trobj.children("#salary").html(salary_);
			trobj.children("#requireTime").html(requireTime_);
			trobj.children("#workArea").val(workArea_);
			trobj.children("#workAreaName").html(workAreaName_);
			trobj.children("#parentCode").val(parentCode_);
			trobj.children("#requirement").html(requirement_);
		}

	});
}

/**
 * 打开弹窗
 * 
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
	demand.id=$("input:hidden[name='demandId']").val();
	demand.companyId =$("#companyId").val();
	demand.description =$("#description").val();
	
	if (!demand.companyId) {
		alert("请搜索已有企业！");
		return false;
	}
	if (!demand.description) {
		alert("备注说明不能为空！");
		return false;
	}
	//
	var demandJobList = [];
	$(".tr-body").each(function(){
		var demandJob = {};
		demandJob.id = $(this).children("#id").val();
		demandJob.jobTypeId =  $(this).children("#jobTypeId").val();
		demandJob.workerCount =  $(this).children("#workerCount").html();
		demandJob.salary =  $(this).children("#salary").html();
		demandJob.requireTime =  $(this).children("#requireTime").html();
		demandJob.workArea =  $(this).children("#workArea").val();
		demandJob.requirement =  $(this).children("#requirement").html();
		demandJobList.push(demandJob);
	});
	
	demand.demandJobList = demandJobList;
	
	if(demandJobList.length==0){
		alert("用工工种不能为空！");
		return false;
	}
	
	var demandId = $("input:hidden[name='demandId']").val();
	
	var v_url = "/demand/saveDemand";
	if (demandId > 0){
		v_url = "/demand/editDemand";
	}
	
	$.ajax({
		url:v_url,
		type:"post",
		dataType:"json",
		contentType:"application/json",
		data:JSON.stringify(demand),
		success:function(data){
			if(data.code == 1){
				if (demandId > 0){
					alert("编辑成功！");
				}else{
					alert("新增成功！");
				}
				
				location.href="/signing/waiting";
			}
			else{
				if (demandId > 0){
					alert("编辑失败！原因："+data.msg);
				}else{
					alert("新增失败！原因："+data.msg);
				}
				
			}
		}
	});

}


function initProvinceSelect(provinceCode){
	$.ajax({
		url:"/common/queryAllProvince",
		type:"get",
		dataType:"json",
		async:true,
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

function initParentJobType(provinceCode){
	$.ajax({
		url:"/common/queryAllProvince",
		type:"get",
		dataType:"json",
		async:true,
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


