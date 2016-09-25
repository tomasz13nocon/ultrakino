angular.module("app")
	.controller("FilmsController", ["$http", "$scope", "Film", "$location", "$rootScope", "$window", function($http, $scope, Film, $location, $rootScope, $window) {
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

		var skipLocationEvent = false;
		ctrl.modelChanged = function() {
			var params = ctrl.processParams($scope.params);
			skipLocationEvent = true;
			$location.search(params);
			ctrl.updateResults(params);
			$scope.params.pageNumber = 0;
		};

		ctrl.locationChanged = function() {
			if (!skipLocationEvent) {
				var params = ctrl.processParams($location.search());
				ctrl.updateResults(params);
				$scope.params = params;
				for (var i = 0; i < params.categories.length; i++)
					$scope.params.categories[params.categories[i]] = true;
			}
			else {
				skipLocationEvent = false;
			}
		};
		$scope.$on("$locationChangeSuccess", ctrl.locationChanged);
		
		// Convert params to be eligible for API request
		ctrl.processParams = function(params) {
			var p = {
				categories: [],
			};
			p.orderBy = params.orderBy ? params.orderBy : "ADDITION_DATE";
			p.asc = params.asc ? true : false; // In case of undefined
			if (params.title) p.title = params.title;
			if (params.pageNumber) p.pageNumber = params.pageNumber;
			if (params.versions) p.versions = params.versions;
			if (params.yearFrom && params.yearFrom != $rootScope.years[$rootScope.years.length-1]) p.yearFrom = params.yearFrom;
			if (params.yearTo && params.yearTo != $rootScope.years[0]) p.yearTo = params.yearTo;
			if (params.categories) {
				var type = Object.prototype.toString.call(params.categories);
				if (type === "[object Object]") {
					for (category in params.categories) {
						if (params.categories[category])
							p.categories.push(category);
					}
				}
				else if (type === "[object Array]") {
					p.categories = params.categories;
				}
				else { // a single number (a string actually)
					p.categories.push(params.categories);
				}
			}

			return p;
		};

		ctrl.updateResults = function(params) {
			Film.get(params, function(film) {
				$scope.films = film.content;
				var first = Math.max(film.pageNumber - 3, 0);
				var last = Math.min(first + 9, film.pageCount);
				var length = last - first;
				if (length < 9) {
					first = Math.max(0, last - 9);
					length = last - first;
				}
				$scope.pages = [];
				$scope.activePage = film.pageNumber;
				$scope.lastPage = film.pageCount - 1;
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

		ctrl.toggleVersion = function(version) {
			if (!$scope.params.versions) $scope.params.versions = [];
			var index = $scope.params.versions.indexOf(version);
			if (index === -1)
				$scope.params.versions.push(version);
			else
				$scope.params.versions.splice(index, 1);
			ctrl.modelChanged();
		};

		// ctrl.toggleVersion = function(version) {

		// };

		var params = $location.search();
		// if (Object.keys(params).length === 0) {
			$scope.params = {
				categories: {},
				orderBy: "ADDITION_DATE",
				asc: false,
				yearFrom: $rootScope.years[$rootScope.years.length-1],
				yearTo: $rootScope.years[0],
			};
			ctrl.modelChanged();
		// }
		// else {
			// ctrl.locationChanged();
		// }

	}]);
