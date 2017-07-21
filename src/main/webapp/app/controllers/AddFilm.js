stepsHeightInterval = null;

angular.module("app")
.controller("AddFilmController", ['$document', '$http', '$interval', '$rootScope', '$route', '$routeParams', '$scope', '$timeout', '$window', 'Film', 'Filmweb', function($document, $http, $interval, $rootScope, $route, $routeParams, $scope, $timeout, $window, Film, Filmweb) {
	if (!$rootScope.authenticated) return;
	var ctrl = this;

	$scope.contentType = "FILM";
	$scope.retrievingFilms = 0;
	$scope.showSupportedHostings = false;
	$scope.title = "";
	$scope.supportedHostings = [];
	ctrl.searchByTitle = true;
	$scope.step = 1;
	ctrl.stepsWrapperElement;
	ctrl.currentStepElement;
	ctrl.heightIntervalStep = -1;

	var id = $routeParams["id"]
	if (id) {
		$scope.step = 2;
		Film.get({ id: id }, function(film) {
			ctrl.pick = film;
			ctrl.pick.coverFilename = $rootScope.images + ctrl.pick.coverFilename;
		})
	}

	var hostingData = {
		"openload": {
			name: "openload",
			displayName: "openload.co",
			regex: /(?:http[s]:\/\/)?(?:www\.)?openload\.co\/f\/([\w-]+)/
		},
		"streamin": {
			name: "streamin",
			displayName: "streamin.to",
			regex: /(?:http[s]:\/\/)?(?:www\.)?streamin\.to\/([\w]+)/
		},
		"vshare": {
			name: "vshare",
			displayName: "vshare.io",
			regex: /(?:http[s]:\/\/)?(?:www\.)?vshare\.io\/d\/([\w]+)/
		},
		"youtube": {
			name: "youtube",
			displayName: "youtube.com",
			regex: /(?:http[s]:\/\/)?(?:www\.)?youtube\.com\/watch\?v\=([\w-]+)/
		},
		//{ name: "vidto", displayName: "vidto.me", regex: /(?:http[s]:\/\/)?(?:www\.)?1/ },
		//{ name: "videowood", displayName: "videowood.tv", regex: /(?:http[s]:\/\/)?(?:www\.)?1/ },
		//{ name: "cda", displayName: "cda.pl", regex: /(?:http[s]:\/\/)?(?:www\.)?1/ },
		//{ name: "nowvideo", displayName: "nowvideo.sx", regex: /(?:http[s]:\/\/)?(?:www\.)?1/ },
	}

	$http.get(api + "/players/hostings").then(function(resp) {
		resp.data.forEach(function(hosting, i) {
			if (hosting in hostingData){
				$scope.supportedHostings.push(hostingData[hosting]);
			}
		});
	})	

	if (stepsHeightInterval) {
		$interval.cancel(stepsHeightInterval);
	}
	stepsHeightInterval = $interval(function() {
		if (!ctrl.stepsWrapperElement)
			 ctrl.stepsWrapperElement = document.getElementsByClassName("steps-wrapper")[0];
		if (ctrl.heightIntervalStep !== $scope.step) {
			ctrl.currentStepElement = document.getElementsByClassName("step" + $scope.step)[0];
			ctrl.heightIntervalStep = $scope.step;
		}
		ctrl.stepsWrapperElement.style.height = ctrl.currentStepElement.clientHeight + "px";
	}, 100);
	$scope.$on("$locationChangeSuccess", function() {
		$interval.cancel(stepsHeightInterval);
	})

	$scope.goToStep = function(step) {
		if ($scope.animatingSteps)
            return;
        if (step >= 1 && step <= 3) {
            currEl = document.getElementsByClassName("step" + $scope.step)[0];
            newEl = document.getElementsByClassName("step" + step)[0];
            if (step > $scope.step) {
                $scope.animatingSteps = true;
                newEl.classList.add("step-show-left");
                currEl.classList.add("step-hide-left");
                $timeout(function() {
                    newEl.classList.remove("step-show-left");
                    currEl.classList.remove("step-hide-left");
                    $scope.animatingSteps = false;
                }, 600);
            }
            else {
                $scope.animatingSteps = true;
                newEl.classList.add("step-show-right");
                currEl.classList.add("step-hide-right");
                $timeout(function() {
                    newEl.classList.remove("step-show-right");
                    currEl.classList.remove("step-hide-right");
                    $scope.animatingSteps = false;
                }, 600);
            }
            $scope.step = step;
        }
	}

	$scope.goToNextStep = function() {
		$scope.goToStep($scope.step + 1);
	};


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
		for (var i=0; i < $scope.supportedHostings.length; i++) {
			var r = link.match($scope.supportedHostings[i].regex);
			if (r != null) {
				$scope.correctLink = true;
				$scope.linkSrc = r[1];
				$scope.linkHosting = $scope.supportedHostings[i].name;
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
			if ($scope.step == 1)
				ctrl.searchOrFindLink();
		}
	});

	ctrl.addPlayers = function(filmId) {
		Film.addPlayer({ id: filmId }, {
			src: $scope.linkSrc,
			hosting: $scope.linkHosting,
			languageVersion: $scope.languageVersion,
		}, function(resp) {
			$scope.filmAdditionFinished = true;
			if (resp.error) {
				$scope.filmAdditionFailed = true;
				$scope.filmAdditionError = resp.error;
			}
			else {
				$scope.filmAdditionSuccessful = true;
				$scope.addedFilmId = filmId;
			}
		}, function(resp) {
			$scope.filmAdditionFinished = true;
			$scope.filmAdditionFailed = true;
			$scope.filmAdditionError = resp.status;
		});
	}

	ctrl.addFilm = function() {
		$scope.filmAdditionFinished = false;
		if (!id) {
			Film.save({}, {
				filmwebId: ctrl.pick.filmwebId,
			}, function(resp) {
				ctrl.addPlayers(resp.id);
			}, function(resp) {
				$scope.filmAdditionFinished = true;
				$scope.filmAdditionFailed = true;
				$scope.filmAdditionError = resp;
			});
		}
		else {
			ctrl.addPlayers(id);
		}
		$scope.goToNextStep();
	};

	ctrl.reset = function() {
		//$interval.cancel(ctrl.stepsHeightInterval);
		//$route.reload();
		
		$scope.filmAdditionError = null;
		$scope.filmAdditionFailed = false;
		$scope.filmAdditionSuccessful = false;
		$scope.goToStep(1);
	}

}]);

