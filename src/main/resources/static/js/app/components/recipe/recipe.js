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

    function openMadeRecipeDialog(ev, recipe, update) {
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
                            //LISTA COM TIME VALUES
                            timeType: recipe.cookTime.timeType
                        },
                        cookDifficulty: recipe.cookDifficulty,
                        cookTastyness: 0
                    },
                    update: update,
                    recipeService: RecipeService,
                    timeOptions: TIME
                }
        })
        .then(function(answer) {
            answer.username = user.name;
            answer.func( recipe.id, answer)
                .then(function (recipe) {
                    $mdToast.show($mdToast.simple().textContent('Feedback salvo com sucesso!'));
                }).catch(function (reason) {
                    $mdToast.show($mdToast.simple().textContent('"Erro ao registrar a feedback.'));
            });
        }, function() {}
        );
    }

    function DialogController($scope, $mdDialog,
                              feedback,
                              recipe,
                              update,
                              recipeService,
                              timeOptions) {
        var vm = $scope;
        vm.recipe = recipe;
        vm.feedback = feedback;
        vm.update = update;
        vm.recipeService = recipeService;
        vm.timeOptions = timeOptions;

        vm.cancel = cancel;
        vm.answer = answer;

        function cancel() {
            $mdDialog.cancel();
        }

        function answer(answer, func) {
            answer.func = func;
            $mdDialog.hide(answer);
        }
    };
});