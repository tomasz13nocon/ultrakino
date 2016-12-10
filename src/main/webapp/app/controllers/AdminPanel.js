angular.module("app")
	.controller("AdminPanelController", ["$http", "$scope", "Film", function($http, $scope, Film) {
		var ctrl = this;

		$scope.page = 1;

		ctrl.alltubeBot = function() {
			$scope.loading = true;
			$scope.error = null;
			$scope.success = false;
			$scope.films = null;

			$http.post(api + "/bots/films", { page: $scope.page }).then(function(resp) {
				$scope.films = resp.data;
				$scope.loading = false;
				$scope.success = true;
			}, function(resp) {
				$scope.error = {
					status: resp.status,
					msg: resp.data.error,
				};
				$scope.loading = false;
			});
		};

		ctrl.tvseriesonlineBot = function() {
			$scope.loading = true;
			$scope.error = null;
			$scope.success = false;

			$http.post(api + "/bots/series", {}).then(function(resp) {
				$scope.loading = false;
				$scope.success = true;
			}, function(resp) {
				$scope.error = {
					status: resp.status,
					msg: resp.data.error,
				};
				$scope.loading = false;
			});
		}

	}]);
