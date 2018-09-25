'use strict';

var foodbookApp = angular.module('Foodbook',
    [
        'ngMaterial',
        'ngMessages',
        'ngResource',
        'ngRoute'
    ]);
var httpHeaders;

foodbookApp.config(function($routeProvider, $httpProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "js/app/components/mainContent/mainContent.html"
        })
        .when("/login", {
            templateUrl: "js/app/components/login/login.html",
            controller: "LoginController",
            controllerAs: "vm"
        })
        .when("/register", {
            templateUrl: "js/app/components/register/register.html",
            controller: "RegisterController",
            controllerAs: "vm"
        })
        .when("/logout", {
            resolve: {
                data: function (AuthenticationService) {
                    AuthenticationService.logout();
                }
            }
        });

    $routeProvider.otherwise({
        redirectTo: "/login"
    });
    httpHeaders = $httpProvider.defaults.headers;
});