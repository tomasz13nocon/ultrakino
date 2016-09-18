angular.module("app")
.factory("User", ["$resource", function($resource) {
	this.showLoginBox = false;
	return $resource(api + "/users/:id");
}]);