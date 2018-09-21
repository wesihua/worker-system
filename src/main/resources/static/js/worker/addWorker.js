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
	$("#cancel").click(function(){
		location.href="/worker/index";
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
		editJobType();
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

function editJobType(){
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
										"			<input type=\"checkbox\" name=\"subMenu_"+jobtype.id+"\" id=\""+subjobtype.id+"\" value=\""+subjobtype.name+"\" "+(subjobtype.selected == 0 ? "" : "checked=\"checked\"")+" /><span>"+subjobtype.name+"</span>"+
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
			
			// 确定按钮点击事件
			parent.$(".add-jobtype").click(function(){
				var selectedJobtypeName = "";
				var selectedJobtypeIds = [];
				parent.$("input[type=checkbox]:checked").each(function(){
					var name = this.name;
					var parentId = name.substring(8);
					var id = this.id;
					if(name.indexOf("subMenu") != -1){
						selectedJobtypeName += this.value+",";
						var jobtypeId = {};
						jobtypeId.firstId = parentId;
						jobtypeId.secondId = id;
						selectedJobtypeIds.push(jobtypeId);
					}
				});
				top.closeDialog();
				$("#jobtype").val(selectedJobtypeName);
				$("#jobtype_value").val(JSON.stringify(selectedJobtypeIds));
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
        format: 'YYYY-MM-DD'
    });
	parent.$(".add-education-content").click(function(){
		top.closeDialog();
		var school = parent.$("#school").val();
		var degree = parent.$("#degree").find("option:selected").text() == "---请选择---" ? "" : parent.$("#degree").find("option:selected").text() ;
		var degree_value = parent.$("#degree").val();
		var beginTime = parent.$("#beginTime").val();
		var endTime = parent.$("#endTime").val();
		var discipline = parent.$("#discipline").val();
		// 拼接学历展示的内容
		var content = "<div class=\"history\">"+
							"	<span class=\"edit fa fa-edit\" name=\"edit-education-dialog\" title=\"编辑\"></span>"+
							"<span class=\"delete fa fa-close\" name=\"remove-education-dialog\" title=\"删除\"></span>"+
							"<ul>"+
							"	<li><span class=\"name\">学校</span> <span class=\"content\" name=\"school_text\">"+school+"</span></li>"+
							"	<li><span class=\"name\">学历</span> <span class=\"content\" name=\"degree_text\">"+degree+"</span></li>"+
							"	<input type=\"hidden\" name=\"degree_value\" value=\""+degree_value+"\" />"+
							"	<input type=\"hidden\" name=\"beginTime_value\" value=\""+beginTime+"\" />"+
							"	<input type=\"hidden\" name=\"endTime_value\" value=\""+endTime+"\" />"+
							"	<li><span class=\"name\">起止日期</span> <span class=\"content\" name=\"schoolTime_text\">"+beginTime+" 至 "+endTime+"</span></li>"+
							"	<li><span class=\"name\">专业名称</span> <span class=\"content\" name=\"discipline_text\">"+discipline+"</span></li>"+
							"</ul>"+
						"</div>";
		$("#education-list").append(content);
		// 绑定删除事件
		$("span[name=remove-education-dialog]").click(function(){
			$(this).parent().remove();
		});
		// 绑定编辑事件
		$("span[name=edit-education-dialog]").click(function(){
			var school = $(this).parent().find("span[name=school_text]").text();
			var degree_value = $(this).parent().find("input[name=degree_value]").val();
			var beginTime = $(this).parent().find("input[name=beginTime_value]").val();
			var endTime = $(this).parent().find("input[name=endTime_value]").val();
			var discipline = $(this).parent().find("span[name=discipline_text]").text();
			editEducationDialog(this,school,degree_value,beginTime,endTime,discipline);
		});
	});
}

function editEducationDialog(ts,school,degree,beginTime,endTime,discipline){
	openDialog("dialog-education-content");
	parent.$('.J-yearMonthPicker-single').datePicker({
        format: 'YYYY-MM-DD'
    });
	parent.$("#school").val(school);
	parent.$("#degree").val(degree);
	parent.$("#beginTime").val(beginTime);
	parent.$("#endTime").val(endTime);
	parent.$("#discipline").val(discipline);
	
	parent.$(".add-education-content").click(function(){
		top.closeDialog();
		$(ts).parent().find("span[name=school_text]").text(parent.$("#school").val());
		var degree_text = parent.$("#degree").find("option:selected").text() == "---请选择---" ? "" : parent.$("#degree").find("option:selected").text();
		var degree_value = parent.$("#degree").val();
		$(ts).parent().find("span[name=degree_text]").text(degree_text);
		$(ts).parent().find("input[name=degree_value]").val(degree_value);
		var beginTime = parent.$("#beginTime").val();
		var endTime = parent.$("#endTime").val();
		var discipline = parent.$("#discipline").val();
		$(ts).parent().find("span[name=schoolTime_text]").text(beginTime +" 至 "+endTime);
		$(ts).parent().find("input[name=beginTime_value]").val(beginTime);
		$(ts).parent().find("input[name=endTime_value]").val(endTime);
		$(ts).parent().find("span[name=discipline_text]").text(discipline);
	});
}

function openExperienceDialog(){
	openDialog("dialog-experience-content");
	parent.$('.J-yearMonthPicker-single').datePicker({
		format: 'YYYY-MM-DD'
	});
	parent.$(".add-experience-content").click(function(){
		top.closeDialog();
		var companyName = parent.$("#exp_company").val();
		var position = parent.$("#exp_position").val();
		var beginTime = parent.$("#exp_beginTime").val();
		var endTime = parent.$("#exp_endTime").val();
		var salary = parent.$("#exp_salary").find("option:selected").text() == "---请选择---" ? "" : parent.$("#exp_salary").find("option:selected").text();
		var salary_value = parent.$("#exp_salary").val();
		var description = parent.$("#exp_description").val();
		
		var content = "<div class=\"history\">"+
						"	<span class=\"edit fa fa-edit\" name=\"edit-experience-dialog\" title=\"编辑\"></span>"+
						"	<span class=\"delete fa fa-close\" name=\"remove-experience-dialog\" title=\"删除\"></span>"+
						"	<ul>"+
						"		<li><span class=\"name\">工作公司</span> <span class=\"content\" name=\"companyName_text\">"+companyName+"</span>"+
						"		</li>"+
						"		<li><span class=\"name\">职位</span> <span class=\"content\" name=\"position_text\">"+position+"</span>"+
						"		</li>"+
						"		<li><span class=\"name\">起止时间</span> <span class=\"content\" name=\"exp_time\">"+beginTime+" 至 "+endTime+"</span>"+
						"		</li>"+
						"		<li><span class=\"name\">月工资</span> <span class=\"content\" name=\"salary_text\">"+salary+"</span>"+
						"		</li>"+
						"		<li><span class=\"name\">工作内容</span> <span class=\"content\" name=\"description_text\">"+description+"</span>"+
						"		</li>"+
							"	<input type=\"hidden\" name=\"salary_value\" value=\""+salary_value+"\" />"+
							"	<input type=\"hidden\" name=\"beginTime_value\" value=\""+beginTime+"\" />"+
							"	<input type=\"hidden\" name=\"endTime_value\" value=\""+endTime+"\" />"+
						"	</ul>"+
						"</div>";
		$("#experience-list").append(content);
		// 绑定删除事件
		$("span[name=remove-experience-dialog]").click(function(){
			$(this).parent().remove();
		});
		// 绑定编辑事件
		$("span[name=edit-experience-dialog]").click(function(){
			var companyName = $(this).parent().find("span[name=companyName_text]").text();
			var position = $(this).parent().find("span[name=position_text]").text();
			var beginTime = $(this).parent().find("input[name=beginTime_value]").val();
			var endTime = $(this).parent().find("input[name=endTime_value]").val();
			var salary_value = $(this).parent().find("input[name=salary_value]").val();
			var description = $(this).parent().find("span[name=description_text]").text();
			editExperienceDialog(this,companyName,position,beginTime,endTime,salary_value,description);
		});
	});
}

function editExperienceDialog(ts,companyName,position,beginTime,endTime,salary_value,description){
	openDialog("dialog-experience-content");
	parent.$('.J-yearMonthPicker-single').datePicker({
		format: 'YYYY-MM-DD'
	});
	parent.$("#exp_company").val(companyName);
	parent.$("#exp_position").val(position);
	parent.$("#exp_beginTime").val(beginTime);
	parent.$("#exp_endTime").val(endTime);
	parent.$("#exp_salary").val(salary_value);
	parent.$("#exp_description").val(description);
	
	parent.$(".add-experience-content").click(function(){
		top.closeDialog();
		$(ts).parent().find("span[name=companyName_text]").text(parent.$("#exp_company").val());
		$(ts).parent().find("span[name=position_text]").text(parent.$("#exp_position").val());
		var salary_text = parent.$("#exp_salary").find("option:selected").text() == "---请选择---" ? "" : parent.$("#exp_salary").find("option:selected").text();
		var salary_value = parent.$("#exp_salary").val();
		$(ts).parent().find("span[name=salary_text]").text(salary_text);
		$(ts).parent().find("input[name=salary_value]").val(salary_value);
		var beginTime = parent.$("#exp_beginTime").val();
		var endTime = parent.$("#exp_endTime").val();
		var description = parent.$("#exp_description").val();
		$(ts).parent().find("span[name=exp_time]").text(beginTime +" 至 "+endTime);
		$(ts).parent().find("input[name=beginTime_value]").val(beginTime);
		$(ts).parent().find("input[name=endTime_value]").val(endTime);
		$(ts).parent().find("span[name=description_text]").text(description);
	});
}

function initSelect(id,type){
	$.ajax({
		url:"/common/queryDicByType",
		type:"get",
		async:false,
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
	var worker = {};
	worker.name = $("#name").val();
	worker.telephone = $("#telephone").val();
	worker.email = $("#email").val();
	worker.idcard = $("#idcard").val();
	worker.workYear = $("#workYear").val();
	worker.maritalStatus = $("#maritalStatus").val();
	worker.expectSalary = $("#expectSalary").val();
	worker.workStatus = $("#workStatus").val();
	worker.languageLevel = $("#languageLevel").val();
	worker.nightWork = $("#nightWork").val();
	worker.birthplaceCode = $("#birthplaceCode").val();
	worker.workplaceCode = $("#workplaceCode").val();
	worker.nation = $("#nation").val();
	worker.title = $("#title").val();
	worker.sex = $("#sex").val();
	worker.position = $("#position").val();
	worker.address = $("#address").val();
	worker.birthday = $("#birthday").val();
	worker.workExpect = $("#workExpect").val();
	worker.jobtypeName = $("#jobtype").val();
	// 收集教育经历
	var educationList = [];
	$("#education-list").find(".history").each(function(){
		var education = {};
		education.school = $(this).find("span[name=school_text]").text();
		education.degree = $(this).find("input[name=degree_value]").val();
		education.beginTime = $(this).find("input[name=beginTime_value]").val();
		education.endTime = $(this).find("input[name=endTime_value]").val();
		education.discipline = $(this).find("span[name=discipline_text]").text();
		educationList.push(education);
	});
	worker.educationList = educationList;
	// 收集工作经历
	var experienceList = [];
	$("#experience-list").find(".history").each(function(){
		var experience = {};
		experience.company = $(this).find("span[name=company_text]").text();
		experience.position = $(this).find("span[name=position_text]").text();
		experience.salary = $(this).find("input[name=salary_value]").val();
		experience.beginTime = $(this).find("input[name=beginTime_value]").val();
		experience.endTime = $(this).find("input[name=endTime_value]").val();
		experience.description = $(this).find("span[name=description_text]").text();
		experienceList.push(experience);
	});
	worker.experienceList = experienceList;
	// 收集工种
	var jobTypeListStr = $("#jobtype_value").val();
	if(jobTypeListStr){
		worker.jobTypeList = JSON.parse(jobTypeListStr);
	}
	
	if(worker.name == null || worker.name.length == 0){
		alert("姓名不能为空！");
		return false;
	}
	if(worker.name.length > 50){
		alert("姓名长度不能超过50个字！");
		return false;
	}
	if(worker.telephone == null || worker.telephone.length == 0){
		alert("联系电话不能为空！");
		return false;
	}
	if(worker.telephone.length > 50){
		alert("电话号码长度不能超过50个字！");
		return false;
	}
	if(worker.idcard == null || worker.idcard.length == 0){
		alert("身份证号不能为空！");
		return false;
	}
	if(worker.idcard.length > 50){
		alert("身份证号长度不能超过50个字！");
		return false;
	}
	if(worker.sex == null || worker.sex.length == 0){
		alert("性别不能为空！");
		return false;
	}
	console.log(worker);
	console.log(JSON.stringify(worker));
	$.ajax({
		url:"/worker/addWorker",
		type:"post",
		dataType:"json",
		contentType:"application/json",
		data:JSON.stringify(worker),
		success:function(data){
			if(data.code == 1){
				alert("新增人才信息成功！");
				location.href="/worker/index";
			}
			else{
				alert("新增人才信息失败！原因："+data.msg);
			}
		}
	});
}
