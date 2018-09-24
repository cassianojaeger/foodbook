package br.ufrgs.foodbook.service.user.impl;

import br.ufrgs.foodbook.DataGenericConverter;
import br.ufrgs.foodbook.configuration.encrypt.Encoders;
import br.ufrgs.foodbook.dao.user.UserDao;
import br.ufrgs.foodbook.dto.user.UserInformationData;
import br.ufrgs.foodbook.dto.user.UserRegistrationData;
import br.ufrgs.foodbook.model.security.Authority;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static java.util.Collections.singletonList;

@Service
public class FoodbookUserService implements UserService
{
    private static final String USER_AUTHORITY = "NORMAL_USER";

    @Resource
    private UserDao userDao;
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
        userDao.save(user);
    }

    @Override
    public UserInformationData getUserInformation(String username)
    {
        return userToUserInformationConverter.convert(userDao.findByUsername(username), new UserInformationData());
    }

    private void encodePassword(User user)
    {
        String encodedPassword = encoders.userPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    private void setUserAuthority(User user)
    {
        Authority authority = new Authority();
        authority.setName(USER_AUTHORITY);
        user.setAuthorities(singletonList(authority));
    }
}
