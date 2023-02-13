app.controller("role-ctrl", function ($scope, $http) {
	$scope.list = [];
	$scope.role = {isDeleted: false};
	
	$scope.load = function () {
		$http.get("/rest/role").then(resp => {
			$scope.list = resp.data;
		}).catch(err => {
			console.log("err ", err);
		})
	}
	
	$scope.load();
	
	$scope.add = function (valid) {
		var check_id=false;
		if(valid){
			for (let i = 0; i < $scope.list.length; i++) {
				var id = $scope.list[i].id;
				if ($scope.role.id == id) {
					check_id=true;
				} else check_id=false;
			}
			if(check_id){
				alert('ID này đã tồn tại. Hãy nhập ID khác!');
			} else{				
				let role = angular.copy($scope.role);
				$http.post("/rest/addRole", role).then(resp => {
					$scope.list.push(resp.data);
					alert("Thêm thành công!");
					$scope.reset();
				}).catch(err => {
					console.log("err ", err);
				})
			}
		} else alert("Hãy nhập đủ thông tin!");
		
	}
	
	$scope.update = function () {
		let role = angular.copy($scope.role);
		$http.put("/rest/updateRole", role).then(resp => {
			let i = $scope.list.findIndex(r => r.id == role.id);
			$scope.list[i] = role;
		}).catch(err => {
			console.log("err ", err);
		})
	}
	
	$scope.updateRoleIsDeleted = function(role) {
		let roleItem = angular.copy(role);
		if(roleItem.isDeleted == false){
			roleItem.isDeleted = true;
		}else {
			roleItem.isDeleted = false;
		}
		
		$http.put("/rest/updateRoleIsDeleted", roleItem).then(resp => {
			let i = $scope.list.findIndex(b => b.id == role.id);
			$scope.list[i] = roleItem;
			
		}).catch(error => {
			alert("Lỗi update isDeleted");
			console.log("Error ", error);
		});
	}
	
	$scope.edit = function (role) {
		$scope.role = angular.copy(role);
		$('#idRole').prop('readonly', true);
		$("#add").attr("disabled","disabled");
		$("#update").removeAttr("disabled");
		$("#id").attr("disabled","disabled")
	}
	
	$scope.reset = ()=>{
		$scope.role = {isDeleted: false};
		$('#idRole').prop('readonly', false);
		$("#update").attr("disabled","disabled");
		$("#add").removeAttr("disabled");
		$("#id").removeAttr("disabled");
	}
})