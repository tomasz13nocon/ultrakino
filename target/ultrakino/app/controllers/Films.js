angular.module("app")
	.controller("FilmsController", ["$http", "$scope", "Film", "$location", function($http, $scope, Film, $location) {
		var ctrl = this;


		$scope.orderBy = {
			"Daty dodania": "ADDITION_DATE",
			"Tytułu": "TITLE",
			"Premiery": "PREMIERE",
		};

		$scope.asc = {
			"Rosnąco": true,
			"Malejąco": undefined,
		};

		var skipLocationEvent = false;
		ctrl.modelChanged = function() {
			var params = ctrl.processParams($scope.params);
			skipLocationEvent = true;
			$location.search(params);
			ctrl.updateResults(params);
		};

		ctrl.locationChanged = function() {
			if (!skipLocationEvent) {
				var params = ctrl.processParams($location.search());
				console.log(params);
				ctrl.updateResults(params);
				$scope.params = params;
				for (var i = 0; i < params.categories.length; i++)
					$scope.params.categories[params.categories[i]] = true;
			}
			else {
				skipLocationEvent = false;
			}
		};
		$scope.$on("$locationChangeSuccess", ctrl.locationChanged);
		
		// Convert params to be eligible for API request
		ctrl.processParams = function(params) {
			var p = {
				categories: [],
			};
			p.orderBy = params.orderBy ? params.orderBy : "ADDITION_DATE";
			p.asc = params.asc ? true : false; // In case of undefined
			if (params.title) p.title = params.title;
			if (params.categories) {
				var type = Object.prototype.toString.call(params.categories);
				if (type === "[object Object]") {
					for (category in params.categories) {
						if (params.categories[category])
							p.categories.push(category);
					}
				}
				else if (type === "[object Array]") {
					p.categories = params.categories;
				}
				else { // a single number (a string actually)
					p.categories.push(params.categories);
				}
			}

			return p;
		};

		ctrl.updateResults = function(params) {
			Film.get(params, function(film) {
				$scope.films = film.content;
			});
		};

		var params = $location.search();
		if (Object.keys(params).length === 0) {
			$scope.params = {
				categories: {},
				orderBy: "ADDITION_DATE",
				asc: false,
			};
			ctrl.modelChanged();
		}
		else {
			ctrl.locationChanged();
		}

	}]);
