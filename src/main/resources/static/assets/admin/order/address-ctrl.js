app.config(['$httpProvider', function($httpProvider) {
	$httpProvider.interceptors.push('ApiInterceptor');
}]);

app.service('ApiInterceptor', [function() {
	var service = this;
	service.request = function(config) {
		//config.headers['Content-Type'] = 'application/json';
		//config.headers['Token'] = 'f805f85a-5739-11ed-9ad7-269dd9db11fd';
		return config;
	};
}]);

app.controller('address-ctrl', function($scope, $http) {

	let urlProvince = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province";
	$scope.itemProvince = {}
	$http.get(urlProvince, {

		headers: { 'Content-Type': 'application/json' },
		headers: { 'Token': 'f805f85a-5739-11ed-9ad7-269dd9db11fd' }
	}).then(resp => {

		$scope.province = resp.data;

		//console.log($scope.itemProvince);
	}).catch(error => {
		console.log(error);
	});




	let urlDistrict = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district";
	$scope.district = [];
	$scope.itemDistrict = {}
	$scope.changeDistrict = function() {
		$scope.district = [];

		$http.post(urlDistrict, { "province_id": $scope.itemProvince.id }, {
		
		headers: {'Content-Type': 'application/json'},
		headers: {'Token' : 'f805f85a-5739-11ed-9ad7-269dd9db11fd'}
		}).then(resp => {
			$scope.district = resp.data;

		}).catch(error => {
			console.log(error);
		});
	}

	$scope.ward = [];
	$scope.itemWard = {};
	let urlWard = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id"
	$scope.changeWard = function() {
		$scope.ward = [];

		$http.post(urlWard, { "district_id": $scope.itemDistrict.id }, {
		
		headers: {'Content-Type': 'application/json'},
		headers: {'Token' : 'f805f85a-5739-11ed-9ad7-269dd9db11fd'}
		}).then(resp => {
			$scope.ward = resp.data;

		}).catch(error => {
			console.log(error);
		});
	}

	$scope.save = function() {
		let nameProvince = $("#prov option:selected").text();
		let nameDistrict = $("#dis option:selected").text();
		let nameWard = $("#ward option:selected").text();
		let txtAdress = $("#txtAdress").val();
		
		var errAddress = document.getElementById("errAddress");
		console.log(errAddress)
		
		if(nameProvince == "" || nameDistrict == "" || nameWard == "" || txtAdress == ""){
			errAddress.style.color="red";
			errAddress.innerText="Hãy nhập địa chỉ đầy đủ.";
		} else{
			let address = $scope.itemWard.id + ":" + nameWard + ","
			+ $scope.itemDistrict.id + ":" + nameDistrict + ","
			+ $scope.itemProvince.id + ":" + nameProvince + ","
			+ txtAdress;
			
			localStorage.setItem("address", address);
			errAddress.innerText="";
		}
	}

	let address = localStorage.getItem("address");
	$scope.loadAddress = function() {
		let arrAddress = address.split(",");

		$scope.itemProvince.id = arrAddress[2].split(":")[0] * 1;

		$http.post(urlDistrict, { "province_id": $scope.itemProvince.id }, {
		
		headers: {'Content-Type': 'application/json'},
		headers: {'Token' : 'f805f85a-5739-11ed-9ad7-269dd9db11fd'}
		}).then(resp => {
			$scope.district = resp.data;

		}).catch(error => {
			console.log(error);
		});

		$scope.itemDistrict.id = arrAddress[1].split(":")[0] * 1;
		$http.post(urlWard, { "district_id": $scope.itemDistrict.id }, {
		
		headers: {'Content-Type': 'application/json'},
		headers: {'Token' : 'f805f85a-5739-11ed-9ad7-269dd9db11fd'}
		}).then(resp => {

			$scope.ward = resp.data;
			$scope.itemWard.id = arrAddress[0].split(":")[0]
		}).catch(error => {
			console.log(error);
		});
		
		$("#txtAdress").val(arrAddress[3]);
	}

	if (address != null) {
		$scope.loadAddress();
	}

});