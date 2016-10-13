angular.module("app")
.controller("FilmController", ["$http", "$scope", "$routeParams", "Film", function($http, $scope, $routeParams, Film) {
	var ctrl = this;

	$scope.stars = new Array(10);
	for (var i = 0; i < $scope.stars.length; i++) {
		$scope.stars[i] = i + 1;
	};
	$scope.activeStar = -1;

	ctrl.starMouseover = function(i) {
		$scope.activeStar = i;
	};
	ctrl.starMouseleave = function() {
		$scope.activeStar = -1;
	};

	ctrl.rate = function(i) {
		Film.rate({ id: $scope.film.uid }, {
			rating: i,
		}, function(rating) {
			$scope.film.userRating = rating.rating;
			$scope.film.rating = ($scope.film.rating * $scope.film.timesRated + rating.rating) / ++$scope.film.timesRated;
			ctrl.calculateRatingColor();
		});
	};

	ctrl.calculateRatingColor = function() {
		ratingColor = "rgb(" +
			Math.min(255, $scope.film.rating * -51 + 510) + "," +
			Math.min(255, $scope.film.rating * 51) + ",0)";
		angular.element(document.querySelector(".rating-actual-rating")).css("color", ratingColor);
	};

	Film.get({ id: $routeParams["id"] }, function(film) {
		for (var i = 0; i < film.players.length; i++) {
			film.players[i].src = "https://openload.co/embed/" + film.players[i].src;
		}
		for (var i = 0; i < film.comments.length; i++) {
			ctrl.processComment(film.comments[i]);
		}

		document.title = film.title + " - Ultrakino";
		$scope.film = film;
		ctrl.calculateRatingColor();
		//$scope.$on("$locationChanged")
	});

	ctrl.postComment = function() {
		Film.postComment({ id: $scope.film.uid }, {
			contents: ctrl.commentContent,
		}, function(comment) {
			$scope.film.comments.push(ctrl.processComment(comment));
		});
	};

	ctrl.processComment = function(comment) {
		if (!comment.addedBy.avatarFilename)
			comment.addedBy.avatarFilename = defaultAvatarFilename;
		comment.submissionDate = new Date(
			comment.submissionDate[0],
			comment.submissionDate[1],
			comment.submissionDate[2],
			comment.submissionDate[3],
			comment.submissionDate[4],
			comment.submissionDate[5]);
		return comment;
	};

}]);
