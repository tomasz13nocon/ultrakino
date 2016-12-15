angular.module("app")
.filter("objectOrderBy", function() {
	return function(items, field, reverse) {
		var filtered = [];
		angular.forEach(items, function(item) {
			filtered.push(item);
		});
		filtered.sort();
		if(reverse) filtered.reverse();
		return filtered;
	};
});
