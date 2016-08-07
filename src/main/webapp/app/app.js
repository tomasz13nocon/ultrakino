angular.module("app", []);

angular.module("app")
    .controller("HomeController", ["$http", function($http) {
        var self = this;

        this.postFilm = function() {
            $http.post("http://localhost:8080/api/films", {title: "JSON Bourne"});
        };

        this.getFilm = function(id) {
            console.log(id);
            $http.get("http://localhost:8080/api/films/" + id).then(function(resp) {
                console.log(resp.data);
                self.film = resp.data;
            });
        };

        this.test = function() {
            $http.get("http://localhost:8080/ultrakino").then(function(resp) {
                self.testData = resp.data;
            });
        }
    }]);
