'use strict';

foodbookApp.service('GroupService', function ($q, $resource) {
    var resource = $resource('/secured/group/:id', {}, {
        create: {method: "POST"},
        get: {method: "GET"},
        getById: {method: "GET", params: {name: "@id"}},
        addMember: {url: "/secured/manage/group/addMember", method: "POST"}
    });

    var service = this;


    service.getUserGroups = getUserGroups;
    service.get = get;
    service.create = create;
    service.addMember = addMember;

    function getUserGroups(params) {
        return resource.get(params).$promise;
    }

    function get(id) {
        return resource.getById({id: id}).$promise;
    }

    function create(group) {
        return resource.create(group).$promise;
    }

    function addMember(request) {
        return resource.addMember(request).$promise;
    }
});