angular.module("app")
.factory("User", ['$resource', '$rootScope', function($resource, $rootScope) {
	var service = $resource(api + "/users/:id");

	service.invalidate = function() {
		$rootScope.authenticated = false;
		$rootScope.isAdmin = false;
		$rootScope.user = undefined;
	};

	return service;
}]);
