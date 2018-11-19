'use strict';

foodbookApp.controller('HomeController', function ($scope, RecipeService) {
    var ctrl = $scope;

    $scope.$on('recipe-pagination', function (event, params) {
        RecipeService.getAll(params)
            .then(function (response) {
                ctrl.recipes = response;
            });
    });

});