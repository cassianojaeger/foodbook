'use strict';

foodbookApp.service('RecipeService', function ($resource) {
    var resource = $resource('/secured/recipe/:name', {}, {
        create: {method: "POST"},
        get: {method: "GET"},
        getByName: {method: "GET", params: {name: "@name"}},
        getByGroup: {url: "/secured/recipe/group/:name", method: "GET", params: {name: "@name"}, isArray: true}
    });

    var service = this;
    service.create = create;
    service.get = get;
    service.getAll = getAll;
    service.getGroupRecipes = getGroupRecipes;


    function create(recipe) {
        return resource.create(recipe).$promise;
    }

    function get(name) {
        return resource.getByName({name: name}).$promise;
    }

    function getAll(params) {
        return resource.get(params).$promise;
    }

    function getGroupRecipes(groupName) {
        return resource.getByGroup({name: groupName}).$promise;
    }

});