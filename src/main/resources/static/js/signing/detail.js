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
	var source = $("input:hidden[name='source']").val();
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
				
				if(state == 2 || state == 3){
					$(".undertokeTime").parent().show();
					$(".totalIncome").parent().show();
					$(".undertokeUserName").parent().show();
				}
				
				if(state == 1 && source == 1){
					$(".signing-botton").show();
				}
				
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
				
				if(state == 1 && source == 0){
					tableContent+= "<tr>"+
									"	<th>用工工种</th>"+
									"	<th>到岗日期</th>"+
									"	<th>月工资（元）</th>"+
									"	<th>工作地区</th>"+
									"	<th>用工要求</th>"+
									"	<th>用工人数</th>"+
									"	<th>已分配人数</th>"+
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
										"</tr>";
					}
				}
				
				if(state == 1 && source == 1){
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
										"	<td><span class=\"des\" onClick=\"showAssignList("+firm.id+")\">"+firm.assignCount+"</span></td>"+
										"	<td><span class=\"des\" onClick=\"assignWorker("+firm.id+")\">分配用工</span></td>"+
										"</tr>";
					}
				}
				
				// 已签约
				if(state == 2){
					// 查看是否有已分配人数
					var isHaveAssign = false;
					for(var i=0; i<firmArr.length; i++){
						var firm = firmArr[i];
						if(firm.assignCount > 0){
							isHaveAssign = true;
							break;
						}
					}
					
					tableContent+= "<tr>"+
									"	<th>用工工种</th>"+
									"	<th>到岗日期</th>"+
									"	<th>工作地区</th>"+
									"	<th>用工要求</th>"+
									"	<th>用工人数</th>"+
									"	<th>签约人数</th>";
					if(isHaveAssign){
						tableContent+= "	<th>已分配签约人数</th>";
						$(".signing-botton").show();
					}
					tableContent+=	"	<th>操作</th>"+
									"</tr>";
					for(var i=0; i<firmArr.length; i++){
						var firm = firmArr[i];
						tableContent+=  "<tr>"+
										"	<td>"+firm.jobTypeName+"</td>"+
										"	<td>"+firm.requireTime+"</td>"+
										"	<td>"+firm.workAreaName +"</td>"+
										"	<td>"+firm.requirement+"</td>"+
										"	<td>"+firm.workerCount+"</td>"+
										"	<td><span class=\"des\" onClick=\"showAssignList("+firm.id+")\">"+firm.signingCount+"</span></td>";
						if(isHaveAssign){
							tableContent+="	<td><span class=\"des\" onClick=\"showAssignList("+firm.id+")\">"+firm.assignCount+"</span></td>";
						}
						tableContent+= "	<td><span class=\"des\" onClick=\"assignWorker("+firm.id+")\">分配用工</span></td>"+
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
										"	<td><span class=\"des\" onClick=\"workerList("+firm.id+")\">查看签约列表</span></td>"+
										"</tr>";
					}
				}
				$("#jobType-list-table").empty().append(tableContent);
			}
		}
	});
}

/**
 * 分配用工
 */
function assignWorker(jobTypeId){
	// 打开弹框
	openDialog("add-worker-box-wp-div");
	
	// 搜索工人 按钮
	parent.$(".select-worker").click(function(){
    	queryWorkerList(1);
    });
	
	queryWorkerList(1);
	
	$("input[name='jobTypeId']").val(jobTypeId)
	
}

// 查工人
function queryWorkerList(pageNum){


    var workerName = parent.$("#workerName").val();
	var telephone = parent.$("#telephone").val();
	var idcard = parent.$("#idcard").val();

    $.ajax({
		url:"/worker/list",
		type:"get",
		data:{name:workerName,telephone:telephone,idcard:idcard,pageNumber:pageNum},
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var workerArr = data.data.data;
				var tableContent="";
				for(var i=0; i<workerArr.length; i++){
					var worker = workerArr[i];
					tableContent+=  "<tr>"+
									"	<td class='worker-name'>"+worker.name+"</td>"+
									"	<td class='worker-idcard'>"+worker.idcard+"</td>"+
									"	<td>"+worker.birthplaceName+"</td>"+
									"	<td>"+(worker.jobtypeName == null ? "" : worker.jobtypeName)+"</td>"+
									"	<td>"+worker.expectSalaryName+"</td>"+
									"	<td>"+worker.workplaceName+"</td>"+
									"	<td><input class='check-input' type=\"checkbox\" name=\"check\" value="+worker.id+" ></td>"+
									"</tr>";
				}
				
				parent.$("#query-worker-list").empty().append(tableContent);
				parent.$("#query-worker-totalCount").text(data.data.totalCount+"个结果");
				parent.$("#query-worker-pagination1").pagination({
					currentPage: data.data.pageNumber,
					totalPage: data.data.pageCount,
					callback: function(current) {
						queryWorkerList(current);
					}
				});
				
				parent.$('.check-input').click(function(){

			    	$that = $(this);
			    	var aaa = $that.prop("checked");
			    	var id = $that.val();
			    	if(aaa){
			    		var name = $that.parents('tr').find('.worker-name').text();
			    		var idcard = $that.parents('tr').find('.worker-idcard').text();
			    		var content = '<li class="order-worder" id=check_'+id+' data="'+ id +'">'+
								            '<div class="select-name">'+name+'（'+ idcard +'）</div>'+
								            '<div class="select-title">'+
								                '<span class="a">签约月工资（元)</span>'+
								                '<span class="b">到岗日期</span>'+
								               ' <span class="c">业务收入（元）</span>'+
								            '</div>'+
								            '<div class="select-input">'+
								                '<input class="a signSalary" type="text" value="" />'+
								                '<div class="c-datepicker-date-editor c-datepicker-single-editor J-yearMonthPicker-single mt10">'+
												'<input autocomplete="off" type="text" placeholder="选择到岗日期"  class="b arriveWorkTime" id="requireTime" name="createTime"/>'+
										        '</div>'+
								                '<input class="c businessIncome" type="text" value="" />'+
								           ' </div>'+
								        '</li>';
			    	}else{
			    		parent.$("#check_"+id+"").remove();
			    	}
			    	
			    	parent.$(".result-area ul").append(content);
			    
			    	parent.$('.J-yearMonthPicker-single').datePicker({
			    		format : 'YYYY-MM-DD'
			    	});
			    
			    });
				
				  parent.$('#confirm-addWorker').click(function(){
				    	
				    	var jobTypeId = $("input[name='jobTypeId']").val();
				    	var workers = [];
				    	var flag = false;
				    	parent.$(".order-worder").each(function(){
				    		if(flag){
				    			return;
				    		}
				    		$that = $(this).find(".select-input");
				    		var orderWorker = {};
				    		var signSalary = $that.find(".signSalary").val();
							if(signSalary!=""&&signSalary.length>0){
								orderWorker.signSalary=signSalary;
				    		}else{
				    			alert("签约月工资不能为空");
				    			flag=true;
				    			return;
				    		}
				    		var arriveWorkTime = $that.find(".arriveWorkTime").val();
				    		if(arriveWorkTime!=""&&arriveWorkTime.length>0){
				    			orderWorker.arriveWorkTime = arriveWorkTime;
				    		}else{
				    			alert("到岗日期不能为空");
				    			flag=true;
				    			return;
				    		}
				    		var businessIncome= $that.find(".businessIncome").val();
				    		if(businessIncome!=""&&businessIncome.length>0){
				    			orderWorker.businessIncome = businessIncome;
				    		}else{
				    			alert("业务收入不能为空");
				    			flag=true;
				    			return;
				    		}
				    		orderWorker.workerId = $(this).attr("data");
				    		workers.push(orderWorker);
				    	});
				    	
				    	if(flag){
				    		return;
				    	}
				    	if(workers.length == 0){
				    		alert("您未选择任何用工！");
			    			flag=true;
				    		return;
				    	}
				    	
				    	// 关闭弹框
				    	top.closeDialog();
				    	
						$.ajax({
							url:"/demand/addOrderWorker",
							type:"post",
							dataType:"json",
							data:{json:JSON.stringify(workers),demandJobId:jobTypeId},
							success:function(data){
								if(data.code == 1){
									alert("添加用工信息成功！");
								}
								else{
									alert("添加用工信息失败！原因："+data.msg);
								}
							}
						});
				    	
				    });
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
	window.location.href = "/signing/workerList?source=0&jobTypeId=" + jobTypeId;
}

/**
 * 签约
 * @param demandId
 * @returns
 */
function signing(){
	var demandId = $("input[name=demandId]").val();
	var b = confirm("确认签约？");
	if(b){
		$.ajax({
			url:"/demand/signing",
			type:"get",
			dataType:"json",
			data:{demandId:demandId},
			success:function(data){
				if(data.code == 1){
					alert("签约成功！");
					queryDetail();
				}
				else{
					alert("签约失败！原因："+data.msg);
				}
			}
		});
	}
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

/**
 * 
 * @param jobTypeId
 * @returns
 */
function showAssignList(jobTypeId){
	
	var pageNum = 1;
    $.ajax({
        url: "/demand/orderWorkerList",
        type: "post",
        data: { demandJobId: jobTypeId ,pageNumber:pageNum},
        dataType: "json",
        success: function (data) {
            if (data.code == 1) {

                var firmArr = data.data.pageData.data;
                var tableContent = "";
               // $(".order-work-job-name").text(data.data.demandJob.jobTypeName);
               // $(".worker-count").text("（需求"+ data.data.demandJob.workerCount +"人）");
                tableContent += "<tr>" +
                    "	<th>用工姓名</th>" +
                    "	<th>籍贯</th>" +
                    "	<th>身份证号</th>" +
                    "	<th>联系电话</th>" +
                    "	<th>擅长工种</th>" +
                    "	<th>签约月工资（元）</th>" +
                    "	<th>到岗日期</th>" +
                    "	<th width='120'>业务收入（元）</th>" +
                    "	<th width='150'>操作</th>" +
                    "</tr>";

                for (var i = 0; i < firmArr.length; i++) {
                    var firm = firmArr[i];
                    var worker = firm.worker;
                    tableContent += "<tr>" +
                        "	<td>" + worker.name + "</td>" +
                        "	<td>" + worker.birthplaceName + "</td>" +
                        "	<td>" + worker.idcard + "</td>" +
                        "	<td>" + worker.telephone + "</td>" +
                        "	<td>" + (worker.jobtypeName == null ? "" : worker.jobtypeName) + "</td>" +
                        "	<td>" + firm.signSalary + "</td>" +
                        "	<td>" + firm.arriveWorkTime + "</td>" +
                        "	<td width='120'>" + firm.businessIncome + "</td>" +
                        "   <td><span class=\"delete\" onClick=\"deleteOrderWorker(" + firm.id + ")\">移除</span><span class=\"edit\" onClick=\"updateOrderWorker(" + firm.id + ",'"+ worker.name + "'," + firm.signSalary + ",'" + firm.arriveWorkTime + "'," + firm.businessIncome +")\">编辑</span></td>" +
                        "</tr>";
                }
                
                if(firmArr.length > 0){
                	openDialog("yifenpei-list");
                    parent.$("#worker-list-table").empty().append(tableContent);
                }else{
                	alert("暂无分配用工！");
                }
                
            }
           
        }
    });
}

function showAssignList2(jobTypeId){
	
	var pageNum = 1;
    $.ajax({
        url: "/demand/orderWorkerList",
        type: "post",
        data: { demandJobId: jobTypeId ,pageNumber:pageNum},
        dataType: "json",
        success: function (data) {
            if (data.code == 1) {

                var firmArr = data.data.pageData.data;
                var tableContent = "";
               // $(".order-work-job-name").text(data.data.demandJob.jobTypeName);
               // $(".worker-count").text("（需求"+ data.data.demandJob.workerCount +"人）");
                tableContent += "<tr>" +
                    "	<th>用工姓名</th>" +
                    "	<th>籍贯</th>" +
                    "	<th>身份证号</th>" +
                    "	<th>联系电话</th>" +
                    "	<th>擅长工种</th>" +
                    "	<th>签约月工资（元）</th>" +
                    "	<th>到岗日期</th>" +
                    "	<th width='120'>业务收入（元）</th>" +
                    "	<th width='150'>操作</th>" +
                    "</tr>";

                for (var i = 0; i < firmArr.length; i++) {
                    var firm = firmArr[i];
                    var worker = firm.worker;
                    tableContent += "<tr>" +
                        "	<td>" + worker.name + "</td>" +
                        "	<td>" + worker.birthplaceName + "</td>" +
                        "	<td>" + worker.idcard + "</td>" +
                        "	<td>" + worker.telephone + "</td>" +
                        "	<td>" + (worker.jobtypeName == null ? "" : worker.jobtypeName) + "</td>" +
                        "	<td>" + firm.signSalary + "</td>" +
                        "	<td>" + firm.arriveWorkTime + "</td>" +
                        "	<td width='120'>" + firm.businessIncome + "</td>" +
                        "   <td><span class=\"des\" onClick=\"deleteOrderWorker(" + firm.id + ")\">移除</span><span class=\"jiedan\" onClick=\"updateOrderWorker(" + firm.id + ",'"+ worker.name + "'," + firm.signSalary + ",'" + firm.arriveWorkTime + "'," + firm.businessIncome +")\">编辑</span></td>" +
                        "</tr>";
                }
                
                if(firmArr.length > 0){
                	openDialog("yifenpei-list");
                    parent.$("#worker-list-table").empty().append(tableContent);
                }else{
                	alert("暂无分配用工！");
                }
                
            }
           
        }
    });
}

function deleteOrderWorker(orderWorkerId) {

	var b = confirm("是否移除该用工？");
	if(b){
		$.ajax({
			url:"/demand/deleteOrderWorker",
			type:"get",
			dataType:"json",
			data:{orderWorkerId:orderWorkerId},
			success:function(data){
				if(data.code == 1){
					alert("移除用工成功！");
					query(1);
				}
				else{
					alert("移除用工失败！原因："+data.msg);
				}
			}
		});
	}

}


