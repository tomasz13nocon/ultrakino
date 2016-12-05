angular.module("app")
.controller("FilmController", ['TheBox', '$rootScope', '$http', '$routeParams', '$scope', 'Comment', 'Film', 'Rating', function(TheBox, $rootScope, $http, $routeParams, $scope, Comment, Film, Rating) {
	var ctrl = this;

	$scope.Rating = Rating;
	$scope.Comment = Comment;

	ctrl.setPlayer = function(index) {
		$scope.currentPlayerIndex = index;
	};

	ctrl.addToWatchlist = function(list) {

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
		Comment.processComments(film);
		$scope.visibleCast = film.castAndCrew.filter(function(el) {
			return el.role === "ACTOR";
		}).sort(function(a, b) {
			if (a.number < b.number) return -1;
			if (a.number > b.number) return 1;
			return 0;
		}).slice(0, 20);

		setTitle(film.title + " - Ultrakino");
		$scope.film = film;
		Rating.calculateRatingColor(film.rating);
		if ($rootScope.authenticated) {
			if (ctrl.isIdIn($rootScope.user.watchlist)) {
				$scope.inWatchlist = true;
			}
			if (ctrl.isIdIn($rootScope.user.favorites)) {
				$scope.isFavorite = true;
			}
		}
	});

}]);
