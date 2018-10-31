'use strict';

foodbookApp.service('RecipeService', function ($resource) {
    var resource = $resource('/secured/recipe/:id', {}, {
        create: {method: "POST"},
        get: {method: "GET"},
        getById: {method: "GET", params: {id: "@id"}},
        getByGroup: {url: "/secured/recipe/group/:id", method: "GET", params: {id: "@id"}, isArray: true}
    });

    var service = this;
    service.create = create;
    service.get = get;
    service.getAll = getAll;
    service.getGroupRecipes = getGroupRecipes;


    function create(recipe) {
        return resource.create(recipe).$promise;
    }

    function get(id) {
        return resource.getById({id: id}).$promise;
    }

    function getAll(params) {
        return resource.get(params).$promise;
    }

    function getGroupRecipes(groupId) {
        return resource.getByGroup({id: groupId}).$promise;
    }

});