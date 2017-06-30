stepsHeightInterval = null;

angular.module("app")
.controller("AddFilmController", ['$document', '$interval', '$rootScope', '$route', '$scope', '$timeout', '$window', 'Film', 'Filmweb', function($document, $interval, $rootScope, $route, $scope, $timeout, $window, Film, Filmweb) {
	if (!$rootScope.authenticationAttempted) return;
	var ctrl = this;

	$scope.contentType = "FILM";
	$scope.retrievingFilms = 0;
	$scope.showSupportedHostings = false;
	$scope.title = "";
	ctrl.searchByTitle = true;
	ctrl.step = 1;
	var stepStylesEl = document.createElement("style");
	document.head.appendChild(stepStylesEl);
	ctrl.stepStyles = stepStylesEl.sheet;

	//$scope.$watch(function() {
		//return document.getElementsByClassName("step" + ctrl.step)[0].clientHeight;
	//}, function() {
		//document.getElementsByClassName("steps-wrapper")[0].style.height = document.getElementsByClassName("step" + ctrl.step)[0].clientHeight + "px";
	//});
	// This is an ugly abomination, but it werks
	if (stepsHeightInterval) {
		$interval.cancel(stepsHeightInterval);
	}
	stepsHeightInterval = $interval(function() {
		document.getElementsByClassName("steps-wrapper")[0].style.height = document.getElementsByClassName("step" + ctrl.step)[0].clientHeight + "px";
	}, 200);
	$scope.$on("$locationChangeSuccess", function() {
		$interval.cancel(stepsHeightInterval);
	})

	ctrl.search = function(query) {
		if (query.length < 2) {
			$scope.films = [];
			return -1;
		}
		$scope.retrievingFilms += 1;
		$scope.error = false;
		$scope.lastQuery = query;
		Filmweb.query({
			title: query,
			contentType: $scope.contentType,
		}, function(results) {
			if ($scope.lastQuery === query) {
				$scope.films = results;
				$scope.retrievingFilms -= 1;
			}
		}, function() {
			$scope.error = true;
			$scope.retrievingFilms -= 1;
			$scope.films = [];
		});
	};

	ctrl.fetchFilmwebLink = function() {
		Filmweb.get({ link: $scope.filmwebLink, contentType: $scope.contentType },
			function(film) {
				ctrl.pick = film;
				$scope.films = [film];
				console.log(film.filmwebId);
			},
			function(resp) {
				// TODO: HANDLE 500 AND 400
			});
	};

	ctrl.searchOrFindLink = function() {
		if (ctrl.searchByTitle) {
			if (ctrl.search($scope.title) == -1) {
				$scope.titleError = true;
			}
		}
		else {
			ctrl.fetchFilmwebLink();
		}
	};


	ctrl.pickFilm = function(film) {
		ctrl.pick = film;
	};

	$scope.pickHarryPotter = function() {
		ctrl.pick = {filmwebId: 30571, title: "Harry Potter"};
		$scope.link = "https://openload.co/f/cUhIHKZwzrw/1489406887448.webm.mp4";
		$scope.languageVersion = "ORIGINAL";
	}

	ctrl.verifyLink = function(link) {
		for (var i=0; i < $rootScope.supportedHostings.length; i++) {
			var r = link.match($rootScope.supportedHostings[i].regex);
			if (r != null) {
				$scope.correctLink = true;
				$scope.linkSrc = r[1];
				$scope.linkHosting = $rootScope.supportedHostings[i].name;
				return;
			}
		}
		$scope.correctLink = false;
	};

	$document.keydown(function(e) {
		if (e.keyCode == 27) {
			$scope.showSupportedHostings = false;
			$scope.$apply();
		}
		if (e.keyCode == 13) {
			if (ctrl.step == 1)
				ctrl.searchOrFindLink();
		}
	});

	ctrl.goToStep = function(step) {
		var oldStep = ctrl.step;
		ctrl.step = step;
		$timeout(function() {
			var oldStepEl = document.getElementsByClassName("step" + oldStep)[0];
			var newStepEl = document.getElementsByClassName("step" + step)[0];
			var transformWidth = oldStepEl.clientWidth/2 + newStepEl.clientWidth/2;
			ctrl.stepStyles.insertRule(".step" + oldStep + ".ng-hide-add-active { transform: translateX(-" + (transformWidth + 200) + "px) }", ctrl.stepStyles.cssRules.length);
			ctrl.stepStyles.insertRule(".step" + step + ".ng-hide-remove-active { transform: translateX(-" + transformWidth + "px) }", ctrl.stepStyles.cssRules.length);
		});
	};

	ctrl.goToNextStep = function() {
		ctrl.goToStep(ctrl.step + 1);
	};

	ctrl.addFilm = function() {
		Film.save({
			filmwebId: ctrl.pick.filmwebId,
			players: [
				{
					src: $scope.linkSrc,
					hosting: $scope.linkHosting,
					languageVersion: $scope.languageVersion,
				}
			],
		}, function(resp) {
			$scope.filmAdditionFinished = true;
			$scope.filmAdditionSuccessful = true;
			console.log(resp);
			$scope.addedFilmId = resp.id;
		}, function(resp) {
			$scope.filmAdditionFinished = true;
			$scope.filmAdditionFailed = true;
			$scope.filmAdditionError = resp;
		});
		ctrl.goToNextStep();
	};

	ctrl.reset = function() {
		$interval.cancel(ctrl.stepsHeightInterval);
		$route.reload();
	}

}]);

