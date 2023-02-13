app.controller("discount-ctrl", function ($scope, $http, $rootScope) {
	$scope.listProduct = [];
	$scope.listDiscount = [];
	$scope.account = $rootScope.admin//{username:'duylnk'}
	$scope.discount = {};
	
	$scope.load = function (){
		$http.get('/rest/productNotInDiscount').then(resp =>{
		
			$scope.listProduct = resp.data;
		});
		
		$scope.tmp=[];
		$http.get('/rest/discount').then(resp =>{
			$scope.tmp = resp.data;

			for(d of $scope.tmp){
				d.createDate = new Date(d.createDate);
				d.endDate = new Date(d.endDate);
			}
			$scope.listDiscount = $scope.tmp;
		});
	}
	$scope.load();
	
	$scope.addToDiscount = function(p){
		$scope.discount = {
			percentage: 1,
			createDate: new Date(),
			endDate: new Date(),
			product: p,
			account: $scope.account
		}
		
		
		$http.post('/rest/discount', $scope.discount).then(resp =>{
			$scope.discount = resp.data;
			$scope.discount.createDate = new Date($scope.discount.createDate);
			$scope.discount.endDate = new Date($scope.discount.endDate);
			
			
			
			$scope.listDiscount.push($scope.discount);
			let index = $scope.listProduct.findIndex(pro => pro.id == p.id)
			$scope.listProduct.splice(index, 1);
			
			 $(".nl1").removeClass('active');
			 $(".nl2").addClass('active');
			 
			 $(".tb1").removeClass('active');
			 $(".tb2").addClass('active show');

		}).catch(err => {
			console.log(err)
		});
	}
	
	$scope.del = function(prod, discountItem){
		$http.delete(`/rest/discount/${discountItem.id}`, $scope.discount).then(resp =>{
			console.log("ok xóa")
			let index = $scope.listDiscount.findIndex(dis => dis.id == discountItem.id)
			$scope.listDiscount.splice(index, 1);
			
			$scope.listProduct.push(prod);
			
			$(".nl2").removeClass('active');
			 $(".nl1").addClass('active');
			 
			 $(".tb2").removeClass('active show');
			 $(".tb1").addClass('active ');
		}).catch(err => {
			console.log(err)
		});
	}
	
	$scope.save = function(discountItem){
		let idCreateDate = `#txt_createDate${discountItem.id}`;
		let idEndDate = `#txt_endDate${discountItem.id}`;
		let idPercentage = `#txt_per${discountItem.id}`;
		
		let creatDate = new Date($(idCreateDate).val());
		let endDate = new Date($(idEndDate).val());
		let percentage = Number($(idPercentage).val());
		let now = new Date();
		let check = false;
		
		const [day_creatDate, month_creatDate, year_creatDate] = (creatDate.getDate() + "-" + 
							(creatDate.getMonth() + 1) + "-" + creatDate.getFullYear()).split('-');
		const date_creatDate = new Date(+year_creatDate, +month_creatDate - 1, +day_creatDate);

		const [day_endDate, month_endDate, year_endDate] = (endDate.getDate() + "-" + 
							(endDate.getMonth() + 1) + "-" + endDate.getFullYear()).split('-');
		const date_endDate = new Date(+year_endDate, +month_endDate - 1, +day_endDate);
		
		const [day_now, month_now, year_now] = (now.getDate() + "-" + 
							(now.getMonth() + 1) + "-" + now.getFullYear()).split('-');
		const date_now = new Date(+year_now, +month_now - 1, +day_now);

		if (date_creatDate.getTime() >= date_now.getTime() && date_endDate.getTime() >= date_now.getTime()) {
			if(percentage>0 && percentage<=100)
				check = true;
			else {
				alert('Phần trăm nằm trong khoảng từ 0 đến 100!');
				check = false;
			}
		}else{
			alert('Ngày tạo và kết thúc phải bắt đầu từ ngày hiện tại!');
			check = false;
		}
		
		if (check) {
			$scope.discountI = discountItem;
			$scope.discountI.createDate = new Date($(idCreateDate).val());
			$scope.discountI.endDate =  new Date($(idEndDate).val());
			$scope.discountI.percentage = $(idPercentage).val() *1;
			
			$http.put('/rest/discount', $scope.discountI).then(resp =>{
				let i = $scope.listDiscount.findIndex(dis => dis.id == discountItem.id);
				$scope.listDiscount[i] = $scope.discountI;
				alert('Thêm thành công')
			}).catch(err => {
				console.log(err)
			});
		}
		
	}
	
	$scope.pager = {
		page: 0,
		size: 6,
		get items (){
			var start = this.page * this.size;
			return $scope.listDiscount.slice(start, start + this.size);
		},
		get count (){
			return Math.ceil(1.0 * $scope.listDiscount.length / this.size);
		},
		
		first (){
			this.page = 0;	
		},
		
		prev (){
			this.page--;
			if(this.page < 0){
				this.last();
			}
		},
		
		next (){
			this.page++;
			if(this.page >= this.count){
				this.first();
			}
		},
		
		last (){
			this.page = this.count - 1;
		}
	};
	
	
})