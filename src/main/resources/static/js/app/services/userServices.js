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

foodbookApp.service('AuthenticationService', function ($resource,
                                                       $http,
                                                       AccessTokenService,
                                                       OAUTH) {

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
            AccessTokenService.setToken(response.data);
        });
    }

    function logout() {

    }

    function isUserAuthenticated() {
        return AccessTokenService.isTokenExpired();
    }
});

foodbookApp.service('AccessTokenService', function ($localStorage) {
    var service = this;
    var accessToken = null;
    service.isTokenExpired = isTokenExpired;
    service.setToken = setToken;
    service.getToken = getToken;

    function setToken(token) {
        accessToken = token;
        accessToken.expires_at = new Date().getTime() + token.expires_in;
        $localStorage['token'] = token;
    }

    function isTokenExpired() {
        return accessToken.expires_at >= new Date().getTime();
    }

    function getToken() {
        return accessToken;
    }
});