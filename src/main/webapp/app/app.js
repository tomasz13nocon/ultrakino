api = "//ultrakino.com.pl/api";
templateDir = "templates";
defaultAvatarFilename = "images/avatar3.png";

angular.module("app", ["ngRoute", "ngAnimate", "ngResource"]);

angular.module("app")
	.run(["$rootScope", function($rootScope) {

		$rootScope.languageVersions = {
			VOICE_OVER: "Lektor",
			ORIGINAL: "Oryginał",
			DUBBING: "Dubbing",
			POLISH_FILM: "Polski Film",
			POLISH_SUBS: "Napisy PL",
			ENGLISH_SUBS: "Napisy ENG",
			"": "Nieznana",
		};

		$rootScope.categories = {
			47: "3D",
			4: "Akcja",
			48: "Animacja",
			49: "Anime",
			50: "Baśń",
			6: "Biograficzny",
			51: "Czarna komedia",
			7: "Dokumentalny",
			8: "Dramat",
			52: "Erotyczny",
			9: "Familijny",
			10: "Fantasy",
			53: "Gangsterski",
			11: "Historyczny",
			12: "Horror",
			54: "Katastroficzny",
			13: "Komedia",
			55: "Komedia romantyczna",
			56: "Kostiumowy",
			14: "Kryminał",
			57: "Musical",
			15: "Muzyczny",
			16: "Obyczajowy",
			58: "Polityczny",
			18: "Przygodowy",
			59: "Przyrodniczy",
			19: "Psychologiczny",
			20: "Romans",
			21: "Sci-Fi",
			22: "Sensacyjny",
			23: "Sportowy",
			61: "Surrealistyczny",
			60: "Szpiegowski",
			24: "Thriller",
			25: "Western",
			26: "Wojenny",
		};

		$rootScope.years = [];
		for (var i = new Date().getFullYear(); i > 1900; i--) {
			$rootScope.years.push(i);
		}

		$rootScope.authenticated = false;
		$rootScope.isAdmin = false;
		$rootScope.images = "//ultrakino.com.pl/";
		$rootScope.noImage = "images/no-image.png";

	}]);

angular.module("app")
	.config(["$routeProvider", "$httpProvider", function($routeProvider, $httpProvider) {
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
			activeTab: "series",
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
		
		.otherwise({
			templateUrl: templateDir + "/404.html",
		});

		// $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

	}]);
