app.controller("category-ctrl", function($scope, $http){
	$scope.pageSize = 5;

	$scope.items = [];
	$scope.form = {};
	const cates = new Map();
	
	$scope.formLoad = function(){
		$http.get("/rest/categories").then(resp => {
			$scope.items = resp.data;
		}).catch(error => {
			console.log(error);
		})
	}
	
	$scope.edit = function(item){
		$scope.form = angular.copy(item);
	}
	
	$scope.changeImage = function (files){
		var data = new FormData();
		data.append("file", files[0]);
        $http.post("/rest/upload/ImageC", data, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(resp => {
			$scope.form.image = resp.data.name;
			console.log($scope.form.image);
        }).catch(error => {
            alert("Lỗi upload hình ảnh");
            console.log("Error ", error);
        });
        
	}
	
	$scope.save = function(valid){
		if (valid) {
			let item = angular.copy($scope.form);
			if (item.isDeleted == undefined) {
				item.isDeleted = false;
			}
			$http.post('/rest/categories', item).then(resp => {
				$scope.itemsNew = resp.data;
				console.log(item);
				$scope.items.push($scope.itemsNew);
				$scope.reset();
				alert("Thêm mới thành công danh mục sản phẩm!");

			}).catch(err => {
				alert("Lỗi thêm mới danh mục sản phẩm!");
				console.log(err);
			});
		} else alert("Điền đầy đủ thông tin!")
	}
	
	$scope.update = function(valid){
		if(valid){
			let item = angular.copy($scope.form);
			$http.put(`/rest/categories/${item.id}`, item).then(resp => {
				let i = $scope.items.findIndex(c => c.id == item.id);
				$scope.items[i] = item;
				alert("Cập nhật thành công sản phẩm!");
			}).catch(err => {
				alert("Lỗi cập nhật sản phẩm!");
				console.log(err);
			});
		}
	}
	
	$scope.delete = function(id){
		if (confirm("Bạn có muốn xóa danh mục này?") == true) {
			$http.delete(`/rest/categories/${id}`).then(resp => {
				let i = $scope.items.findIndex(p => p.id == id);
				$scope.items.splice(i, 1);
				$scope.reset();
				alert("Xóa thành công sản phẩm!");
			}).catch(err => {
				alert("Lỗi xóa sản phẩm!");
				console.log(err);
			});
		}
	}
	
	$scope.updateBrandIsDeleted = function(category) {
		let categoryItem = angular.copy(category);
		if(categoryItem.isDeleted == false){
			categoryItem.isDeleted = true;
		}else {
			categoryItem.isDeleted = false;
		}
		$http.put("/rest/updateCateIsDeleted", categoryItem).then(resp => {
			let i = $scope.items.findIndex(c => c.id == category.id);
			$scope.items[i] = categoryItem;
		}).catch(error => {
			alert("Lỗi update isDeleted");
			console.log("Error ", error);
		});
	}
	
	$scope.reset = function(){
		$scope.form = {
			image: 'no_img.png',
			id: "",
			name: ""
		};
	}
	
	$scope.formLoad();
	$scope.reset();
	
})