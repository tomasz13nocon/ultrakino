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
			p.categories = Object.keys(params.categories);

			return p;
		};

		ctrl.qwe = function() {

		};

		ctrl.updateResults = function() {
			$scope.films = Film.query($scope.params);
		};

		// ctrl.updateResults();

		/*$scope.params = {
			categories: {},
			orderBy: "ADDITION_DATE",
			asc: false,
		};*/
		$scope.params = ctrl.processParams({});

	}]);
