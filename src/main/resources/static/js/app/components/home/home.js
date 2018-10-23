"use strict";

foodbookApp.controller('HomeController', function ($location) {
    var ctrl = this;

    ctrl.goToCreateGroup = goToCreateGroup;

    function goToCreateGroup() {
        $location.path("/group/create");
    };

});