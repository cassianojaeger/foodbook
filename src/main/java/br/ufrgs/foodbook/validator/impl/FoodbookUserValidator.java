package br.ufrgs.foodbook.validator.impl;

import br.ufrgs.foodbook.dto.user.UserRegistrationData;
import br.ufrgs.foodbook.exception.InvalidUserRegistrationException;
import br.ufrgs.foodbook.service.impl.FoodbookUserService;
import br.ufrgs.foodbook.validator.Validator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FoodbookUserValidator implements Validator<UserRegistrationData>
{
    @Resource
    FoodbookUserService foodbookUserService;

    private static final String EMAIL_FIELD = "email";
    private static final String CONFIRMED_PASSWORD_FIELD = "confirmedPassword";
    private static final String PASSWORD_FIELD = "password";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";
    private static final String USERNAME_FIELD = "username";

    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Override
    public void validate(UserRegistrationData user)
    {
        validateAlreadyTakenUsername(user.getUsername());
        validateEmail(user.getEmail());
        validatePasswordRegex(user.getPassword());
        validatePasswordEquality(user.getPassword(), user.getConfirmedPassword());
    }

    private void validateAlreadyTakenUsername(String username)
    {
        if(foodbookUserService.isUsernameAlreadyTaken(username))
            throw new InvalidUserRegistrationException(USERNAME_FIELD, "Nome de usuário já existente");
    }

    private void validateEmail(String email)
    {
        if(!emailValidator.isValid(email))
            throw new InvalidUserRegistrationException(EMAIL_FIELD, "Email inválido");
    }

    private void validatePasswordRegex(String password)
    {
        if(!password.matches(PASSWORD_REGEX))
            throw new InvalidUserRegistrationException(PASSWORD_FIELD, "Senha não é válida");
    }

    private void validatePasswordEquality(String password, String confirmedPassword)
    {
        if(!password.equalsIgnoreCase(confirmedPassword))
            throw new InvalidUserRegistrationException(CONFIRMED_PASSWORD_FIELD, "Senhas não coincidem");
    }
}
