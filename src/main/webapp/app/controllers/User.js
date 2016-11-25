angular.module("app")
.controller("UserController", ["$http", "$rootScope", "User", "TheBox", function($http, $rootScope, User, TheBox) {
	var ctrl = this;

	ctrl.User = User;
	ctrl.TheBox = TheBox;
	ctrl.form = {};

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
			}
			else {
				$rootScope.authenticated = false;
				$rootScope.isAdmin = false;
				if (ctrl.TheBox.theBoxVisible)
					ctrl.TheBox.authenticationFailed = true;
			}
		}, function(resp) {
			$rootScope.authenticated = false;
			$rootScope.isAdmin = false;
			if (ctrl.TheBox.theBoxVisible)
				ctrl.TheBox.authenticationFailed = true;
		});
	};

	ctrl.logout = function() {
		$http.post("/logout").then(function(resp) {
			$rootScope.authenticated = false;
			$rootScope.isAdmin = false;
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
