const app = angular.module("shopping-cart-app", ["angularUtils.directives.dirPagination"]);

app.config([ '$httpProvider',   function($httpProvider) {
  $httpProvider.interceptors.push('ApiInterceptor');
} ]);

app.service('ApiInterceptor', [function() {
  var service = this;
  service.request = function(config) {
      // config.headers['Content-Type']= 'application/json';
      // config.headers['Token']= 'f805f85a-5739-11ed-9ad7-269dd9db11fd';
      return config;
  };
}]);

app.filter('between', function (){
	return function (input, min, max){
		let array =[];
		for (let i = 0; i < input.length; i++) {
			if(input[i].price >= min && input[i].price <= max){
				array.push(input[i])
			}
		}
		
		return array;
	}
	
})

app.controller("shopping-cart-ctrl", function($scope, $http) {

	const regPhone = /((09|03|07|08|05)+([0-9]{8})\b)/g;
	const regEmail = /^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6})*$/;
	
	$scope.cart = {
		items: [],

		add(id) {		 
			var item = this.items.find(item => item.id == id)
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			} else {
				$http.get(`/rest/products/${id}`).then(resp => {
					resp.data.qty = 1;
					this.items.push(resp.data);
					this.saveToLocalStorage();
				});
			}
		},
		
		addsale(id,percentage) {		 
			var item = this.items.find(item => item.id == id)
			var x = (100-percentage)/100
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			} else {
				$http.get(`/rest/products/${id}`).then(resp => {
					resp.data.qty = 1;
					resp.data.price = resp.data.price * x;
					this.items.push(resp.data);
					console.log(resp.data);
					this.saveToLocalStorage();
				});
			}
		},
		addtt(url) {		 
			alert(url)
		},
		
		message() {
			alert("Đơn hàng của bạn đã được xác nhận hiện không thể hủy đơn")
		},

		remove(id) {
			var index = this.items.findIndex(item => item.id = id);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
		},

		clear() {
			this.items = [];
			this.saveToLocalStorage();
		},

		amt_of(item) { },

		get count() {
			return this.items.map(item => item.qty).reduce((total, qty) => total += qty, 0);
		},

		get amount() {
			return this.items.map(item => item.qty * item.price).reduce((total, qty) => total += qty, 0);
		},

		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.items));
			localStorage.setItem("cart", json);
		},

		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.items = json ? JSON.parse(json) : [];
		}
	};

	$scope.cart.loadFromLocalStorage();

	
	
	// xoa don hang
	$scope.delete = function(id){
		$http.delete(`/rest/orders/${id}`).then(resp => {
			let i = $scope.cart.items.findIndex(o => o.id == id);
			$scope.cart.items.splice(i,1);
			location.href = "/order/list";
			alert("Hủy đơn thành công")
		}).catch(err => {
			alert("Lỗi")
			console.log(err);
		});
		 
	}
	
	// lấy thông tin user
	$scope.user = {};
	function load_user(){
		var username =  $("#username").text()
		sessionStorage.setItem("admin", username);
		console.log(username);
		$http.get(`/rest/account/${username}`).then(resp => {
			$scope.user = resp.data;
			// console.log(resp.data);
// $scope.order.phoneNumber = resp.data.phoneNumber;
// $scope.order.fullname = resp.data.fullname;
			$scope.order.address = resp.data.address;
			
			$scope.account.phoneNumber = resp.data.phoneNumber;
			$scope.account.fullname = resp.data.fullname;
			$scope.account.address = resp.data.address;
			
			$scope.loadAddressfromDB($scope.user);
		}).catch(error => {
			console.log(error);
		});
	}
	load_user();
	
	// dang ky
	$scope.account = {
			username:"",
			password:"",
			fullname:"",
			phoneNumber:"",
			email:"",
			photo:"",
			address:"",
			isDeleted:true,
		create(valid){
			var username = document.getElementById("usernameSigin").value;
				if(valid){
						$http.get(`/rest/account/${username}`).then(resp => {
							let data = resp.data;
								if(data == ""){
									var confirmpassword = $("#confirm-password").val();
									if ($scope.account.password == confirmpassword) {
										document.getElementById("messErConfirmPass").innerHTML = "";
										var account = angular.copy(this);
										$http.post("/rest/account", account).then(resp => {
											location.href = "/security/login/form";
											document.getElementById("errSigin").innerHTML = "Đăng ký thành công.";
											document.getElementById("errSigin").style.color = "red";
										}).catch(error => {
											console.log(error);
										});
									} else {
										document.getElementById("messErConfirmPass").innerHTML = "Mật khẩu không khớp.";
										document.getElementById("messErConfirmPass").style.color = "red";
									}
								}else{
									document.getElementById("errSigin").innerHTML = "Tài khoản đã tồn tại.";
									document.getElementById("errSigin").style.color = "red";
								}
							})
					
				}
		},
	
		// doimk
		changepass(){
			var passhientai = $("#passhientai").val()
			var passnew = $("#passnew").val()
			var confirm = $("#confirm").val()			
			var password = $("#password").text()
		// console.log(confirm + " " + password + " " + passhientai + " "
		// +passnew)
		if($scope.user.password == passhientai){
			if(passnew == confirm){
				// alert("thang dep zai")
				var account = angular.copy($scope.user);
				account.password = passnew;
				$http.put("/rest/account", account).then(resp => {
					alert("Đổi mật khẩu thành công!");
					 location.href = "/security/account_detail";
				}).catch(error => {
					alert("Lỗi!");
					console.log(error);
				});
			}else{
				alert("Mật khẩu không khớp")
			}
		}else{
			alert("Sai mật khẩu")
		}
	  },
	  
	  
	  updateAccount(){
		
		
		var messErPhone  =document.getElementById("messErPhone");
		  var account = angular.copy($scope.user);
		  
		  var phone = $("#phoneNumber ").val();
		  var fullname = $("#fullname").val();
		  var email = $("#emailUpdate").val();
		var isValidPhone = regPhone.test(phone);
		var isValidEmail = regEmail.test(email);
		
		if(!isValidPhone){
			messErPhone.style.color="red";
			messErPhone.innerHTML="Số điện thoại không phù hợp.";
		}else{
			messErPhone.innerHTML="";
		}
		
		if(!isValidEmail){
			messErEmail.style.color="red";
			messErEmail.innerHTML="Email không phù hợp.";
		}else{
			messErEmail.innerHTML="";
		}
		
		if(isValidPhone == true  && isValidEmail  == true){
			
			account.phoneNumber = phone;
			  account.fullname = fullname;
			  account.email = email;
			  account.address = $scope.save();
			  
			  $http.put("/rest/account", account).then(resp => {
					alert("Cập nhật thành công!");
					location.href = "/security/account_detail";
				}).catch(error => {
					alert("Lỗi!");
					console.log(error);
				});
		}	
		} 	  
	}
	
	$scope.client = {
			
	}
	
	$scope.check = function(){
		$scope.client.to_province_name = $scope.itemProvince.p.ProvinceName;	
		$scope.client.to_district_name = $scope.itemDistrict.d.DistrictName;
		$scope.client.to_ward_name = $scope.itemWard.w.WardName;
	}

	let urlProvince = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province";
	$scope.itemProvince = {}
	$http.get(urlProvince, {

		headers: { 'Content-Type': 'application/json' },
		headers: { 'Token': 'f805f85a-5739-11ed-9ad7-269dd9db11fd' }
	}).then(resp => {

		$scope.province = resp.data;

		// console.log($scope.itemProvince);
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
		

		let address = $scope.itemWard.id + ":" + nameWard + ","
			+ $scope.itemDistrict.id + ":" + nameDistrict + ","
			+ $scope.itemProvince.id + ":" + nameProvince + ","
			+ txtAdress;

		console.log(address);
		return address;
		// localStorage.setItem("address", address);
	}
	
	$scope.loadAddressfromDB = function(user){

			let getAccount = angular.copy(user);
			
			let address = getAccount.address;
	
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
	
	
	
 // dat hang
	$scope.order = {
		account: { username: $("#username").text()},
		createDate: new Date(),
		address:"",
		orderStatus:"Chưa xác nhận",
		// fullname:"",
		note:"",
		// phoneNumber:"",
		get orderDetails() {
			return $scope.cart.items.map(item => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty
				}
			});
		},
		purchase(pay) {
			// alert(pay)
			var order = angular.copy(this);
			if(pay == 'paypal'){
				order.orderStatus="Chưa xác nhận"
			}
			/*
			 * order.address= $scope.client.to_address +". " +
			 * $scope.itemWard.w.WardName + ", " +
			 * $scope.itemDistrict.d.DistrictName + ", " +
			 * $scope.itemProvince.p.ProvinceName + ", ";
			 */
			order.address= $scope.save();
				// order.phoneNumber = $scope.client.to_phone;
				// order.fullname = $scope.client.to_name;
			console.log(order);
			// thực hiện đặt hàng
			$http.post("/rest/orders", order).then(resp => {
				if(pay == 'normal'){
					alert("Đặt hàng thành công!");
					// $scope.cart.clear();
					location.href = "/order/detail/" + resp.data.id;
				}
			}).catch(error => {
				alert("Đặt hàng lỗi!");
				console.log(error);
			});

		}
		
		
	}
	
	
	
	// Danh gia san pham
	$scope.rating = {	
		account: "",
		evalution:"",
		comment:"",
		product:"",
		status: false,
		createDate: new Date(),
		
		numberstart(numberstart) {
			$scope.numberstart = numberstart;
		},
	
		addstart() {
			let Url = window.location.pathname.split("/");
			let pid = Url[Url.length-1];
			var cmt = $("#comment").val();
			
			var rating = angular.copy(this);
			rating.account = { username: $("#username").text()};
			rating.evalution = $scope.numberstart;
			rating.product = {id:pid*1};
			rating.comment = cmt;
			
			// console.log(rating);

			$http.get(`/rest/rating/${rating.account.username}/${rating.product.id}`).then(resp =>{
				console.log(resp.data)
				if(resp.data == true){
					$http.post("/rest/rating",rating).then(resp => {
						alert("Đánh giá đã được gửi! Chờ quản trị viên xét duyệt");
						console.log(rating);
						// $scope.cart.clear();
						location.href = "/product/detail/" + rating.product.id;
					}).catch(error => {
						alert("Lỗi!");
						console.log(error);
					});
				}else{
					alert("Bạn không thể đánh giá vì chưa mua sản phẩm n");
				}
			}).catch(error => {
				alert("Lỗi!");
				console.log(error);
			});
		}
	}
	
	// carousel nhieu anh
	$scope.selectImage = function(url){
		document.getElementById("changeImage").src="/assets/images/"+url;
	}
 
	// sms quen pass
	const reg= /((09|03|07|08|05)+([0-9]{8})\b)/g;
	$scope.sms = function(){
			let mess = document.getElementById("messEr");
			var sdt = $("#sdt").val();
			const isValid = reg.test(sdt);
			if(isValid){
				var code = Math.floor(Math.random() * 10000);
				sessionStorage.setItem("key_codephone", code);
				$scope.code_phone = code
				$http.get(`/rest/account/sdt/${sdt}`).then(resp =>{	
					// console.log(resp.data.phoneNumber);
					$scope.phone = resp.data.phoneNumber;
					if($scope.phone != undefined){
						let newphone = "+84" + $scope.phone.substring(1);
						alert(code);
						sessionStorage.setItem("key_sdt", $scope.phone);
						$http.post(`/sms`,{to:newphone,message:code}).then(resp =>{	
							alert(code);
							location.href = "/phone/form";
						}).catch(error => {
							console.log(error);
						});
					}else{
						alert("Số điện thoại không tồn tại");
					}
				}).catch(error => {
					console.log(error);
				});
			}else{
				mess.style.color="red";
				mess.innerHTML="Số điện thoại không đúng định dạng.";
			}
				
	}
	
	$scope.checkcode = function() {
		let code_sdt = $("#code_sdt").val();
		let key_codephone = sessionStorage.getItem("key_codephone");
		
		// alert(key_codephone + "-" + code_sdt );
		if(key_codephone == code_sdt){
			alert("Thành công");
			location.href = "/matkhaumoi/form";
		}else{
			alert("Mã code không đúng");
		}
	}
	
	$scope.forgotpass = function() {
		let sdt = sessionStorage.getItem("key_sdt")
		$http.get(`/rest/account/sdt/${sdt}`).then(resp =>{	
			$scope.ac = resp.data
			// console.log($scope.ac);
		}).catch(error => {
			console.log(error);
		});
		var passnew = $("#passnew").val()
		var confirm = $("#confirm").val()			

		if(passnew == confirm){
			// alert("thang dep zai")
			var account = angular.copy($scope.ac);
			account.password = passnew;
			$http.put("/rest/account", account).then(resp => {
				alert("Đổi mật khẩu thành công!");
				location.href = "/security/login/form";
			}).catch(error => {
				alert("Lỗi!");
				console.log(error);
			});
		}else{
			alert("Mật khẩu không khớp")
		}
  }
	
	
	// list_product
	$scope.products = [];
	$scope.brands = [];
	
	$scope.loadProduct = function(id){
		localStorage.setItem('idCate', id);
		$http.get(`/rest/productsByCateId/${id}`).then(resp => {
			$scope.products = resp.data;
			console.log($scope.products);
		}).catch(error => {
			console.log(error);
		});
		
		$http.get(`/rest/brandName/${id}`).then(resp => {
			$scope.brands = resp.data;
			
		}).catch(error => {
			console.log(error);
		});
		
		$scope.s = sessionStorage.getItem("s");
		sessionStorage.setItem("s", "");
	}
	$scope.getPrice = function (){
		let price = document.getElementById("price").innerHTML;
		let splitStr = price.split("-")
		let min = splitStr[0].trim().substring(0, splitStr[0].length - 3);
		let max = splitStr[1].trim().substring(0, splitStr[1].length - 3);
		

		$scope.min = min
		$scope.max = max
		
		console.log($scope.min + " " + $scope.max);
	}
	$scope.min = 0
	$scope.max = 100000000
	
	let id_cate = localStorage.getItem('idCate');
	$scope.loadProduct(id_cate);
	
	
	$scope.choseBrand = function(id){
		$scope.idC = id;
	}
	
	$scope.pri ="";
	$scope.sxTdenC = function(){
		$scope.pri = "'price'"
			console.log($scope.pri);
	}
	
	$scope.sxCdenT = function(){
		$scope.pri = "-'price'"
		console.log($scope.pri);
	}

	// SEARCH
	$scope.search = function(){
		sessionStorage.setItem("s", $scope.s);
	}
		let discount = [];
		//giam gia
		$http.get(`/rest/discount`).then(resp => {
			discount = resp.data;
			let eDate = $("#endDate").text();
			//thoi gian
			
			let check = setInterval(function(){
				for(let i = 0; i < discount.length; i++){
					let endDate = new Date(discount[i].endDate).getTime();
	
				    let now = new Date().getTime();
				    let distance = endDate - now;
				    
				    let day = Math.floor(distance / (24*60*60*1000));
				    let hour = Math.floor((distance % (24*60*60*1000)) / (60* 60*1000));
				    let minute = Math.floor((distance % (60* 60*1000)) / (60*1000));
				    let seconds = Math.floor((distance % (60*1000)) / 1000);
	
				    document.getElementById(`day_${discount[i].id}`).innerText = day;
				    document.getElementById(`hour_${discount[i].id}`).innerText = hour;
				    document.getElementById(`minute_${discount[i].id}`).innerText = minute;
				    document.getElementById(`seconds_${discount[i].id}`).innerText = seconds;
				    if(distance <= 0){
				        clearInterval(check);
				    }
				    if(day==0 && hour==0 && minute==0 && seconds==0){
				    	delectDiscount(discount[i].id);
				    }
				}
			}, 1000);
		}).catch(error => {
			console.log(error);
		});
		
		function delectDiscount (id){
			if(id == ""){
				return;
			} else{
				$http.delete(`/rest/discount/${id}`).then(resp => {
					location.reload();
				}).catch(erorr => {
					console.log(erorr);
				})
			}
		}
		
});

