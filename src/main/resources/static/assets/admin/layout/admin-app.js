app = angular.module("admin-app", ["angularUtils.directives.dirPagination","ngRoute"]);

app.config(function ($routeProvider) {
	$routeProvider
	.when("/product", {
		templateUrl: "/assets/admin/product/product.html",
		controller: "product-ctrl"
	})
	.when("/product_images", {
		templateUrl: "/assets/admin/product/product_images.html",
		controller: "product_images-ctrl"
	})
	.when("/categories", {
		templateUrl: "/assets/admin/categories/category.html",
		controller: "category-ctrl"
	})
	.when("/order", {
		templateUrl: "/assets/admin/order/order.html",
		controller: "order-ctrl"
	})
	.when("/detail/:id", {
		templateUrl: "/assets/admin/order/order_detail.html",
		controller: "detail-ctrl"
	})
	.when("/account", {
		templateUrl: "/assets/admin/account/account.html",
		controller: "account-ctrl"
	})
	.when("/autho", {
		templateUrl: "/assets/admin/account/authority.html",
		controller: "authority-ctrl"
	})
	.when("/dis", {
		templateUrl: "/assets/admin/discount/discount.html",
		controller: "discount-ctrl"
	})
	.when("/chartCategories", {
		templateUrl: "/assets/admin/chart/statsbycate.html",
		controller: "statsbycate-ctrl"
	})
	.when("/chartByMonth", {
		templateUrl: "/assets/admin/chart/statsbymonth.html",
		controller: "statsbymonth-ctrl"
	})
	
	.when("/chartByQtyProduct", {
		templateUrl: "/assets/admin/chart/statsbyqty_producct.html",
		controller: "statsbyqty_producct-ctrl"
	})
	
	.when("/chartByClient", {
		templateUrl: "/assets/admin/chart/statsbyclient.html",
		controller: "statsbyclient-ctrl"
	})
	
	.when("/brand", {
		templateUrl: "/assets/admin/brand/brand.html",
		controller: "brand-ctrl"
	})
	.when("/role", {
		templateUrl: "/assets/admin/role/role.html",
		controller: "role-ctrl"
	})
	.when("/address", {
		templateUrl: "/assets/admin/order/address.html",
		controller: "address-ctrl"
	})
	.when("/evalution", {
		templateUrl: "/assets/admin/evalution/evalution.html",
		controller: "evalution-ctrl"
	})

	.otherwise({
		//template: "<h1 class='text-center'>Administration</h1>"
		templateUrl: "/assets/admin/layout/main.html",
		controller: "main-ctrl"
	});
})