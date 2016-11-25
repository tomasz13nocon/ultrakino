angular.module("app")
.factory("Episode", ["$resource", function($resource) {
	return $resource(api + "/series/:seriesId/episodes?/:id", { seriesId: "@seriesId", id: "@id" }, {
		//recommend: {
			//method: "POST",
			//params: {
				//sub: "recommendationDate",
			//}
		//},
	});
}]);

