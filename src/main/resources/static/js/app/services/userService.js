'use strict';

foodbookApp.service('UserService', function ($resource) {
    var resource = $resource('/user');
    var service = this;

    service.getUser = getUser;

    function getUser() {
        return resource.get().$promise;
    }
});