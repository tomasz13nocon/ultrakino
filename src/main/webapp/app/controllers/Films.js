angular.module("app")
	.controller("FilmsController", ["$http", "$scope", "Film", function($http, $scope, Film) {

		$scope.params = {
			categories: [],
			orderBy: "additionDate",
			asc: false
		};

		$scope.orderBy = {
			"Daty dodania": "additionDate",
			"Tytułu": "title",
			"Premiery": "year",
		};

		$scope.asc = {
			"Rosnąco": true,
			"Malejąco": undefined
		};

		this.updateResults = function() {
			var cats = $scope.params.categories;
			for (var i = 0; i < cats.length; i++) {
				if (cats[i] == -1)
					delete cats[i];
			}
				
			// TODO: Optimization. When I only change, say, a letter in the title,
			// don't traverse through all the params
			var params = {};
			Object.keys($scope.params).forEach(function(key) {
				if ($scope.params[key])
					params[key] = $scope.params[key];
			});

			$scope.films = Film.query(params);
		};

		this.updateResults();

	}]);
