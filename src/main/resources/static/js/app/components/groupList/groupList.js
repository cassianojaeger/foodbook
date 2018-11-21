'use strict';

foodbookApp.directive('groupList', function () {
    return {
        templateUrl: "js/app/components/groupList/groupList.html",
        controller: ControllerFn,
        controllerAs: "vm",
        scope: {
            groups: "="
        }
    };

    function ControllerFn($location) {
        var vm = this;
        vm.goToGroup = goToGroup;

        function goToGroup(id) {
            $location.path("/groups/" + id);
        }
    }
});