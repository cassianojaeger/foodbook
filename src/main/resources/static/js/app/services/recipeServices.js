'use strict';

foodbookApp.service('RecipeService', function ($resource) {
    var resource = $resource('/secured/recipe/:id', {}, {
        create: {method: "POST"},
        registerFeedback: {url: "/secured/manage/recipe/:recipeId/giveFeedback", params: {recipeId: "@recipeId"}, method: "POST"},
        isFavorite: {url: "/secured/manage/recipe/:recipeId/isFavorite", params: {recipeId: "@recipeId"}, method: "GET"},
        addFavorite: {url: "/secured/manage/recipe/:recipeId/addFavorite", params: {recipeId: "@recipeId"}, method: "POST"},
        removeFavorite: {url: "/secured/manage/recipe/:recipeId/removeFavorite", params: {recipeId: "@recipeId"}, method: "DELETE"},
        updateFeedback: {url: "/secured/manage/recipe/:recipeId/updateFeedback", params: {recipeId: "@recipeId"}, method: "PUT"},
        update: {url: "/secured/recipe/update", method: "PUT"},
        delete: {url: "/secured/recipe/remove", method: "DELETE"},
        get: {method: "GET"},
        getById: {method: "GET", params: {id: "@id"}},
        getFeedbacks: {method: "GET", url: "/secured/manage/recipe/:recipeId/getFeedbacks", params: {id: "@id"}},
        getByGroup: {url: "/secured/recipe/group/:id", method: "GET", params: {id: "@id"}},
        getFavoriteRecipes: {method: "GET", url: "/secured/user/:username/getFavorites", params: {username: "@username"}},
        search: {url: "/secured/recipe/search/:name", method: "GET", params: {name: "@name"}}
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
    service.addFavorite = addFavorite;
    service.isFavorite = isFavorite;
    service.removeFavorite = removeFavorite;
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

    function getGroupRecipes(groupId, params) {
        return resource.getByGroup({id: groupId, page: params.page, size: params.size}).$promise;
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

    function search(name, params) {
        return resource.search({name: name, page: params.page, size: params.size}).$promise;
    }

    function getFavoriteRecipes(username, params) {
        return resource.getFavoriteRecipes({username: username, page: params.page, size: params.size}).$promise;
    }

    function isFavorite(recipeId) {
        return resource.isFavorite({recipeId: recipeId}).$promise;
    }

    function addFavorite(recipeId) {
        return resource.addFavorite({recipeId: recipeId}).$promise;
    }

    function removeFavorite(recipeId) {
        return resource.removeFavorite({recipeId: recipeId}).$promise;
    }

});