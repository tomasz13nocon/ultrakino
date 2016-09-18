angular.module("app")
	.controller("SearchController", ["$http", "$scope", "$timeout", "Film", function($http, $scope, $timeout, Film) {
		var controller = this;

		this.search = function() {
			if (controller.query.length < 2) {
				controller.films = [];
				return;
			}
			controller.films = Film.query({
				title: controller.query,
				resultLimit: 5,
			});
		};
	}]);
