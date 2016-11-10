var app = angular.module('restApp', ['infinite-scroll', 'ngMaterial']);

var NO_FILTER = "no_filter";

function infiniteScrollFix() {
	// Hot fix for when the contents don't fill the page
	// and I don't want to compile the newest version of
	// the library because I don't know how and I am lazy
    $(window).scroll();
}

app.controller('CarsController', function($scope, $http, cars) {
	$scope.cars = new cars();
	$scope.new_car = {};
	
	$http.get('api/colors').success(function(res) {
		$scope.colors = res;
		$scope.new_car.color = res[0];
		$scope.filter_color = NO_FILTER;
	});
	
	$scope.loadManufacturers = function(text) {
		return $http.get('api/manufacturers', {params: {contains: text}}).then(function(res) {
			return res.data;
		});
	}
});

// Factory based on code from https://sroze.github.io/ngInfiniteScroll/demo_async.html
app.factory('cars', ['$http', function($http) {
	var cars = function() {
		this.clean();
		this.color_filter = null;
		this.manufacturer_filter = null;
	}
	
	cars.prototype.clean = function() {
		this.items = [];
		this.busy = false;
		this.shown_all = false;
		this.shown_pages = 0;
	}
	
	cars.prototype.create = function(new_car) {
		$http.post('api/cars', new_car).success(function() {
			new_car.manufacturer = "";
			new_car.model = "";
		});
	}

	cars.prototype.loadNext = function() {
		if (this.busy) return;
		this.busy = true;
		
		var options = {
			params: {
				page: this.shown_pages
			}
		};
		
		if(this.color_filter)
			options.params.color = this.color_filter;	
		
		if(this.manufacturer_filter)
			options.params.manufacturer = this.manufacturer_filter;
	
		$http.get('api/cars', options).success(function(res) {
			for (var i = 0; i < res.length; i++) {
				this.items.push(res[i]);
			}
	      
			this.shown_pages++;
			infiniteScrollFix();
		}.bind(this)).error(function(err, status) {
			if(status == 404)
				this.shown_all = true;
		}.bind(this)).finally(function() {
			this.busy = false;
		}.bind(this));
	}
	
	cars.prototype.loadFilteredByColor = function(color) {
		this.color_filter = color == NO_FILTER ? null : color;
		this.clean();
		this.loadNext();
	}
	
	cars.prototype.loadFilteredByManufacturer = function(manufacturer) {
		this.manufacturer_filter = manufacturer;
		this.clean();
		this.loadNext();
	}
	
	return cars;
}]);