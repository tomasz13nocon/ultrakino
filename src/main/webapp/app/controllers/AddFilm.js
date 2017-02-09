angular.module("app")
.controller("AddFilmController", ['$scope', '$timeout', 'Film', 'Filmweb', function($scope, $timeout, Film, Filmweb) {
	var ctrl = this;

	$scope.contentType = "FILM";
	$scope.retrievingFilms = 0;
	ctrl.step = 1;
	var stepStylesEl = document.createElement("style");
	document.head.appendChild(stepStylesEl);
	ctrl.stepStyles = stepStylesEl.sheet;
	// Set the height of steps-wrapper to the height of first step, so that height animations work properly
	$timeout(function() {
		document.getElementsByClassName("steps-wrapper")[0].style.height = document.getElementsByClassName("step1")[0].clientHeight + "px";
		console.log(document.getElementsByClassName("steps-wrapper")[0].style.height);
	});

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
			var stepsWrapper = document.getElementsByClassName("steps-wrapper")[0];
			stepsWrapper.style.height = newStepEl.clientHeight + "px";
		});
	};

}]);

