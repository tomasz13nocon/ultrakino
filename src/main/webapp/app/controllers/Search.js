angular.module("app")
.controller("SearchController", ["$http", "$scope", "$timeout", "Film", "$location", function($http, $scope, $timeout, Film, $location) {
	var ctrl = this;

	ctrl.resultClicked = function(film) {
		$location.url("filmy/" + film.uid + "/" + film.title + "-" + film.year);
		$scope.showResults = false;
	};

	ctrl.search = function() {
		if (ctrl.query.length < 2) {
			ctrl.films = [];
			return;
		}
		ctrl.films = Film.get({
			title: ctrl.query,
			resultLimit: 5,
		}, function(results) {
			if (results.content.length === 0)
				$scope.noResults = true;
			else
				$scope.noResults = false;
			ctrl.films = results.content;
		});
	};
}]);
