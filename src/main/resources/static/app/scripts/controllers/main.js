'use strict';

/**
 * @ngdoc function
 * @name webclientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the webclientApp
 */
angular.module('webclientApp')
  .controller('MainCtrl', function ($resource) {
    this.customer = {id: null, name: 'guest'};
    //var Customer = $resource('/api/customer/:customerId', {customerId: '@id'});
    var Customer = $resource('/server/customerDetails');
    var main = this;
    Customer.get().$promise.then(function(customer) {
      main.customer = customer;
    });
  });
