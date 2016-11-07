angular.module("app")
.controller("OneSeriesController", ['$interval', '$location', '$route', '$routeParams', '$scope', '$timeout', 'Comment', 'Episode', 'Rating', 'Series', function($interval, $location, $route, $routeParams, $scope, $timeout, Comment, Episode, Rating, Series) {
	var ctrl = this;

	ctrl.loadEpisodes = function(season = $scope.seasons[0]) {
		if (season == $scope.activeSeason && $scope.episode == null)
			return;
		$scope.episodeListAnimation = true;
		var fetchedEpisodes = false;
		ctrl.stop = $interval(function() {
			if (fetchedEpisodes) {
				$scope.episodeListAnimation = false;
				$scope.episodes = ctrl.episodes;
				$interval.cancel(ctrl.stop);
			}
		}, 10);
		$scope.activeSeason = season;
		Episode.query({ seriesId: $routeParams["id"], season: season }, function(episodes) {
			ctrl.episodes = episodes;
			fetchedEpisodes = true;
		});
		$scope.episode = null;
		$location.path("seriale/" + $scope.series.uid + "/" + $scope.series.title + "-" + $scope.series.year, false);
	};

	ctrl.loadEpisode = function(id) {
		Episode.get({ seriesId: $routeParams["id"], id: id }, function(episode) {
			$scope.episode = episode;
			$route.updateParams({ episodeId: episode.uid, season: "sezon-" + episode.season, episode: "odcinek-" + episode.episodeNumber });
			for (var i = 0; i < episode.players.length; i++) {
				$scope.currentPlayerIndex = 0;
				if (episode.players[i].hosting == "openload") {
					$scope.currentPlayerIndex = i;
					break;
				}
			}
			for (var i = 0; i < episode.comments.length; i++) {
				Comment.process(episode.comments[i]);
			}
		});
		$scope.episodes = null;
	};

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
		Rating.save({}, {
			contentId: $scope.episode.uid,
			rating: i,
		}, function(rating) {
			$scope.episode.userRating = rating.rating;
			$scope.episode.rating = ($scope.episode.rating * $scope.episode.timesRated + rating.rating) / ++$scope.episode.timesRated;
			ctrl.calculateRatingColor();
		});
	};

	ctrl.calculateRatingColor = function() {
		ratingColor = "rgb(" +
			Math.min(255, $scope.episode.rating * -51 + 510) + "," +
			Math.min(255, $scope.episode.rating * 51) + ",0)";
		angular.element(document.querySelector(".rating-actual-rating")).css("color", ratingColor);
	};
	ctrl.setPlayer = function(index) {
		$scope.currentPlayerIndex = index;
	};

	ctrl.postComment = function() {
		Comment.save({ comment: ctrl.commentContent, contentId: $scope.episode.uid }, function(comment) {
			$scope.episode.comments.push(Comment.process(comment));
		});
	};

	Series.get({ id: $routeParams["id"] }, function(series) {
		$scope.series = series;
		$scope.seasons = [];
		for (var i = 0; i < series.seasonCount; i++) {
			$scope.seasons[i] = series.seasonCount - i;
		}
		if ($routeParams["episodeId"]) {
			ctrl.loadEpisode($routeParams["episodeId"]);
		}
		else {
			ctrl.loadEpisodes();
		}
	});

}]);

