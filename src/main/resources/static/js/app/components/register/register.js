'use strict';

foodbookApp.controller('RegisterController', function (UserService, $location) {
    var ctrl = this;

    ctrl.register = register;
    ctrl.goBack = goBack;

    function register(user) {
        UserService
            .createUser(user)
            .then(function (value) {
                debugger
            })
    }

    function goBack() {
        $location.path("/login");
    }
});