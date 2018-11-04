package br.ufrgs.foodbook.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateData extends UserRegistrationData
{
    Boolean changePassword;
    String oldPassword;
}
