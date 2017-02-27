angular.module("app")
.factory("Rating", ['$resource', '$rootScope', 'TheBox', 'User', function($resource, $rootScope, TheBox, User) {
	var self = $resource(api + "/ratings");

	self.stars = new Array(10);
	for (var i = 0; i < self.stars.length; i++) {
		self.stars[i] = i + 1;
	};
	self.activeStar = -1;

	self.starMouseover = function(i) {
		self.activeStar = i;
	};
	self.starMouseleave = function() {
		self.activeStar = -1;
	};

	self.rate = function(rateTarget, i) {
		if ($rootScope.authenticated) {
			self.save({}, {
				contentId: rateTarget.uid,
				rating: i,
			}, function(resp) {
				rateTarget.userRating = resp.rating;
				rateTarget.rating = (rateTarget.rating * rateTarget.timesRated + resp.rating) / ++rateTarget.timesRated;
				self.calculateRatingColor(rateTarget.rating);
			}, function(resp) {
				if (resp.status === 401) {
					User.invalidate();
					TheBox.showLoginBox("Sesja wygasła. Zaloguj się ponownie.");
				}
			});
		}
		else {
			TheBox.showRegisterBox("Załóż konto, lub zaloguj się żeby móc oceniać filmy!");
		}
	};

	self.calculateRatingColor = function(rating) {
		ratingColor = "rgb(" +
			Math.min(255, rating * -51 + 510) + "," +
			Math.min(255, rating * 51) + ",0)";
		console.log(ratingColor);
		angular.element(document.querySelector(".rating-actual-rating")).css("color", ratingColor);
	};

	return self;
}]);


