var app = angular.module('restApp', ['infinite-scroll', 'ngMaterial']);

var NO_FILTER = "no_filter";
var PER_PAGE = 20;

function infiniteScrollFix() {
	// Hot fix for when the contents don't fill the page
	// and I don't want to compile the newest version of
	// the library because I don't know how and I am lazy
    $(window).scroll();
}

app.controller('CarsController', function($scope, $http, $timeout, cars) {
	$scope.cars = new cars();
	$scope.new_car = {
        error: ""
    };
	
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
	
	$scope.loadModels = function(text) {
		return $http.get('api/models', {params: {contains: text}}).then(function(res) {
			return res.data;
		});
	}
	
	$scope.loadYears = function(text) {
		return $http.get('api/years', {params: {contains: text}}).then(function(res) {
			return res.data;
		});
	}

	$scope.showError = function(text) {
        $scope.new_car.error = text;

        $timeout(function () {
            $scope.new_car.error = "";
        }, 3000);
    }

    $scope.create = function(new_car) {
        if($scope.validate_car(new_car) == -1)
            return -1;

        // The data is almost the same as the object new_car,
        // but doesn't contain the 'error' field and it seems
        // better to send only this fields specifically instead of
        // the object new_car
        var data = {
            manufacturer: new_car.manufacturer,
            model: new_car.model,
            year: new_car.year,
            color: new_car.color
        }

        $http.post('api/cars', data).success(function() {
            new_car.manufacturer = "";
            new_car.model = "";
        });
    }

    $scope.validate_car = function (new_car) {
        if(!new_car.manufacturer || new_car.manufacturer == "") {
            $scope.showError("Please enter a manufacturer");
            return -1;
        }

        if(!new_car.model || new_car.model == "") {
            $scope.showError("Please enter a model");
            return -1;
        }

        if(!new_car.year || new_car.year == "") {
            $scope.showError("Please enter a year");
            return -1;
        }

        if(!new_car.color || new_car.color == "") {
            $scope.showError("Please enter a color");
            return -1;
        }
    }
});

// Factory based on code from https://sroze.github.io/ngInfiniteScroll/demo_async.html
app.factory('cars', ['$http', function($http) {
	var cars = function() {
		this.clean();
		this.color_filter = null;
		this.manufacturer_filter = null;
		this.model_filter = null;
		this.year_filter = null;
	}
	
	cars.prototype.clean = function() {
		this.items = [];
		this.busy = false;
		this.shown_all = false;
		this.shown_pages = 0;
	}

	cars.prototype.loadNext = function() {
		if (this.busy) return;
		this.busy = true;
		
		var options = {
			params: {
				page: this.shown_pages,
                perPage: PER_PAGE
			}
		};
		
		if(this.color_filter)
			options.params.color = this.color_filter;
		
		if(this.manufacturer_filter)
			options.params.manufacturer = this.manufacturer_filter;
		
		if(this.model_filter)
			options.params.model = this.model_filter;
		
		if(this.year_filter)
			options.params.year = this.year_filter;
	
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
	
	cars.prototype.loadFilteredByModel = function(model) {
		this.model_filter = model;
		this.clean();
		this.loadNext();
	}
	
	cars.prototype.loadFilteredByYear = function(year) {
		this.year_filter = year;
		this.clean();
		this.loadNext();
	}
	
	return cars;
}]);