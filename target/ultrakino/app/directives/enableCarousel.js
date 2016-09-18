angular.module("app")
	.directive('enableCarousel', function() {
		var link = function(scope, element, attrs) {
			if (scope.$last){
				var owl = $(".owl-carousel");
				// owl.on("changed.owl.carousel", centerElement);
				/*owl.owlCarousel({
					items: 1,
					nav:true,
					navRewind: true,
					center: true,
					autoplay: true,
					autoplayTimeout: 3000,
					navText : ["⬅", "➡"],
					animateOut: 'carousel-out',
					animateIn: 'carousel-in',
				});*/
				owl.owlCarousel({
					singleItem: true,
					navigation:true,
					navigationText : ["⬅", "➡"],
					autoPlay: 8000,
					// stopOnHover: true,
					// transitionStyle: "fadeUp",
					transitionStyle: "goDown",
				});
			}
		};
		return {
			restrict: "A",
			link: link,
		}
	});
	