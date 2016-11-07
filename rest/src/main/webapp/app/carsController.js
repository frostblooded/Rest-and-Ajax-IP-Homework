var app = angular.module('restApp', ['infinite-scroll']);

app.controller('CarsController', function($scope, $http, cars) {
	$scope.cars = new cars();
	
	$http.get('api/colors').success(function(res) {
		$scope.colors = res;
	});
});

app.factory('cars', ['$http', function($http) {
	var cars = function() {
		this.items = [];
		this.busy = false;
		this.shown_pages = 0;
	}

	cars.prototype.loadNext = function() {
		if (this.busy) return;
		this.busy = true;
		
		var options = {
			params: {
				page: this.shown_pages
			}
		};
	
		$http.get('api/cars', options).success(function(res) {
			for (var i = 0; i < res.length; i++) {
				this.items.push(res[i]);
			}
	      
			this.shown_pages++;
			this.busy = false;
			
			// Hot fix for when the contents don't fill the page
			// and I don't want to compile the newest version of
			// the library because I don't know how and I am lazy
	        $(window).scroll();
		}.bind(this));
	}
	
	return cars;
}]);