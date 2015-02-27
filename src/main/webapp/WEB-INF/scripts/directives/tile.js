'use strict';

/**
 * @ngdoc directive
 * @name testApp.directive:tile
 * @description
 * # tile
 */
angular.module('testApp')
  .directive('tile', function () {
    return {
      template: '<div class="tile">' + 
      			'<div class="head">' + 
              '<a href="{{data.userProfileUrl}}" target="_blank">{{data.userName}}</a>' +
              '<img class="social" ng-src="images/{{data.source}}.png"/></div>' +
            '<div class="content">' + 
              '<img class="usr img-circle" ng-src="{{data.userPhotoUrl}}"/>' +
      				'<p class="msg">{{data.message}}</p>' +
      				'<div ng-show="data.recordPhotoUrl"><img class="img-thumbnail" ng-src="{{data.recordPhotoUrl}}"/></div>' + 
      			'</div></div></div>',
      restrict: 'E',
      scope: {
      	data: '=model',
      },
      link: function postLink(scope, element, attrs) {
      }
    };
  });
