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

        function getGroupId(recipe) {
            return recipe.group? recipe.group.id : recipe.groupId;
        }

        function goToRecipe(recipe) {
            $location.path("/groups/" + getGroupId(recipe) + "/recipes/" + recipe.id);
        }
    }
});