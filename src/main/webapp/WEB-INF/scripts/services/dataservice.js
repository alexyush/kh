'use strict';

/**
 * @ngdoc service
 * @name testApp.dataService
 * @description
 * # dataService
 * Service in the testApp.
 */
angular.module('testApp')
.service('dataService', ['$http', function ($http) {
	this.getAll = function(callback) {
     $http.get('records/top').success(callback);
  };
  this.getByTags = function(tags, callback) {
     $http.post('records/tags', {'names':tags}).success(callback);
  };
  
  this.getTags = function(callback) {
     $http.get('records/toptags').success(callback);
  }
}]);