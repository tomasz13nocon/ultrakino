angular.module("app")
	.controller("AdminPanelController", ["$http", "$scope", "Film", function($http, $scope, Film) {
		var ctrl = this;

		$scope.page = 1;
		$scope.requestUrl = api + "/";
		$scope.requestData = {};

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

		$scope.performRequest = function() {
			$http({
				method: $scope.requestMethod,
				url: $scope.requestUrl,
				data: $scope.requestData,
			}).then(function(resp) {
				$scope.requestSuccessful = true;
				$scope.responseBody = resp;
				console.log(resp);
			}, function(resp) {
				$scope.requestSuccessful = false;
				$scope.responseBody = resp;
				console.log(resp);
			});
		}

		$scope.addRequestDataEntry = function() {
			$scope.requestData[$scope.requestDataKey] = $scope.requestDataValue;
			$scope.requestDataKey = null;
			$scope.requestDataValue = null;
		}

	}]);
