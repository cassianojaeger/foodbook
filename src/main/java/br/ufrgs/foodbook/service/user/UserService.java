package br.ufrgs.foodbook.service.user;

import br.ufrgs.foodbook.dto.user.UserInformationData;
import br.ufrgs.foodbook.dto.user.UserRegistrationData;

public interface UserService
{
    void registerNewUser(UserRegistrationData userRegistrationData);

    UserInformationData getUserInformation(String user);
}
