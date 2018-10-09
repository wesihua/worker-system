
$(function(){
	// 柱状图
	var color = Chart.helpers.color;
	var barChartData = {
		labels : [ 'January', 'February', 'March', 'April', 'May', 'June', 'July' ],
		datasets : [
				{
					label : '月份数量',
					backgroundColor : color(window.chartColors.red).alpha(0.5).rgbString(),
					//backgroundColor : 'pink',
					borderColor : window.chartColors.red,
					borderWidth : 1,
					data : [ 10, 20, 35,40,60,100,300 ]
				} ]

	};
	var ctx = document.getElementById('chart-bar').getContext('2d');
	window.myBar = new Chart(ctx, {
		type : 'bar',
		data : barChartData,
		options : {
			responsive : true,
			legend : {
				position : 'top',
			},
			title : {
				display : true,
				text : 'Chart.js Bar Chart'
			}
		}
	});
	
	// 饼图
	var pieData = {
		type: 'pie',
		data: {
			datasets: [{
				data: [0.2, 0.3, 0.2, 0.15, 0.15],
				backgroundColor: [
					window.chartColors.red,
					window.chartColors.orange,
					window.chartColors.yellow,
					window.chartColors.green,
					window.chartColors.blue,
				],
				label: '月份占比'
			}],
			labels: [
				'Red',
				'Orange',
				'Yellow',
				'Green',
				'Blue'
			]
		},
		options: {
			responsive: true,
			title : {
				display : true,
				text : 'Chart.js Pie Chart'
			}
		}
	};
	var ctx = document.getElementById('chart-pie').getContext('2d');
	window.myPie = new Chart(ctx, pieData);
	
	// 折线图
	var lineData = {
		type: 'line',
		data: {
			labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
			datasets: [{
				label: 'My First dataset',
				backgroundColor: window.chartColors.red,
				borderColor: window.chartColors.red,
				data: [ 10, 20, 35,40,60,100,300 ],
				fill: false
			}]
		},
		options: {
			responsive: true,
			title: {
				display: true,
				text: 'Chart.js Line Chart'
			},
			tooltips: {
				mode: 'index',
				intersect: false,
			},
			hover: {
				mode: 'nearest',
				intersect: true
			}
		}
	};

	var ctx = document.getElementById('chart-line').getContext('2d');
	window.myLine = new Chart(ctx, lineData);
});
