app.controller("main-ctrl", function ($scope, $http, $rootScope) {
	let username = sessionStorage.getItem("admin");
	$scope.welcome= "Chào mừng đến trang admin"
		$http.get(`/rest/account/${username}`).then(resp => {
			$rootScope.admin = resp.data;
			
		})
		
		$http.get(`/rest/getAuth/${username}`).then(resp => {
			$rootScope.data = resp.data;
			$rootScope.rol= $rootScope.data.role.id
			console.log($rootScope.rol)
		})
	
})