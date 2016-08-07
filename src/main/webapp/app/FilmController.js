angular.module("app")
    .controller("FilmController", ["$http", function($http) {
        var self = this;

        $http.get("http://localhost:8080/rest/films/recommended").then(function(resp) {
            self.recommendedFilms = resp.data;
        });

    }]);