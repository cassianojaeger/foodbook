"use strict";

foodbookApp.controller('TopbarController', function () {
    var ctrl = this;
});

foodbookApp.directive('topbar', function () {
    return {
        templateUrl: 'js/app/components/topbar/topbar.html',
        controller: 'TopbarController',
        controllerAs: 'vm',
        restrict: 'E'
    };
});