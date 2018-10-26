'use strict';

foodbookApp.controller('HomeController', function ($scope, RecipeService) {
    var ctrl = $scope;

    RecipeService.getGroupRecipes()
        .then(function (response) {
            ctrl.recipes = response.content;
        });
});