'use strict';

foodbookApp.controller('GroupSearchController', function ($scope, groupName, GroupService) {
    var vm = $scope;
    vm.groups = [];

    GroupService.search(groupName).then(function (groups) {
        vm.groups = groups;
    });
});