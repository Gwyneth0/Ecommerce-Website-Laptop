app.controller("brand-ctrl", function($scope, $http) {
	$scope.list = [];
	$scope.brand = { isDeleted: false };

	$scope.load = function() {

		$http.get("/rest/brand").then(resp => {
			$scope.list = resp.data;
			console.log("djdjjd ", resp.data)
		});
	}

	$scope.changeImage = function(files) {
		var data = new FormData();
		data.append("file", files[0]);
		$http.post("/rest/upload/ImageB", data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.brand.image = resp.data.name;
			console.log($scope.brand.image);
		}).catch(error => {
			alert("Lỗi upload hình ảnh");
			console.log("Error ", error);
		});

	}

	$scope.add = function(valid) {
		if(valid){
			let brand = angular.copy($scope.brand);
			$http.post("/rest/addBrand", brand).then(resp => {
				$scope.list.push(resp.data);
				$scope.reset();
				alert("Thêm hãng thành công!");
			}).catch(error => {
				alert("Lỗi add");
				console.log("Error ", error);
			});			
		}else alert("Hãy điền đủ thông tin!");
	}
	
	$scope.updateBrandIsDeleted = function(brand) {
		let brandItem = angular.copy(brand);
		if(brandItem.isDeleted == false){
			brandItem.isDeleted = true;
		}else {
			brandItem.isDeleted = false;
		}
		$http.put("/rest/updateBrandIsDeleted", brandItem).then(resp => {
			let i = $scope.list.findIndex(b => b.id == brand.id);
			$scope.list[i] = brandItem;
		}).catch(error => {
			alert("Lỗi update isDeleted");
			console.log("Error ", error);
		});
	}
	
	$scope.update = function (){
		let brandItem = angular.copy($scope.brand);
		$http.put("/rest/updateBrand", brandItem).then(resp => {
			let i = $scope.list.findIndex(b => b.id == brandItem.id);
			$scope.list[i] = brandItem;
			alert("Cập nhật thành công!");
		}).catch(error => {
			alert("Lỗi update ");
			console.log("Error ", error);
		});
	}
	
	$scope.edit = function(p) {
		$scope.brand = angular.copy(p);
	}
	
	$scope.reset = function (){
		$scope.brand = { isDeleted: false };
	}

	$scope.load();
})