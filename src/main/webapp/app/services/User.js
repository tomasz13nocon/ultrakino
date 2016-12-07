angular.module("app")
.factory("User", ['$resource', '$rootScope', '$timeout', function($resource, $rootScope, $timeout) {
	var self = $resource(api + "/users/:id/:sub");

	self.invalidate = function() {
		$rootScope.authenticated = false;
		$rootScope.isAdmin = false;
		$rootScope.user = undefined;
	};

	self.pushNotification = function(msg) {
		self.closeNotification();
		self.notification = msg;
		self.showNotification = true;
		self.notificationTimeout = $timeout(function() {
			self.showNotification = false;
		}, 5000);
	};

	self.closeNotification = function() {
		$timeout.cancel(self.notificationTimeout);
		self.showNotification = false;
	};

	return self;
}]);
