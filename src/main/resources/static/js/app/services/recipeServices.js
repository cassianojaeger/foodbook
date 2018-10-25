'use strict';

foodbookApp.service('RecipeService', function ($resource) {
    var resource = $resource('/secured/recipe', {}, {
        create: {method: "POST"}
    });

    var service = this;
    service.create = create;


    function create(recipe) {
        return resource.create(recipe).$promise;
    }

});