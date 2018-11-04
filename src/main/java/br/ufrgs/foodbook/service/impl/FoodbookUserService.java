package br.ufrgs.foodbook.service.impl;

import br.ufrgs.foodbook.configuration.encrypt.Encoders;
import br.ufrgs.foodbook.dao.AuthorityDao;
import br.ufrgs.foodbook.dao.UserDao;
import br.ufrgs.foodbook.dto.user.UserInformationData;
import br.ufrgs.foodbook.dto.user.UserRegistrationData;
import br.ufrgs.foodbook.dto.user.UserUpdateData;
import br.ufrgs.foodbook.exception.InvalidRegistrationException;
import br.ufrgs.foodbook.model.security.Authority;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.service.UserService;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.validator.impl.FoodbookUserUpdateValidator;
import br.ufrgs.foodbook.validator.impl.FoodbookUserValidator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Collections;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class FoodbookUserService implements UserService
{
    private static final String USER_AUTHORITY = "NORMAL_USER";
    private static final String GENERAL_UPDATE_ERROR_MESSAGE = "Este usuário nao existe ou você não tem permissão para modifica-lo";
    private static final String GENERAL_ERROR = "GENERAL_ERROR";

    @Resource
    FoodbookUserValidator foodbookUserValidator;
    @Resource
    FoodbookUserUpdateValidator foodbookUserUpdateValidator;
    @Resource
    private UserDao userDao;
    @Resource
    private AuthorityDao authorityDao;
    @Resource
    private Encoders encoders;
    @Resource
    private AbstractGenericConverter<User, UserInformationData> userInformationConverter;
    @Resource
    private AbstractGenericConverter<UserRegistrationData, User> userRegistrationReverseConverter;

    @Override
    @Transactional
    public void registerNewUser(UserRegistrationData userRegistrationData)
    {
        foodbookUserValidator.validate(userRegistrationData);
        User user = userRegistrationReverseConverter.convert(userRegistrationData);
        encodePassword(user);
        setUserAuthority(user);
        enableUser(user);
        userDao.save(user);
    }

    @Override
    @Transactional
    public UserInformationData getUserInformation(String username)
    {
        User user = userDao.findByUsername(username);
        if (nonNull(user))
        {
            return userInformationConverter.convert(user);
        }
        throw new UsernameNotFoundException(username);
    }

    @Override
    @Transactional
    public boolean isUsernameAlreadyTaken(String username)
    {
        User user = userDao.findByUsername(username);

        return nonNull(user);
    }

    @Override
    public void updateUser(UserUpdateData userData)
    {
        User originalUser = userDao.findByUsername(userData.getUsername());

        if(isInvalidRequest(userData, originalUser))
            throw new InvalidRegistrationException(GENERAL_ERROR, GENERAL_UPDATE_ERROR_MESSAGE);

        foodbookUserUpdateValidator.validate(userData);

        originalUser.setEmail(userData.getEmail());
        originalUser.setPhone(userData.getPhone());

        if(userData.getChangePassword())
        {
            originalUser.setPassword(userData.getPassword());
            encodePassword(originalUser);
        }

        userDao.save(originalUser);
    }

    private boolean isInvalidRequest(UserRegistrationData userData, User originalUser)
    {
        return isNull(originalUser) || !originalUser.getUsername().equals(userData.getCreatorName());
    }

    private void encodePassword(User user)
    {
        String encodedPassword = encoders.userPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    private void setUserAuthority(User user)
    {
        Authority authority = authorityDao.findAuthorityByName(USER_AUTHORITY);
        user.setAuthorities(Collections.singleton(authority));
    }

    private void enableUser(User user)
    {
        user.setEnabled(true);
    }
}
