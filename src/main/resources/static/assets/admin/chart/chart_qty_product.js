app.controller("statsbyqty_producct-ctrl", function($scope, $http) {
	$scope.names = [];
	$scope.quantyti = [];
	$scope.stats = [];
	
	$http.get("/rest/statsByQuantity").then(resq => {
		$scope.stats = resq.data;
		for(const [key, value] of $scope.stats){
			$scope.names.push(`${key}`);
			$scope.quantyti.push(`${value}`);
		}
		
		cateChart("myChart");
	}).catch(error => {
		console.log("Error ", error);
	});
	
	function generateColor() {
		let r = parseInt(Math.random() * 255);
		let g = parseInt(Math.random() * 255);
		let b = parseInt(Math.random() * 255);
		return `rgb(${r}, ${g}, ${b})`
	}

	function cateChart(id) {

		let colors = [];
		for (let i = 0; i < $scope.quantyti.length; i++) {
			colors.push(generateColor())
		}

		const data = {
			labels: $scope.names,
			datasets: [{
				label: 'Thong ke theo so luong',
				data: $scope.quantyti,
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

		let ctx = document.getElementById(id).getContext("2d")
		new Chart(ctx, config)
	}
})

/*var quantity = JSON.parse(localStorage.getItem("quantity")); //get them back
var categoriesName = JSON.parse(localStorage.getItem("categoriesName")); //get them back*/