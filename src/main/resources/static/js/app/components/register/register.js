'use strict';

foodbookApp.controller('RegisterController', function (UserService, $location) {
    var ctrl = this;

    ctrl.register = register;

    function register(user) {
        UserService
            .createUser(user)
            .then(function (value) {
                debugger
            })
    }
});