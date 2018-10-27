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
		loadBar(startDate, endDate);
		loadPie(startDate, endDate);
	});
	$("#today").click(function(){
		var startDate = getNowFormatDate();
		$("#startDate").val(startDate);
		loadBar(startDate);
		loadPie(startDate);
	});
	$("#thisWeek").click(function(){
		var startDate = getFirstDayOfWeek(new Date());
		$("#startDate").val(startDate);
		loadBar(startDate);
		loadPie(startDate);
	});
	$("#thisMonth").click(function(){
		var startDate = getFirstDayOfMonth(new Date());
		$("#startDate").val(startDate);
		loadBar(startDate);
		loadPie(startDate);
	});
	$("#thisSeason").click(function(){
		var startDate = getFirstDayOfSeason(new Date());
		$("#startDate").val(startDate);
		loadBar(startDate);
		loadPie(startDate);
	});
	$("#thisYear").click(function(){
		var startDate = getFirstDayOfYear(new Date());
		$("#startDate").val(startDate);
		loadBar(startDate);
		loadPie(startDate);
	});
	
	loadBar();
	loadPie();
	
	$("#type").change(function(){
		var startDate = $("#startDate").val();
		loadWorkerBar(startDate);
	});
});

function loadBar(startDate,endDate){
	$.ajax({
		url:"/report/orderBar",
		type:"get",
		async:false,
		dataType:"json",
		data:{beginDate:startDate,endDate:endDate},
		success:function(data){
			if(data.code == 1){
				var data = data.data;
				
				renderDemandBar(data.demandBar);
				renderOrderBar(data.orderBar);
				renderOrderMemberBar(data.orderMemberBar);
				renderOrderIncomeBar(data.orderIncomeBar);
			}
		}
	});
}

function loadPie(startDate,endDate){
	$.ajax({
		url:"/report/orderPie",
		type:"get",
		async:false,
		dataType:"json",
		data:{beginDate:startDate,endDate:endDate},
		success:function(data){
			if(data.code == 1){
				var data = data.data;
				
				renderDemandTakerPie(data.demandTakerPie);
				renderOrderTakerPie(data.orderTakerPie);
				renderOrderMemberTakerPie(data.orderMemberTakerPie);
				renderOrderIncomeTakerPie(data.orderIncomeTakerPie);
			}
		}
	});
}

function loadWorkerBar(startDate,endDate){
	var type = $("#type").val();
	
}


function renderDemandTakerPie(data){
	var series = [];
	for(var i=0; i<data.length; i++){
		var info = data[i];
		var obj = {};
		obj.name = info.name;
		obj.y = info.percentage;
		series.push(obj);
	}
	// 企业招聘需求接单人pie
	Highcharts.chart('demandTakerPie', {
		chart: {
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false,
				type: 'pie'
		},
		title: {
				text: '招聘需求接单人分布'
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
				data: series
		}]
	});
}
function renderOrderTakerPie(data){
	var series = [];
	for(var i=0; i<data.length; i++){
		var info = data[i];
		var obj = {};
		obj.name = info.name;
		obj.y = info.percentage;
		series.push(obj);
	}
	Highcharts.chart('orderTakerPie', {
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie'
		},
		title: {
			text: '已签订订单接单人分布'
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
			data: series
		}]
	});
}
function renderOrderMemberTakerPie(data){
	var series = [];
	for(var i=0; i<data.length; i++){
		var info = data[i];
		var obj = {};
		obj.name = info.name;
		obj.y = info.percentage;
		series.push(obj);
	}
	// 企业招聘需求接单人pie
	Highcharts.chart('orderMemberTakerPie', {
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie'
		},
		title: {
			text: '已签订人数接单人分布'
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
			data: series
		}]
	});
}
function renderOrderIncomeTakerPie(data){
	var series = [];
	for(var i=0; i<data.length; i++){
		var info = data[i];
		var obj = {};
		obj.name = info.name;
		obj.y = info.percentage;
		series.push(obj);
	}
	// 企业招聘需求接单人pie
	Highcharts.chart('orderIncomeTakerPie', {
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie'
		},
		title: {
			text: '订单总收入接单人分布'
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
			data: series
		}]
	});
}

function renderDemandBar(data){
	// 企业需求
	var demandBarCategories = [];
	var demandBarSeries = [];
	
	var demandData = data;
	for(var i=0; i<demandData.length; i++){
		var info = demandData[i];
		demandBarCategories.push(info.name);
		demandBarSeries.push(info.count);
	}
	// 企业招聘需求bar chart
	Highcharts.chart('demandBar', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: '企业招聘需求数量'
	    },
	    xAxis: {
	        categories: demandBarCategories,
	        crosshair: true
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: '数量 (个)'
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
	        name: '新增企业需求数',
	        data: demandBarSeries

	    }]
	});
}
function renderOrderBar(data){
	var orderData = data;
	// 订单
	var orderBarCategories = [];
	var orderBarSeries = [];
	
	for(var i=0; i<orderData.length; i++){
		var info = orderData[i];
		orderBarCategories.push(info.name);
		orderBarSeries.push(info.count);
	}
	// 订单bar chart
	Highcharts.chart('orderbar', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: '订单数量'
	    },
	    xAxis: {
	        categories: orderBarCategories,
	        crosshair: true
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: '数量 (个)'
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
	        name: '新增订单数',
	        data: orderBarSeries

	    }]
	});
}

function renderOrderMemberBar(data){
	var orderMemberData = data;
	// 订单人数
	var orderMemberBarCategories = [];
	var orderMemberBarSeries = [];
	
	for(var i=0; i<orderMemberData.length; i++){
		var info = orderMemberData[i];
		orderMemberBarCategories.push(info.name);
		orderMemberBarSeries.push(info.count);
	}
	// 订单签订人数bar chart
	Highcharts.chart('orderMemberBar', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: '订单签订人数'
	    },
	    xAxis: {
	        categories: orderMemberBarCategories,
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
	        name: '签订人数',
	        data: orderMemberBarSeries

	    }]
	});
}

function renderOrderIncomeBar(data){
	var orderIncomeData = data;
	// 订单金额
	var orderIncomeBarCategories = [];
	var orderIncomeBarSeries = [];
	for(var i=0; i<orderIncomeData.length; i++){
		var info = orderIncomeData[i];
		orderIncomeBarCategories.push(info.name);
		orderIncomeBarSeries.push(info.count);
	}
	// 订单签订金额bar chart
	Highcharts.chart('orderIncomeBar', {
		chart: {
			type: 'column'
		},
		title: {
			text: '订单收入总额'
		},
		xAxis: {
			categories: orderIncomeBarCategories,
			crosshair: true
		},
		yAxis: {
			min: 0,
			title: {
				text: '金额 (元)'
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
			name: '签订金额',
			data: orderIncomeBarSeries
			
		}]
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