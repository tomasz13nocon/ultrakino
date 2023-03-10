angular.module("app")
.controller("ContentController", ['$http', '$location', '$routeParams', function($http, $location, $routeParams) {
	var ctrl = this;

	var id = $routeParams["id"];
	if (/^[1-9]{1}[0-9]*$/.test(id)) {
		$http.get(api + "/contents/" + id + "/type").then(function(resp) {
			var url = "";
			switch(resp.data.type) {
				case "FILM":
					url += "filmy";
					break;
				case "SERIES":
					url += "seriale";
					break;
			}
			url += "/" + id;
			$location.path(url);
			console.log("qwe");
		}).then(function(resp) {
			// TODO here. This callback doesn't get called
			console.log(resp);
			$location.path("/not-found");
		});
	}
	else {
		$location.path("/not-found");
	}
}]);

