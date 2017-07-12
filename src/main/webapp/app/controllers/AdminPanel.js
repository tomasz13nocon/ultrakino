//angular.module("app")
//.controller("AdminPanelController", ["$http", "$scope", "Film", "$interval", "User", adminPanelCtrl]);

function adminPanelCtrl($http, $scope, Film, $interval, User, Player) {
	var ctrl = this;

	$scope.usersPage = 0;
	$scope.adminPanelTab = 'bots';
	$scope.page = 1;
	$scope.requestUrl = api + "/films";
	$scope.requestData = {};
	$scope.requestMethod = "POST";

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

	$scope.fetchUsers = function() {
		User.query({ start: $scope.usersPage * 10, maxResults: 10 }, function(resp) {
			resp.forEach(function(user) {
				if (user.registrationDate) {
					date = arrayToDate(user.registrationDate);
					user.registrationDate = date.toLocaleDateString('PL-pl') + "  " + date.toLocaleTimeString('PL-pl');
				}
			})
			$scope.users = resp;
			console.log(resp);
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

	$scope.removeUser = function(id, i) {
		if (confirm("Na pewno usunąć tego użytkownika i wszystkie jego dane z bazy danych?")) {
			User.remove({ id: id }, function() {
				$scope.users.splice(i, 1);
			}, function(resp) {
				$scope.error = resp;
			});
		}
	}

	$scope.removePlayer = function(id, i, userI) {
		if (confirm("Na pewno chcesz usunąć ten link z bazy danych?")) {
			Player.remove({ id: id }, function() {
				$scope.users[userI].addedPlayers.splice(i, 1);
			}, function(resp) {
				$scope.error = resp;
			});
		}
	}

	$scope.setTab = function(tab) {
		$scope.adminPanelTab = tab;
		switch(tab) {
			case 'users':
				if (!$scope.users)
					$scope.fetchUsers();
				break;
		}
	}

}
