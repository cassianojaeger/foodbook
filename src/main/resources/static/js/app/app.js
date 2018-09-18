'use strict';

var foodbookApp = angular.module('Foodbook',
    [
        'ngMaterial',
        'ngMessages',
        'ngResource',
        'ngRoute'
    ]);

foodbookApp.config(function($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "js/app/components/mainContent/mainContent.html"
        })
        .when("/login", {
            templateUrl: "js/app/components/login/login.html"
        });
});