"use strict";

foodbookApp.controller('TopbarController', function ($location) {
    var vm = this;

    vm.search = search;
    vm.searchedName = "";
    vm.searchedType = "group";

    function search() {
        if(vm.searchedName !== "") {
            if(vm.searchedType === "recipe" )
                $location.path("/search/"+ vm.searchedName + "/recipe");
            else
                $location.path("/search/"+ vm.searchedName + "/group");
        }
    }
});

foodbookApp.directive('topbar', function () {
    return {
        templateUrl: 'js/app/components/topbar/topbar.html',
        controller: 'TopbarController',
        controllerAs: 'vm',
        restrict: 'E'
    };
});