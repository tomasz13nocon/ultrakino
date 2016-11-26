angular.module("app")
.component("adminTools", {
	templateUrl: templateDir + "/admin-tools.html",
	controller: "AdminToolsController",
	controllerAs: "adminToolsCtrl",
	bindings: { content: '=' },
});
