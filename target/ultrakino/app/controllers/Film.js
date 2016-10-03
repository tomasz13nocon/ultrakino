angular.module("app")
.controller("FilmController", ["$http", "$scope", "$routeParams", "Film", function($http, $scope, $routeParams, Film) {
	var ctrl = this;

	$scope.film = Film.get({ id: $routeParams["id"] }, function(film) {
		for (var i = 0; i < film.players.length; i++) {
			film.players[i].src = "https://openload.co/embed/" + film.players[i].src;
		}
		for (var i = 0; i < film.comments.length; i++) {
			ctrl.processComment(film.comments[i]);
		}

		document.title = film.title + " - Ultrakino";
		$scope.$on("$locationChanged")
	});

	ctrl.postComment = function() {
		Film.postComment({ id: $scope.film.uid }, {
			contents: ctrl.commentContent,
		}, function(comment) {
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
