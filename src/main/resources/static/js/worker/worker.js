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
	
	//按钮事件绑定
	$("#query").click(function(){
		query(1);
	});
	$("#reset").click(function(){
		resetQuery();
	});
	$("#add-worker").click(function(){
		location.href="/worker/add"
	});
	$("#download").click(function(){
		var workerName = $("#workerName").val();
		var telephone = $("#telephone").val();
		var idcard = $("#idcard").val();
		var firstId = "";
		var secondId = "";
		if($("#firstId").selectivity('data')){
			firstId = $("#firstId").selectivity('data').id;
		}
		if($("#secondId").selectivity('data')){
			secondId = $("#secondId").selectivity('data').id;
		}	
		var createUser = $("#createUser").val();
		var source = $("#source").val();
		var workStatus = $("#workStatus").val();
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
		var company = $("#company").val();
		var minAge = $("#minAge").val();
		var maxAge = $("#maxAge").val();
		var sex = $("#sex").val();
		var degree = $("#degree").val();
		var expectSalary = $("#expectSalary").val();
		var workYear = $("#workYear").val();
		var discipline = $("#discipline").val();
		
		window.open("/worker/export?name="+workerName+"&telephone="+telephone+"" +
				"&idcard="+idcard+"&firstId="+firstId+"&secondId="+secondId+"" +
				"&company="+company+"&minAge="+minAge+"&maxAge="+maxAge+"&sex="+sex+"&workStatus="+workStatus+"" +
				"&degree="+degree+"&expectSalary="+expectSalary+"&workYear="+workYear+"&discipline="+discipline+""+
				"&createUser="+createUser+"&source="+source+"&beginTime="+beginTime+"&endTime="+endTime);
	});
	// 初始化来源
	initSelect();
	
	// 初始化工作状态
	//initWorkStatusSelect("workStatus","work_status");
	// 初始化学历要求
	//initDegreeSelect("degree","degree");
	// 初始化期望薪资
	//initExpectSalarySelect("expectSalary","expect_salary");
	// 初始化一级工种
	initFirstIdSelect("firstId");
	// 二级工种联动
	$("#firstId").change(function(){
		if(!$(this).selectivity('data')){
			$("#secondId").selectivity('clear');
			$("#secondId").selectivity({
				allowClear: true,
			    items: [],
			    placeholder: '二级工种'
			});
		}
		else{
			var firstId = $(this).selectivity('data').id;
			$("#secondId").selectivity('clear');
			$.ajax({
				url:"/jobType/queryByParentId",
				type:"get",
				dataType:"json",
				data:{parentId:firstId},
				success:function(data){
					if(data.code == 1){
						var dics = data.data;
						var infoList = [];
						for(var i=0; i<dics.length; i++){
							var dic = dics[i];
							var info = {};
							info.id = dic.id;
							info.text = dic.name;
							infoList.push(info);
						}
						$("#secondId").selectivity({
							allowClear: true,
						    items: infoList,
						    placeholder: '二级工种'
						});
					}
				}
			});
		}
		
	});
	// 初始化创建人
	initCreateUserSelect("createUser");
	
	$('.J-datepicker-range').datePicker({
        hasShortcut: true,
        format : 'YYYY-MM-DD HH:mm',
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
	
	// 进入页面自动查询
	query(1,1);
});

function resetQuery(){
	$("#createUser").val("");
	$("#source").val("");
	$("#company").val("");
	$("#beginTime").val("");
	$("#endTime").val("");
	$("#minAge").val("");
	$("#maxAge").val("");
	$("#sex").val("");
	$("#degree").val("");
	$("#expectSalary").val("");
	$("#workYear").val("");
	$("#discipline").val("");
	$("#workStatus").val("");
	$("#workerName").val("");
	$("#telephone").val("");
	$("#idcard").val("");
	if($("#firstId").selectivity('data')){
		$("#firstId").selectivity('clear');
	}
	var secondId = "";
	if($("#secondId").selectivity('data')){
		$("#secondId").selectivity('clear');
	}
}

/**
 * 查询
 * @returns
 */
function query(currentPage,onPage){
	var workerName = $("#workerName").val();
	var telephone = $("#telephone").val();
	var idcard = $("#idcard").val();
	
	if(!onPage){
		var firstId = "";
		if($("#firstId").selectivity('data')){
			firstId = $("#firstId").selectivity('data').id;
		}
		var secondId = "";
		if($("#secondId").selectivity('data')){
			secondId = $("#secondId").selectivity('data').id;
		}
	}
	
	var createUser = $("#createUser").val();
	var source = $("#source").val();
	var company = $("#company").val();
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	var minAge = $("#minAge").val();
	var maxAge = $("#maxAge").val();
	var sex = $("#sex").val();
	var degree = $("#degree").val();
	var expectSalary = $("#expectSalary").val();
	var workYear = $("#workYear").val();
	var discipline = $("#discipline").val();
	var workStatus = $("#workStatus").val();
	$.ajax({
		url:"/worker/list",
		type:"get",
		data:{name:workerName,telephone:telephone,idcard:idcard,createUser:createUser,
			souce:source,firstId:firstId,secondId:secondId,beginTime:beginTime,
			minAge:minAge,maxAge:maxAge,sex:sex,degree:degree,expectSalary:expectSalary,
			workYear:workYear,company:company,discipline:discipline,workStatus:workStatus,
			endTime:endTime,pageNumber:currentPage},
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var workerArr = data.data.data;
				var tableContent="";
				for(var i=0; i<workerArr.length; i++){
					var worker = workerArr[i];
					tableContent+=  "<tr>"+
									"	<td>"+worker.name+"</td>"+
									"	<td>"+worker.telephone+"</td>"+
									"	<td>"+(worker.idcard == null ? "" : worker.idcard)+"</td>"+
									"	<td>"+worker.sexName+"</td>"+
									"	<td>"+(worker.age == null ? "" : worker.age)+"</td>"+
									"	<td>"+(worker.title == null ? "" : worker.title)+"</td>"+
									"	<td>"+(worker.jobtypeName == null ? "" : worker.jobtypeName)+"</td>";
									if(worker.experienceList != null && worker.experienceList.length > 0){
										var experience = worker.experienceList[0];
										var beginTime = experience.beginTime == null ? "暂无" : experience.beginTime;
										var endTime = experience.endTime == null ? "暂无" : experience.endTime;
										tableContent += "<td>"+experience.company+"</td><td>"+beginTime+"-"+endTime+"</td>";
									}
									else{
										tableContent += "<td></td><td></td>";
									}
									tableContent += "	<td>"+(worker.bank == null ? "" : worker.bank)+"</td>"+
									"	<td>"+(worker.bankAccount == null ? "" : worker.bankAccount)+"</td>"+
									"	<td>"+worker.workStatusName+"</td>"+
									"	<td>"+worker.createUserName+"</td>"+
									"	<td>"+worker.sourceName+"</td>"+
									"	<td>"+worker.createTime+"</td>"+
									"	<td><span class=\"des\" onClick=\"editWorker("+worker.id+")\">编辑</span>" +
									"<span class=\"delete\" onClick=\"deleteWorker("+worker.id+")\">删除</span>" +
									"<span class=\"delete\" onClick=\"downloadResume("+worker.id+")\">简历</span>" +
									"<span class=\"delete\" onClick=\"detailWorker('"+worker.id+"','"+worker.createUserName+"')\">详情</span></td>"+
									"</tr>";
				}
				$("tbody").empty().append(tableContent);
				$("#totalCount").text(data.data.totalCount+"个结果");
				$("#pagination1").pagination({
					currentPage: data.data.pageNumber,
					totalPage: data.data.pageCount,
					callback: function(current) {
						query(current);
					}
				});
			}
		}
	});
}

function editWorker(workerId){
	location.href="/worker/edit?workerId="+workerId
}
function detailWorker(workerId,createUserName){
	location.href="/worker/detail?workerId="+workerId+"&createUserName="+createUserName;
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

function initSelect(){
	var types = "expect_salary,work_status,degree,worker_souce";
	$.ajax({
		url:"/common/queryDicByTypes",
		type:"get",
		//async:true,
		dataType:"json",
		data:{types:types},
		global: false,
		success:function(data){
			if(data.code == 1){
				var all = data.data;
				// 组装期望薪资
				var expectSalaryDics = all.expect_salary;
				var contentexpectSalary = "<option value=\"\">---期望薪资---</option>";
				for(var i=0; i<expectSalaryDics.length; i++){
					var expectSalaryDic = expectSalaryDics[i];
					contentexpectSalary += "<option value=\""+expectSalaryDic.code+"\">"+expectSalaryDic.name+"</option>";
				}
				$("#expectSalary").empty().html(contentexpectSalary);
				// 组装工作状态
				var workStatusDics = all.work_status;
				var contentWorkStatus = "<option value=\"\">---工作状态---</option>";
				for(var i=0; i<workStatusDics.length; i++){
					var workStatusDic = workStatusDics[i];
					contentWorkStatus += "<option value=\""+workStatusDic.code+"\">"+workStatusDic.name+"</option>";
				}
				$("#workStatus").empty().html(contentWorkStatus);
				// 组装学历
				var degreeDics = all.degree;
				var contentdegree = "<option value=\"\">---学历---</option>";
				for(var i=0; i<degreeDics.length; i++){
					var degreeDic = degreeDics[i];
					contentdegree += "<option value=\""+degreeDic.code+"\">"+degreeDic.name+"</option>";
				}
				$("#degree").empty().html(contentdegree);
				// 组装来源
				var sourceDics = all.worker_souce;
				var contentsource = "<option value=\"\">---来源---</option>";
				for(var i=0; i<sourceDics.length; i++){
					var sourceDic = sourceDics[i];
					contentsource += "<option value=\""+sourceDic.code+"\">"+sourceDic.name+"</option>";
				}
				$("#source").empty().html(contentsource);
			}
		}
	});
}

function initDegreeSelect(id,type){
	$.ajax({
		url:"/common/queryDicByType",
		type:"get",
		dataType:"json",
		data:{type:type},
		global: false,
		success:function(data){
			if(data.code == 1){
				var dics = data.data;
				var content = "<option value=\"\">---学历要求---</option>";
				for(var i=0; i<dics.length; i++){
					var dic = dics[i];
					content += "<option value=\""+dic.code+"\">"+dic.name+"</option>";
				}
				$("#"+id).empty().html(content);
			}
		}
	});
}

function initWorkStatusSelect(id,type){
	$.ajax({
		url:"/common/queryDicByType",
		type:"get",
		dataType:"json",
		data:{type:type},
		global: false,
		success:function(data){
			if(data.code == 1){
				var dics = data.data;
				var content = "<option value=\"\">---工作状态---</option>";
				for(var i=0; i<dics.length; i++){
					var dic = dics[i];
					content += "<option value=\""+dic.code+"\">"+dic.name+"</option>";
				}
				$("#"+id).empty().html(content);
			}
		}
	});
}
function initExpectSalarySelect(id,type){
	$.ajax({
		url:"/common/queryDicByType",
		type:"get",
		dataType:"json",
		data:{type:type},
		global: false,
		success:function(data){
			if(data.code == 1){
				var dics = data.data;
				var content = "<option value=\"\">---薪资要求---</option>";
				for(var i=0; i<dics.length; i++){
					var dic = dics[i];
					content += "<option value=\""+dic.code+"\">"+dic.name+"</option>";
				}
				$("#"+id).empty().html(content);
			}
		}
	});
}

function initCreateUserSelect(id){
	$.ajax({
		url:"/user/queryByRealName",
		type:"get",
		dataType:"json",
		global: false,
		success:function(data){
			if(data.code == 1){
				var dics = data.data;
				var content = "<option value=\"\">---录入人---</option>";
				for(var i=0; i<dics.length; i++){
					var dic = dics[i];
					content += "<option value=\""+dic.id+"\">"+dic.realName+"</option>";
				}
				$("#"+id).empty().html(content);
			}
		}
	});
}

function initFirstIdSelect(id){
	$.ajax({
		url:"/jobType/queryRootJobType",
		type:"get",
		dataType:"json",
		global: false,
		success:function(data){
			if(data.code == 1){
				var dics = data.data;
				var infoList = [];
				for(var i=0; i<dics.length; i++){
					var dic = dics[i];
					var info = {};
					info.id = dic.id;
					info.text = dic.name;
					infoList.push(info);
				}
				$("#"+id).selectivity({
					allowClear: true,
				    items: infoList,
				    placeholder: '一级工种'
				});
				$("#secondId").selectivity({
					allowClear: true,
				    items: [],
				    placeholder: '二级工种'
				});
			}
		}
	});
}

function deleteWorker(workerId){
	var b = confirm("是否删除该人才？");
	if(b){
		$.ajax({
			url:"/worker/deleteWorker",
			type:"get",
			dataType:"json",
			data:{workerId:workerId},
			success:function(data){
				if(data.code == 1){
					alert("删除人才成功！");
					query(1);
				}
				else{
					alert("删除人才失败！原因："+data.msg);
				}
			}
		});
	}
}

function downloadResume(workerId){
	window.open("/word/export?workerId="+workerId);
}