package br.ufrgs.foodbook.service;

import br.ufrgs.foodbook.dto.user.UserInformationData;
import br.ufrgs.foodbook.dto.user.UserRegistrationData;
import br.ufrgs.foodbook.dto.user.UserUpdateData;

public interface UserService
{
    void registerNewUser(UserRegistrationData userRegistrationData);

    UserInformationData getUserInformation(String user);

    boolean isUsernameAlreadyTaken(String username);

    void updateUser(UserUpdateData user);
}
