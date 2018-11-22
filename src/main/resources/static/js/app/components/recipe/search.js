'use strict';

foodbookApp.controller('RecipeSearchController', function ($scope, recipeName, RecipeService) {
    var vm = $scope;
    vm.recipes = [];

    $scope.$on('recipe-pagination', function (event, params) {
        RecipeService.search(recipeName, params).then(function (recipes) {
            vm.recipes = recipes;
        });
    });
});