package br.ufrgs.foodbook.validator.impl;

import br.ufrgs.foodbook.exception.InvalidUserDataException;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.validator.UserValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

@Component
public class FoodbookUserValidator implements UserValidator
{
    EmailValidator emailValidator;

    @Override
    public void validate(User user)
    {
        //TODO: ADICIONAR AS OUTRAS VALIDAÇÕES
        throw new InvalidUserDataException("");
    }
}
