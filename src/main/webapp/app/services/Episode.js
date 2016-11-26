angular.module("app")
.factory("Episode", ["$resource", function($resource) {
	return $resource(api + "/series/:seriesId/:episodes/:id", { seriesId: "@seriesId", id: "@id", episodes: "episodes" }, {
		//recommend: {
			//method: "POST",
			//params: {
				//sub: "recommendationDate",
			//}
		//},
	});
}]);

