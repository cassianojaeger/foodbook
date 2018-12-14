'use strict';

foodbookApp.controller('GroupFormController', function ($scope, group, GroupService, $location) {

    $scope.goBack = goBack;
    $scope.saveGroup = saveGroup;
    $scope.group = group;
    $scope.newGroup = !group.id;

    function goBack() {
        $location.path("/home");
    }

    function saveGroup(group) {
        clearMessages();
        if (isFieldsFilled(group)) {
            if ($scope.newGroup) {
                GroupService
                    .create(group)
                    .then(function () {
                        $scope.message = "Grupo " + group.name + " salvo com sucesso!";
                    })
                    .catch(function (reason) {
                        $scope.error = reason.data;
                    })
            } else {
                GroupService
                    .update(group)
                    .then(function () {
                        $scope.message = "Grupo " + group.name + " salvo com sucesso!";
                    })
                    .catch(function (reason) {
                        $scope.error = reason.data;
                    })
            }

        }
        else {
            $scope.error = "Todos campos são obrigatórios!";
        }
    }

    function isFieldsFilled(group) {
        return group && Object.prototype.hasOwnProperty.call(group, "name") && Object.prototype.hasOwnProperty.call(group, "description");
    }

    function clearMessages() {
        $scope.message = null;
        $scope.error = null;
    }

});