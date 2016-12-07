angular.module("app")
.controller("MyAccountController", ['$animate', '$http', '$rootScope', '$scope', 'User', function($animate, $http, $rootScope, $scope, User) {
	var ctrl = this;

	ctrl.setActiveTab = function(tab) {
		if (tab === $scope.activeTab) return;
		$scope.activeTab = tab;
		User.query({ id: $rootScope.user.uid, sub: tab }, function(resp) {
			$animate.enabled(false);
			$scope.contentList = resp;
			window.setTimeout(function() {
				$animate.enabled(true);
			}, 0);
		});
	};
	if ($rootScope.user) {
		ctrl.setActiveTab("watchlist");
	}

	ctrl.removeFromList = function(content) {
		User.delete({ id: $rootScope.user.uid, sub: $scope.activeTab, subId: content.uid }, function(resp) {
			$scope.contentList.splice($scope.contentList.indexOf(content), 1);
		});
	};

}]);

