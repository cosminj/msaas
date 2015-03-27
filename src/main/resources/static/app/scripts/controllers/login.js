'use strict';

/**
 * @ngdoc function
 * @name webclientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the webclientApp
 */
angular.module('webclientApp').controller('LoginCtrl', function ($scope, $resource, $location, $http) {

    this.getToken = function (username, password) {
      $http.defaults.headers.common.Authorization = 'Basic ' + btoa('mSaasWebClient:jhfads07ay7qwhcrq6787436ghrc8q3746fgx8347fgj97634gfx9j3467fg927');
      $http.defaults.headers.Accept = 'application/json';

      var Token = $resource('/oauth/token', {
        'grant_type': 'password',
        scope: 'read write',
        'client_secret': 'jhfads07ay7qwhcrq6787436ghrc8q3746fgx8347fgj97634gfx9j3467fg927',
        'client_id': 'mSaasWebClient'
      }, {
        authorize: {
          method: 'POST',
          params: {
            username: username,
            password: password
          }
        }
      });

      var token = new Token();
      token.$authorize(function (tokenRes) {
        $http.defaults.headers.common.Authorization = tokenRes.token_type + ' ' + tokenRes.access_token;
        $location.path('/main.html');
      });
    };
  });
