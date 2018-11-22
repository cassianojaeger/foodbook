'use strict';

foodbookApp.service('RecipeService', function ($resource) {
    var resource = $resource('/secured/recipe/:id', {}, {
        create: {method: "POST"},
        registerFeedback: {url: "/secured/manage/recipe/:recipeId/giveFeedback", params: {recipeId: "@recipeId"}, method: "POST"},
        updateFeedback: {url: "/secured/manage/recipe/:recipeId/updateFeedback", params: {recipeId: "@recipeId"}, method: "PUT"},
        update: {url: "/secured/recipe/update", method: "PUT"},
        delete: {url: "/secured/recipe/remove", method: "DELETE"},
        get: {method: "GET"},
        getById: {method: "GET", params: {id: "@id"}},
        getFeedbacks: {method: "GET", url: "/secured/manage/recipe/:recipeId/getFeedbacks", params: {id: "@id"}},
        getByGroup: {url: "/secured/recipe/group/:id", method: "GET", params: {id: "@id"}, isArray: true},
        getFavoriteRecipes: {method: "GET", url: "/secured/user/:username/getFavorites", params: {username: "@username"}},
        search: {url: "/secured/recipe/search/:name", method: "GET", params: {name: "@name"}, isArray: true}
    });

    var service = this;
    service.save = save;
    service.get = get;
    service.getAll = getAll;
    service.getGroupRecipes = getGroupRecipes;
    service.delete = deleteFn;
    service.registerFeedback = registerFeedback;
    service.updateFeedback = updateFeedback;
    service.getFeedbacks = getFeedbacks;
    service.getFavoriteRecipes = getFavoriteRecipes;
    service.search = search;

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

    function registerFeedback(recipeId, feedback) {
        return resource.registerFeedback({recipeId: recipeId}, feedback).$promise;
    }

    function updateFeedback(recipeId, feedback) {
        return resource.updateFeedback({recipeId: recipeId}, feedback).$promise;
    }

    function getFeedbacks(id) {
        return resource.getFeedbacks({recipeId: id}).$promise;
    }

    function search(name) {
        return resource.search({name: name}).$promise;
    }

    function getFavoriteRecipes(username) {
        return resource.getFavoriteRecipes({username: username}).$promise;
    }

});