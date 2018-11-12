'use strict';

foodbookApp.controller('HomeController', function ($scope, user, RecipeService) {
    var ctrl = $scope;

    RecipeService.getAll()
        .then(function (response) {
            ctrl.recipes = response.content;
        });

    RecipeService.getFavoriteRecipes(user.name)
        .then(function (response) {
            ctrl.favRecipes = response.content;
        });
});