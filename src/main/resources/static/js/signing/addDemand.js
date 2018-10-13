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
	$("#public-bottom2").click(function(){
		addDemand();
	});
	
	//监听公司名称变化
	$('#companyName').bind('input propertychange', function() {
		queryCompany();
	});
	
	// 初始化省
	initProvinceSelect();
	
	
});

function deleteJob(obj){
	var thisObj=$(obj);
	thisObj.parent().parent().remove();
}

// 查工种
function queryJobType(){
	var name = parent.$("#jobTypeName").val();

	$.ajax({
		url:"/jobType/queryByName",
		type:"get",
		data:{name:name},
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var firmArr = data.data;
				console.log(firmArr);
				var divContent="";
				if(firmArr.length > 0){
					for(var i=0; i<firmArr.length; i++){
						var jobType = firmArr[i];
						console.log(jobType);
						divContent+=  "<div class='li' value_id= "+jobType.id+" value_name= "+jobType.name+"  onclick='changJobType(this)'>"+jobType.name +"</div>";
					}
				}
				parent.$("#jobTypeList").show();
				parent.$("#jobTypeList").empty().append(divContent);
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

function addJob(){
	openDialog("add-job-dialog");
	parent.$('.J-yearMonthPicker-single').datePicker({
        format: 'YYYY-MM-DD'
    });
	
	// 监听工种名称变化
	parent.$('#jobTypeName').bind('input propertychange', function() {
		queryJobType();
	});
	
	parent.$("#province").change(function(){
		var parentCode = this.value;
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
						content += "<option value=\""+dic.code+"\">"+dic.name+"</option>";
					}
					parent.$("#workAreaName").empty().html(content);
				}
			}
		});
	});
	
	parent.$(".add-job-type-content").click(function(){
		top.closeDialog();
		
		var jobTypeName = parent.$("#jobTypeName").val();
		var workerCount = parent.$("#workerCount").val();
		var salary = parent.$("#salary").val();
		var requireTime = parent.$("#requireTime").val();
		var workAreaName = parent.$("#workAreaName").val();
		var requirement = parent.$("#requirement").val();
		var content = "<tr class=\"tr-body\">"+
					  "  <td id='jobTypeName'>"+jobTypeName+"</td>"+
					  "  <input id='jobTypeId' type=\"hidden\" name=\"jobTypeId\" value="+ jobTypeName +">" +
					  "  <td id='workerCount'>"+workerCount+"</td>"+
					  "  <td id='salary'>"+salary+"</td>"+
					  "  <td id='requireTime'>"+requireTime+"</td>"+
					  "  <td id='workAreaName'>"+workAreaName+"</td>"+
					  "  <input id='workArea' type='hidden' name='workArea' value="+ workAreaName +">" +
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
	var workArea =  trobj.children("#workArea").html();
	var requirement =  trobj.children("#requirement").html();
	parent.$("#jobTypeName").val(jobTypeName);
	parent.$("#workerCount").val(workerCount);
	parent.$("#salary").val(salary);
	parent.$("#requireTime").val(requireTime);
	parent.$("#workArea").val(workArea);
	parent.$("#requirement").val(requirement);
	
	parent.$(".add-job-type-content").click(function(){
		
		var jobTypeName_ = parent.$("#jobTypeName").val();
		var workerCount_ = parent.$("#workerCount").val();
		var salary_ = parent.$("#salary").val();
		var requireTime_ = parent.$("#requireTime").val();
		var workArea_ = parent.$("#workArea").val();
		var requirement_ = parent.$("#requirement").val();
		top.closeDialog();
		
		trobj.children("#jobTypeName").html(jobTypeName_);
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
				location.href="/worker/index";
			}
			else{
				alert("新增失败！原因："+data.msg);
			}
		}
	});

}


function initProvinceSelect(){
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
					content += "<option value=\""+dic.code+"\">"+dic.name+"</option>";
				}
				$("#province").empty().html(content);
			}
		}
	});
}
