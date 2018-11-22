'use strict';

foodbookApp.controller('HomeController', function ($scope, user, RecipeService) {
    var ctrl = $scope;

    $scope.$on('recipe-pagination', function (event, params) {
        RecipeService.getAll(params)
            .then(function (response) {
                ctrl.recipes = response;
            });


    });

    RecipeService.getFavoriteRecipes(user.name)
        .then(function (response) {
            ctrl.favRecipes = response.content;
        });
});