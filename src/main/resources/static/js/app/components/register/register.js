'use strict';

foodbookApp.controller('RegisterController', function (UserService, $location) {
    var ctrl = this;

    ctrl.register = register;
    ctrl.goBack = goBack;

    function register(user) {
        clearMessages();
        if (passwordsMatch(user)) {
            UserService
                .createUser(user)
                .then(function (user) {
                    ctrl.message = "Usuário " + user.username + "cadastrado com sucesso!";
                })
                .catch(function (reason) {
                    ctrl.error = reason.data.password;
                })
        }
        else {
            ctrl.error = "As senhas não concidem!";
        }
    }

    function clearMessages() {
        ctrl.message = null;
        ctrl.error = null;
    }

    function passwordsMatch(user) {
        return user.password === user.confirmedPassword;
    }

    function goBack() {
        $location.path("/login");
    }
});