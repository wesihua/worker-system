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
    
    $(document).on('click','input[name="check"]',function(){
    	$that = $(this);
    	var aaa = $that.prop("checked");
    	var id = $that.val();
    	if(aaa){
    		var name = $that.parents('tr').find('.worker-name').text();
    		var idcard = $that.parents('tr').find('.worker-idcard').text();
/*    		var signSalary = $that.parents('tr').find('[name="signSalary"]').val();
    		var arriveWorkTime = $that.parents('tr').find('[name="arriveWorkTime"]').val();
    		var businessIncome = $that.parents('tr').find('[name="businessIncome"]').val();*/
    		var content = '<li class="order-worder" id=check_'+id+' data="'+ id +'">'+
					            '<div class="select-name">'+name+'（'+ idcard +'）</div>'+
					            '<div class="select-title">'+
					                '<span class="a">签约月工资（元)</span>'+
					                '<span class="b">到岗日期</span>'+
					               ' <span class="c">业务收入（元）</span>'+
					            '</div>'+
					            '<div class="select-input">'+
					                '<input class="a signSalary" type="text" value="" />'+
					                '<input autocomplete="off" type="text"  class="pt c-datepicker-data-input b arriveWorkTime"/>'+
					                '<input class="c businessIncome" type="text" value="" />'+
					           ' </div>'+
					        '</li>';
    	}else{
    		$("#check_"+id+"").remove();
    	}
    	
    	$(".result-area ul").append(content);
    
    	
    });
    
    $('.addWorker').click(function(){
    	
    	var jobTypeId = $("input[name='jobTypeId']").val();
    	var workers = [];
    	$(".order-worder").each(function(){
    		$that = $(this).find(".select-input");
    		var orderWorker = {};
    		orderWorker.signSalary = $that.find(".signSalary").val();
    		orderWorker.arriveWorkTime = $that.find(".arriveWorkTime").val();
    		orderWorker.businessIncome = $that.find(".businessIncome").val();
    		orderWorker.workerId = $(this).attr("data");
    		workers.push(orderWorker);
    	});
    	
		$.ajax({
			url:"/demand/addOrderWorker",
			type:"post",
			dataType:"json",
			data:{json:JSON.stringify(workers),demandJobId:jobTypeId},
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
    	
    })

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
									"	<td class='worker-name'>"+worker.name+"</td>"+
									"	<td class='worker-idcard'>"+worker.idcard+"</td>"+
									"	<td>"+worker.birthplaceName+"</td>"+
									"	<td>"+worker.jobtypeName+"</td>"+
									"	<td>"+worker.expectSalaryName+"</td>"+
									"	<td>"+worker.workplaceName+"</td>"+
									"	<td><input type=\"checkbox\" name=\"check\" value="+worker.id+" ></td>"+
									/*"	<input type=\"hidden\" name=\"signSalary\" value="+worker.signSalary+" >"+
									"	<input type=\"hidden\" name=\"arriveWorkTime\" value="+worker.arriveWorkTime+" >"+
									"	<input type=\"hidden\" name=\"businessIncome\" value="+worker.businessIncome+" >"+*/
									"</tr>";
					/*"<span class=\"des\" onClick=\"updateWorker("+worker.id+")\">编辑</span>" */
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