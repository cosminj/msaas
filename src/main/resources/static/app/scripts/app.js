'use strict';

/**
 * @ngdoc overview
 * @name webclientApp
 * @description
 * # webclientApp
 *
 * Main module of the application.
 */
angular.module('webclientApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'restangular'
  ]).config(['$routeProvider', function ($routeProvider) {
  $routeProvider
    .when('/', {
      templateUrl: 'views/main.html',
      controller: 'MainCtrl'
    })
    .when('/about', {
      templateUrl: 'views/about.html',
      controller: 'AboutCtrl'
    })
    .when('/login', {
      templateUrl: 'views/login.html',
      controller: 'LoginCtrl'
    })
    .when('/video', {
      templateUrl : 'views/video.html',
      controller : 'VideoCtrl'
    })
    .otherwise({
      redirectTo: '/'
    });
}]);
