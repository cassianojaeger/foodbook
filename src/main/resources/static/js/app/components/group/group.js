'use strict';

foodbookApp.controller('GroupController', function (group, recipes) {
    var vm = this;
    vm.group = group;
    vm.recipes = recipes;
});