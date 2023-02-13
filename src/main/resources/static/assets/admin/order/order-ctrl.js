app.config(['$httpProvider', function($httpProvider) {
	$httpProvider.interceptors.push('ApiInterceptor');
}]);

app.service('ApiInterceptor', [function() {
	var service = this;
	service.request = function(config) {
		// config.headers['Content-Type'] = 'application/json';
		// config.headers['Token'] = 'f805f85a-5739-11ed-9ad7-269dd9db11fd';
		return config;
	};
}]);

app.controller("order-ctrl", function($scope, $http) {
	$scope.listOrder = [];
	$scope.order = {};

	function load() {
		let list = [];
		$http.get("/rest/order").then(resp => {
			list = resp.data;
			
			for(let i = 0; i < list.length; i++){
				let address = list[i].address.split(",");
				let xa = address[0].split(":");
				let quan = address[1].split(":");
				let tp = address[2].split(":");
				
				let newAddress = address[3]+","+xa[1] + "," + quan[1] + "," + tp[1];
				
				list[i].address = newAddress;
			}
			$scope.listOrder = list;
		})
	}

	load();

	$scope.linkDetail = function(id) {
		window.location = `#!detail/${id}`
	}

	function loadAddressFrom (){
		let addressFrom = "";
		try{
			addressFrom = localStorage.getItem("address");
			if(addressFrom == undefined){
				alert('Vui lòng điền địa chỉ giao hàng');
				return;
			}
		}catch(err){
			
		}
		
		
		let arrAddressFrom = addressFrom.split(",");
		let provinceItemId = arrAddressFrom[2].split(":")[1];
		let districtItemId = arrAddressFrom[1].split(":")[1]
		let wardItemId = arrAddressFrom[0].split(":")[1]
		let addressItem =  arrAddressFrom[3]
		
		let addressFromItem = {
			province: provinceItemId,
			district: districtItemId,
			ward: wardItemId,
			address: addressItem
		}
		return addressFromItem;
	}
	
	 function loadAddressTo (address){
		 	let ad = address.split(",");
		 	let dia_chi = ad[0];
		 	let xa = ad[1];
		 	let quan = ad[2];
		 	let tp = ad[3];
			
		 
		let addressToItem = {
			province: tp,
			district: quan,
			ward: xa,
			address:dia_chi
		}
		
		console.log(addressToItem)
		
		return addressToItem;
	}
	
	function Detail (name, idPrduct, quantity, price, cateName){
		this.name = name;
		this.code = `${idPrduct}`;
		this.quantity = quantity;
		this.price = price;
		this.length = 12;
		this.width = 12;
		this.height = 12;
		this.category = cateName
		
	}
	
	function itemDetail(id){
		const arrDetail = [];
		$http.get(`/rest/getOrderDetail/${id}`).then(resp => {
		 	resp.data.forEach((item) => {
  				let detail = new Detail(item.product.name, item.product.id, 
  								item.quantity, item.price, {level3: item.product.category.name});
  				arrDetail.push(detail);
			});
			
		});
		return arrDetail;
	}
	
	$scope.xacNhan = function(order) {
		let addressFrom = loadAddressFrom();
		
		$scope.order = angular.copy(order);
		$scope.order.orderStatus = "Đã giao";

		let addressTo = loadAddressTo(order.address);
		let arrDetail = itemDetail(order.id)
		// console.log(arrDetail)
		 dataOrder(order, addressFrom, addressTo, arrDetail);
		
		 $scope.createHD ();
		 $http.put("/rest/orderUpdate", $scope.order).then(resp => {
			 let i = $scope.listOrder.findIndex(o => o.id = order.id);
			 $scope.listOrder[i].orderStatus = "Đã giao";
			
			 alert("Đã cập nhật")
			 location.reload();
		 }).catch(error => {
			console.log(error);
		 });
		
	}

	function dataOrder(order, addressFrom, addressTo, arrDetail) {
		$scope.GHN = {
			"payment_type_id": 2,
			"note": order.note,// order.note
			"from_name": "Tin", // account.fullname
			"from_phone": "0909999999", // account.phoneNumber
			"from_address": addressFrom.address,
			"from_ward_name": addressFrom.ward,
			"from_district_name": addressFrom.district,
			"from_province_name": addressFrom.province,
			"required_note": "CHOXEMHANGKHONGTHU",
			"return_name": "Tin",
			"return_phone": "0909999999",
			"return_address": addressFrom.address,
			"return_ward_name": addressFrom.ward,
			"return_district_name": addressFrom.district,
			"return_province_name": addressFrom.province,
			"client_order_code": "",
			"to_name": order.account.fullname, // order.account
			"to_phone": order.account.phoneNumber,
			"to_address": addressTo.address,
			"to_ward_name": addressTo.ward,
			"to_district_name": addressTo.district,
			"to_province_name": addressTo.province,
			"cod_amount": 10000000,
			"content": "Theo New York Times",
			"weight": 30000,
			"length": 150,
			"width": 150,
			"height": 150,
			"pick_station_id": null,
			"deliver_station_id": null,
			"insurance_value": 5000000,
			"service_id": 0,
			"service_type_id": 2,
			"coupon": null,
			"pick_shift": null,
			"pickup_time": null,
			"items": arrDetail
		}
	}

	$scope.createHD = function() {
		let urlProvince = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create";
		$scope.itemProvince = {}
		console.log($scope.GHN)
		$http.post(urlProvince, $scope.GHN, {

		headers: { 'Content-Type': 'application/json' },
		headers: { 'Token': 'f805f85a-5739-11ed-9ad7-269dd9db11fd' }
	}).then(resp => {
//			console.log(resp.data)
//			alert("ok")
		}).catch(error => {
			console.log(error);
		});
		
	}

//	$scope.adr = function (){
//		console.log("ok " + $scope.listOrder.length)
//		
//			
//			for(let i = 0;i < $scope.listOrder.length; i++){
//				try {
//				let idd = `#ad${$scope.listOrder[i].id}`;
//				let data = $(idd).text();
//				 
//				 let address = data.split(",");
//				 let xa = address[0].split(":");
//				 let quan = address[1].split(":");
//				 let tp = address[2].split(":");
//					
//				 let newAddress = address[3]+","+xa[1] + "," + quan[1] + "," + tp[1];
//				 
//				 let iddd = `ad${$scope.listOrder[i].id}`;
//				document.getElementById(iddd).innerHTML = newAddress
//				console.log(i)
//				}catch(error){
//					console.log(error)
//				}
//			}
//
//		
//		
//	}
	
	

})
