app.controller("account-ctrl", function ($scope, $http) {
	$scope.accounts = [];
	$scope.form = {
		photo: 'no_img.png',
		isDeleted: false
	};
	
	$scope.load = function(){
		$http.get("/ad/rest/account").then(resp => {
			$scope.accounts = resp.data;	
		})
	}
	
	$scope.load();
	
	$scope.save = function(valid){
		if(valid){
			let nameProvince = $("#prov option:selected").text();
			let nameDistrict = $("#dis option:selected").text();
			let nameWard = $("#ward option:selected").text();
			let txtAdress = $("#txtAdress").val();
			
			var errAddress = document.getElementById("errAddress");
			
			if(nameProvince == "" || nameDistrict == "" || nameWard == "" || txtAdress == ""){
				errAddress.style.color="red";
				errAddress.innerText="Hãy nhập địa chỉ đầy đủ.";
			} else{	
				let item = angular.copy($scope.form);
				item.address = $scope.adr();
				console.log(item.address)
				$http.post("/ad/rest/account", item).then(resp => {
					$scope.accounts.push(resp.data);
					alert("Thêm mới tài khoản thành công!");
					autho(resp.data)
					
					$scope.clear();
				}).catch(err => {
					alert("Lỗi thêm mới tài khoản!");
					console.log(err);
				});
			}
		}
		else {
			alert("Hãy điền đủ thông tin!")
		};
	}
	
	function autho(acc){
		let autho = {
				account: {username:acc.username},
				role: {id:"STAFF"}
		}
		$http.post("/rest/autho", autho).then(resp => {
			$scope.authorities.push(resp.data);
		}).catch(err => {
			console.log("Lỗi thêm quyền" + err);
		});
	}
	
	$scope.update = function(){
		let item = angular.copy($scope.form);
		item.address = $scope.save();
		$http.put("/ad/rest/account", item).then(resp => {
			let i = $scope.accounts.findIndex(a => a.username == item.username);
			$scope.accounts[i] = item;
			alert("Cập nhật tài khoản thành công!");
		}).catch(err => {
			alert("Lỗi cập nhật tài khoản!");
			console.log(err);
		});
		
	}
	
	$scope.changeIsDel = function (a) {
		let item = angular.copy(a);
		if(item.isDeleted){
			item.isDeleted = false;
		}else {
			item.isDeleted = true;
		}
		
		$http.put("/ad/rest/account", item).then(resp => {
			let i = $scope.accounts.findIndex(a => a.username == item.username);
			$scope.accounts[i] = item;
			alert("Cập nhật tài khoản thành công!");
		}).catch(err => {
			alert("Lỗi cập nhật tài khoản!");
			console.log(err);
		});
	}
	
	$scope.edit = function (a){
		$('#username').attr('readonly', true);
		$scope.form = angular.copy(a);
		$scope.loadAddress($scope.form);
	}
	
	
	$scope.clear = function () {
		$scope.form = {
			photo: 'no_img.png',
			isDeleted: false
		};
		
		$('#username').attr('readonly', false);
	}
	
	$scope.changeImage = function (files){
		//console.log(files)
		var data = new FormData();
		data.append("file", files[0]);
        $http.post("/rest/upload/ImageA", data, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(resp => {
			$scope.form.photo = resp.data.name;
			console.log($scope.form.photo);
        }).catch(error => {
            alert("Lỗi upload hình ảnh");
            console.log("Error ", error);
        });
        
	}
	
	
	// api
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

	$scope.adr = function() {
		let nameProvince = $("#prov option:selected").text();
		let nameDistrict = $("#dis option:selected").text();
		let nameWard = $("#ward option:selected").text();
		let txtAdress = $("#txtAdress").val();
		

		let address = $scope.itemWard.id + ":" + nameWard + ","
			+ $scope.itemDistrict.id + ":" + nameDistrict + ","
			+ $scope.itemProvince.id + ":" + nameProvince + ","
			+ txtAdress;

		addressAcc = address;
		//console.log(address);
		return address;
		// localStorage.setItem("address", address);
	}
	
	
	
	$scope.loadAddress = function(user) {
		let address = user.address;
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

})