angular.module("app")
	.controller("HomeController", ["$http", "$scope", "Film", function($http, $scope, Film) {
		var controller = this;

		var query = function(orderBy) {
			return Film.query({ orderBy: orderBy });
		};

		controller.recommendedFilms = query("recommendationDate");
		controller.mostWatchedFilms = query("views");
		controller.newestFilms = query("additionDate");
		
	}]);
	