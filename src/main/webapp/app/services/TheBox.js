angular.module("app")
.factory("TheBox", [function() {
	var self = this;

	self.showLoginBox = function(msg) {
		if (msg) {
			self.loginMessage = msg;
		}
		self.showTheBox();
		self.loginTab = true;
	};
	self.showRegisterBox = function(msg) {
		if (msg) {
			self.registerMessage = msg;
		}
		self.showTheBox();
		self.loginTab = false; 
	};
	self.showTheBox = function() {
		self.theBoxVisible = true;
	};
	self.hideTheBox = function() {
		self.theBoxVisible = false;
		self.authenticationFailed = false;
		self.loginMessage = undefined;
		self.registerMessage = undefined;
	};

	return self;
}]);
