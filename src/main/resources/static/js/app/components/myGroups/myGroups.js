'use strict';

foodbookApp.directive('myGroups', function (GroupService, $location) {
    return {
        templateUrl: "js/app/components/myGroups/myGroups.html",
        controller: ControllerFn,
        controllerAs: "vm"
    };

    function ControllerFn() {
        var vm = this;
        vm.goToGroup = goToGroup;

        GroupService.getUserGroups().then(function (groups) {
            vm.groups = groups;
        });

        function goToGroup(groupId) {
            $location.path("/groups/" + groupId);
        }
    }
});