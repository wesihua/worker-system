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
	querySignDetail();
	
});

function querySignDetail(){
	var demandId = $("input:hidden[name='demandId']").val();
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
				
				if(state == 1){
					$(".signing-botton").show();
				}
				
				// 处理中
				if(state == 1){
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
					
					if(isHaveAssign){
						
						$(".signing-botton").show();
						
						tableContent += "<tr>"+
										"	<th>用工工种</th>"+
										"	<th>到岗日期</th>"+
										"	<th>工作地区</th>"+
										"	<th>用工要求</th>"+
										"	<th>用工人数</th>"+
										"	<th>签约人数</th>"+
										"	<th>已分配人数</th>"+
										"	<th>操作</th>"+
										"</tr>";
						
						for(var i=0; i<firmArr.length; i++){
							var firm = firmArr[i];
							tableContent+=  "<tr>"+
											"	<td>"+firm.jobTypeName+"</td>"+
											"	<td>"+firm.requireTime+"</td>"+
											"	<td>"+firm.workAreaName +"</td>"+
											"	<td>"+firm.requirement+"</td>"+
											"	<td>"+firm.workerCount+"</td>"+
											"	<td><span class=\"des\" onClick=\"showSigningList("+firm.id+")\">"+firm.signingCount+"</span></td>"+
							                "	<td><span class=\"des\" onClick=\"showAssignList("+firm.id+")\">"+firm.assignCount+"</span></td>"+
							                "	<td><span class=\"des\" onClick=\"assignWorker("+firm.id+")\">分配用工</span></td>"+
											"</tr>";
						}
						
					} else {
						
						tableContent += "<tr>"+
										"	<th>用工工种</th>"+
										"	<th>到岗日期</th>"+
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
											"	<td>"+firm.workAreaName +"</td>"+
											"	<td>"+firm.requirement+"</td>"+
											"	<td>"+firm.workerCount+"</td>"+
											"	<td><span class=\"des\" onClick=\"showSigningList("+firm.id+")\">"+firm.signingCount+"</span></td>"+
											"	<td><span class=\"des\" onClick=\"assignWorker("+firm.id+")\">分配用工</span></td>"+
											"</tr>";
						}
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
			    		var content = "<li class=\"order-worder\" id=\"check_"+id+" data="+ id +">"+
								            "<div class=\"select-name\">"+name+"（"+ idcard +"）</div>"+
								            "<div class=\"select-title\">"+
								                "<span class=\"a\">签约月工资（元)</span>"+
								                "<span class=\"b\">到岗日期</span>"+
								               " <span class=\"c\">业务收入（元）</span>"+
								            "</div>"+
								            "<div class=\"select-input\">"+
								                "<input name='xiaoshu' class=\"a signSalary\" type=\"text\" value=''/>"+
								                "<div class=\"c-datepicker-date-editor c-datepicker-single-editor J-yearMonthPicker-single mt10\">"+
												"<input autocomplete=\"off\" type=\"text\" placeholder=\"选择到岗日期\"  class=\"b arriveWorkTime\" id=\"requireTime\" name=\"createTime\"/>"+
										        "</div>"+
								                "<input name='xiaoshu' class=\"c businessIncome\" type=\"text\" value=''/>"+
								           "</div>"+
								        "</li>";
			    	}else{
			    		parent.$("#check_"+id+"").remove();
			    	}
			    	
			    	parent.$(".result-area ul").append(content);
			    	
			    	parent.$(".result-area ul").on("keyup afterpaste","input[name='xiaoshu']",function(){
			    		
			    		if(this.value.length==1){
			    			this.value=this.value.replace(/[^1-9]/g,'')
			    		}else{
			    			this.value = this.value.replace(/[^\d.]/g,''); 
			    			this.value = this.value.replace(/^\./g,'');
			    			this.value = this.value.replace(/\.{2,}/g,'');
			    			this.value = this.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');
			    			this.value = this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
			    		}
			    	});
			    
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
									querySignDetail();
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
 * 查询签约信息
 * @param demandId
 * @returns
 */
function signingDetail(){
	var demandId = $("input[name=demandId]").val();

	$.ajax({
		url:"/demand/demandAssignList",
		type:"get",
		dataType:"json",
		data:{demandId:demandId},
		success:function(data){
			if(data.code == 1){
                $("#companyName").text("客户名称:" + data.data.demand.companyName );
                $("#demandNumber").text("需求单号:" + data.data.demand.demandNumber);
                $("#workerCount").text("本次签约人数:" + data.data.workerCount);
                $("#income").text("本次签约总金额:" + data.data.income +"(元)");
               
                var firmArr = data.data.orderWorkerList;
                var tableContent = "";
      
                tableContent += "<tr>" +
                    "	<th>用工姓名</th>" +
                    "	<th>身份证号</th>" +
                    "	<th>联系电话</th>" +
                    "	<th>擅长工种</th>" +
                    "	<th>签约月工资（元）</th>" +
                    "	<th>到岗日期</th>" +
                    "	<th width='120'>业务收入（元）</th>" +
                    "</tr>";

                for (var i = 0; i < firmArr.length; i++) {
                    var firm = firmArr[i];
                    var worker = firm.worker;
                    tableContent += "<tr>" +
                        "	<td>" + worker.name + "</td>" +
                        "	<td>" + worker.idcard + "</td>" +
                        "	<td>" + worker.telephone + "</td>" +
                        "	<td>" + (worker.jobtypeName == null ? "" : worker.jobtypeName) + "</td>" +
                        "	<td>" + firm.signSalary + "</td>" +
                        "	<td>" + (firm.arriveWorkTime == null ? "" : firm.arriveWorkTime) + "</td>" +
                        "	<td width='120'>" + firm.businessIncome + "</td>" +
                        "</tr>";
                }
                
                
                if(firmArr.length > 0){
                	openDialog("signing-detail");
                    parent.$("#signing-detail-table").empty().append(tableContent);
                    
                    parent.$('#confirm-signing').click(function(){
                    	signing();
                    });
                    
                    
                }else{
                	alert("暂无分配用工！");
                }
			}
			else{
				alert("查询待签约详情失败！原因："+data.msg);
			}
		}
	});
}


/**
 * 签约
 * @param demandId
 * @returns
 */
function signing() {
	var demandId = $("input[name=demandId]").val();

	$.ajax({
		url : "/demand/signing",
		type : "get",
		dataType : "json",
		data : {
			demandId : demandId
		},
		success : function(data) {
			if (data.code == 1) {
				alert("签约成功！");
				toSignedPage();
			} else {
				alert("签约失败！原因：" + data.msg);
			}
		}
	});

}


function toSignedPage(){
	window.location.href="/signing/signed";
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
        url: "/demand/orderWorkerAssignList",
        type: "get",
        data: { demandJobId: jobTypeId},
        dataType: "json",
        success: function (data) {
            if (data.code == 1) {

                var firmArr = data.data.orderWorkerList;
                var tableContent = "";
                $("#order-worker-list-span").text("本次已分配用工");
               // $(".worker-count").text("（需求"+ data.data.demandJob.workerCount +"人）");
                tableContent += "<tr>" +
                    "	<th>用工姓名</th>" +
                    "	<th>籍贯</th>" +
                    "	<th>身份证号</th>" +
                    "	<th>联系电话</th>" +
                    "	<th>擅长工种</th>" +
                    "	<th>签约月工资（元）</th>" +
                    "	<th>到岗日期</th>" +
                    "	<th width='80'>业务收入（元）</th>" +
                    "	<th width='150'>操作</th>" +
                    "</tr>";

                for (var i = 0; i < firmArr.length; i++) {
                    var firm = firmArr[i];
                    var worker = firm.worker;
                    tableContent += "<tr>" +
                        "   <input id=\"id\" type=\"hidden\" value="+ firm.id +">" +
                        "	<td id=\"id\">" + worker.name + "</td>" +
                        "	<td>" + worker.birthplaceName + "</td>" +
                        "	<td>" + worker.idcard + "</td>" +
                        "	<td>" + worker.telephone + "</td>" +
                        "	<td>" + (worker.jobtypeName == null ? "" : worker.jobtypeName) + "</td>" +
                        "	<td id=\"signSalary\">" + firm.signSalary + "</td>" +
                        "	<td id=\"arriveWorkTime\">" + (firm.arriveWorkTime == null ? "" : firm.arriveWorkTime) + "</td>" +
                        "	<td id=\"businessIncome\" width='120'>" + firm.businessIncome + "</td>" +
                        "   <td><span class='delete' id='delete-worker'>移除</span><span class='edit' id='edit-worker'>编辑</span><span class='edit' style ='display:none' id='confirm-edit'>确定</span></td>" +
                        "</tr>";
                    
                    // <span class=\"edit\" id=\"edit-worker\">编辑</span>
                }
                
                if(firmArr.length > 0){
                	openDialog("yifenpei-list");
                    parent.$("#worker-list-table").empty().append(tableContent);
                }else{
                	alert("暂无分配用工！");
                }
                
                // 编辑 签约薪水
//                parent.$("#worker-list-table").on("dblclick","#signSalary",function(){
//                	var id = $(this).parents('tr').children('#id').val();
//                	var signSalary = $(this).text();
//                	var signSalaryTd = $(this);
//
//            		// 签约薪水变成可输入框
//            		var signSalaryTxt = $("<input type='text'>").val(signSalary);
//            		signSalaryTxt.blur(function(){
//            			// 失去焦点，保存值。
//            			var newSignSalary = $(this).val();
//            			
//            			if (!newSignSalary) {
//            				alert("签约工资不能为空！");
//            				return;
//            			}
//            			
//            			signSalaryTxt.remove();
//            		    // 移除文本框,显示新值
//            			signSalaryTd.text(newSignSalary);
//            		    if(signSalary != newSignSalary){
//            		    	updateOrderWorker(id,newSignSalary,null,null);
//            		    }
//            			
//            		       
//            		    });
//            		signSalaryTd.text("");
//            		signSalaryTd.append(signSalaryTxt);
//                });
                
                
//                parent.$("#worker-list-table").on("dblclick","#businessIncome",function(){
//            	
//                	var id = $(this).parents('tr').children('#id').val();
//                	var businessIncome = $(this).text();
//                	var businessIncomeTd = $(this);
//                	
//                	var businessIncomeTxt = $("<input type='text'>").val(businessIncome);
//                	businessIncomeTxt.blur(function(){
//                		// 失去焦点，保存值。
//                		var newText = $(this).val();
//                		
//                		if (!newText) {
//            				alert("业务收入不能为空！");
//            				return;
//            			}
//                		$(this).remove();
//                	    // 移除文本框,显示新值
//                		businessIncomeTd.text(newText);
//                		if(newText != businessIncome){
//                			updateOrderWorker(id,null,newText,null);
//                		}
//                		   
//                	    });
//                	businessIncomeTd.text("");
//                	businessIncomeTd.append(businessIncomeTxt);
//                });
                
                // 编辑时间
//                parent.$("#worker-list-table").on("dblclick","#arriveWorkTime",function(){
//                	
//                	var id = $(this).parents('tr').children('#id').val();
//                	var arriveWorkTime = $(this).text();
//                	var arriveWorkTimeTd = $(this);
//                	
//                	var arriveWorkTimeDev = $('<div class="c-datepicker-date-editor c-datepicker-single-editor J-yearMonthPicker-single mt10">');
//                 	
//                 	var arriveWorkTimeTxt = $("<input type='text' placeholder='选择到岗日期'  onchange=''>").val(arriveWorkTime);
//                 	arriveWorkTimeTd.text("");
//                 	arriveWorkTimeDev.append(arriveWorkTimeTxt);
//                 	arriveWorkTimeTd.append(arriveWorkTimeDev);
//                 	parent.$('.J-yearMonthPicker-single').datePicker({
//                 		format : 'YYYY-MM-DD'
//                 	});
//                 	arriveWorkTimeTxt.change(function(){
//                 		// 
//                 		var newText = $(this).val();
//                 		$(this).remove();
//                 	    // 移除文本框,显示新值
//                 		arriveWorkTimeTd.text(newText);
//                 		
//                 		updateOrderWorker(id,null,null,newText);
//                 		
//                 	    });
//                	
//                });
               
                //#
                
                parent.$("#worker-list-table").on("click","#delete-worker",function(){
                	deleteOrderWorker($(this).parents('tr').children('#id').val());
                });
                
                // 点击编辑
                parent.$("#worker-list-table").on("click","#edit-worker",function(){
                	 $(this).parents('td').children('#confirm-edit').show();
                	 $(this).hide();

                	var signSalary = $(this).parents('tr').children('#signSalary').text();
                	var signSalaryTd = $(this).parents('tr').children('#signSalary');

            		// 签约薪水变成可输入框
            		var signSalaryTxt = $("<input id='signSalary-input' type='text'>").val(signSalary);
            		signSalaryTd.text("");
            		signSalaryTd.append(signSalaryTxt);
            		
                	var businessIncome = $(this).parents('tr').children('#businessIncome').text();
                	var businessIncomeTd = $(this).parents('tr').children('#businessIncome');
                	var businessIncomeTxt = $("<input id='businessIncome-input' type='text'>").val(businessIncome);
                	businessIncomeTd.text("");
                	businessIncomeTd.append(businessIncomeTxt);
                	
                	var arriveWorkTime = $(this).parents('tr').children('#arriveWorkTime').text();
                	var arriveWorkTimeTd = $(this).parents('tr').children('#arriveWorkTime');
                	
                	var arriveWorkTimeDev = $('<div class="c-datepicker-date-editor c-datepicker-single-editor J-yearMonthPicker-single mt10">');
                 	
                 	var arriveWorkTimeTxt = $("<input id='arriveWorkTime-input' type='text' placeholder='选择到岗日期'  onchange=''>").val(arriveWorkTime);
                 	arriveWorkTimeTd.text("");
                 	arriveWorkTimeDev.append(arriveWorkTimeTxt);
                 	arriveWorkTimeTd.append(arriveWorkTimeDev);
                 	parent.$('.J-yearMonthPicker-single').datePicker({
                 		format : 'YYYY-MM-DD'
                 	});
                });
                
                
             
                // 确认编辑
            	parent.$("#worker-list-table").on("click","#confirm-edit",function(){
            		
            		var id = $(this).parents('tr').children('#id').val();
	                	
	                // 隐藏确定  恢复编辑按钮	
	               	$(this).parents('td').children('#edit-worker').show();
	               	$(this).hide();
	               	 // 取值
	               	var signSalary = $(this).parents('tr').find('#signSalary-input').val();
	               	var signSalaryTd = $(this).parents('tr').children('#signSalary');
	               	
	               	$(this).parents('tr').children('#signSalary-input').remove();
	               	signSalaryTd.text(signSalary);
	
	               	var businessIncome = $(this).parents('tr').find('#businessIncome-input').val();
	               	$(this).parents('tr').children('#businessIncome').text(businessIncome);;
	               	$(this).parents('tr').children('#businessIncome-input').remove();
	               	
	               	var arriveWorkTime = $(this).parents('tr').find('#arriveWorkTime-input').val();
	               	var arriveWorkTimeTd = $(this).parents('tr').children('#arriveWorkTime');
	               	$(this).parents('tr').children('#arriveWorkTime-input').remove();
	               	$(this).parents('tr').children('#arriveWorkTime').text(arriveWorkTime);
	               	
	               	// 加校验
	               	if (!signSalary) {
        				alert("签约工资不能为空！");
        				return;
        			}
	               		
               		if (!businessIncome) {
        				alert("业务收入不能为空！");
        				return;
        			}
	               	
	               	
	               	updateOrderWorker(id,signSalary,businessIncome,arriveWorkTime);
               });
            }
        }
    });
}

function showSigningList(jobTypeId){
	
    $.ajax({
        url: "/demand/orderWorkerSigningList",
        type: "get",
        data: { demandJobId: jobTypeId},
        dataType: "json",
        success: function (data) {
            if (data.code == 1) {

                var firmArr = data.data.orderWorkerList;
                var tableContent = "";
                $("#order-worker-list-span").text("已签约用工");
                tableContent += "<tr>" +
                    "	<th>用工姓名</th>" +
                    "	<th>籍贯</th>" +
                    "	<th>身份证号</th>" +
                    "	<th>联系电话</th>" +
                    "	<th>擅长工种</th>" +
                    "	<th>签约月工资（元）</th>" +
                    "	<th>到岗日期</th>" +
                    "	<th width='120'>业务收入（元）</th>" +
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

/**
 * 删除已分配用工
 * @param orderWorkerId
 * @returns
 */
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
					querySignDetail(1);
				}
				else{
					alert("移除用工失败！原因："+data.msg);
				}
				
				// 关闭弹框
		    	top.closeDialog();
			}
		});
	}

}


function updateOrderWorker(id,signSalary,businessIncome,arriveWorkTime) {
	
	var orderWorker = {};
	
	orderWorker.signSalary=signSalary;
	orderWorker.businessIncome=businessIncome;
	
	orderWorker.arriveWorkTime = arriveWorkTime;
	
	
	orderWorker.id =id;
	
	$.ajax({
		url : "/demand/editOrderWorker",
		type : "post",
		dataType : "json",
		data : {json : JSON.stringify(orderWorker)},
		success : function(data) {
			if (data.code != 1) {
				alert("更新用工信息失败！原因：" + data.msg);
			}
		}
	});

	/**
	 * 
	 
	var $that = $(obj);
	
	var signSalaryTd = $that.parents('tr').children('#signSalary');
	var arriveWorkTimeTd = $that.parents('tr').children('#arriveWorkTime');
	var businessIncomeTd = $that.parents('tr').children('#businessIncome');
	
	var id = $that.parents('tr').children('#id').val();
	var signSalary = signSalaryTd.text();
	var arriveWorkTime = arriveWorkTimeTd.text();
	var businessIncome = businessIncomeTd.text();
	
	// 初始化时间控件
	parent.$('.J-yearMonthPicker-single').datePicker({
		format : 'YYYY-MM-DD'
	});
	*/
	
	
	
	
	/**
	 * 
	 
	// 业务收入
	
	
	
	
	


	top.$(".complete-edit").click(function() {

		var orderWorker = {};
		var signSalary_ = parent.$("#worker-signSalary").val();
		var arriveWorkTime_ = parent.$("#worker-arriveWorkTime").val();
		var businessIncome_ = parent.$("#worker-businessIncome").val();
		
		if (!signSalary_) {
			alert("签约工资不能为空！");
			return false;
		}
		if (!businessIncome_) {
			alert("业务收入不能为空！");
			return false;
		}
		if (!arriveWorkTime_) {
			alert("到岗时间不能为空！");
			return false;
		}
		top.closeDialog();
		

	});
	*/
}




