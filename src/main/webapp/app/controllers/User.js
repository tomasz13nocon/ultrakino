angular.module("app")
.controller("UserController", ['$http', '$rootScope', '$route', '$scope', '$window', 'TheBox', 'User', function($http, $rootScope, $route, $scope, $window, TheBox, User) {
	var ctrl = this;

	ctrl.User = User;
	ctrl.TheBox = TheBox;
	ctrl.form = {};
	$scope.dropdown = false;

	$scope.$on("$locationChangeSuccess", function() {
		$scope.dropdown = false;
	});

	ctrl.logout = function() {
		$http.post("/logout").then(function(resp) {
			User.invalidate();
			$route.reload();
			//$window.location.reload();
		});
	};

	ctrl.createAccount = function(credentials) {

	}

	ctrl.accountDropdownVisible = false;
	ctrl.toggleAccountDropdown = function() {
		ctrl.accountDropdownVisible = !ctrl.accountDropdownVisible;
	};
	ctrl.hideAccountDropdown = function() {
		ctrl.accountDropdownVisible = false;
	};

}]);
