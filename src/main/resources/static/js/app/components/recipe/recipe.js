'use strict';

foodbookApp.controller('RecipeController', function (recipe, group, user, $scope, TIME, $mdDialog,
                                                     RecipeService, $mdToast, $timeout, $location) {
    var vm = $scope;
    recipe.cookTime.timeType = TIME.find(function (time) {return time.code === recipe.cookTime.timeType}).name;
    vm.recipe = recipe;
    vm.group = group;
    vm.isOwnRecipe = recipe.creator.username === user.name;

    vm.deleteRecipe = deleteRecipe;
    vm.openMadeRecipeDialog = openMadeRecipeDialog;

    function deleteRecipe(recipe) {
        RecipeService.delete(recipe.id)
            .then(function () {
                $mdToast.show($mdToast.simple().textContent('Receita deletada com sucesso!'));
                $timeout(function () {
                    $location.path("/groups/" + group.id);
                }, 500);
            });
    }

    function openMadeRecipeDialog(ev, recipe) {
        $mdDialog.show({
            controller: DialogController,
            templateUrl: 'js/app/components/recipe/madeRecipeDialog.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose:true,
            fullscreen: true
        })
            .then(function(answer) {
                console.log('You said the information was "' + answer + '".');
            }, function() {
                console.log('You cancelled the dialog.');
            });
    }

    function DialogController($scope, $mdDialog) {
        $scope.hide = function() {
            $mdDialog.hide();
        };
    };
});