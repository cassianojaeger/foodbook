'use strict';

foodbookApp.controller('HomeController', function ($scope, user, RecipeService) {
    var ctrl = $scope;

    $scope.$on('recipe-pagination', function (event, params) {
        RecipeService.getFavoriteRecipes(user.name, params)
            .then(function (response) {
                ctrl.favRecipes = response;
            });

    });

});