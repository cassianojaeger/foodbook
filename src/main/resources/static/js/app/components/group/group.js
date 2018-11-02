'use strict';

foodbookApp.controller('GroupController', function (group, user, GroupService, $mdToast) {
    var vm = this;
    vm.group = group;
    vm.recipes = group.recipes;
    vm.user = user;
    vm.isGroupOwner = group.administrator.username === user.name;
    vm.isGroupMember = group.members.some(function (member) {return member.username === user.name});


    vm.enterGroup = enterGroup;
    vm.leaveGroup = leaveGroup;

    function enterGroup(user, group) {
        GroupService.addMember({groupId: group.id, memberName: user.name})
            .then(function () {
                vm.isGroupMember = true;
                $mdToast.show($mdToast.simple().textContent('Usuário adicionado ao grupo com sucesso!'));
            });
    }

    function leaveGroup(user, group) {
        GroupService.removeMember({groupId: group.id, memberName: user.name})
            .then(function () {
                vm.isGroupMember = false;
                $mdToast.show($mdToast.simple().textContent('Usuário removido do grupo com sucesso!'));
            });
    }
});