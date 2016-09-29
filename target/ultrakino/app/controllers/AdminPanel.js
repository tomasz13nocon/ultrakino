angular.module("app")
	.controller("AdminPanelController", ["$http", "$scope", "Film", function($http, $scope, Film) {
		var ctrl = this;

		ctrl.getAlltubeFilms = function() {
			$scope.loading = true;
			$http.post(api + "/alltube", { page: 1 }).then(function(resp) {
				$scope.films = resp.data;
				$scope.error = null;
				$scope.loading = false;
			}, function(resp) {
				$scope.error = {
					status: resp.status,
					msg: resp.data.error,
				};
				$scope.loading = false;
				$scope.films = null;
			});
		};

	}]);
