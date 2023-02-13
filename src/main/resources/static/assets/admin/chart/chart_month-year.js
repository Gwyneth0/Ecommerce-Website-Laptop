app.controller("statsbymonth-ctrl", function($scope, $http) {
	$scope.items = [];
	$scope.time = [];
	$scope.sum = [];
	$scope.fromDate="";
	$scope.toDate="";
	
	$http.get("/rest/statsMonths").then(resq => {
		$scope.items = resq.data;
		console.log($scope.items)
		$scope.list_1 = [];
		for(let i = 0; i < $scope.items.length; i++){
			$scope.list_1 = $scope.items[i];
			for(let j = 0; j < $scope.list_1.length; j++){
				$scope.time.push(new Date($scope.list_1[1]).toLocaleDateString("vi-VN"));
				$scope.sum.push($scope.list_1[2]);
				break;
			}
		}
		cateChart()
	}).catch(error => {
		console.log("Error ", error);
	});
	
	$scope.click = function(){
		var start = new Date($scope.fromDate);
		var start_day = start.getDate();
		var start_month = start.getMonth()+1;
		var start_year = start.getFullYear();
		
		var end = new Date($scope.toDate);
		var end_day = end.getDate();
		var end_month = end.getMonth()+1;
		var end_year = end.getFullYear();
		
		$http.get(`/rest/statsMonthCheck/${start_day + "-" + start_month + "-" + start_year}/
		${end_day + "-" + end_month + "-" + end_year}`).then(resq => {
		$scope.items = resq.data;
		if($scope.items.length==0){
			alert('Không có dữ liệu!');
		}
		
		$scope.time =[];
		$scope.sum =[];
		$scope.list_2 = [];
		for(let i = 0; i < $scope.items.length; i++){
			$scope.list_2 = $scope.items[i];
			for(let j = 0; j < $scope.list_2.length; j++){
				$scope.time.push($scope.list_2[1]);
				$scope.sum.push($scope.list_2[2]);
				break;
			}
		}
		
		const list = document.getElementById("chartjs");
		list.removeChild(list.firstElementChild);
		
		const para = document.createElement("canvas");
		para.id = "myChart";
		list.appendChild(para);
		cateChart();
	}).catch(error => {
		return;
	});
	}
	
	function generateColor() {
		let r = parseInt(Math.random() * 255);
		let g = parseInt(Math.random() * 255);
		let b = parseInt(Math.random() * 255);
		return `rgb(${r}, ${g}, ${b})`
	}
	
	function cateChart(){
		let colors = [];
		for (let i = 0; i < $scope.sum.length; i++) {
			colors.push(generateColor())
		}

		const data = {
			labels: $scope.time,
			datasets: [{
				label: 'THỐNG KÊ DOANH THU THÁNG-NĂM',
				data: $scope.sum,
				backgroundColor: colors,
				borderWidth: 1
			}]
		};
		const config = {
			type: 'bar',
			data: data,
			options: {
				scales: {
					y: {
						beginAtZero: true
					}
				}
			},
		};
		let ctx = document.getElementById("myChart").getContext("2d")
		new Chart(ctx, config)
	}
})