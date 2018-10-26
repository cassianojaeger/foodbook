'use strict';

foodbookApp.service('GroupService', function ($q, $resource) {
    var resource = $resource('/secured/group/:name', {}, {
        create: {method: "POST"},
        get: {method: "GET"},
        getByName: {method: "GET", params: {name: "@name"}}
    });

    var service = this;


    service.getUserGroups = getUserGroups;
    service.get = get;
    service.create = create;

    function getUserGroups(params) {
        return resource.get(params).$promise;
    }

    function get(name) {
        return resource.getByName({name: name}).$promise;
    }

    function create(group) {
        return resource.create(group).$promise;
    }
});