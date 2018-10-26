'use strict';

foodbookApp.controller('CreateRecipeController', function (group, $location, RecipeService, TIME) {
    var ctrl = this;
    ctrl.timeOptions = TIME;

    ctrl.recipe = {
        groupName: group.name,
        cookDifficulty: 0,
        cookTime: {
            timeType: "MINUTES",
            timeValue: 1
        }
    };

    ctrl.goBack = goBack;
    ctrl.createRecipe = createRecipe;

    function goBack() {
        $location.path("/home");
    }

    function createRecipe(recipe) {
        clearMessages();
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