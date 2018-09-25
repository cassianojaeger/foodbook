package br.ufrgs.foodbook.controller;

import br.ufrgs.foodbook.dto.user.UserInformationData;
import br.ufrgs.foodbook.dto.user.UserRegistrationData;
import br.ufrgs.foodbook.service.user.UserService;
import br.ufrgs.foodbook.service.user.impl.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/secured/user")
public class UserInformationController
{
    @Resource
    UserService userService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public UserInformationData get() {
        return userService.getUserInformation("jones");
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity create(@RequestBody UserRegistrationData user) {
        userService.registerNewUser(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
