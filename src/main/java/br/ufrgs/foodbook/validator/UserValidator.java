package br.ufrgs.foodbook.validator;

import br.ufrgs.foodbook.model.security.User;

public interface UserValidator
{
    void validate(User user);
}
