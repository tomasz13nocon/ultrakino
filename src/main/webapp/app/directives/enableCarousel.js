angular.module("app")
.directive('enableCarousel', function() {
	var link = function(scope, element, attrs) {
		if (scope.$last){
			var owl = $(".owl-carousel");
			// owl.on("changed.owl.carousel", centerElement);
			// owl2
			//owl.owlCarousel({
				//items: 1,
				//nav:true,
				//navRewind: true,
				//center: true,
				//autoplay: true,
				//autoplayTimeout: 3000,
				//navText : ["⬅", "➡"],
				//animateOut: 'carousel-out',
				//animateIn: 'carousel-in',
			//});
			// owl1
			owl.owlCarousel({
				singleItem: true,
				navigation: true,
				navigationText: ["🢀", "🢂"],
				//navigationText: ["🡰", "🡲"],
				autoPlay: 7000,
				//stopOnHover: true,
				transitionStyle: "goDown",
			});
		}
	};
	return {
		restrict: "A",
		link: link,
	}
});
	
