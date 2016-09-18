angular.module("app")
	.controller("TabsController", ["$scope", "$route", function($scope, $route) {
		$scope.$route = $route;
	}]);
