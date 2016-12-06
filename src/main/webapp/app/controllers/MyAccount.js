angular.module("app")
.controller("MyAccountController", ['$http', '$rootScope', '$scope', 'User', function($http, $rootScope, $scope, User) {
	var ctrl = this;

	ctrl.setActiveTab = function(tab) {
		if (tab === $scope.activeTab) return;
		$scope.activeTab = tab;
		User.query({ id: $rootScope.user.uid, sub: tab }, function(resp) {
			$scope.contentList = resp;
		});
	};
	if ($rootScope.user) {
		ctrl.setActiveTab("watchlist");
	}

}]);

