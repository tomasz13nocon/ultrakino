angular.module("app")
.controller("AddFilmController", ['$scope', 'Film', function($scope, Film) {
	var ctrl = this;

	ctrl.search = function(query) {
		if (query.length < 2) {
			$scope.films = [];
			return;
		}
		$scope.films = Film.get({
			title: query,
			resultLimit: 5,
		}, function(results) {
			if (results.content.length === 0)
				$scope.noResults = true;
			else
				$scope.noResults = false;
			$scope.films = results.content;
		});
	};

	ctrl.searchFilmweb = function() {
		ctrl.search($scope.title);
	};

	ctrl.fetchFilmwebLink = function() {
	};

}]);

