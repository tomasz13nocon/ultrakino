angular.module("app")
.factory("User", ['$resource', '$rootScope', function($resource, $rootScope) {
	var self = $resource(api + "/users/:id/:sub");

	self.invalidate = function() {
		$rootScope.authenticated = false;
		$rootScope.isAdmin = false;
		$rootScope.user = undefined;
	};

	return self;
}]);
