angular.module("app")
	.controller("HomeController", ["$http", "$scope", "Film", function($http, $scope, Film) {
		var ctrl = this;

		Film.get({ orderBy: "RECOMMENDATION_DATE", asc: true }, function(film) {
			ctrl.recommendedFilms = film.content;
		});
		Film.get({ orderBy: "VIEWS", asc: true  }, function(film) {
			ctrl.mostWatchedFilms = film.content;
		});
		Film.get({ orderBy: "ADDITION_DATE", asc: true  }, function(film) {
			ctrl.newestFilms = film.content;
		});
		
	}]);
	