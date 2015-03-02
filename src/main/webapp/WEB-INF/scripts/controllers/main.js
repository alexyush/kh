'use strict';

/**
 * @ngdoc function
 * @name testApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the testApp
 */
angular.module('testApp')
  .controller('MainCtrl', ['$scope', '$rootScope', '$controller', 'dataService', function ($scope, $rootScope, $controller, dataService) {

  	$.extend(this, $controller('BaseCtrl', {$scope: $scope}));

  	$rootScope.$on('tag-changed', function(event,args) {
  		for(var i=0, max=$scope.data.tags.length; i<max; i++) {
  			var tag = $scope.data.tags[i];
  			if (tag.name === args.name) {
  				tag.selected = args.selected;
  			}
  		}
  		updatePosts();
	    event.preventDefault();
	});


  	function getSelectedTags() {
  		var selected = [];
  		for(var i=0, max=$scope.data.tags.length; i<max; i++) {
  			var tag = $scope.data.tags[i];
  			if (tag.selected === true) {
  				selected.push(tag.name);
  			}
  		}
  		return selected;
  	}

  	function updatePosts() {
  		var selectedTags = getSelectedTags();
  		if (selectedTags.length > 0) {
  			dataService.getByTags(selectedTags, function(data) {
		  		$scope.posts = data;
		  	});
  		} else {
	  		dataService.getAll(function(data) {
		  		$scope.posts = data;
		  	});
	  	}
  	}

  	updatePosts();
 /*
	$scope.posts = [
		{
			msg: "sdfsdlfjsdlkj sdflkjd slkfjsdlkfjsldf jsdlfjsd lkfjsds",
			hashTags: ['#tag', '#tag2'],
			imageUrl: 'http://parikmaherov.net/upload/blogs/c382f3a82f299f64d591d01f900a2a87.jpg'
		},
		{
			msg: "hello world",
			hashTags: ['#tag', '#tag2'],
		},
		{
			msg: "hello world sdfsdjfsldkfj lkdjflk sjdfs sfdsdfs ",
			hashTags: ['#tag', '#tag2'],
		},
		{
			msg: "hello world",
			hashTags: ['#tag', '#tag2'],
		},
		{
			msg: "hello world sdfjkfs j ljfedlkhj sldkfj slkjs ",
			hashTags: ['#tag', '#tag2'],
		},
		{
			msg: "hello world",
			hashTags: ['#tag', '#tag2'],
		}
	];
*/
	if ($scope.tags) {}
 
  }]);
