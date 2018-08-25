'use strict';

foodbookApp.directive('foodbookApp', function (UserService) {
    return {
        controller: ControllerFn,
        controllerAs: 'vm',
        templateUrl: 'js/app/components/foodbookApp/foodbookApp.html'
    };

    function ControllerFn() {
        var ctrl = this;

        ctrl.getUser = getUser;

        function getUser() {
            UserService.getUser().then(function (value) {
                debugger;
            });
        }
    }
});