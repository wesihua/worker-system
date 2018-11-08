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
	$("#add-worker").click(function(){
		location.href="/worker/add"
	});
	$("#download").click(function(){
		var workerName = $("#workerName").val();
		var telephone = $("#telephone").val();
		var idcard = $("#idcard").val();
		var firstId = $("#firstId").selectivity('data').id;
		var secondId = $("#secondId").selectivity('data').id;
		var createUser = $("#createUser").val();
		var source = $("#source").val();
		//var workStatus = $("#workStatus").val();
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
		window.open("/worker/export?name="+workerName+"&telephone="+telephone+"" +
				"&idcard="+idcard+"&firstId="+firstId+"&secondId="+secondId+"" +
				"&createUser="+createUser+"&source="+source+"&beginTime="+beginTime+"&endTime="+endTime);
	});
	// 初始化来源
	initSelect("source","worker_souce");
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
	$.ajax({
		url:"/worker/list",
		type:"get",
		data:{name:workerName,telephone:telephone,idcard:idcard,createUser:createUser,
			souce:source,firstId:firstId,secondId:secondId,beginTime:beginTime,company:company,
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
									"	<td>"+(worker.jobtypeName == null ? "" : worker.jobtypeName)+"</td>"+
									"	<td>"+worker.workStatusName+"</td>"+
									"	<td>"+worker.createUserName+"</td>"+
									"	<td>"+worker.sourceName+"</td>"+
									"	<td>"+worker.createTime+"</td>"+
									"	<td><span class=\"des\" onClick=\"editWorker("+worker.id+")\">编辑</span>" +
									"<span class=\"delete\" onClick=\"deleteWorker("+worker.id+")\">删除</span>" +
									"<span class=\"delete\" onClick=\"downloadResume("+worker.id+")\">导出简历</span>" +
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

function initSelect(id,type){
	$.ajax({
		url:"/common/queryDicByType",
		type:"get",
		dataType:"json",
		data:{type:type},
		global: false,
		success:function(data){
			if(data.code == 1){
				var dics = data.data;
				var content = "<option value=\"\">---请选择来源---</option>";
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
				var content = "<option value=\"\">---请选择创建人---</option>";
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