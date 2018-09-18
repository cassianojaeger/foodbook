"use strict";

foodbookApp.controller('LoginController', function (UserService) {
    var ctrl = this;
    ctrl.user = {};

    this.login = function (user) {
      console.log(user);
    };
});