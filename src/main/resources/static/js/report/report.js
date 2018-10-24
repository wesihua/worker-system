$(function(){
	loadWorkerBar();
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
				Highcharts.chart('container', {
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