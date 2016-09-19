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
			// process stuff from model
			// disable location event
			// put them into location
			// updateResults()
			var params = ctrl.processParams($scope.params);
			skipLocationEvent = true;
			$location.search(params);
			ctrl.updateResults(params);
		};

		ctrl.locationChanged = function() {
			// process stuff from location
			// put them into $scope.params
			// updateResults()
			if (!skipLocationEvent) {
				var params = ctrl.processParams($location.search());
				$scope.params = params;
				ctrl.updateResults(params);
			}
			else {
				skipLocationEvent = false;
			}
		};
		$scope.$on("$locationChangeSuccess", ctrl.locationChanged);

		ctrl.processParams = function(params) {
			var p = {};
			p.orderBy = params.orderBy ? params.orderBy : "ADDITION_DATE";
			p.asc = params.asc ? true : false; // In case of undefined
			if (params.title) p.title = params.title;
			if (params.categories) {
				var type = Object.prototype.toString.call(params.categories);
				if (type === "[object Object]") {
					var keys = Object.keys(params.categories);
					if (keys.length !== 0) 
						p.categories = keys;
				}
				else if (type === "[object Array]") {

				}
				else { // a single number (a string actually)

				}
			}

			return p;
		};

		ctrl.qwe = function() {

		};

		ctrl.updateResults = function(params) {
			Film.get(params, function(film) {
				$scope.films = film.content;
			});
		};


		$scope.params = {
			categories: {},
			orderBy: "ADDITION_DATE",
			asc: false,
		};
		$scope.params = ctrl.processParams({});
		ctrl.updateResults($scope.params);

	}]);
