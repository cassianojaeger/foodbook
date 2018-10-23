'use strict';

foodbookApp.controller('CreateGroupController', function (GroupService, $location) {
    var ctrl = this;

    ctrl.goBack = goBack;
    ctrl.createGroup = createGroup;

    function goBack() {
        $location.path("/home");
    }

    function createGroup(group) {
        clearMessages();
        if (isFieldsFilled(group)) {
            GroupService
                .create(group, 7890) //TODO: create method to get the logged user id
                .then(function () {
                    ctrl.message = "Grupo " + group.name + " criado com sucesso!";
                })
                .catch(function (reason) {
                    ctrl.error = reason.data;
                })
        }
        else {
            ctrl.error = "Todos campos são obrigatórios!";
        }
    }

    function isFieldsFilled(group) {
        return group && Object.prototype.hasOwnProperty.call(group, "name") && Object.prototype.hasOwnProperty.call(group, "description");
    }

    function clearMessages() {
        ctrl.message = null;
        ctrl.error = null;
    }

});