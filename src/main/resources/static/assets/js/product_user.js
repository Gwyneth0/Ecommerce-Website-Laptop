
app.controller("shopping-cart-ctrl", function($scope, $http) {
	$scope.products = [];
	
	$scope.idC = 2;
	$scope.loadProduct = function (cate_id){
		console.log(cate_id)
		$scope.idC = cate_id;
		$http.get(`/rest/productByCateId/${cate_id}`).then(resp => {
			$scope.products = resp.data;
		}).catch(err => {
			console.log("err productByCateId " + err)
		});
	}
	
	
})