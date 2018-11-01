'use strict';

foodbookApp.controller('RecipeFormController', function (group, recipe, $location, RecipeService, TIME) {
    var ctrl = this;
    ctrl.timeOptions = TIME;
    ctrl.recipe = recipe;
    ctrl.recipe.groupName = ctrl.recipe.groupName || group.name;
    ctrl.recipe.groupId = ctrl.recipe.groupId || group.id;
    if (ctrl.recipe.id) {
        ctrl.recipe.cookTime.timeValue = parseInt(ctrl.recipe.cookTime.timeValue);
    }

    ctrl.goBack = goBack;
    ctrl.saveRecipe = saveRecipe;

    function goBack() {
        $location.path("/home");
    }

    function saveRecipe(recipe) {
        clearMessages();
        RecipeService.save(recipe)
            .then(function (recipe) {
                ctrl.message = "Receita salva com sucesso";
            }).catch(function (reason) {
                ctrl.error = "Erro ao salvar a receita";
        });
    }

    function clearMessages() {
        ctrl.message = null;
        ctrl.error = null;
    }

});