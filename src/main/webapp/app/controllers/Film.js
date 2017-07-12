angular.module("app")
.controller("FilmController", ['$http', '$rootScope', '$route', '$routeParams', '$scope', 'Comment', 'Film', 'Rating', 'TheBox', 'User', 'seourlFilter', function($http, $rootScope, $route, $routeParams, $scope, Comment, Film, Rating, TheBox, User, seourlFilter) {
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
				$rootScope.user.watchlist.push(film);
			});
		}
		else {
			User.delete({ id: $rootScope.user.uid, sub: "watchlist", subId: film.uid }, function(resp) {
				User.pushNotification("Film '" + film.title + "' został usunięty z 'Do obejrzenia'.");
				film.inWatchlist = false;
				$rootScope.user.watchlist.splice($rootScope.user.watchlist.indexOf(film), 1);
			});
		}
	};

	ctrl.toggleFavorites = function(film) {
		ctrl.animateButton("film-button-favorites", "film-button-favorites-anim");
		if (!film.inFavorites) {
			User.save({ id: $rootScope.user.uid, sub: "favorites" }, { contentId: film.uid }, function(resp) {
				User.pushNotification("Film '" + film.title + "' został dodany do ulubionych.");
				film.inFavorites = true;
				$rootScope.user.favorites.push(film);
			});
		}
		else {
			User.delete({ id: $rootScope.user.uid, sub: "favorites", subId: film.uid }, function(resp) {
				User.pushNotification("Film '" + film.title + "' został usunięty z ulubionych.");
				film.inFavorites = false;
				$rootScope.user.favorites.splice($rootScope.user.favorites.indexOf(film), 1);
			});
		}
	};


	ctrl.isIdIn = function(list) {
		for (var i = 0; i < list.length; i++) {
			if (list[i].uid === $scope.film.uid) {
				return true;
			}
		}
		return false;
	};

	ctrl.commentPostedCallback = function() {
		ctrl.commentContent = undefined;
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
		Comment.processComments(film);
		$scope.visibleCast = film.castAndCrew.filter(function(el) {
			return el.role === "ACTOR";
		}).sort(function(a, b) {
			if (a.number < b.number) return -1;
			if (a.number > b.number) return 1;
			return 0;
		}).slice(0, 20);

		setTitle(film.title + " - Ultrakino");
		$route.updateParams({ title: seourlFilter(film.title) + "-" + film.year });
		$scope.film = film;
		Rating.calculateRatingColor(film.rating);
		if ($rootScope.authenticated) {
			// TODO: $rootScope.user doesn't have those lists anymore
			//if (ctrl.isIdIn($rootScope.user.watchlist)) {
				//film.inWatchlist = true;
			//}
			//if (ctrl.isIdIn($rootScope.user.favorites)) {
				//film.inFavorites = true;
			//}
		}
	}, function(resp) {
		if (resp.status = 404) {
			$scope.error = "Nie znaleziono filmu.\nFilm o tym adresie nie istnieje.";
		}
	});

}]);
