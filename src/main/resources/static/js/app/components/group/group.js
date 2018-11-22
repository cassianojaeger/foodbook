'use strict';

foodbookApp.controller('GroupController', function (group,
                                                    user,
                                                    GroupService,
                                                    RecipeService,
                                                    $mdToast,
                                                    $scope) {
    var vm = $scope;
    vm.group = group;
    vm.user = user;
    vm.isGroupOwner = group.administrator.username === user.name;
    vm.isGroupMember = group.members.some(function (member) {return member.username === user.name}) && !vm.isGroupOwner;

    $scope.$on('recipe-pagination', function (event, params) {
        RecipeService.getGroupRecipes(group.id, params)
            .then(function (response) {
                vm.recipes = response;
            });
    });

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