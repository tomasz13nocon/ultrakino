angular.module("app")
.controller("UserController", ['$http', '$rootScope', '$route', '$scope', '$window', 'TheBox', 'User', function($http, $rootScope, $route, $scope, $window, TheBox, User) {
	var ctrl = this;

	ctrl.User = User;
	ctrl.TheBox = TheBox;
	ctrl.form = {};
	$scope.dropdown = false;

	$scope.$on("$locationChangeSuccess", function() {
		$scope.dropdown = false;
	});

	ctrl.authenticate = function(credentials) {
		var headers = credentials ? {
			Authorization: "Basic " + btoa(credentials.username + ":" + credentials.password) 
		} : {};
		$http.get("/api/user", {headers: headers}).then(function(resp) {
			if (resp.data.name) {
				$rootScope.authenticated = true;
				for (var i=0; i < resp.data.authorities.length; i++) {
					if (resp.data.authorities[i].authority === "ROLE_ADMIN")
						$rootScope.isAdmin = true;
				}
				if (!$rootScope.user)
					$rootScope.user = resp.data.details;
				ctrl.TheBox.theBoxVisible = false;
				ctrl.TheBox.authenticationFailed = false;
				$route.reload();
			}
			else {
				$rootScope.authenticated = false;
				$rootScope.isAdmin = false;
				if (ctrl.TheBox.theBoxVisible)
					ctrl.TheBox.authenticationFailed = true;
			}
			$rootScope.authenticationAttempted = true;
		}, function(resp) {
			$rootScope.authenticated = false;
			$rootScope.isAdmin = false;
			// TODO: Custom error msgs. Here there can be 500 status code, not wrong credentials.
			if (ctrl.TheBox.theBoxVisible)
				ctrl.TheBox.authenticationFailed = true;
		});
	};

	ctrl.logout = function() {
		$http.post("/logout").then(function(resp) {
			User.invalidate();
			$route.reload();
			//$window.location.reload();
		});
	};

	ctrl.createAccount = function(credentials) {

	}

	ctrl.accountDropdownVisible = false;
	ctrl.toggleAccountDropdown = function() {
		ctrl.accountDropdownVisible = !ctrl.accountDropdownVisible;
	};
	ctrl.hideAccountDropdown = function() {
		ctrl.accountDropdownVisible = false;
	};

}]);
