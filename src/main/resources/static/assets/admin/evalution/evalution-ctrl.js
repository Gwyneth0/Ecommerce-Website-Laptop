app.controller("evalution-ctrl", function($scope, $http) {
	$scope.list = [];

	$http.get("/rest/getProductEvalution").then(resp => {
		$scope.list = resp.data;

	}).catch(err => {
		console.log("err ", err);
	})

	$scope.stars = [];
	for (let i = 0; i < 5; i++) {
		$scope.stars.push(i);
	}

	// popup 
	$(document).on('mouseenter click', '.popup-link', function() {
		var popupContent = $(this).parent('.popup-container').find('.popup-body');
		popupContent.addClass('show-popup');
	})

	$(document).on('mouseleave', '.popup-body', function() {
		$(".popup-body").removeClass('show-popup');
	})

	$(document).on('click touch', function(e) {
		if (!e.target.classList.contains(".popup-body")) {
			$(".popup-body").removeClass('show-popup');
		}
	});
	// end popup
	

	$scope.status = function (eva) {
		let item = angular.copy(eva);
		if(eva.status == false){
			item.status = true;
		}else {
			item.status = false;
		}
		$http.put(`/rest/upProductEvalution`, item).then(resp => {
			let i = $scope.list.findIndex(e => e.id == item.id);
			$scope.list[i] = item;
		}).catch(err =>{
			console.log(err);
		});
		
	}
	
	$scope.del = function(evaId){
		$http.delete(`/rest/delProductEvalution/${evaId}`).then(resp => {
			let i = $scope.list.findIndex(e => e.id == evaId);
			$scope.list.splice(i,1);
		}).catch(err =>{
			console.log(err);
		});
	}
})