'use strict';

foodbookApp.controller('GroupFormController', function (group, GroupService, $location) {
    var ctrl = this;

    ctrl.goBack = goBack;
    ctrl.saveGroup = saveGroup;
    ctrl.group = group;
    ctrl.newGroup = !!group.id;

    function goBack() {
        $location.path("/home");
    }

    function saveGroup(group) {
        clearMessages();
        if (isFieldsFilled(group)) {
            GroupService
                .create(group)
                .then(function () {
                    ctrl.message = "Grupo " + group.name + " salvo com sucesso!";
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