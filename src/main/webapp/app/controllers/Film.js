angular.module("app")
.controller("FilmController", ['TheBox', '$rootScope', '$http', '$routeParams', '$scope', 'Comment', 'Film', 'Rating', function(TheBox, $rootScope, $http, $routeParams, $scope, Comment, Film, Rating) {
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
		if ($rootScope.authenticated) {
			Rating.save({}, {
				contentId: $scope.film.uid,
				rating: i,
			}, function(rating) {
				$scope.film.userRating = rating.rating;
				$scope.film.rating = ($scope.film.rating * $scope.film.timesRated + rating.rating) / ++$scope.film.timesRated;
				ctrl.calculateRatingColor();
			});
		}
		else {
			TheBox.message = "Załóż konto żeby móc oceniać filmy!";
			TheBox.showRegisterBox();
		}
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

	ctrl.addToWatchlist = function(list) {
		$scope.inWatchlist = true;
	}


	ctrl.isIdIn = function(list) {
		for (var i = 0; i < list.length; i++) {
			if (list[i].uid === $scope.film.uid) {
				return true;
			}
		}
		return false;
	}

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
		for (var i = 0; i < film.comments.length; i++) {
			Comment.process(film.comments[i]);
		}
		$scope.visibleCast = film.castAndCrew.filter(function(el) {
			return el.role === "ACTOR";
		}).sort(function(a, b) {
			if (a.number < b.number) return -1;
			if (a.number > b.number) return 1;
			return 0;
		}).slice(0, 6);

		setTitle(film.title + " - Ultrakino");
		$scope.film = film;
		ctrl.calculateRatingColor();
		if ($rootScope.authenticated) {
			if (ctrl.isIdIn($rootScope.user.watchlist)) {
				$scope.inWatchlist = true;
			}
			if (ctrl.isIdIn($rootScope.user.favorites)) {
				$scope.isFavorite = true;
			}
		}
	});

	ctrl.postComment = function() {
		Comment.save({ comment: ctrl.commentContent, contentId: $scope.film.uid }, function(comment) {
			$scope.film.comments.push(Comment.process(comment));
		});
	};

}]);
