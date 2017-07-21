angular.module("app")
.factory("User", ['$http', '$resource', '$rootScope', '$route', '$timeout', 'TheBox', function($http, $resource, $rootScope, $route, $timeout, TheBox) {
	var self = $resource(api + "/users/:id/:sub/:subId");

	self.authenticate = function(credentials) {
		var headers = credentials ? {
			Authorization: "Basic " + btoa(credentials.username + ":" + credentials.password) 
		} : {};
		$http.get(api + "/user", {headers: headers}).then(function(resp) {
			if (resp.data.username) {
				$rootScope.authenticated = true;
				if (resp.data.roles.indexOf("ROLE_ADMIN") !== -1) {
					$rootScope.isAdmin = true;
					addAdminScripts();
				}
				if (!$rootScope.user)
					$rootScope.user = resp.data;
				TheBox.theBoxVisible = false;
				TheBox.authenticationFailed = false;
				$route.reload();
			}
			else {
				$rootScope.authenticated = false;
				$rootScope.isAdmin = false;
				if (TheBox.theBoxVisible)
					TheBox.authenticationFailed = true;
			}
		}, function(resp) {
			$rootScope.authenticated = false;
			$rootScope.isAdmin = false;
			if (TheBox.theBoxVisible)
				TheBox.authenticationFailed = true;
		});
	};

	self.invalidate = function() {
		$rootScope.authenticated = false;
		$rootScope.isAdmin = false;
		$rootScope.user = undefined;
	};

	self.pushNotification = function(msg, duration, undoCallback, type) {
		if (typeof duration === "undefined")
			duration = 5000;
		if (typeof type === "undefined")
			type = "success";
		self.closeNotification();
		self.notification = msg;
		self.undo = undoCallback;
		self.showNotification = true;
		self.notificationType = type;
		self.notificationTimeout = $timeout(function() {
			self.showNotification = false;
		}, duration);
	};

	self.closeNotification = function() {
		$timeout.cancel(self.notificationTimeout);
		self.showNotification = false;
	};

	return self;
}]);
