'use strict';

var foodbookApp = angular.module('Foodbook',
    [
        'ngMaterial',
        'ngMessages',
        'ngResource',
        'ngStorage',
        'ngRoute',
        'jkAngularRatingStars'
    ]);
var httpHeaders;

foodbookApp.config(function($routeProvider, $httpProvider, $mdThemingProvider) {
    $routeProvider.whenAuthenticated = function(path, route){
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
        // authenticated routes
        .whenAuthenticated("/home", {
            templateUrl: "js/app/components/home/home.html",
            controller: "HomeController",
            controllerAs: "vm"
        })
        .whenAuthenticated("/group/create", {
            templateUrl: "js/app/components/group/create.html",
            controller: "CreateGroupController",
            controllerAs: "vm"
        })
        .whenAuthenticated("/groups/:name", {
            templateUrl: "js/app/components/group/group.html",
            controller: "GroupController",
            resolve: {
                group: function (GroupService, $route) {
                    return GroupService.get($route.current.params.name);
                }
            },
            controllerAs: "vm"
        })
        .whenAuthenticated("/groups/:name/recipe/create", {
            templateUrl: "js/app/components/recipe/create.html",
            controller: "CreateRecipeController",
            controllerAs: "vm",
            resolve: {
                group: function (GroupService, $route) {
                    return GroupService.get($route.current.params.name);
                }
            }
        })
        .whenAuthenticated("/recipes/:name", {
            templateUrl: "js/app/components/recipe/recipe.html",
            controller: "RecipeController",
            resolve: {
                group: function (Recipeervice, $route) {
                    return Recipeervice.get($route.current.params.name);
                }
            },
            controllerAs: "vm"
        });

    $routeProvider.otherwise({
        redirectTo: "/home"
    });
    httpHeaders = $httpProvider.defaults.headers;

    $mdThemingProvider.theme('default')
        .primaryPalette('red')
        .accentPalette('orange');
});