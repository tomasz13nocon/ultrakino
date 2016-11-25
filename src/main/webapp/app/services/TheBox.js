angular.module("app")
.factory("TheBox", [function() {
	var theBox = this;

	theBox.showLoginBox = function() {
		theBox.showTheBox();
		theBox.loginTab = true;
	};
	theBox.showRegisterBox = function() {
		theBox.showTheBox();
		theBox.loginTab = false; 
	};
	theBox.showTheBox = function() {
		theBox.theBoxVisible = true;
	};
	theBox.hideTheBox = function() {
		theBox.theBoxVisible = false;
		theBox.authenticationFailed = false;
		theBox.message = undefined;
	};

	return theBox;
}]);
