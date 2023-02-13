app.controller("statsbyproduct-ctrl", function($scope, $http) {
	$scope.items = [];
	$scope.productnames = [];
	$scope.sumByProduct = [];

	$http.get("/rest/statsByProducts").then(resq => {
		$scope.items = resq.data;
		$scope.list=[];
		
		for(let i = 0; i < $scope.items.length; i++){
			$scope.list = $scope.items[i];
			for(let j = 0; j < $scope.list.length; j++){
				$scope.productnames.push($scope.list[1]);
				$scope.sumByProduct.push($scope.list[4]);
				break;
			}
		}
		
		function generateColor() {
			let r = parseInt(Math.random() * 255);
			let g = parseInt(Math.random() * 255);
			let b = parseInt(Math.random() * 255);
			return `rgb(${r}, ${g}, ${b})`
		}

		function productChart(id) {
			let colors = [];
			for (let i = 0; i < $scope.sumByProduct.length; i++) {
				colors.push(generateColor())
			}
			const data = {
				labels: $scope.productnames,
				datasets: [{
					label: 'THỐNG KÊ DOANH THU THEO SẢN PHẨM',
					data: $scope.sumByProduct,
					fill: false,
					borderColor: colors,
					tension: 0.1
				}]
			};

			const config = {
				type: 'line',
				data: data,
			};

			let ctx = document.getElementById(id).getContext("2d")
			new Chart(ctx, config)
		}
		productChart("myChart");
	}).catch(error => {
		console.log("Error ", error);
	});
})