package br.ufrgs.foodbook.service.impl;

import br.ufrgs.foodbook.configuration.encrypt.Encoders;
import br.ufrgs.foodbook.dao.AuthorityDao;
import br.ufrgs.foodbook.dao.UserDao;
import br.ufrgs.foodbook.dto.user.UserInformationData;
import br.ufrgs.foodbook.dto.user.UserRegistrationData;
import br.ufrgs.foodbook.model.security.Authority;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.service.UserService;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.validator.impl.FoodbookUserValidator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static java.util.Objects.nonNull;

@Service
public class FoodbookUserService implements UserService
{
    private static final String USER_AUTHORITY = "NORMAL_USER";

    @Resource
    FoodbookUserValidator foodbookUserValidator;
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
        makeUserEnable(user);
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

    private void makeUserEnable(User user)
    {
        user.setEnabled(true);
    }
}
