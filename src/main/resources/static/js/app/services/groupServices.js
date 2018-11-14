'use strict';

foodbookApp.service('GroupService', function ($q, $resource) {
    var resource = $resource('/secured/group/:id', {}, {
        create: {method: "POST"},
        get: {method: "GET"},
        getById: {method: "GET", params: {name: "@id"}},
        addMember: {url: "/secured/manage/group/addMember", method: "POST"},
        removeMember: {url: "/secured/manage/group/removeMember", method: "POST"},
        search: {url: "/secured/group/search/:name", method: "GET", params: {name: "@name"}, isArray: true}
    });

    var service = this;


    service.getUserGroups = getUserGroups;
    service.get = get;
    service.create = create;
    service.addMember = addMember;
    service.removeMember = removeMember;
    service.search = search;

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

    function removeMember(request) {
        return resource.removeMember(request).$promise;
    }

    function search(name) {
        return resource.search({name: name}).$promise;
    }
});