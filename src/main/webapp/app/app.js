api = "http://localhost:8080/ultrakino/api";
templateDir = "templates";

angular.module("app", ["ngRoute", "ngAnimate"]);

angular.module("app")
	.config(["$routeProvider", function($routeProvider) {
		$routeProvider
		.when("/", {
			templateUrl: templateDir + "/home.html",
			controller: "HomeController",
			controllerAs: "homeCtrl",
		})
		.when("/test", {
			templateUrl: templateDir + "/test.html",
			controller: "FilmController",
			controllerAs: "filmCtrl",
		})
		.when("/filmy/:id/:title*", {
			templateUrl: templateDir + "/film.html",
			controller: "FilmController",
			controllerAs: "filmCtrl",
		})
		.when("/filmy", {
			templateUrl: templateDir + "/films.html",
			controller: "FilmsController",
			controllerAs: "filmsCtrl",
			activeTab: "films",
		})
		.when("/seriale", {
			templateUrl: templateDir + "/404.html",
			activeTab: "series",
		})
		.when("/dodaj-film", {
			templateUrl: templateDir + "/404.html",
			activeTab: "addFilm",
		})
		.when("/test", {
			templateUrl: templateDir + "/404.html",
			activeTab: "test",
		})

		.otherwise({
			templateUrl: templateDir + "/404.html",
		});
	}]);
