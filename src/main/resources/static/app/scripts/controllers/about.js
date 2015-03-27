'use strict';

/**
 * @ngdoc function
 * @name webclientApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the webclientApp
 */
angular.module('webclientApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
