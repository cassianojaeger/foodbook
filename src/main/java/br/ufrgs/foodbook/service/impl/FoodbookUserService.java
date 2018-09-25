package br.ufrgs.foodbook.service.impl;

import br.ufrgs.foodbook.configuration.encrypt.Encoders;
import br.ufrgs.foodbook.converter.impl.DataGenericConverter;
import br.ufrgs.foodbook.dao.AuthorityDao;
import br.ufrgs.foodbook.dao.UserDao;
import br.ufrgs.foodbook.dto.user.UserInformationData;
import br.ufrgs.foodbook.dto.user.UserRegistrationData;
import br.ufrgs.foodbook.model.security.Authority;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;

@Service
public class FoodbookUserService implements UserService
{
    private static final String USER_AUTHORITY = "NORMAL_USER";

    @Resource
    private UserDao userDao;
    @Resource
    private AuthorityDao authorityDao;
    @Resource
    private Encoders encoders;
    @Resource
    private DataGenericConverter<User, UserInformationData> userToUserInformationConverter;
    @Resource
    private DataGenericConverter<UserRegistrationData, User> userRegistrationToDataConverter;

    @Override
    @Transactional
    public void registerNewUser(UserRegistrationData userRegistrationData)
    {
//        TODO: USER VALIDATIONS, criar validator
        User user = userRegistrationToDataConverter.convert(userRegistrationData, new User());
        encodePassword(user);
        setUserAuthority(user);
        user.setEnabled(true);
        userDao.save(user);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserInformationData getUserInformation(String username)
    {
        User user = userDao.findByUsername(username);
        if (nonNull(user))
        {
            return userToUserInformationConverter.convert(user, new UserInformationData());
        }
        throw new UsernameNotFoundException(username);
    }

    private void encodePassword(User user)
    {
        String encodedPassword = encoders.userPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    private void setUserAuthority(User user)
    {
        Authority authority = authorityDao.findAuthorityByName(USER_AUTHORITY);
        user.setAuthorities(singletonList(authority));
    }
}
