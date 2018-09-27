'use strict';

var foodbookApp = angular.module('Foodbook',
    [
        'ngMaterial',
        'ngMessages',
        'ngResource',
        'ngStorage',
        'ngRoute'
    ]);
var httpHeaders;

foodbookApp.config(function($routeProvider, $httpProvider) {
    $routeProvider.accessWhen = function(path, route){
        route.resolve || (route.resolve = {});
        route.resolve.user = function ($location, AuthenticationService) {
            if (!AuthenticationService.isUserAuthenticated()) {
                AuthenticationService.logout();
                $location.path("/login");
            }
        };
        return $routeProvider.when(path, route);
    };

    $routeProvider
        .accessWhen("/", {
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
                data: function (AuthenticationService, $location) {
                    AuthenticationService.logout();
                    $location.path("/login");
                }
            }
        })
        .accessWhen("/home", {
            templateUrl: "js/app/components/home/home.html",
            controller: "HomeController",
            controllerAs: "vm"
        });

    $routeProvider.otherwise({
        redirectTo: "/login"
    });
    httpHeaders = $httpProvider.defaults.headers;
});