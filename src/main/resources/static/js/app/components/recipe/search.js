'use strict';

foodbookApp.controller('RecipeSearchController', function ($scope, recipeName, RecipeService) {
    var vm = $scope;
    vm.recipes = [];

    RecipeService.search(recipeName).then(function (recipes) {
        vm.recipes = recipes;
    });
});