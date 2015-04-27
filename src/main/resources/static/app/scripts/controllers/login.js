'use strict';

angular.module('app').controller('LoginCtrl', function ($scope, $resource, $location, $http, loginService, $rootScope) {
    $scope.getToken = function (username, password) {
        loginService.login(username, password, function () {

            var Customer = $resource('/server/myDetails');
            var main = this;
            Customer.get().$promise.then(function (customer) {
                $rootScope.customer = customer;
            });
            $location.path('/main.html');
        });
    }
});