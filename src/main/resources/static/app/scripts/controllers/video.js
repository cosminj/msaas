'use strict';

angular.module('app').controller('VideoCtrl', function ($scope, $timeout, Restangular) {
    var availableCameras = [];

    var fetch = function () {
        Restangular.one('server/nextScreen').get()
                .then(function (nextScreen) {
                    angular.forEach(nextScreen.cameras, function (camera) {
                        var imgDOM = angular.element('<img>');
                        var preload = new Image();
                        preload.onload = function () {
                            imgDOM.attr('src', this.src);
                            imgDOM.attr('style', 'width: 600px; height: 500px;');
                            availableCameras.push(imgDOM);
                        };
                        // start preloading
                        preload.src = camera.url;
                    });
                });
    };

    var replaceLatest = function () {
        for (var i = 0; i < 4; i++) {
            var tmp = availableCameras.shift();
            if (tmp) {
                tmp.attr('id', 'gogu' + i);
                var current = angular.element('#gogu' + i);
                current.replaceWith(tmp);
            } else {
                break;
            }
        }
    };

    fetch();

    this.cameraList = function () {
        fetch();
        replaceLatest();
    };

});
