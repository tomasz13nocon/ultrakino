angular.module("app")
.controller("AddFilmController", ['$interval', '$rootScope', '$scope', '$timeout', 'Film', 'Filmweb', function($interval, $rootScope, $scope, $timeout, Film, Filmweb) {
	if (!$rootScope.authenticationAttempted) return;
	var ctrl = this;

	$scope.contentType = "FILM";
	$scope.retrievingFilms = 0;
	ctrl.step = 1;
	var stepStylesEl = document.createElement("style");
	document.head.appendChild(stepStylesEl);
	ctrl.stepStyles = stepStylesEl.sheet;

	// This is an ugly abomination, but it werks
	ctrl.stepsHeightInterval = $interval(function() {
		document.getElementsByClassName("steps-wrapper")[0].style.height = document.getElementsByClassName("step" + ctrl.step)[0].clientHeight + "px";
	}, 200);
	$scope.$on("$locationChangeSuccess", function() {
		$interval.cancel(ctrl.stepsHeightInterval);
	})

	ctrl.search = function(query) {
		if (query.length < 2) {
			$scope.films = [];
			return;
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
			$scope.films = results;
		});
	};

	ctrl.searchFilmweb = function() {
		ctrl.search($scope.title);
	};

	ctrl.fetchFilmwebLink = function() {
	};

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

}]);

