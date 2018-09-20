$(function(){
	// 超时跳到登录
	$(document).ajaxSuccess(function(event, xhr, settings){
		if(xhr.responseJSON.code == 1002){
			top.location.href="/";
		}
	});
	$("#add-worker").click(function(){
		addWorker();
	});
	// 初始化民族
	initSelect("nation","nation");
	// 初始化性别
	initSelect("sex","gender");
	// 初始化婚姻状况
	initSelect("maritalStatus","marital_status");
	// 初始化工资要求
	initSelect("expectSalary","expect_salary");
	// 初始化工作状态
	initSelect("workStatus","work_status");
	// 初始化语言能力
	initSelect("languageLevel","language_level");
	// 初始化是否接受夜班
	initSelect("nightWork","night_work");
	// 初始化学历
	initSelect("degree","degree");
	// 初始化工资要求
	initSelect("exp_salary","expect_salary");
	// 初始化工种
	$("#jobtype").click(function(){
		editMenuRight();
	});
	// 初始化省份
	initProvinceSelect();
	// 初始化时间控件
	$('.J-yearMonthPicker-single').datePicker({
        format: 'YYYY-MM-DD'
    });
	$("#add-education").click(function(){
		openEducationDialog();
	});
	$("#add-experience").click(function(){
		openExperienceDialog();
	});
	// 市区联动
	$("#province").change(function(){
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
					$("#birthplaceCode").empty().html(content);
				}
			}
		});
	});
	$("#province2").change(function(){
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
					$("#workplaceCode").empty().html(content);
				}
			}
		});
	});

});

function editMenuRight(){
	// 暂存roleId
	var workerId = $("#workerId").val();
	var url = null;
	if(workerId){
		url = "/worker/querySelectedJobType";
	}
	else{
		url = "/jobType/queryTree";
	}
	$.ajax({
		url:url,
		type:"get",
		dataType:"json",
		data:{workerId:workerId},
		success:function(data){
			if(data.code == 1){
				var jobtypeList = data.data;
				var content = "";
				for(var i=0; i<jobtypeList.length; i++){
					var jobtype = jobtypeList[i];
					if(jobtype.children.length > 0){
						content += "<div class=\"select\">"+
									"	<div class=\"s\">"+
									"		<div class=\"workType\">"+
									"			<input type=\"checkbox\" name=\"parentMenu\" id=\""+jobtype.id+"\" "+(jobtype.selected == 0 ? "" : "checked=\"checked\"")+"/><span>"+jobtype.name+"</span>"+
									"		</div>"+
									"	</div>";
						for(var j=0; j<jobtype.children.length; j++){
							var subjobtype = jobtype.children[j];
							content += "	<div class=\"s two\">"+
										"		<div class=\"workType\">"+
										"			<input type=\"checkbox\" name=\"subMenu_"+jobtype.id+"\" id=\""+subjobtype.id+"\" "+(subjobtype.selected == 0 ? "" : "checked=\"checked\"")+" /><span>"+subjobtype.name+"</span>"+
										"		</div>"+
										"	</div>";
						}
									
						content += "</div>";
					}
					else{
						content += "<div class=\"select\">"+
									"	<div class=\"s\">"+
									"		<div class=\"workType\">"+
									"			<input type=\"checkbox\" name=\"parentMenu\" id=\""+jobtype.id+"\" "+(jobtype.selected == 0 ? "" : "checked=\"checked\"")+" /><span>"+jobtype.name+"</span>"+
									"		</div>"+
									"	</div>"+
									"</div>";
					}
				}
				$("#jobtype-content").empty().html(content);
				openDialog("dialog-jobtype-content");
			}
			
			parent.$("input[type=checkbox]").click(function(){
				var name = this.name;
				if(name.indexOf("parentMenu") != -1){
					var id = this.id;
					if($(this).is(":checked")){
						parent.$("input[name=subMenu_"+id+"]").prop('checked',true);
				    }else{
				    	parent.$("input[name=subMenu_"+id+"]").prop('checked',false);
				    }
				}
				if(name.indexOf("subMenu") != -1){
					var parentId = name.substring(8);
					if($(this).is(":checked")){
						parent.$("#"+parentId).prop('checked',true);
				    }
				}
			});
			parent.$(".save-menuright").click(function(){
				saveMenuRight();
			});
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
	top.$("#dialog").fadeIn(300);
	// 因为弹窗页面是重新渲染到top页面的。所以事件绑定只能在渲染之后。否则不起作用！
	top.$(".cancel-dialog").click(function(){
		top.closeDialog();
	});
	top.$("#close-dialog").click(function(){
		top.closeDialog();
	});
}

function openEducationDialog(){
	openDialog("dialog-education-content");
	parent.$('.J-yearMonthPicker-single').datePicker({
        format: 'YYYY-MM'
    });
	parent.$(".add-education-content").click(function(){
		top.closeDialog();
		var school = parent.$("#school").val();
		var degree = parent.$("#degree").find("option:selected").text();
		var beginTime = parent.$("#beginTime").val();
		var endTime = parent.$("#endTime").val();
		var discipline = parent.$("#discipline").val();
		// 拼接学历展示的内容
		var content = "<div class=\"history\">"+
							"<span class=\"delete fa fa-close\" onClick=\"removeEducationDialog()\"></span>"+
							"<ul>"+
							"	<li><span class=\"name\">学校</span> <span class=\"content\" id=\"school_text\">"+school+"</span></li>"+
							"	<li><span class=\"name\">学历</span> <span class=\"content\" id=\"degree_text\">"+degree+"</span></li>"+
							"	<li><span class=\"name\">起止日期</span> <span class=\"content\" id=\"schoolTime_text\">"+beginTime+"-"+endTime+"</span></li>"+
							"	<li><span class=\"name\">专业名称</span> <span class=\"content\" id=\"discipline_text\">"+discipline+"</span></li>"+
							"</ul>"+
						"</div>";
		$("#education-list").append(content);
	});
}
function removeEducationDialog(){
	alert($(this).parent().html());
	//$(this).parents(".history").remove();
}

function openExperienceDialog(){
	openDialog("dialog-experience-content");
	parent.$('.J-yearMonthPicker-single').datePicker({
		format: 'YYYY-MM-DD'
	});
}

function initSelect(id,type){
	$.ajax({
		url:"/common/queryDicByType",
		type:"get",
		dataType:"json",
		data:{type:type},
		success:function(data){
			if(data.code == 1){
				var dics = data.data;
				var content = "<option value=\"\">---请选择---</option>";
				for(var i=0; i<dics.length; i++){
					var dic = dics[i];
					content += "<option value=\""+dic.code+"\">"+dic.name+"</option>";
				}
				$("#"+id).empty().html(content);
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
				$("#province2").empty().html(content);
			}
		}
	});
}



function addWorker(){
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
