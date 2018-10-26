'use strict';

foodbookApp.directive('myGroups', function (GroupService, $location) {
    return {
        templateUrl: "js/app/components/myGroups/myGroups.html",
        controller: ControllerFn,
        controllerAs: "vm",
        scope: {}
    };

    function ControllerFn() {
        var vm = this;
        vm.goToGroup = goToGroup;

        GroupService.getUserGroups().then(function (response) {
            vm.groups = response.content;
        });

        function goToGroup(name) {
            $location.path("/groups/" + name);
        }
    }
});