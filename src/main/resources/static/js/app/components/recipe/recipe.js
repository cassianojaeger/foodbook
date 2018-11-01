'use strict';

foodbookApp.controller('RecipeController', function (recipe, group, $scope, TIME) {
    var vm = $scope;
    recipe.cookTime.timeType = TIME.find(function (time) {return time.code === recipe.cookTime.timeType}).name;
    vm.recipe = recipe;
    vm.group = group;

});