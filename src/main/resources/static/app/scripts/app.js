'use strict';

angular.module('app', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
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
                templateUrl: 'views/video.html',
                controller: 'VideoCtrl'
            })
            .when('/mycameras', {
                templateUrl: 'views/myCameras.html',
                controller: 'CustomerCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
}]);




