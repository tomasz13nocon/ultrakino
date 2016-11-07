angular.module("app")
.factory("Comment", ["$resource", function($resource) {
	var res = $resource(api + "/comments/:id", { id: "@id" }, {});

	res.process = function(comment) {
		if (!comment.addedBy.avatarFilename)
			comment.addedBy.avatarFilename = defaultAvatarFilename;
		comment.submissionDate = new Date(
			comment.submissionDate[0],
			comment.submissionDate[1],
			comment.submissionDate[2],
			comment.submissionDate[3],
			comment.submissionDate[4],
			comment.submissionDate[5]);
		return comment;
	};

	return res;
}]);

