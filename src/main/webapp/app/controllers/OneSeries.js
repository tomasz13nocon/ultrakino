angular.module("app")
.controller("OneSeriesController", ['$interval', '$location', '$route', '$routeParams', '$scope', '$timeout', 'Comment', 'Episode', 'Rating', 'Series', function($interval, $location, $route, $routeParams, $scope, $timeout, Comment, Episode, Rating, Series) {
	var ctrl = this;

	ctrl.loadEpisodes = function(season) {
		if (season == $scope.activeSeason && $scope.episode == null)
			return;
		$scope.episodeListAnimation = true;
		var fetchedEpisodes = false;
		ctrl.stop = $interval(function() {
			if (fetchedEpisodes) {
				$scope.episodeListAnimation = false;
				$scope.episodes = ctrl.episodes;
				$scope.episode = null;
				$interval.cancel(ctrl.stop);
			}
		}, 20);
		if (season === undefined)
			season = Series.activeSeason;
		else
			Series.activeSeason = season;
		$scope.activeSeason = season;
		Episode.query({ seriesId: $routeParams["id"], season: season }, function(episodes) {
			ctrl.episodes = episodes;
			fetchedEpisodes = true;
		});
	};

	ctrl.loadEpisode = function(id) {
		Episode.get({ seriesId: $routeParams["id"], id: id }, function(episode) {
			$scope.episode = episode;
			$scope.episodes = null;
			$scope.activeSeason = episode.season;
			$route.updateParams({ episodeId: episode.uid, season: "sezon-" + episode.season, episode: "odcinek-" + episode.episodeNumber });
			for (var i = 0; i < episode.players.length; i++) {
				$scope.currentPlayerIndex = 0;
				if (episode.players[i].hosting == "openload") {
					$scope.currentPlayerIndex = i;
					break;
				}
			}
			Comment.processComments(episode);
			Rating.calculateRatingColor(episode.rating);
		});
	};

	$scope.Rating = Rating;
	$scope.Comment = Comment;

	ctrl.setPlayer = function(index) {
		$scope.currentPlayerIndex = index;
	};

	ctrl.commentPostedCallback = function() {
		ctrl.commentContent = undefined;
	};

	Series.get({ id: $routeParams["id"] }, function(series) {
		$scope.series = series;
		$scope.seasons = [];
		for (var i = 0; i < series.seasonCount; i++) {
			$scope.seasons[i] = series.seasonCount - i;
		}
		$scope.visibleCast = series.castAndCrew.filter(function(el) {
			return el.role === "ACTOR";
		}).sort(function(a, b) {
			if (a.number < b.number) return -1;
			if (a.number > b.number) return 1;
			return 0;
		}).slice(0, 20);

		setTitle(series.title + " - Ultrakino");

		if ($routeParams["episodeId"]) {
			ctrl.loadEpisode($routeParams["episodeId"]);
		}
		else {
			ctrl.loadEpisodes($scope.seasons[0]);
		}
	});

}]);

