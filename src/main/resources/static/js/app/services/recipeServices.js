'use strict';

foodbookApp.service('RecipeService', function ($resource) {
    var resource = $resource('/secured/recipe/:id', {}, {
        create: {method: "POST"},
        update: {url: "/secured/recipe/update", method: "PUT"},
        delete: {url: "/secured/recipe/remove", method: "DELETE"},
        get: {method: "GET"},
        getById: {method: "GET", params: {id: "@id"}},
        getByGroup: {url: "/secured/recipe/group/:id", method: "GET", params: {id: "@id"}, isArray: true}
    });

    var service = this;
    service.save = save;
    service.get = get;
    service.getAll = getAll;
    service.getGroupRecipes = getGroupRecipes;
    service.delete = deleteFn;

    function save(recipe) {
        return recipe.id? update(recipe) : create(recipe);
    }

    function update(recipe) {
        recipe.recipeId = recipe.id;
        return resource.update(recipe).$promise;
    }

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

    function deleteFn(recipeId) {
        return resource.delete({recipeId: recipeId}).$promise;
    }

});