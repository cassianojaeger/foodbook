'use strict';

foodbookApp
    .constant('OAUTH', {
        clientId: 'spring-security-oauth2-read-write-client',
        clientSecret: 'spring-security-oauth2-read-write-client-password1234',
        scope: 'read write'
    })
    .constant('TIME', [
        {code: "SECONDS", name: "Segundos"},
        {code: "MINUTES", name: "Minutos"},
        {code: "HOURS", name: "Horas"}
    ]);