app.controller("product-ctrl", function ($scope, $http) {
	$scope.pageSize = 10;

	$scope.items = [];
	$scope.cates = [];
	$scope.brand = [];
	$scope.form = {};
	const mapBrand = new Map();
	const mapCate = new Map();
	
	$scope.initilize = function (){
		$http.get("/rest/products").then(resp =>{
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createDate = new Date(item.createDate)
			})
		})
		
		$http.get("/rest/categories").then(resp =>{
			$scope.cates = resp.data;
			
			for (let x in $scope.cates) {
  				mapCate.set($scope.cates[x].id, $scope.cates[x]);
			}
		})
		
		
		$http.get("/rest/brandIsDeleted").then(resp =>{
			$scope.brand = resp.data;
			
			for (let x in $scope.brand) {
  				mapBrand.set($scope.brand[x].id, $scope.brand[x]);
			}
		});
	}
	
	$scope.edit = function(item){
		$scope.form = angular.copy(item);
		editor1.setHTMLCode($scope.form.discription)
		page_scrolltop();
	}
	
	function page_scrolltop(){
		$('html,body').animate({
			scrollTop: 0
	    }, 0);
	}
	
	$scope.changeImage = function (files){
		let dataF = new FormData();
        dataF.append("file", files[0]);
        console.log(dataF.get("file"))
        $http.post("/rest/upload/ImageP", dataF, {
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
	
	$scope.create = function(valid){
		if(valid){
			$scope.form.discription = editor1.getHTMLCode();
			let item = angular.copy($scope.form);
			if(item.available == undefined) {
				item.available = true;
			}
			$http.post('/rest/products', item).then(resp =>{
				resp.data.createDate = new Date(resp.data.createDate);
				$scope.itemsNew = resp.data;
				$scope.itemsNew.brand = mapBrand.get(resp.data.brand.id);
				$scope.itemsNew.category = mapCate.get(resp.data.category.id);
				$scope.items.push($scope.itemsNew);
				$scope.reset();
				alert("Thêm mới thành công sản phẩm!");
				
				$('body,html').animate({ scrollTop: $('body').height() }, 0);
			}).catch(err => {
				alert("Lỗi thêm mới sản phẩm!");
				console.log(err);
			});
		} else alert(valid);
	}
	
	$scope.update = function(){
		$scope.form.discription = editor1.getHTMLCode();
		let item = angular.copy($scope.form);
		$http.put(`/rest/products/${item.id}`, item).then(resp => {
			let i = $scope.items.findIndex(p => p.id == item.id);
			item.brand = mapBrand.get(item.brand.id);
			item.category = mapCate.get(item.category.id);
			$scope.items[i] = item;
			
			alert("Cập nhật thành công sản phẩm!");
		}).catch(err => {
			alert("Lỗi cập nhật sản phẩm!");
			console.log(err);
		});
	}
	
	$scope.delete = function(id){
		if (confirm("Bạn có muốn xóa sản phẩm này?") == true) {
			$http.delete(`/rest/products/${id}`).then(resp => {
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
	
	let editor1 = new RichTextEditor("#div_editor1");
	$scope.reset = function(){
		$scope.form = {
			image: 'no_img.png',
			category: {id: 1},
			brand: {id: 1},
			available: true,
			createDate: new Date(),
			discription:""
		};
		editor1.setHTMLCode($scope.form.discription)
	}
	
	
	
	$scope.initilize();
	$scope.reset();
	
	$scope.export = function(){
		$http.get('/rest/export').then(resp =>{
			
		}).catch(err => {
			alert("Lỗi xuất file");
			console.log(err);
		});
	}
	

    
})