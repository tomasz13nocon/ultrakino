angular.module("app")
.factory("Player", ["$resource", function($resource) {
	return $resource(api + "/players/:id", { id: "@id" });
}]);
