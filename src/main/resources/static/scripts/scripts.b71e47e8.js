"use strict";angular.module("webclientApp",["ngAnimate","ngAria","ngCookies","ngMessages","ngResource","ngRoute","ngSanitize","ngTouch","restangular"]).config(["$routeProvider",function(a){a.when("/",{templateUrl:"views/main.html",controller:"MainCtrl"}).when("/about",{templateUrl:"views/about.html",controller:"AboutCtrl"}).when("/login",{templateUrl:"views/login.html",controller:"LoginCtrl"}).when("/video",{templateUrl:"views/video.html",controller:"VideoCtrl"}).otherwise({redirectTo:"/"})}]),angular.module("webclientApp").controller("MainCtrl",["$resource",function(a){this.customer={id:null,name:"guest"};var b=a("/server/customerDetails"),c=this;b.get().$promise.then(function(a){c.customer=a})}]),angular.module("webclientApp").controller("AboutCtrl",["$scope",function(a){a.awesomeThings=["HTML5 Boilerplate","AngularJS","Karma"]}]),angular.module("webclientApp").controller("LoginCtrl",["$scope","$resource","$location","$http",function(a,b,c,d){this.getToken=function(a,e){d.defaults.headers.common.Authorization="Basic "+btoa("clientapp:123456"),d.defaults.headers.Accept="application/json";var f=b("/oauth/token",{grant_type:"password",scope:"read write",client_secret:"123456",client_id:"clientapp"},{authorize:{method:"POST",params:{username:a,password:e}}}),g=new f;g.$authorize(function(a){d.defaults.headers.common.Authorization=a.token_type+" "+a.access_token,c.path("/main.html")})}}]),angular.module("webclientApp").controller("VideoCtrl",["$scope","$timeout","Restangular",function(a,b,c){var d=[],e=function(){c.one("server/nextScreen").get().then(function(a){angular.forEach(a.cameras,function(a){var b=angular.element("<img>"),c=new Image;c.onload=function(){b.attr("src",this.src),b.attr("style","width: 600px; height: 500px;"),d.push(b)},c.src=a.url})})},f=function(){for(var a=0;4>a;a++){var b=d.shift();if(!b)break;b.attr("id","gogu"+a);var c=angular.element("#gogu"+a);c.replaceWith(b)}};e(),this.cameraList=function(){e(),f()}}]);