angular.module("app")
.factory("Comment", ['$resource', 'User', function($resource, User) {
	var self = $resource(api + "/comments/:id", { id: "@id" }, {});

	self.process = function(comment) {
		comment.submissionDate = new Date(
			comment.submissionDate[0],
			comment.submissionDate[1],
			comment.submissionDate[2],
			comment.submissionDate[3],
			comment.submissionDate[4],
			comment.submissionDate[5]);
		return comment;
	};

	self.processComments = function(content) {
		for (var i = 0; i < content.comments.length; i++) {
			self.process(content.comments[i]);
		}
	};


	// TODO: Maybe refresh all comments here
	self.postComment = function(comment, content, successCallback) {
		if (typeof comment === 'undefined') { // ng-maxlength and ng-minlength make it undefined if it exceeds the limits.
			User.pushNotification("Komentarz musi zawierać od 1 do 500 znaków.", undefined, undefined, "error");
			return;
		}
		self.save({ comment: comment, contentId: content.uid }, function(comment) {
			content.comments.push(self.process(comment));
			if (typeof successCallback === "function") {
				successCallback();
			}
		}, function(resp) {
			if (resp.status === 401) {
				User.invalidate();
				TheBox.showLoginBox("Sesja wygasła. Zaloguj się ponownie.");
			}
		});
	};

	return self;
}]);

