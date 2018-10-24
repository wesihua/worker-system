$(function(){
	loadWorkerBar();
	loadDemandBar();
	loadWorkerSourcePie();
	loadWorkerCreateUserPie();
	loadWorkerDegreePie();
});

function loadWorkerBar(){
	$.ajax({
		url:"/report/workerBar",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var categories = [];
				var series = [];
				var data = data.data;
				for(var i=0; i<data.length; i++){
					var info = data[i];
					categories.push(info.name);
					series.push(info.count);
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
				        categories: categories,
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
				        data: series

				    }]
				});
			}
		}
	});
}

function loadWorkerSourcePie(){
	$.ajax({
		url:"/report/workerSourcePie",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var series = [];
				var data = data.data;
				for(var i=0; i<data.length; i++){
					var info = data[i];
					var obj = {};
					obj.name = info.name;
					obj.y = info.percentage;
					series.push(obj);
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
							data: series
					}]
				});
			}
		}
	});
}

function loadWorkerCreateUserPie(){
	$.ajax({
		url:"/report/workerCreateUserPie",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var series = [];
				var data = data.data;
				for(var i=0; i<data.length; i++){
					var info = data[i];
					var obj = {};
					obj.name = info.name;
					obj.y = info.percentage;
					series.push(obj);
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
							data: series
					}]
				});
			}
		}
	});
}

function loadWorkerDegreePie(){
	$.ajax({
		url:"/report/workerDegreePie",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var series = [];
				var data = data.data;
				for(var i=0; i<data.length; i++){
					var info = data[i];
					var obj = {};
					obj.name = info.name;
					obj.y = info.percentage;
					series.push(obj);
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
							data: series
					}]
				});
			}
		}
	});
}

function loadDemandBar(){
	$.ajax({
		url:"/report/demandBar",
		type:"get",
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				var categories = [];
				var series = [];
				var data = data.data;
				for(var i=0; i<data.length; i++){
					var info = data[i];
					categories.push(info.name);
					series.push(info.count);
				}
				// 生产bar chart
				Highcharts.chart('demandBar', {
				    chart: {
				        type: 'column'
				    },
				    title: {
				        text: '企业需求月增量'
				    },
				    xAxis: {
				        categories: categories,
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
				            '<td style="padding:0"><b>{point.y:.1f} 个</b></td></tr>',
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
				        name: '新增需求',
				        data: series

				    }]
				});
			}
		}
	});
}