'use strict';

/**
 * @ngdoc directive
 * @name testApp.directive:tag
 * @description
 * # tag
 */ 
angular.module('testApp')
  .directive('tag', function ($rootScope) {
    return {
      template: '<div class="tag" ng-class="{selected: tag.selected===true}" ng-click="toggleTag(tag)">#{{tag.name}}</div>',
      restrict: 'E',
      scope: {
      	tag: '=model'
      },
      link: function postLink(scope, element, attrs) {
        scope.toggleTag = function () {
          if (scope.tag.selected === undefined) {
            scope.tag.selected = true;
          } else {
            scope.tag.selected = !scope.tag.selected;
          }
          $rootScope.$emit('tag-changed', scope.tag);
        }
      }
    };
  }); 