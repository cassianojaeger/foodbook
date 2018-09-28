"use strict";

foodbookApp.controller('LoginController', function (UserService, AuthenticationService, $location) {
    var ctrl = this;
    ctrl.user = {};

    this.login = function (user) {
        AuthenticationService
            .login(user)
            .then(function (value) {
                $location.path("/");
            })
            .catch(function (reason) {
                ctrl.error = "Usuário ou senha inválida!";
            })
    };
});