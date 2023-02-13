app.controller("product_images-ctrl", function($scope, $http) {
	$scope.listProduct = [];
	$scope.itemProduct = {};
	$scope.itemProd = {};
	$scope.listFileName = [];
	
	$scope.load = function (){
		$http.get('/rest/products').then(resp =>{
			$scope.listProduct = resp.data;
		});
	}
	$scope.load();
	
	function previewImage(e, selectedFiles, imagesArray) {
		const elemContainer = document.createElement('div');
		elemContainer.setAttribute('class', 'item-images');
		elemContainer.setAttribute('class', 'row');
		for (let i = 0; i < selectedFiles.length; i++) {
			imagesArray.push(selectedFiles[i]);
			const imageContainer = document.createElement('div');
			imageContainer.setAttribute('class', 'col-2')
			const elem = document.createElement('img');
			elem.setAttribute('src', URL.createObjectURL(selectedFiles[i]));
			elem.setAttribute('class', 'item-photo__preview')
			const removeButton = document.createElement('button');
			removeButton.setAttribute('type', 'button');
			removeButton.classList.add('delete');
			removeButton.dataset.filename = selectedFiles[i].name,
				removeButton.innerHTML = '<span>&times;</span>'
			imageContainer.appendChild(elem);
			imageContainer.appendChild(removeButton);
			elemContainer.appendChild(imageContainer);
		}

		return elemContainer;
	}
	let item_images = [];
	document.getElementById('photo-upload').addEventListener('change', (e) => {
		let selectedFiles = e.target.files;
		const photoPreviewContainer = document.querySelector('#photo-upload__preview');
		const elemContainer = previewImage(e, selectedFiles, item_images);
		
		photoPreviewContainer.appendChild(elemContainer);
	});

	document.getElementById('photo-upload__preview').addEventListener('click', (e) => {
		const tgt = e.target.closest('button');
		if (tgt.classList.contains('delete')) {
			tgt.closest('div').remove();
			const fileName = tgt.dataset.filename
			item_images = item_images.filter(img => img.name != fileName)
			document.getElementById('photo-upload').value = "";
		}

	})

	$scope.listFileName = [];
	$scope.save = function() {
		var data = new FormData();
		for (let i = 0; i < item_images.length; i++) {
            data.append("files", item_images[i]);
        }
	
		$http.post(`/rest/product_photo/ImageP/${$scope.itemProduct.id}`, data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			//console.log(resp.data)
			$scope.listFileName.push(...resp.data);
			const collection = document.getElementsByClassName("upload-preview");
			collection[0].innerHTML = "";
			document.getElementById('photo-upload').value = "";
			item_images = [];
			alert("Upload thành công hình ảnh");
		}).catch(error => {
			alert("Lỗi upload hình ảnh");
			console.log("Error ", error);
		});
		console.log(item_images)
	}
	
	$scope.select = function() {
		//console.log($scope.itemProduct.id);
		if($scope.itemProduct.id != undefined){
		
		$http.get(`/rest/ProductPhotoByProId/${$scope.itemProduct.id}`).then(resp =>{
			$scope.listFileName = [];
			$scope.listFileName.push(...resp.data);
			
			let i = $scope.listProduct.findIndex(p => p.id == $scope.itemProduct.id);
			$scope.itemProd = $scope.listProduct[i];
			
			console.log($scope.itemProd)
		}).catch(error => {
			console.log("Error ", error);
		});
		}
		
	};
	
	$scope.delete = function (name){
		console.log(name)
		
		$http.delete(`/rest/deleteProductPhoto/${name}`).then (resp => {
			let i = $scope.listFileName.findIndex(n => n == name);
			$scope.listFileName.splice(i, 1);
			alert("Xóa thành công");
		}).catch(error => {
			alert("Lỗi khi xóa");
			console.log("Error ", error);
		});

	};
	
})