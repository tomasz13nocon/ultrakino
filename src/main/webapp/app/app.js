api = "/api";
templateDir = "templates";

angular.module("app", ["ngRoute", "ngAnimate", "ngResource"]);

angular.module("app")
	.run(['$http', '$location', '$rootScope', '$route', 'User', function($http, $location, $rootScope, $route, User) {

		$rootScope.languageVersions = {
			VOICE_OVER: "Lektor",
			ORIGINAL: "Oryginał",
			DUBBING: "Dubbing",
			POLISH_FILM: "Polski Film",
			POLISH_SUBS: "Napisy PL",
			ENGLISH_SUBS: "Napisy ENG",
		};

		$rootScope.filmCategoriesIds = {};
		$rootScope.filmCategories = [];
		$http.get(api + "/films/categories").then(function(resp) {
			for (var i = 0; i < resp.data.length; i++) {
				if (resp.data[i].name === "") {
					resp.data.splice(i--, 1);
					continue;
				}
				$rootScope.filmCategories.push(resp.data[i].name);
				$rootScope.filmCategoriesIds[resp.data[i].name] = resp.data[i].id;
			}
			$rootScope.filmCategories.sort();
		});

		$rootScope.seriesCategoriesIds = {};
		$rootScope.seriesCategories = [];
		$http.get(api + "/series/categories").then(function(resp) {
			for (var i = 0; i < resp.data.length; i++) {
				$rootScope.seriesCategories.push(resp.data[i].name);
				$rootScope.seriesCategoriesIds[resp.data[i].name] = resp.data[i].id;
			}
			$rootScope.seriesCategories.sort();
		});

		$rootScope.years = [];
		for (var i = new Date().getFullYear(); i > 1900; i--) {
			$rootScope.years.push(i);
		}

		$rootScope.authenticationAttempted = false;
		$rootScope.authenticated = false;
		$rootScope.isAdmin = false;
		User.authenticate();
		// TODO: Remove on prod
		if (window.location.href.indexOf("localhost") > -1)
			$rootScope.images = "//localhost:8000/";
		else if (window.location.href.indexOf("192.168.1.13") > -1)
			$rootScope.images = "//192.168.1.13:8000/";
		else
			$rootScope.images = "//images.ultrakino.pl/";
		$rootScope.noImage = "images/no-image.png";

		$rootScope.$on("$locationChangeSuccess", function() {
			setTitle("Ultrakino.pl - oglądaj najlepsze filmy i seriale za darmo!");
		});
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
			controller: "AddFilmController",
			controllerAs: "addFilmCtrl",
		})
		.when("/panel-admina", {
			templateUrl: templateDir + "/admin-panel.html",
			controller: "AdminPanelController",
			controllerAs: "adminPanelCtrl",
		})
		//.when("/user/:id", {
			//templateUrl: templateDir + "/user-details.html",
			//controller: "UserDetailsController",
			//controllerAs: "userDetailsCtrl",
		//})
		.when("/moje-konto", {
			templateUrl: templateDir + "/my-account.html",
			controller: "MyAccountController",
			controllerAs: "myAccountCtrl",
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
