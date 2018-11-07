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
            fullscreen: true,
            controllerAs: "vm",
            locals: {
                    recipe: recipe,
                    feedback: {
                        timeValue: 0,
                        cookDifficulty: recipe.cookDifficulty,
                        cookTasty: 0
                    }
                }
        })
        .then(function(answer) {
            answer.userId = user.name;
            console.log(answer);
            RecipeService.registerFeedback( recipe.id, answer)
                .then(function (recipe) {
                    $mdToast.show($mdToast.simple().textContent('Feedback salvo com sucesso!'));
                }).catch(function (reason) {
                    $mdToast.show($mdToast.simple().textContent('"Erro ao registrar a feedback.'));
            });
        }, function() {}
        );
    }

    function DialogController($scope, $mdDialog, feedback, recipe) {
        var vm = $scope;
        vm.recipe = recipe;
        vm.feedback = feedback;

        vm.cancel = cancel;
        vm.answer = answer;

        function cancel() {
            $mdDialog.cancel();
        };

        function answer(answer) {
            $mdDialog.hide(answer);
        };
    };
});