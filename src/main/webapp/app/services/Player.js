angular.module("app")
.factory("Player", ["$resource", function($resource) {
	var self = $resource(api + "/players/:id/:sub", { id: "@id" }, {
		vote: {
			method: "POST",
			params: {
				sub: "votes"
			}
		}
	});

	self.calculateColor = function(player, el) {
		var rating = player.upvotes / player.votes.length * 100;
		var style = "linear-gradient(to right, #483 " + rating + "%, #943 " + rating + "%)";
		el.style["background-image"] = style;
	}

	self.calculatePlayerRatingColor = function(players) {
		var els = document.getElementsByClassName("player-vote-bar");
		players.forEach(function(player, i) {
			if (player.votes.length) {
				self.calculateColor(player, els[i])
			}
		});
	}

	return self;
}]);
