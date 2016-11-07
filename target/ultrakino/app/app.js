api = "/api";
templateDir = "templates";
defaultAvatarFilename = "images/avatar3.png";

angular.module("app", ["ngRoute", "ngAnimate", "ngResource"]);

angular.module("app")
	.run(['$http', '$location', '$rootScope', '$route', function($http, $location, $rootScope, $route) {

		$rootScope.languageVersions = {
			VOICE_OVER: "Lektor",
			ORIGINAL: "Oryginał",
			DUBBING: "Dubbing",
			POLISH_FILM: "Polski Film",
			POLISH_SUBS: "Napisy PL",
			ENGLISH_SUBS: "Napisy ENG",
			"": "Nieznana",
		};

		$rootScope.filmCategories = {};
		$http.get(api + "/films/categories").then(function(resp) {
			for (var i = 0; i < resp.data.length; i++) {
				$rootScope.filmCategories[resp.data[i].id] = resp.data[i].name;
			}
		});

		$rootScope.seriesCategories = {};
		$http.get(api + "/series/categories").then(function(resp) {
			for (var i = 0; i < resp.data.length; i++) {
				$rootScope.seriesCategories[resp.data[i].id] = resp.data[i].name;
			}
		});

		//$rootScope.categories = {
			//47: "3D",
			//4: "Akcja",
			//48: "Animacja",
			//49: "Anime",
			//50: "Baśń",
			//6: "Biograficzny",
			//51: "Czarna komedia",
			//7: "Dokumentalny",
			//8: "Dramat",
			//52: "Erotyczny",
			//9: "Familijny",
			//10: "Fantasy",
			//53: "Gangsterski",
			//11: "Historyczny",
			//12: "Horror",
			//54: "Katastroficzny",
			//13: "Komedia",
			//55: "Komedia romantyczna",
			//56: "Kostiumowy",
			//14: "Kryminał",
			//57: "Musical",
			//15: "Muzyczny",
			//16: "Obyczajowy",
			//58: "Polityczny",
			//18: "Przygodowy",
			//59: "Przyrodniczy",
			//19: "Psychologiczny",
			//20: "Romans",
			//21: "Sci-Fi",
			//22: "Sensacyjny",
			//23: "Sportowy",
			//61: "Surrealistyczny",
			//60: "Szpiegowski",
			//24: "Thriller",
			//25: "Western",
			//26: "Wojenny",
		//};

		$rootScope.years = [];
		for (var i = new Date().getFullYear(); i > 1900; i--) {
			$rootScope.years.push(i);
		}

		$rootScope.authenticated = false;
		$rootScope.isAdmin = false;
		// TODO: Remove on prod
		if (window.location.href.indexOf("localhost") > -1)
			$rootScope.images = "//localhost:8000/";
		else
			$rootScope.images = "//images.ultrakino.pl/";
		$rootScope.noImage = "images/no-image.png";

		var original = $location.path;
		$location.path = function (path, reload) {
			if (reload === false) {
				var lastRoute = $route.current;
				var un = $rootScope.$on('$locationChangeSuccess', function () {
					$route.current = lastRoute;
					un();
				});
			}
			return original.apply($location, [path]);
		};

	}]);

angular.module("app")
	.config(["$routeProvider", "$httpProvider", "$locationProvider", function($routeProvider, $httpProvider, $locationProvider) {
		$routeProvider
		.when("/", {
			templateUrl: templateDir + "/home.html",
			controller: "HomeController",
			controllerAs: "homeCtrl",
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
			reloadOnSearch: false,
		})
		.when("/seriale", {
			templateUrl: templateDir + "/series.html",
			controller: "SeriesController",
			controllerAs: "seriesCtrl",
			activeTab: "series",
		})
		.when("/seriale/:id/:title/:episodeId?/:season?/:episode?", {
			templateUrl: templateDir + "/one-series.html",
			controller: "OneSeriesController",
			controllerAs: "oneSeriesCtrl",
			reloadOnSearch: false,
		})
		.when("/dodaj-film", {
			templateUrl: templateDir + "/add-film.html",
			activeTab: "addFilm",
		})
		.when("/panel-admina", {
			templateUrl: templateDir + "/admin-panel.html",
			controller: "AdminPanelController",
			controllerAs: "adminPanelCtrl",
			activeTab: "adminPanel",
		})
		.when("/user/:id", {
			templateUrl: templateDir + "/user-details.html",
			controller: "UserDetailsController",
			controllerAs: "userDetailsCtrl",
		})
		.when("/redirect", {
			templateUrl: templateDir + "/redirect.html",
		})
		
		.otherwise({
			templateUrl: templateDir + "/404.html",
		});

		// $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
		
		//$locationProvider.html5Mode(true);

	}]);
