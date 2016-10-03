angular.module("app")
.controller("UserDetailsController", ["$routeParams", "$scope", "User", function($routeParams, $scope, User) {
	var ctrl = this;

	User.get({ id: $routeParams.id }, function(user) {
		$scope.user = user;
	});

}]);
