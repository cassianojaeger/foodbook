'use strict';

foodbookApp.service('RecipeService', function ($resource) {
    var resource = $resource('/secured/recipe/:name', {}, {
        create: {method: "POST"},
        get: {method: "GET"},
        getByName: {method: "GET", params: {name: "@name"}}
    });

    var service = this;
    service.create = create;
    service.get = get;
    service.getGroupRecipes = getGroupRecipes;


    function create(recipe) {
        return resource.create(recipe).$promise;
    }

    function get(name) {
        return resource.get(name).$promise;
    }

    function getGroupRecipes(params) {
        return resource.get(params).$promise;
    }

});