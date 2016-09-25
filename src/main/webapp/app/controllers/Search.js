angular.module("app")
	.controller("SearchController", ["$http", "$scope", "$timeout", "Film", function($http, $scope, $timeout, Film) {
		var ctrl = this;

		this.search = function() {
			if (ctrl.query.length < 2) {
				ctrl.films = [];
				return;
			}
			ctrl.films = Film.get({
				title: ctrl.query,
				resultLimit: 5,
			}, function(film) {
				ctrl.films = film.content;
			});
		};
	}]);
