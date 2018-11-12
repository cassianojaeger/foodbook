'use strict';

foodbookApp.directive('userList', function () {
    return {
        templateUrl: "js/app/components/userList/userList.html",
        controller: ControllerFn,
        controllerAs: "vm",
        scope: {
            members: "="
        }
    };

    function ControllerFn($location) {
        var vm = this;
        vm.gotToUser = goToUser;

        function getUsername(member) {
            return member.username;
        }

        function goToUser(member) {
            $location.path("/user/" + getUsername(member));
        }
    }
});