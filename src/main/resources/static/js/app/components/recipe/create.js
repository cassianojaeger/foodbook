'use strict';

foodbookApp.controller('CreateRecipeController', function ($location) {
    var ctrl = this;

    ctrl.goBack = goBack;
    ctrl.createRecipe = createRecipe;

    function goBack() {
        //$location.path("??????");
    }

    function createRecipe(recipe) {
        clearMessages();

    }

    function clearMessages() {
        ctrl.message = null;
        ctrl.error = null;
    }

});