angular.module("app")
.controller("MyAccountController", ['$animate', '$http', '$interval', '$rootScope', '$scope', '$timeout', 'User', function($animate, $http, $interval, $rootScope, $scope, $timeout, User) {
	if (!$rootScope.user) return;

	var ctrl = this;

	ctrl.setActiveTab = function(tab) {
		if (tab === $scope.activeTab) return;
		$scope.activeTab = tab;
		$scope.loadingTab = true;
		switch (tab) {
			case "watchlist":
			case "favorites":
				User.query({ id: $rootScope.user.uid, sub: tab }, function(resp) {
					$scope.loadingTab = false;
					var element = angular.element(document.querySelector(".account-films"));
					$animate.enabled(element, false);
					$scope.contentList = resp;
					window.setTimeout(function() {
						$animate.enabled(element, true);
					}, 0);
				});
				break;
			case "settings":

				break;
		}
	};


	ctrl.removeFromList = function(content) {
		tab = $scope.activeTab;
		User.delete({ id: $rootScope.user.uid, sub: tab, subId: content.uid }, function(resp) {
			var index = $scope.contentList.indexOf(content);
			$scope.contentList.splice(index, 1);
			User.pushNotification("Pozycja '" + content.title + "' została usunięta z tej listy", 8000, function() {
				User.save({ id: $rootScope.user.uid, sub: tab }, { contentId: content.uid }, function(resp) {
					User.pushNotification("Akcja została cofnięta.", 3500);
					if (tab === $scope.activeTab)
						$scope.contentList.splice(index, 0, content);
				});
			});
		});
	};

	ctrl.setActiveTab("watchlist");
	User.get({ id: $rootScope.user.uid }, function(user) {
		user.registrationDate = arrayToDate(user.registrationDate);
		$scope.user = user;
	});

}]);

