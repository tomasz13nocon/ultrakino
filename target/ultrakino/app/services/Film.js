angular.module("app")
.factory("Film", ["$resource", function($resource) {
	return $resource(api + "/films/:id");
}]);
