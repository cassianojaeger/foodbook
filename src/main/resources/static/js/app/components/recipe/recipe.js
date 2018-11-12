'use strict';

foodbookApp.controller('RecipeController', function (recipe, group, user, $scope, TIME, $mdDialog,
                                                     RecipeService, $mdToast, $timeout, $location) {
    var vm = $scope;
    recipe.cookTime.timeType = TIME.find(function (time) {return time.code === recipe.cookTime.timeType}).name;
    vm.recipe = recipe;
    vm.recipe.ingredients = vm.recipe.ingredients.replace(/↵/g, "\n");
    vm.group = group;
    vm.isOwnRecipe = recipe.creator.username === user.name;

    RecipeService.getFeedbacks(recipe.id)
        .then(function (feedbacks) {
            vm.alreadyDidIt = feedbacks.usernames.includes(user.name);
            vm.feedbackTime = "em media " + feedbacks.cookTime.timeValue + " " + feedbacks.cookTime.timeType;
            vm.feedbackDifficulty = "em media " + feedbacks.cookDifficulty + " de 5";
            vm.feedbackTastyness = "Nivel de sabor: " + feedbacks.cookTastyness + " de 5";
        });

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
                        cookTime: {
                            timeValue: recipe.cookTime.timeValue,
                            timeType: recipe.cookTime.timeType
                        },
                        cookDifficulty: recipe.cookDifficulty,
                        cookTastyness: 0
                    }
                }
        })
        .then(function(answer) {
            answer.username = user.name;
            if(recipe.cookTime.timeType === "Segundos") answer.cookTime.timeType = "SECONDS";
            if(recipe.cookTime.timeType === "Minutos") answer.cookTime.timeType = "MINUTES";
            if(recipe.cookTime.timeType === "Horas") answer.cookTime.timeType = "HOURS";
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