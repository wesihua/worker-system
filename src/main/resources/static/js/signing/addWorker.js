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

    query(1);

    $(".select-worker").click(function(){
        query(1);
    });

});

function query(pageNum){

    var workerName = $("#workerName").val();
	var telephone = $("#telephone").val();
	var idcard = $("#idcard").val();

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
									"	<td>"+worker.name+"</td>"+
									"	<td>"+worker.idcard+"</td>"+
									"	<td>"+worker.birthplaceName+"</td>"+
									"	<td>"+worker.jobtypeName+"</td>"+
									"	<td>"+worker.expectSalaryName+"</td>"+
									"	<td>"+worker.workplaceName+"</td>"+
									"	<td><span class=\"des\" onClick=\"updateWorker("+worker.id+")\">编辑</span>" +
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

function updateWorker(){
	
}