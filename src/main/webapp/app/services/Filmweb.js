angular.module("app")
.factory("Filmweb", ["$resource", function($resource) {
	return $resource(api + "/filmweb");
}]);
