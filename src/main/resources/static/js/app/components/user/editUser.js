'use strict';

foodbookApp.controller('EditUserController', function ( user, UserService, $scope, $location ) {
    var ctrl = this;
    var vm = $scope;
    $scope.updateUser = saveUser;
    $scope.goBack = goBack;
    vm.user = user;
    var user2 = {};
    user.principal.phone = parseInt(user.principal.phone, 10);
    function goBack()
    {
        $location.path("/myprofile");
    }

    function saveUser( user )
    {
        clearMessages();
        user2.email = user.principal.email;
        user2.phone = user.principal.phone;
        user2.changePassword = false;
        user2.username = user.principal.username;
        user2.creatorName = user.principal.username;
        console.log(user2);
        UserService.updateUser( user.principal.username, user2 )
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