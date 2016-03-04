var exec = require('cordova');

function FeatherEditor() { 
	console.log("FeatherEditor.js: is created");
}

FeatherEditor.prototype.show = function(src, options) {
	console.log("FeatherEditor.js: show");

	cordova.exec(options.success, options.error, "FeatherEditor", "show", [{src: src}]);
}

var featherEditor = new FeatherEditor();
module.exports = featherEditor;
