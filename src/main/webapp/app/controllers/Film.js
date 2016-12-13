angular.module("app")
.controller("FilmController", ['$http', '$rootScope', '$routeParams', '$scope', 'Comment', 'Film', 'Rating', 'TheBox', 'User', function($http, $rootScope, $routeParams, $scope, Comment, Film, Rating, TheBox, User) {
	var ctrl = this;

	$scope.Rating = Rating;
	$scope.Comment = Comment;

	ctrl.setPlayer = function(index) {
		$scope.currentPlayerIndex = index;
	};

	ctrl.animateButton = function(elementId, animClass) {
		var element = document.getElementById(elementId);
		element.classList.remove(animClass);
		window.setTimeout(function() {
			element.classList.add(animClass);
		}, 0);
	};

	ctrl.toggleWatchlist = function(film) {
		ctrl.animateButton("film-button-watchlist", "film-button-watchlist-anim");
		if (!film.inWatchlist) {
			User.save({ id: $rootScope.user.uid, sub: "watchlist" }, { contentId: film.uid }, function(resp) {
				User.pushNotification("Film '" + film.title + "' został dodany do 'Do obejrzenia'.");
				film.inWatchlist = true;
			});
		}
		else {
			User.delete({ id: $rootScope.user.uid, sub: "watchlist", subId: film.uid }, function(resp) {
				User.pushNotification("Film '" + film.title + "' został usunięty z 'Do obejrzenia'.");
				film.inWatchlist = false;
			});
		}
	}

	ctrl.toggleFavorites = function(film) {
		ctrl.animateButton("film-button-favorites", "film-button-favorites-anim");
		if (!film.inFavorites) {
			User.save({ id: $rootScope.user.uid, sub: "favorites" }, { contentId: film.uid }, function(resp) {
				User.pushNotification("Film '" + film.title + "' został dodany do ulubionych.");
				film.inFavorites = true;
			});
		}
		else {
			User.delete({ id: $rootScope.user.uid, sub: "favorites", subId: film.uid }, function(resp) {
				User.pushNotification("Film '" + film.title + "' został usunięty z ulubionych.");
				film.inFavorites = false;
			});
		}
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
				film.inWatchlist = true;
			}
			if (ctrl.isIdIn($rootScope.user.favorites)) {
				film.isFavorite = true;
			}
		}
	});

}]);
