angular.module("app")
.directive('clickAnywhereButHere', ["$document", "ClickAnywhereButHereService", function($document, ClickAnywhereButHereService){
  return {
    restrict: 'A',
    link: function(scope, elem, attr, ctrl) {
      var handler = function(e) {
        e.stopPropagation();
      };
      elem.on('click', handler);

      scope.$on('$destroy', function(){
        elem.off('click', handler);
      });

      ClickAnywhereButHereService(scope, attr.clickAnywhereButHere);
    }
  };
}]);
