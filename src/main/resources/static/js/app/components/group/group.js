'use strict';

foodbookApp.controller('GroupController', function (group, recipes, user, GroupService) {
    var vm = this;
    vm.group = group;
    vm.recipes = recipes;
    vm.user = user;
    vm.isGroupOwner = group.administrator.username === user.name;
    vm.isGroupMember = false;

    vm.enterGroup = enterGroup;

    function enterGroup(user, group) {
        GroupService.addMember({groupId: group.id, memberName: user.name})
            .then(function () {
                debugger
            });
    }
});