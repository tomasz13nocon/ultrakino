angular.module("app")
.controller("FilmController", ['$http', '$routeParams', '$scope', 'Comment', 'Film', function($http, $routeParams, $scope, Comment, Film) {
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

	ctrl.setPlayer = function(index) {
		$scope.currentPlayerIndex = index;
	};

	$scope.isPlayer = false; // TODO: remove
	Film.get({ id: $routeParams["id"] }, function(film) {
		$scope.currentPlayerIndex = 0;
		for (var i = 0; i < film.players.length; i++) {
			if (film.players[i].hosting == "openload") {
				$scope.currentPlayerIndex = i;
				$scope.isPlayer = true;
				break;
			}
		}
		console.log($scope.currentPlayerIndex);
		console.log(film.players);
		for (var i = 0; i < film.comments.length; i++) {
			ctrl.processComment(film.comments[i]);
		}

		document.title = film.title + " - Ultrakino";
		$scope.film = film;
		ctrl.calculateRatingColor();
	});

	ctrl.postComment = function() {
		Comment.save({ comment: ctrl.commentContent, contentId: $scope.film.uid }, function(comment) {
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
