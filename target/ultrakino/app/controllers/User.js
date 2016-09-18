angular.module("app")
.controller("UserController", ["$http", "$rootScope", "User", function($http, $rootScope, User) {
	var ctrl = this;

	ctrl.User = User;
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
				ctrl.User.username = resp.data.name;
				var avatar = resp.data.avatarFilename;
				ctrl.User.avatarFilename = avatar ? avatar : "images/default-avatar.png";
				ctrl.User.showLoginBox = false;
			}
			else {
				$rootScope.authenticated = false;
				$rootScope.isAdmin = false;
				if (ctrl.User.showLoginBox)
					ctrl.authenticationFailed = true;
			}
		}, function(resp) {
			$rootScope.authenticated = false;
			$rootScope.isAdmin = false;
			if (ctrl.User.showLoginBox)
				ctrl.authenticationFailed = true;
		});
	};

	ctrl.logout = function() {
		$http.post("/logout").then(function(resp) {
			$rootScope.authenticated = false;
			$rootScope.isAdmin = false;
		});
	};

	ctrl.authenticate();

}]);
