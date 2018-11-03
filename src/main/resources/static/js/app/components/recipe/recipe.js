'use strict';

foodbookApp.controller('RecipeController', function (recipe, group, user, $scope, TIME,
                                                     RecipeService, $mdToast, $timeout, $location) {
    var vm = $scope;
    recipe.cookTime.timeType = TIME.find(function (time) {return time.code === recipe.cookTime.timeType}).name;
    vm.recipe = recipe;
    vm.group = group;
    vm.isOwnRecipe = recipe.creator.username === user.name;

    vm.deleteRecipe = deleteRecipe;

    function deleteRecipe(recipe) {
        RecipeService.delete(recipe.id)
            .then(function () {
                $mdToast.show($mdToast.simple().textContent('Receita deletada com sucesso!'));
                $timeout(function () {
                    $location.path("/groups/" + group.id);
                }, 500);
            });
    }

});