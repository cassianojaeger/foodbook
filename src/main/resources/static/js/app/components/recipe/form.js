'use strict';

foodbookApp.controller('RecipeFormController', function ($scope, group, recipe, $location, RecipeService, TIME) {
    $scope.timeOptions = TIME;
    $scope.recipe = recipe;
    $scope.recipe.groupName = $scope.recipe.groupName || group.name;
    $scope.recipe.groupId = $scope.recipe.groupId || group.id;
    if ($scope.recipe.id) {
        $scope.recipe.cookTime.timeValue = parseInt($scope.recipe.cookTime.timeValue);
    }

    $scope.goBack = goBack;
    $scope.saveRecipe = saveRecipe;

    function goBack() {
        $location.path("/home");
    }

    function saveRecipe(recipe) {
        clearMessages();
        RecipeService.save(recipe)
            .then(function (recipe) {
                $scope.message = "Receita salva com sucesso";
            }).catch(function (reason) {
                $scope.error = "Erro ao salvar a receita";
        });
    }

    function clearMessages() {
        $scope.message = null;
        $scope.error = null;
    }

});