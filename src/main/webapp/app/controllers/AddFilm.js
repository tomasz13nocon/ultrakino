angular.module("app")
.controller("AddFilmController", ['$scope', 'Film', 'Filmweb', function($scope, Film, Filmweb) {
	var ctrl = this;

	$scope.contentType = "FILM";
	$scope.retrievingFilms = 0;

	ctrl.search = function(query) {
		if (query.length < 2) {
			$scope.films = [];
			return;
		}
		$scope.retrievingFilms += 1;
		Filmweb.query({
			title: query,
			contentType: $scope.contentType,
		}, function(results) {
			$scope.films = results;
			$scope.retrievingFilms -= 1;
		});
	};

	ctrl.searchFilmweb = function() {
		ctrl.search($scope.title);
	};

	ctrl.fetchFilmwebLink = function() {
	};

}]);

