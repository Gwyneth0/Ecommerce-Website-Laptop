app.controller("statsbyclient-ctrl", function($scope, $http) {
	$scope.clients = [];
	$scope.names_product = [];
	$scope.money = [];
	$scope.clients_by_key = [];
	
	let list = [];
	$http.get("/rest/statsByClient").then(resq => {
		$scope.clients = resq.data;
	}).catch(error => {
		console.log("Error ", error);
	});
	
	$scope.select = function(){
		$scope.list = [];
		$scope.names_product=[];
		$scope.money=[]
		const keyWord = $scope.keyW;
		$http.get(`/rest/statsClientByKeyword/${keyWord}`).then(resq => {
			$scope.clients_by_key = resq.data;
			if($scope.clients_by_key.length != 0){
				for (let i = 0; i < $scope.clients_by_key.length; i++) {
					$scope.list = $scope.clients_by_key[i]
					for (let j = 0; j < $scope.list.length; j++) {
						$scope.names_product.push($scope.list[0]);
						$scope.money.push($scope.list[2]);
						break;
					}
				}
				const list = document.getElementById("chartjs");
				list.removeChild(list.firstElementChild);

				const para = document.createElement("canvas");
				para.id = "myChart";
				list.appendChild(para);
				cateChart("myChart", $scope.names_product, $scope.money);
			} else{
				alert('Không có hóa đơn nào!');
			}
		}).catch(error => {
			console.log("Error ", error);
		});
	}
	
	function generateColor() {
		let r = parseInt(Math.random() * 255);
		let g = parseInt(Math.random() * 255);
		let b = parseInt(Math.random() * 255);
		return `rgb(${r}, ${g}, ${b})`
	}

	function cateChart(id, labels, moneys) {
		let colors = [];
		for (let i = 0; i < moneys.length; i++) {
			colors.push(generateColor())
		}

		const data = {
			labels: labels,
			datasets: [{
				label: 'Thống kê theo khách hàng',
				data: moneys,
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

		let ctx = document.getElementById(id).getContext("2d");
		new Chart(ctx, config);
	
	}
})

