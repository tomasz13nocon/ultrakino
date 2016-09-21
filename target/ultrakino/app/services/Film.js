angular.module("app")
.factory("Film", ["$resource", function($resource) {
	return $resource(api + "/films/:id/:sub", { id: "@id" }, {
		recommend: {
			method: "POST",
			params: {
				sub: "recommendationDate",
			}
		}
	});
}]);
