angular.module("app")
.factory("Comment", ["$resource", function($resource) {
	return $resource(api + "/comments/:id", { id: "@id" }, {

	});
}]);

