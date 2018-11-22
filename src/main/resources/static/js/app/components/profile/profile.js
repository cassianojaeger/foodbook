'use strict';

foodbookApp.controller('ProfileController', function (user, $scope) {
    var vm = $scope;

    vm.user = user.principal;
    delete user.principal.password;
    user.principal.phone = parseInt(user.principal.phone);
});