'use strict';

foodbookApp.controller('CreateRecipeController', function (group, $location, RecipeService) {
    var ctrl = this;
    ctrl.recipe = {
        cookDifficulty: 0
    };

    ctrl.goBack = goBack;
    ctrl.createRecipe = createRecipe;

    function goBack() {
        $location.path("/home");
    }

    function createRecipe(recipe) {
        clearMessages();
        recipe.groupName = group.name;
        recipe.cookTime = {
            timeType: "SECONDS",
            timeValue: "3"
        };
        RecipeService.create(recipe)
            .then(function (recipe) {
                ctrl.message = "Receita criada com sucesso";
            });
    }

    function clearMessages() {
        ctrl.message = null;
        ctrl.error = null;
    }

});