angular.module("app")
.controller("MyAccountController", ["$scope", "User", "$http", function($scope, User, $http) {
	var ctrl = this;

	$http.get(api + "/userdetails").then(function(resp) {
		$scope.user = resp.data;
		console.log($scope.user);
		if (!$scope.user.avatarFilename) {
			$scope.user.avatarFilename = defaultAvatarFilename;
		}
	});
}]);

