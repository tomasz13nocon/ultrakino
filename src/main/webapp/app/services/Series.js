angular.module("app")
.factory("Series", ["$resource", function($resource) {
	return $resource(api + "/series/:id/:sub", { id: "@id" }, {
		recommend: {
			method: "POST",
			params: {
				sub: "recommendationDate",
			}
		},
		deleteRecommendation : {
			method: "DELETE",
			params: {
				sub: "recommendationDate",
			}
		},
		postComment: {
			method: "POST",
			params: {
				sub: "comments",
			}
		},
		rate: {
			method: "POST",
			params: {
				sub: "ratings",
			}
		}
	});
}]);

