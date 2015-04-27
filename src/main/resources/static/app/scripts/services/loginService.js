angular.module('app').service('loginService', function ($http, $resource, $rootScope) {
    $http.defaults.headers.common.Authorization = 'Basic ' + btoa('mSaasWebClient:jhfads07ay7qwhcrq6787436ghrc8q3746fgx8347fgj97634gfx9j3467fg927');
    $http.defaults.headers.Accept = 'application/json';

    var self = this;

    self.login = function (username, password, callback) {
        var Token = $resource('/oauth/token',

        {
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

            var Tabs = $resource('/server/tabs');
            $rootScope.tabs = Tabs.query();

            callback();
        });
    }
});