angular.module("app")
.controller("FilmController", ['$http', '$rootScope', '$route', '$routeParams', '$scope', '$timeout', 'Comment', 'Film', 'Player', 'Rating', 'TheBox', 'User', 'seourlFilter', function($http, $rootScope, $route, $routeParams, $scope, $timeout, Comment, Film, Player, Rating, TheBox, User, seourlFilter) {
	var ctrl = this;

	$scope.Rating = Rating;
	$scope.Comment = Comment;
	$scope.currentPlayerIndex = 0;

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
		if (!film.inUsersWatchlist) {
			User.save({ id: $rootScope.user.uid, sub: "watchlist" }, { contentId: film.uid }, function(resp) {
				User.pushNotification("Film '" + film.title + "' został dodany do 'Do obejrzenia'.");
				film.inUsersWatchlist = true;
				$rootScope.user.watchlist.push(film);
			});
		}
		else {
			User.delete({ id: $rootScope.user.uid, sub: "watchlist", subId: film.uid }, function(resp) {
				User.pushNotification("Film '" + film.title + "' został usunięty z 'Do obejrzenia'.");
				film.inUsersWatchlist = false;
				$rootScope.user.watchlist.splice($rootScope.user.watchlist.indexOf(film), 1);
			});
		}
	};

	ctrl.toggleFavorites = function(film) {
		ctrl.animateButton("film-button-favorites", "film-button-favorites-anim");
		if (!film.inUsersFavorites) {
			User.save({ id: $rootScope.user.uid, sub: "favorites" }, { contentId: film.uid }, function(resp) {
				User.pushNotification("Film '" + film.title + "' został dodany do ulubionych.");
				film.inUsersFavorites = true;
				$rootScope.user.favorites.push(film);
			});
		}
		else {
			User.delete({ id: $rootScope.user.uid, sub: "favorites", subId: film.uid }, function(resp) {
				User.pushNotification("Film '" + film.title + "' został usunięty z ulubionych.");
				film.inUsersFavorites = false;
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

	$scope.vote = function(positive) {
		if (!$rootScope.authenticated) {
			TheBox.showRegisterBox("Załóż konto, lub zaloguj się żeby móc oceniać filmy!");
			return;
		}
		var player = $scope.film.players[$scope.currentPlayerIndex];
		if (player.userVote !== null) {
			return;
		}
		Player.vote({ id: player.uid }, {
			positive: positive,
		}, function(vote) {
			player.votes.push(vote);
			player.userVote = vote.positive;
			if (vote.positive)
				player.upvotes++;
			else
				player.downvotes++;
			Player.calculateColor(player, document.getElementsByClassName("player-vote-bar")[$scope.currentPlayerIndex]);
		});
	};


	$scope.$on("elementLoaded", function(element) {
		if ($scope.film) {
			Player.calculatePlayerRatingColor($scope.film.players);
		}
	});

	Film.get({ id: $routeParams["id"] }, function(film) {
		Comment.processComments(film);
		$scope.visibleCast = film.castAndCrew.filter(function(el) {
			return el.role === "ACTOR";
		}).sort(function(a, b) {
			if (a.number < b.number) return -1;
			if (a.number > b.number) return 1;
			return 0;
		}).slice(0, 20); // TODO dont slice

		setTitle(film.title + " - Ultrakino");
		$route.updateParams({ title: seourlFilter(film.title) + "-" + film.year });
		$scope.film = film;
		Rating.calculateRatingColor(film.rating);
	}, function(resp) {
		if (resp.status = 404) {
			$scope.error = "Nie znaleziono filmu.\nFilm o tym adresie nie istnieje.";
		}
	});

}]);
