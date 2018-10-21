package br.ufrgs.foodbook.controller;

import br.ufrgs.foodbook.dto.user.UserInformationData;
import br.ufrgs.foodbook.dto.user.UserRegistrationData;
import br.ufrgs.foodbook.exception.InvalidUserRegistrationException;
import br.ufrgs.foodbook.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/secured/user")
public class UserInformationController
{
    @Resource
    private UserService userService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Principal get(Principal principal) {
        return principal;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity create(@RequestBody UserRegistrationData user) {
        userService.registerNewUser(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/test")
    @ResponseStatus(value = HttpStatus.OK)
    public UserInformationData ola(@RequestParam String name) {
        return userService.getUserInformation(name);
    }

    @ExceptionHandler({ InvalidUserRegistrationException.class })
    @ResponseBody
    public ResponseEntity handleInvalidUserRegistration(final InvalidUserRegistrationException e)
    {
        Map<String, String> errorMessage = new HashMap<>();

        errorMessage.put(e.getFieldName(), e.getErrorMessage());

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorMessage);
    }

}
