angular.module("app")
.factory("User", ['$resource', '$rootScope', '$timeout', function($resource, $rootScope, $timeout) {
	var self = $resource(api + "/users/:id/:sub/:subId");

	self.invalidate = function() {
		$rootScope.authenticated = false;
		$rootScope.isAdmin = false;
		$rootScope.user = undefined;
	};

	self.pushNotification = function(msg, duration, undoCallback) {
		if (typeof duration === "undefined")
			duration = 5000;
		self.closeNotification();
		self.notification = msg;
		self.undo = undoCallback;
		self.showNotification = true;
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
