$(function(){
	// 超时跳到登录
	$(document).ajaxSuccess(function(event, xhr, settings){
		if(xhr.responseJSON.code == 1002){
			top.location.href="/";
		}
	});
	// 进入页面自动查询
	query(1);
	//按钮事件绑定
	$("#query").click(function(){
		query(1);
	});
	$("#add-worker").click(function(){
		location.href="/worker/add"
	});
	// 初始化来源
	initSelect("source","worker_souce");
	// 初始化一级工种
	initFirstIdSelect("firstId");
	// 二级工种联动
	$("#firstId").change(function(){
		var firstId = this.value;
		if(!firstId){
			$("#"+id).empty().html("<option value=\"\">---请选择---</option>");
		}
		$.ajax({
			url:"/jobType/queryByParentId",
			type:"get",
			dataType:"json",
			data:{parentId:firstId},
			success:function(data){
				if(data.code == 1){
					var dics = data.data;
					var content = "<option value=\"\">---请选择---</option>";
					for(var i=0; i<dics.length; i++){
						var dic = dics[i];
						content += "<option value=\""+dic.id+"\">"+dic.name+"</option>";
					}
					$("#secondId").empty().html(content);
				}
			}
		});
	});
	// 初始化创建人
	initCreateUserSelect("createUser");
	
	$('.J-datepicker-range').datePicker({
        hasShortcut: true,
        isRange: true,
        shortcutOptions: [{
          name: '昨天',
          day: '-1,-1',
          time: '00:00:00,23:59:59'
        },{
          name: '最近一周',
          day: '-7,0',
          time:'00:00:00,'
        }, {
          name: '最近一个月',
          day: '-30,0',
          time: '00:00:00,'
        }, {
          name: '最近三个月',
          day: '-90, 0',
          time: '00:00:00,'
        }]
      });
});

/**
 * 查询
 * @returns
 */
function query(currentPage){
	var workerName = $("#workerName").val();
	var telephone = $("#telephone").val();
	var idcard = $("#idcard").val();
	var firstId = $("#firstId").val();
	var secondId = $("#secondId").val();
	var createUser = $("#createUser").val();
	var source = $("#source").val();
	//var workStatus = $("#workStatus").val();
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	$.ajax({
		url:"/worker/list",
		type:"get",
		data:{name:workerName,telephone:telephone,idcard:idcard,createUser:createUser,
			souce:source,firstId:firstId,secondId:secondId,beginTime:beginTime,
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
									"	<td>"+worker.idcard+"</td>"+
									"	<td>"+worker.sexName+"</td>"+
									"	<td>"+worker.age+"</td>"+
									"	<td>"+(worker.title == null ? "" : worker.title)+"</td>"+
									"	<td>"+worker.jobtypeName+"</td>"+
									"	<td>"+worker.workStatusName+"</td>"+
									"	<td>"+worker.createUserName+"</td>"+
									"	<td>"+worker.sourceName+"</td>"+
									"	<td>"+worker.createTime+"</td>"+
									"	<td><span class=\"des\" onClick=\"editWorker("+worker.id+")\">编辑</span>" +
									"<span class=\"delete\" onClick=\"deleteWorker("+worker.id+")\">删除</span>" +
									"<span class=\"delete\" onClick=\"downloadResume("+worker.id+")\">导出简历</span>" +
									"<span class=\"delete\" onClick=\"detailWorker("+worker.id+")\">详情</span></td>"+
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
function detailWorker(workerId){
	location.href="/worker/detail?workerId="+workerId
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

function initCreateUserSelect(id){
	$.ajax({
		url:"/user/queryByRealName",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var dics = data.data;
				var content = "<option value=\"\">---请选择---</option>";
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
		success:function(data){
			if(data.code == 1){
				var dics = data.data;
				var content = "<option value=\"\">---请选择---</option>";
				for(var i=0; i<dics.length; i++){
					var dic = dics[i];
					content += "<option value=\""+dic.id+"\">"+dic.name+"</option>";
				}
				$("#"+id).empty().html(content);
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