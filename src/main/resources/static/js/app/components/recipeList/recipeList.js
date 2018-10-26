'use strict';

foodbookApp.directive('recipeList', function (RecipeService, $location) {
    return {
        templateUrl: "js/app/components/recipeList/recipeList.html",
        controller: ControllerFn,
        controllerAs: "vm"
    };

    function ControllerFn() {
        var vm = this;
        vm.goToRecipe = goToRecipe;

        GroupService.getUserGroups().then(function (response) {
            vm.groups = response.content;
        });

        function goToRecipe(name) {
            $location.path("/recipe/" + name);
        }
    }
});