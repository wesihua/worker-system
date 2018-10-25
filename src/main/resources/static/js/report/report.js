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
	
	$('.J-yearMonthPicker-single').datePicker({
        format: 'YYYY-MM-DD'
    });
	$("#query").click(function(){
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		loadWorkerBar(startDate, endDate);
		loadWorkerSourcePie(startDate, endDate);
		loadWorkerCreateUserPie(startDate, endDate);
		loadWorkerDegreePie(startDate, endDate);
	});
	$("#today").click(function(){
		var startDate = getNowFormatDate();
		$("#startDate").val(startDate);
		loadWorkerBar(startDate);
		loadWorkerSourcePie(startDate);
		loadWorkerCreateUserPie(startDate);
		loadWorkerDegreePie(startDate);
	});
	$("#thisWeek").click(function(){
		var startDate = getFirstDayOfWeek(new Date());
		$("#startDate").val(startDate);
		loadWorkerBar(startDate);
		loadWorkerSourcePie(startDate);
		loadWorkerCreateUserPie(startDate);
		loadWorkerDegreePie(startDate);
	});
	$("#thisMonth").click(function(){
		var startDate = getFirstDayOfMonth(new Date());
		$("#startDate").val(startDate);
		loadWorkerBar(startDate);
		loadWorkerSourcePie(startDate);
		loadWorkerCreateUserPie(startDate);
		loadWorkerDegreePie(startDate);
	});
	$("#thisSeason").click(function(){
		var startDate = getFirstDayOfSeason(new Date());
		$("#startDate").val(startDate);
		loadWorkerBar(startDate);
		loadWorkerSourcePie(startDate);
		loadWorkerCreateUserPie(startDate);
		loadWorkerDegreePie(startDate);
	});
	$("#thisYear").click(function(){
		var startDate = getFirstDayOfYear(new Date());
		$("#startDate").val(startDate);
		loadWorkerBar(startDate);
		loadWorkerSourcePie(startDate);
		loadWorkerCreateUserPie(startDate);
		loadWorkerDegreePie(startDate);
	});
	
	loadCountTable();
	loadWorkerBar();
	loadWorkerSourcePie();
	loadWorkerCreateUserPie();
	loadWorkerDegreePie();
	
	$("#type").change(function(){
		var startDate = $("#startDate").val();
		loadWorkerBar(startDate);
	});
});

function loadWorkerBar(startDate,endDate){
	var type = $("#type").val();
	$.ajax({
		url:"/report/workerBar",
		type:"get",
		async:false,
		dataType:"json",
		data:{beginDate:startDate,endDate:endDate,type:type},
		success:function(workerdata){
			if(workerdata.code == 1){
				var workerCategories = [];
				var workerSeries = [];
				var data = workerdata.data;
				for(var i=0; i<data.length; i++){
					var info = data[i];
					workerCategories.push(info.name);
					workerSeries.push(info.count);
				}
				// 生产bar chart
				Highcharts.chart('workerBar', {
				    chart: {
				        type: 'column'
				    },
				    title: {
				        text: '人才信息月增量'
				    },
				    xAxis: {
				        categories: workerCategories,
				        crosshair: true
				    },
				    yAxis: {
				        min: 0,
				        title: {
				            text: '数量 (人)'
				        }
				    },
				    tooltip: {
				        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
				        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
				            '<td style="padding:0"><b>{point.y:.1f} 人</b></td></tr>',
				        footerFormat: '</table>',
				        shared: true,
				        useHTML: true
				    },
				    plotOptions: {
				        column: {
				            pointPadding: 0.2,
				            borderWidth: 0
				        }
				    },
				    series: [{
				        name: '新增人数',
				        data: workerSeries

				    }]
				});
			}
		}
	});
}


function loadWorkerSourcePie(startDate,endDate){
	$.ajax({
		url:"/report/workerSourcePie",
		type:"get",
		async:false,
		dataType:"json",
		data:{beginDate:startDate,endDate:endDate},
		success:function(sourcedata){
			if(sourcedata.code == 1){
				var workerSourceSeries = [];
				var data = sourcedata.data;
				for(var i=0; i<data.length; i++){
					var info = data[i];
					var obj = {};
					obj.name = info.name;
					obj.y = info.percentage;
					workerSourceSeries.push(obj);
				}
				// 生产pie chart
				Highcharts.chart('workerSourcePie', {
					chart: {
							plotBackgroundColor: null,
							plotBorderWidth: null,
							plotShadow: false,
							type: 'pie'
					},
					title: {
							text: '人才信息来源分布'
					},
					tooltip: {
							pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
					},
					plotOptions: {
							pie: {
									allowPointSelect: true,
									cursor: 'pointer',
									dataLabels: {
											enabled: true,
											format: '<b>{point.name}</b>: {point.percentage:.1f} %',
											style: {
													color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
											}
									}
							}
					},
					series: [{
							name: '占比',
							colorByPoint: true,
							data: workerSourceSeries
					}]
				});
			}
		}
	});
}

function loadWorkerCreateUserPie(startDate,endDate){
	
	$.ajax({
		url:"/report/workerCreateUserPie",
		type:"get",
		async:false,
		dataType:"json",
		data:{beginDate:startDate,endDate:endDate},
		success:function(createUserdata){
			if(createUserdata.code == 1){
				var barCategories = [];
				var barData = [];
				var workerCreateUserSeries = [];
				var data = createUserdata.data;
				for(var i=0; i<data.length; i++){
					var info = data[i];
					barCategories.push(info.name);
					barData.push(info.count);
					var obj = {};
					obj.name = info.name;
					obj.y = info.percentage;
					workerCreateUserSeries.push(obj);
				}
				// 生产pie chart
				Highcharts.chart('workerCreateUserPie', {
					chart: {
							plotBackgroundColor: null,
							plotBorderWidth: null,
							plotShadow: false,
							type: 'pie'
					},
					title: {
							text: '人才信息录入人分布'
					},
					tooltip: {
							pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
					},
					plotOptions: {
							pie: {
									allowPointSelect: true,
									cursor: 'pointer',
									dataLabels: {
											enabled: true,
											format: '<b>{point.name}</b>: {point.percentage:.1f} %',
											style: {
													color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
											}
									}
							}
					},
					series: [{
							name: '占比',
							colorByPoint: true,
							data: workerCreateUserSeries
					}]
				});
				// 生成柱状图
				Highcharts.chart('workerCreateUserBar', {
				    chart: {
				        type: 'column'
				    },
				    title: {
				        text: '录入人录入数量'
				    },
				    xAxis: {
				        categories: barCategories,
				        crosshair: true
				    },
				    yAxis: {
				        min: 0,
				        title: {
				            text: '数量 (人)'
				        }
				    },
				    tooltip: {
				        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
				        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
				            '<td style="padding:0"><b>{point.y:.1f} 人</b></td></tr>',
				        footerFormat: '</table>',
				        shared: true,
				        useHTML: true
				    },
				    plotOptions: {
				        column: {
				            pointPadding: 0.2,
				            borderWidth: 0
				        }
				    },
				    series: [{
				        name: '新增人数',
				        data: barData

				    }]
				});
			}
		}
	});
}

function loadWorkerDegreePie(startDate,endDate){
	$.ajax({
		url:"/report/workerDegreePie",
		type:"get",
		async:false,
		dataType:"json",
		data:{beginDate:startDate,endDate:endDate},
		success:function(degreedata){
			if(degreedata.code == 1){
				var workerDegreeSeries = [];
				var data = degreedata.data;
				for(var i=0; i<data.length; i++){
					var info = data[i];
					var obj = {};
					obj.name = info.name;
					obj.y = info.percentage;
					workerDegreeSeries.push(obj);
				}
				// 生产pie chart
				Highcharts.chart('workerDegreePie', {
					chart: {
							plotBackgroundColor: null,
							plotBorderWidth: null,
							plotShadow: false,
							type: 'pie'
					},
					title: {
							text: '人才信息学历分布'
					},
					tooltip: {
							pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
					},
					plotOptions: {
							pie: {
									allowPointSelect: true,
									cursor: 'pointer',
									dataLabels: {
											enabled: true,
											format: '<b>{point.name}</b>: {point.percentage:.1f} %',
											style: {
													color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
											}
									}
							}
					},
					series: [{
							name: '占比',
							colorByPoint: true,
							data: workerDegreeSeries
					}]
				});
			}
		}
	});
}

function loadCountTable(){
	$.ajax({
		url:"/report/statistic",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var roleArr = data.data;
				var tableContent="";
				for(var i=0; i<roleArr.length; i++){
					var role = roleArr[i];
					tableContent+=  "<tr>"+
									"	<td>"+role.name+"</td>"+
									"	<td>"+role.day+"</td>"+
									"	<td>"+role.week+"</td>"+
									"	<td>"+role.month+"</td>"+
									"	<td>"+role.season+"</td>"+
									"	<td>"+role.year+"</td>"+
									"</tr>";
				}
				$("#someCount").find("tbody").empty().append(tableContent);
			}
		}
	});
}



/**
 * 获取指定日期的周的第一天、月的第一天、季的第一天、年的第一天
 * @param date new Date()形式，或是自定义参数的new Date()
 * @returns 返回值为格式化的日期，yy-mm-dd
 */
//日期格式化，返回值形式为yy-mm-dd
function timeFormat(date) {
    if (!date || typeof(date) === "string") {
        this.error("参数异常，请检查...");
    }
    var y = date.getFullYear(); //年
    var m = date.getMonth() + 1; //月
    var d = date.getDate(); //日

    return y + "-" + m + "-" + d;
}

//获取这周的周一
function getFirstDayOfWeek (date) {

    var weekday = date.getDay()||7; //获取星期几,getDay()返回值是 0（周日） 到 6（周六） 之间的一个整数。0||7为7，即weekday的值为1-7

    date.setDate(date.getDate()-weekday+1);//往前算（weekday-1）天，年份、月份会自动变化
    return timeFormat(date);
}

//获取当月第一天
function getFirstDayOfMonth (date) {
    date.setDate(1);
    return timeFormat(date);
}

//获取当季第一天
function getFirstDayOfSeason (date) {
    var month = date.getMonth();
    if(month <3 ){
        date.setMonth(0);
    }else if(2 < month && month < 6){
        date.setMonth(3);
    }else if(5 < month && month < 9){
        date.setMonth(6);
    }else if(8 < month && month < 11){
        date.setMonth(9);
    }
    date.setDate(1);
    return timeFormat(date);
}

//获取当年第一天
function getFirstDayOfYear (date) {
    date.setDate(1);
    date.setMonth(0);
    return timeFormat(date);
}

//获取当前时间，格式YYYY-MM-DD
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}