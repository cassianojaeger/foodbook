package br.ufrgs.foodbook.validator.impl;

import br.ufrgs.foodbook.configuration.encrypt.Encoders;
import br.ufrgs.foodbook.dao.UserDao;
import br.ufrgs.foodbook.dto.user.UserRegistrationData;
import br.ufrgs.foodbook.dto.user.UserUpdateData;
import br.ufrgs.foodbook.exception.InvalidRegistrationException;
import br.ufrgs.foodbook.model.security.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FoodbookUserUpdateValidator extends FoodbookUserValidator
{
    private static final String OLD_PASSWORD = "oldPassword";
    private static final String OLD_PASSWORD_FIELD_ERROR_MESSAGE = "Sua senha antiga está incorreta";
    @Resource
    UserDao userDao;
    @Resource
    private Encoders encoders;

    @Override
    public void validate(UserRegistrationData user)
    {
        User originalUser = userDao.findByUsername(user.getUsername());

        UserUpdateData userUpdate = ((UserUpdateData) user);
        validateEmail(user.getEmail());

        if(userUpdate.getChangePassword())
        {
            validatePasswordEquality(user.getPassword(), user.getConfirmedPassword());
            validatePasswordRegex(user.getPassword());

            if(!isOldPasswordValid(userUpdate, originalUser))
                throw new InvalidRegistrationException(OLD_PASSWORD, OLD_PASSWORD_FIELD_ERROR_MESSAGE);
        }

    }

    private boolean isOldPasswordValid(UserUpdateData userData, User originalUser)
    {
        return encoders.userPasswordEncoder()
                .matches(
                        userData.getOldPassword(),
                        originalUser.getPassword()
                );
    }
}
