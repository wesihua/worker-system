$(function () {
    // 超时跳到登录
    $(document).ajaxSuccess(function (event, xhr, settings) {
        if (xhr.responseJSON.code == 1002) {
            top.location.href = "/";
        }
    });
    
   
    
    $(document).bind("ajaxSend", function () {
		parent.$("#loading").show();
    }).bind("ajaxComplete", function () {
    	parent.$("#loading").hide();
    });

    $("#addWorder").click(function () {
        var jobTypeId = $("input[name=jobTypeId]").val();
        window.location.href = "/signing/addWorker?jobTypeId=" + jobTypeId;
    });
    
    var source = $("input[name=source]").val();
    if( source == 1){
    	 $("#addWorder").show();
    }
    
    query(1);
});

function query(pageNum) {
    var jobTypeId = $("input[name=jobTypeId]").val();

    $.ajax({
        url: "/demand/orderWorkerList",
        type: "post",
        data: { demandJobId: jobTypeId ,pageNumber:pageNum},
        dataType: "json",
        success: function (data) {
            if (data.code == 1) {

                var firmArr = data.data.pageData.data;
                var tableContent = "";
                $(".order-work-job-name").text(data.data.demandJob.jobTypeName);
                $(".worker-count").text("（需求"+ data.data.demandJob.workerCount +"人）");
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
                    var worder = firm.worker;
                    tableContent += "<tr>" +
                        "	<td>" + worder.name + "</td>" +
                        "	<td>" + worder.birthplaceName + "</td>" +
                        "	<td>" + worder.idcard + "</td>" +
                        "	<td>" + worder.telephone + "</td>" +
                        "	<td>" + worder.jobtypeName + "</td>" +
                        "	<td>" + firm.signSalary + "</td>" +
                        "	<td>" + firm.arriveWorkTime + "</td>" +
                        "	<td width='120'>" + firm.businessIncome + "</td>" +
                        "   <td><span class=\"des\" onClick=\"deleteOrderWorker(" + firm.id + ")\">移除</span><span class=\"jiedan\" onClick=\"updateOrderWorker(" + firm.id + ",'"+ worder.name + "'," + firm.signSalary + ",'" + firm.arriveWorkTime + "'," + firm.businessIncome +")\">编辑</span></td>" +
                        "</tr>";
                }
            }
            
            $("table").empty().append(tableContent);
            $("#totalCount").text(data.data.pageData.totalCount+"个结果");
            $("#pagination1").pagination({
            	currentPage: data.data.pageData.pageNumber,
            	totalPage: data.data.pageData.pageCount,
            	callback: function(current) {
            		query(current);
            	}
            });
        }
    });



    // 初始化时间控件
//    $('.J-yearMonthPicker-single').datePicker({
//        format: 'YYYY-MM-DD'
//    });
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

function updateOrderWorker(id, name, signSalary, arriveWorkTime, businessIncome) {
	openDialog("worker-edit");
	// 初始化时间控件
	parent.$('.J-yearMonthPicker-single').datePicker({
		format : 'YYYY-MM-DD'
	});

	parent.$("#worker-name").val(name);
	parent.$("#worker-signSalary").val(signSalary);
	parent.$("#worker-arriveWorkTime").val(arriveWorkTime);
	parent.$("#worker-businessIncome").val(businessIncome);

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
		
		var orderWorker = {};
		orderWorker.signSalary=signSalary_;
		orderWorker.arriveWorkTime=arriveWorkTime_;
		orderWorker.businessIncome=businessIncome_;
		orderWorker.id =id;
		
		$.ajax({
			url : "/demand/editOrderWorker",
			type : "post",
			dataType : "json",
			data : {json : JSON.stringify(orderWorker)},
			success : function(data) {
				if (data.code == 1) {
					top.closeDialog();
					alert("更新用工信息成功！");
					query(1);
				} else {
					alert("更新用工信息失败！原因：" + data.msg);
				}
			}
		});

	});
}



/**
 * 打开弹窗
 * @returns
 */
function openDialog(id) {
    var content = $("#" + id).html();
    top.$("#dialog").html(content);
    top.$("#dialog").fadeIn(300);
    // 因为弹窗页面是重新渲染到top页面的。所以事件绑定只能在渲染之后。否则不起作用！
    top.$(".cancel-edit").click(function () {
        top.closeDialog();
    });
//    top.$(".complete-edit").click(function () {
//        top.closeDialog();
//    });
}

