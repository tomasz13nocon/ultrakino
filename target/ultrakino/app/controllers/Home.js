angular.module("app")
	.controller("HomeController", ["$http", "$scope", "Film", function($http, $scope, Film) {
		var ctrl = this;

		Film.get({ orderBy: "RECOMMENDATION_DATE" }, function(film) {
			ctrl.recommendedFilms = film.content;
		});
		Film.get({ orderBy: "VIEWS" }, function(film) {
			ctrl.mostWatchedFilms = film.content;
		});
		Film.get({ orderBy: "ADDITION_DATE" }, function(film) {
			ctrl.newestFilms = film.content;
		});
		
	}]);
	