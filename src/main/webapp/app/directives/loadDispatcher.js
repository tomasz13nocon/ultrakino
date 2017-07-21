angular.module("app")
.directive("loadDispatcher", function() {
    return {
        restrict: "A",
        link: function(scope, element, attrs) {
			if (scope.$last === true) {
                scope.$emit("elementLoaded", element);
			}
        }
    };
})
