'use strict';

foodbookApp.controller('ChangePasswordController', function ( user, UserService, $scope, $location ) {
    var ctrl = this;
    var vm = $scope;
    $scope.changePassword = changePassword;
    $scope.goBack = goBack;
    console.log(user);
    vm.user = user;
    var userData = {};
    userData.username = user.principal.username;
    userData.email = user.principal.email;
    userData.phone = user.principal.phone;
    userData.changePassword = true;
    $scope.userData = userData;

    function goBack()
    {
        $location.path("/myprofile");
    }

    function changePassword( user )
    {
        clearMessages();
        console.log(user);
        if (user.password == user.confirmedPassword)
        {
            UserService.updateUser( user.username, user )
                .then( function (){
                    $scope.message = "Perfil atualizado com sucesso";
                    console.log("Perfil salvo com sucesso!");
                }).catch(function (reason) {
                $scope.error = reason.data;
                console.log(reason.data);
            })
        } else
        {
            console.log("senhas diferentes" + user.password + user.confirmedPassword );
        }
    }

    function clearMessages() {
        $scope.message = null;
        $scope.error = null;
    }
});