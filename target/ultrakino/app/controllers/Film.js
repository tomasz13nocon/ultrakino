angular.module("app")
.controller("FilmController", ["$http", "$scope", "$routeParams", "Film", function($http, $scope, $routeParams, Film) {
	var ctrl = this;

	$scope.film = Film.get({ id: $routeParams["id"] }, function(film) {
		for (var i = 0; i < film.players.length; i++) {
			film.players[i].src = "https://openload.co/embed/" + film.players[i].src;
		}
		for (var i = 0; i < film.comments.length; i++) {
			if (!film.comments[i].addedBy.avatarFilename) {
				film.comments[i].addedBy.avatarFilename = defaultAvatarFilename;
			}
		}
		document.title = $scope.film.title + " - Ultrakino";
	});

	ctrl.postComment = function() {
		Film.postComment({ id: $scope.film.filmId }, {
			contents: ctrl.commentContent,
		}, function(resp) {
			console.log(resp);
		});
	};

}]);
