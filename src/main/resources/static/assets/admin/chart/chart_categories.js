app.controller("statsbycate-ctrl", function($scope, $http) {
	$scope.names = [];
	$scope.quantyti = [];
	$scope.stats = [];
	
	$http.get("/rest/statsByCategories").then(resq => {
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
				label: 'My First Dataset',
				data: $scope.quantyti,
				backgroundColor: colors,
				hoverOffset: 4
			}]
		};
		const config = {
			type: 'doughnut',
			data: data,
		};

		let ctx = document.getElementById(id).getContext("2d")
		new Chart(ctx, config)
	}
})