angular.module("app")
.controller("OneSeriesController", ['$interval', '$location', '$route', '$routeParams', '$scope', '$timeout', 'Comment', 'Episode', 'Series', function($interval, $location, $route, $routeParams, $scope, $timeout, Comment, Episode, Series) {
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
				if (episode.players[i].hosting == "openload") {
					$scope.isPlayer = true;
					$scope.firstValidPlayer = i;
					break;
				}
			}
		});
		$scope.episodes = null;
	};

	ctrl.postComment = function(comment) {
		Comment.postComment(comment);
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

