'use strict';

foodbookApp.directive('recipeList', function (RecipeService, $location) {
    return {
        templateUrl: "js/app/components/recipeList/recipeList.html",
        controller: ControllerFn,
        controllerAs: "vm",
        scope: {
            recipes: "="
        }
    };

    function ControllerFn() {
        var vm = this;
    }
});