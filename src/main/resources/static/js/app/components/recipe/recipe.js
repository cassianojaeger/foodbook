'use strict';

foodbookApp.controller('RecipeController', function (recipe, group, user, $scope, TIME) {
    var vm = $scope;
    recipe.cookTime.timeType = TIME.find(function (time) {return time.code === recipe.cookTime.timeType}).name;
    vm.recipe = recipe;
    vm.group = group;
    vm.isOwnRecipe = recipe.creator.username === user.name;

});