'use strict';

foodbookApp.service('GroupService', function ($q) {
    var service = this;

    service.getUserGroups = getUserGroups;
    service.get = get;

    function getUserGroups(params) {
        return $q(function (resolve, reject) {
             resolve([
                {id : 1, name: "Vegans group", description: "This is a group for vegans"},
                {id : 2, name: "Meat group", description: "This is a group for meat lovers"},
                {id : 2, name: "Carb group", description: "For all pizza lovers"}
            ]);
        });
    }

    function get(groupId) {
        return $q(function (resolve, reject) {
            resolve(
                {
                    id : groupId,
                    name: "Carne",
                    description: "This is the default app group",
                    admin: {
                        name: "Pimenta"
                    }
                }
            );
        });
    }
});