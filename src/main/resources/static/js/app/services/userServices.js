'use strict';

foodbookApp.service('UserService', function ($resource) {
    var resource = $resource('/secured/user', {}, {
        create: {method: "POST"},
        update: {url: "/secured/user/:userName/updateUser", params: {userName: "@userName" },method: "PUT"}
    });
    var service = this;

    service.getLoggedUser = getLoggedUser;
    service.createUser = createUser;
    service.updateUser = updateUser;

    function getLoggedUser() {
        return resource.get().$promise;
    }

    function createUser(user) {
        return resource.create(user).$promise;
    }

    function updateUser( userName, user )
    {
        return resource.update({userName: userName}, user).$promise;
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
            AccessTokenService.setToken(response.data);
        });
    }

    function logout() {
        AccessTokenService.expireToken();
        delete httpHeaders.common['Authorization'];
    }

    function isUserAuthenticated() {
        if (AccessTokenService.getToken()) {
            AccessTokenService.setToken(AccessTokenService.getToken());
        }
        return !AccessTokenService.isTokenExpired();
    }
});

foodbookApp.service('AccessTokenService', function ($localStorage, $window) {
    var service = this;
    var accessToken = null;
    service.isTokenExpired = isTokenExpired;
    service.setToken = setToken;
    service.getToken = getToken;
    service.expireToken = expireToken;
    $window.forceLogout = expireToken;

    function setToken(token) {
        httpHeaders.common['Authorization'] = "Bearer " + token.access_token;
        accessToken = token;
        accessToken.expires_at = new Date().getTime() + (token.expires_in * 1000);
        $localStorage['token'] = token;
    }

    function isTokenExpired() {
        var token = getToken();
        if (token)
            return token.expires_at <= new Date().getTime();
        else
            return true;
    }

    function expireToken() {
        accessToken = null;
        delete $localStorage.token;
    }

    function getToken() {
        accessToken = accessToken || $localStorage.token;
        return accessToken;
    }
});