'use strict';

foodbookApp.controller('EditUserController', function ( user, UserService, $scope, $location ) {
    var ctrl = this;
    var vm = $scope;
    $scope.updateUser = saveUser;
    $scope.goBack = goBack;
    var userUpdateData = {};
    userUpdateData.email = user.principal.email;
    userUpdateData.phone = parseInt(user.principal.phone, 10);
    userUpdateData.changePassword = false;
    userUpdateData.username = user.principal.username;
    userUpdateData.creatorName = user.principal.username;
    vm.user = userUpdateData;

    function goBack()
    {
        $location.path("/myprofile");
    }

    function saveUser( user )
    {
        clearMessages();
        UserService.updateUser( user.username, user )
            .then( function (){
                $scope.message = "Perfil atualizado com sucesso";
                console.log("Perfil salvo com sucesso!");
            }).catch(function (reason) {
                $scope.error = reason.data;
                console.log(reason.data);
        })
    }

    function clearMessages() {
        $scope.message = null;
        $scope.error = null;
    }
});