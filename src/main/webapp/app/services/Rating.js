angular.module("app")
.factory("Rating", ["$resource", function($resource) {
	var res = $resource(api + "/ratings");


	return res;
}]);


