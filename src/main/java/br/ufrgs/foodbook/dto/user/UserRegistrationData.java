package br.ufrgs.foodbook.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationData
{
    String username, email, phone, password;
}
