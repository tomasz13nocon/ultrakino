angular.module("app")
.controller("AdminToolsController", ["$timeout", "$scope", "Film", function($timeout, $scope, Film) {
	var ctrl = this;

	ctrl.recommendFilm = function() {
		Film.recommend({id: ctrl.content.uid}, function(resp) {
			ctrl.content.recommendationDate = true;
		});
	};

	ctrl.deleteRecommendation = function() {
		Film.deleteRecommendation({id: ctrl.content.uid}, function(resp) {
			ctrl.content.recommendationDate = null;
		});
	};

}]);
