angular.module("app")
.controller("SeriesController", ["$scope", "Series", "$rootScope", function($scope, Series, $rootScope) {
	var ctrl = this;

	$scope.orderBy = {
		"Daty dodania": "ADDITION_DATE",
		"Tytułu": "TITLE",
		"Premiery": "PREMIERE",
	};

	$scope.asc = {
		"Malejąco": undefined,
		"Rosnąco": true,
	};

	ctrl.modelChanged = function() {
		var params = ctrl.processParams($scope.params);
		ctrl.updateResults(params);
		$scope.params.pageNumber = 0;
	};

	// Convert params to be eligible for API request
	ctrl.processParams = function(params) {
		var p = {
			categories: [],
		};
		p.orderBy = params.orderBy ? params.orderBy : "ADDITION_DATE";
		p.asc = params.asc ? true : false; // In case of undefined
		if (params.title) p.title = params.title;
		if (params.pageNumber) p.pageNumber = params.pageNumber;
		if (params.categories) {
			for (category in params.categories) {
				if (params.categories[category])
					p.categories.push($rootScope.seriesCategoriesIds[category]);
			}
		}

		return p;
	};

	ctrl.updateResults = function(params) {
		Series.get(params, function(series) {
			$scope.series = series.content;
			var first = Math.max(series.pageNumber - 3, 0);
			var last = Math.min(first + 9, series.pageCount);
			var length = last - first;
			if (length < 9) {
				first = Math.max(0, last - 9);
				length = last - first;
			}
			$scope.pages = [];
			$scope.activePage = series.pageNumber;
			$scope.lastPage = series.pageCount - 1;
			for (var i = 0; i < length; i++) {
				$scope.pages[i] = first + i;
			}
		});
	};

	ctrl.setPage = function(page) {
		if (page < 0 || page > $scope.lastPage || page == $scope.activePage)
			return;
		$scope.params.pageNumber = page;
		ctrl.modelChanged();
		$window.scrollTo(0, 0);
	}

	ctrl.clearCategories = function() {
		$scope.params.categories = {}
		ctrl.modelChanged();
	};

	$scope.params = {
		categories: {},
		orderBy: "ADDITION_DATE",
		asc: false,
	};
	ctrl.modelChanged();

}]);

