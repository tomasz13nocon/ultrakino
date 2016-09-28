angular.module("app")
	.controller("AdminPanelController", ["$http", "$scope", "Film", function($http, $scope, Film) {
		var ctrl = this;

		ctrl.getAlltubeFilms = function() {
			$scope.loading = true;
			$http.post(api + "/alltube", {}).then(function(resp) {
				console.log(resp);
				$scope.error = null;
				$scope.loading = false;
			}, function(resp) {
				$scope.error = {
					status: resp.status,
					msg: resp.data.error,
				};
				$scope.loading = false;
				console.log(resp);
			});
		};

	}]);
