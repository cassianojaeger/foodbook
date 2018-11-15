'use strict';

foodbookApp.controller('UserInformationController', function ( user, UserService, $scope, $location ) {
    var ctrl = this;
    var vm = $scope;
    vm.user = user;
    vm.goBack = goBack;

    function goBack()
    {
        $location.path("/home");
    }

});