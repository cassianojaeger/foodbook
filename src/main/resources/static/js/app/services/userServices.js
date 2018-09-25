'use strict';

foodbookApp.service('UserService', function ($resource) {
    var resource = $resource('/secured/user', {}, {
        create: {method: "POST"}
    });
    var service = this;

    service.getUser = getUser;
    service.createUser = createUser;

    function getUser() {
        return resource.get().$promise;
    }

    function createUser(user) {
        return resource.create(user).$promise
    }
});

foodbookApp.service('AuthenticationService', function ($resource, $http , OAUTH) {

    var service = this;

    service.isUserAuthenticated = isUserAuthenticated;
    service.login = login;
    service.logout = logout;

    function login(user) {
        var params = "client_id=" + OAUTH.clientId + "&scope="+ OAUTH.scope + "&grant_type=password";
        params += "&username=" + user.username + "&password="+ user.password;
        return $http.post('oauth/token', params, {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                Accept: "application/json",
                Authorization: "Basic " + btoa(OAUTH.clientId + ":" + OAUTH.clientSecret)
            },
            ignoreAuthModule: "ignoreAuthModule"
        }).then(function (response) {
            httpHeaders.common['Authorization'] = "Bearer " + response.data.access_token;
        });
    }

    function logout() {

    }

    function isUserAuthenticated() {
        return true;
    }
});