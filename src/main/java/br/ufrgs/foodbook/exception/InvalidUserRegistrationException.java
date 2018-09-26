package br.ufrgs.foodbook.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidUserRegistrationException extends RuntimeException
{
    private final String fieldName;
    private final String errorMessage;

    public InvalidUserRegistrationException(String fieldName, String message)
    {
        super(message);

        this.fieldName = fieldName;
        this.errorMessage = message;
    }
}
