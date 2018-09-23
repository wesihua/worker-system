$(function () {
    // 超时跳到登录
    $(document).ajaxSuccess(function (event, xhr, settings) {
        if (xhr.responseJSON.code == 1002) {
            top.location.href = "/";
        }
    });


    $("#addWorder").click(function(){
        var jobTypeId = $("input[name=jobTypeId]").val();
		window.location.href = "/signing/addWorker?jobTypeId=" + jobTypeId;
    });
    

});

function query(pageNum) {

    var tableContent="";
    tableContent += "<tr>" +
        "	<th>单号</th>" +
        "	<th>录单日期</th>" +
        "	<th>企业客户</th>" +
        "	<th>用工工种</th>" +
        "	<th>用工人数</th>" +
        "	<th>状态</th>" +
        "	<th>录单人员</th>" +
        "	<th width='120'>备注说明</th>" +
        "	<th width='150'>操作</th>" +
        "</tr>";

    for (var i = 0; i < firmArr.length; i++) {
        var firm = firmArr[i];
        tableContent += "<tr>" +
            "	<td>" + firm.demandNumber + "</td>" +
            "	<td>" + firm.createTime + "</td>" +
            "	<td>" + firm.companyName + "</td>" +
            "	<td>" + firm.jobTypeName + "</td>" +
            "	<td>" + firm.workCount + "</td>" +
            "	<td>" + firm.stateName + "</td>" +
            "	<td>" + firm.createUserName + "</td>" +
            "	<td width='120'>" + firm.description + "</td>" +
            "   <td><span class=\"des\" onClick=\"demandDetail(" + firm.id + ")\">详情</span><span class=\"jiedan\" onClick=\"undertakeDemand(" + firm.id + ")\">接单</span><span class=\"delete \" onClick=\"closeDemand(" + firm.id + ")\">关单</span></td>" +
            "</tr>";
    }

    $("table").empty().append(tableContent);
}

function editWorder(){
    openDialog("worker-edit");
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
	top.$(".cancel-edit").click(function(){
		top.closeDialog();
	});
	top.$(".complete-edit").click(function(){
		top.closeDialog();
	});
}

