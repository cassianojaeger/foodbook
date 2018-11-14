"use strict";

foodbookApp.controller('TopbarController', function ($location) {
    var vm = this;

    vm.search = search;
    vm.searchedName = "";

    function search() {
        if(vm.searchedName != "")
            $location.path("/search/"+ vm.searchedName + "/recipe");
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