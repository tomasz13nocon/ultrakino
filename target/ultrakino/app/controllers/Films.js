angular.module("app")
	.controller("FilmsController", ["$http", "$scope", "Film", "$location", function($http, $scope, Film, $location) {
		var ctrl = this;


		$scope.orderBy = {
			"Daty dodania": "ADDITION_DATE",
			"Tytułu": "TITLE",
			"Premiery": "YEAR",
		};

		$scope.asc = {
			"Rosnąco": true,
			"Malejąco": false,
		};


		ctrl.modelChanged = function() {
			// var params = ctrl.processParams($scope.params);
			ctrl.updateResults();
		};

		ctrl.locationChanged = function() {

		};
		$scope.$on("$locationChangeSuccess", ctrl.locationChanged);

		ctrl.processParams = function(params) {
			var p = {};
			p.orderBy = params.orderBy ? params.orderBy : "ADDITION_DATE";
			p.asc = params.asc ? true : false; // In case of undefined
			if (params.title) p.title = params.title;
			if (params.categories) {
				var keys = Object.keys(params.categories);
				if (keys.length !== 0) 
					p.categories = keys;
			}

			return p;
		};

		ctrl.qwe = function() {

		};

		ctrl.updateResults = function() {
			Film.get($scope.params, function(film) {
				console.log(film);
				$scope.films = film.content;
			});
		};


		$scope.params = {
			categories: {},
			orderBy: "ADDITION_DATE",
			asc: false,
		};
		$scope.params = ctrl.processParams({});
		ctrl.updateResults();

	}]);
