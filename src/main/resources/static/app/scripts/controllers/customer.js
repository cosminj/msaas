'use strict';

angular.module('app').controller('CustomerCtrl', function ($scope, $timeout, Restangular) {

    Restangular.all('server/customer/cameras').getList().then(function(cameras) { $scope.cameras = cameras; });

});