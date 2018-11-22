'use strict';

foodbookApp.directive('recipeList', function () {
    return {
        templateUrl: "js/app/components/recipeList/recipeList.html",
        controller: ControllerFn,
        controllerAs: "vm",
        scope: {
            recipes: "="
        }
    };

    function ControllerFn($location, $scope) {
        var vm = this;
        vm.goToRecipe = goToRecipe;
        vm.numOfPages = numOfPages;
        vm.currentPage = 1;
        vm.maxSize = 5;

        $scope.$watch('recipes', function (newVal, oldVal) {
            if ($scope.recipes) {
                vm.currentPage = ($scope.recipes.number || 0) + 1;
                vm.maxSize = $scope.recipes.size;
            }
        });

        $scope.$watch(function () {
            return vm.currentPage;
        }, function(newVal, oldVal) {
            $scope.$emit('recipe-pagination', {page: newVal - 1, size: ($scope.recipes && $scope.recipes.size) || vm.maxSize});
        });

        function getGroupId(recipe) {
            return recipe.group? recipe.group.id : recipe.groupId;
        }

        function goToRecipe(recipe) {
            $location.path("/groups/" + getGroupId(recipe) + "/recipes/" + recipe.id);
        }

        function numOfPages() {
            return ($scope.recipes && $scope.recipes.totalPages) || 1;
        }
    }
});