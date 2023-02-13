app.controller("detail-ctrl", function($scope, $routeParams, $http) {
	
	$scope.detail =[];
	
	function loadDetail(){
		
		$http.get(`/rest/getOrderDetail/${$routeParams.id}`).then(resp => {
			$scope.detail = resp.data
			console.log($scope.detail)
		});
	}
	
	loadDetail();
	
	
})