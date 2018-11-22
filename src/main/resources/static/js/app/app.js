'use strict';

var foodbookApp = angular.module('Foodbook',
    [
        'ngMaterial',
        'ngMessages',
        'ngResource',
        'ngStorage',
        'ngRoute',
        'jkAngularRatingStars',
        'ui.bootstrap'
    ]);
var httpHeaders;

foodbookApp.config(function($routeProvider, $httpProvider, $mdThemingProvider) {
    $routeProvider.whenAuthenticated = function(path, route){
        route.resolve || (route.resolve = {});
        route.resolve.user = function ($location, AuthenticationService, UserService) {
            if (!AuthenticationService.isUserAuthenticated()) {
                AuthenticationService.logout();
                $location.path("/login");
            }
            return UserService.getLoggedUser();
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
            templateUrl: "js/app/components/group/form.html",
            controller: "GroupFormController",
            controllerAs: "vm",
            resolve: {
                group: function ($q) {
                    return $q.resolve({name: "", description: ""});
                }
            }
        })
        .whenAuthenticated("/groups/:id/edit", {
            templateUrl: "js/app/components/group/form.html",
            controller: "GroupFormController",
            controllerAs: "vm",
            resolve: {
                group: function (GroupService, $route) {
                    return GroupService.get($route.current.params.id);
                }
            }
        })
        .whenAuthenticated("/groups/:id", {
            templateUrl: "js/app/components/group/group.html",
            controller: "GroupController",
            resolve: {
                group: function (GroupService, $route) {
                    return GroupService.get($route.current.params.id);
                }
            },
            controllerAs: "vm"
        })
        .whenAuthenticated("/groups/:id/recipe/create", {
            templateUrl: "js/app/components/recipe/form.html",
            controller: "RecipeFormController",
            controllerAs: "vm",
            resolve: {
                group: function (GroupService, $route) {
                    return GroupService.get($route.current.params.id);
                },
                recipe: function ($q) {
                    return $q.resolve(
                        {
                            cookDifficulty: 0,
                            cookTime: {
                                timeType: "MINUTES",
                                timeValue: 1
                            }
                        });
                }
            }
        })
        .whenAuthenticated("/search/:name/recipe", {
            templateUrl: "js/app/components/recipe/search.html",
            controller: "RecipeSearchController",
            controllerAs: "vm",
            resolve: {
                recipeName: function ($route) {
                    return $route.current.params.name;
                }
            }
        })
        .whenAuthenticated("/search/:name/group", {
            templateUrl: "js/app/components/group/search.html",
            controller: "GroupSearchController",
            controllerAs: "vm",
            resolve: {
                groupName: function ($route) {
                    return $route.current.params.name;
                }
            }
        })
        .whenAuthenticated("/groups/:groupId/recipe/:recipeId/edit", {
            templateUrl: "js/app/components/recipe/form.html",
            controller: "RecipeFormController",
            controllerAs: "vm",
            resolve: {
                group: function (GroupService, $route) {
                    return GroupService.get($route.current.params.groupId);
                },
                recipe: function (RecipeService, $route) {
                    return RecipeService.get($route.current.params.recipeId);
                }
            }
        })
        .whenAuthenticated("/groups/:groupId/recipes/:recipeId", {
            templateUrl: "js/app/components/recipe/recipe.html",
            controller: "RecipeController",
            resolve: {
                group: function (GroupService, $route) {
                    return GroupService.get($route.current.params.groupId);
                },
                recipe: function (RecipeService, $route) {
                    return RecipeService.get($route.current.params.recipeId);
                },
                isFavorite: function (RecipeService, $route) {
                    return RecipeService.isFavorite($route.current.params.recipeId);
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

    $httpProvider.interceptors.push('AuthInterceptor');
})

.factory('AuthInterceptor', function ($rootScope, $location) {

    function redirectToHomeIfUnauthenticated(response) {
        if (response.status === 401) {
            $rootScope.$broadcast('auth-logout');
            $location.path('/login');
        }
        return response;
    }

    return {
        response: redirectToHomeIfUnauthenticated,
        responseError: redirectToHomeIfUnauthenticated
    };
});