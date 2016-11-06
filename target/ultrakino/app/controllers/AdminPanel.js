angular.module("app")
	.controller("AdminPanelController", ["$http", "$scope", "Film", function($http, $scope, Film) {
		var ctrl = this;

		$scope.page = 1;

		ctrl.alltubeBot = function() {
			$scope.loading = true;
			$http.post(api + "/bots/films", { page: $scope.page }).then(function(resp) {
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

		ctrl.tvseriesonlineBot = function() {
			$scope.loading = true;
			$scope.error = false;
			$http.post(api + "/bots/series", {}).then(function(resp) {
				$scope.loading = false;
				console.log("OK");
			}, function(resp) {
				$scope.error = {
					status: resp.status,
					msg: resp.data.error,
				};
				$scope.loading = false;
			});
		}

	}]);
