'use strict';

/**
 * @ngdoc overview
 * @name testApp
 * @description
 * # testApp
 *
 * Main module of the application.
 */
angular
  .module('testApp', [
    'ngRoute'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });

angular.module('testApp')
  .controller('BaseCtrl', ['$scope', 'dataService', function ($scope, dataService) {
    $scope.data = {};
    $scope.data.tags = [];

    dataService.getTags(function(result) {
        $scope.data.tags = result;
    });

    /*
    $scope.data.tags = [
      {name: 'Магилеу', id:0}, {name: 'шукаю', id:1}
    ];
  */
}]);