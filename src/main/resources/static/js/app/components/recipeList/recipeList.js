'use strict';

foodbookApp.directive('recipeList', function () {
    return {
        templateUrl: "js/app/components/recipeList/recipeList.html",
        controller: ControllerFn,
        controllerAs: "vm",
        scope: {
            recipes: "="
        }
    };

    function ControllerFn($location) {
        var vm = this;
        vm.goToRecipe = goToRecipe;

        function goToRecipe(recipe) {
            $location.path("/groups/" + recipe.group.id + "/recipes/" + recipe.id);
        }
    }
});