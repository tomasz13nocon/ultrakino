angular.module("app")
.controller("AdminToolsController", ['$route', '$scope', '$timeout', 'Film', function($route, $scope, $timeout, Film) {
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

	ctrl.deleteFilm = function() {
		if(confirm("Na pewno chcesz usunąć ten film i wszystkie jego linki, komentarze itd. z bazy danych?")) {
			Film.delete({id: ctrl.content.uid}, function(resp) {
				$route.reload();
			});
		}
	};

}]);
