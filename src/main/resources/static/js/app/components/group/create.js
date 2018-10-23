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
        console.log(group);
        if (group && Object.prototype.hasOwnProperty.call(group, "name") && Object.prototype.hasOwnProperty.call(group, "description") ) {
            GroupService
                .create(group, 7890) //TODO: create method to get the logged uder id
                .then(function (group) {
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

    function clearMessages() {
        ctrl.message = null;
        ctrl.error = null;
    }

});