angular.module("app")
.controller("AdminToolsController", ["$scope", "Film", function($scope, Film) {
	var ctrl = this;
	// Tight coupling, but this controller is only to be used with filmController.
	// This is a seperate component only because of security.
	ctrl.film = $scope.$parent.film;
	var parent = $scope.$parent.filmCtrl;

	ctrl.recommendFilm = function() {
		Film.recommend({id: ctrl.film.uid}, function(resp) {
			
		});
	};

}]);