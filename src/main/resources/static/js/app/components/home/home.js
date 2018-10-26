'use strict';

foodbookApp.controller('HomeController', function ($scope, RecipeService) {
    var ctrl = $scope;

    RecipeService.getAll()
        .then(function (response) {
            ctrl.recipes = response.content;
        });
});