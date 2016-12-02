angular.module("app")
.factory("Rating", ['$resource', '$rootScope', 'TheBox', 'User', function($resource, $rootScope, TheBox, User) {
	var service = $resource(api + "/ratings");

	service.stars = new Array(10);
	for (var i = 0; i < service.stars.length; i++) {
		service.stars[i] = i + 1;
	};
	service.activeStar = -1;

	service.starMouseover = function(i) {
		service.activeStar = i;
	};
	service.starMouseleave = function() {
		service.activeStar = -1;
	};

	service.rate = function(rateTarget, i) {
		if ($rootScope.authenticated) {
			service.save({}, {
				contentId: rateTarget.uid,
				rating: i,
			}, function(rating) {
				rateTarget.userRating = rating.rating;
				rateTarget.rating = (rateTarget.rating * rateTarget.timesRated + rating.rating) / ++rateTarget.timesRated;
				service.calculateRatingColor(rateTarget.rating);
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

	service.calculateRatingColor = function(rating) {
		ratingColor = "rgb(" +
			Math.min(255, rating * -51 + 510) + "," +
			Math.min(255, rating * 51) + ",0)";
		angular.element(document.querySelector(".rating-actual-rating")).css("color", ratingColor);
	};

	return service;
}]);


