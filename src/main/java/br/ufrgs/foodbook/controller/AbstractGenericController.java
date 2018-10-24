package br.ufrgs.foodbook.controller;

import br.ufrgs.foodbook.exception.InvalidRegistrationException;
import br.ufrgs.foodbook.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public abstract class AbstractGenericController
{
    @ExceptionHandler({ InvalidRegistrationException.class })
    @ResponseBody
    public ResponseEntity handlePaginationError(final InvalidRegistrationException e)
    {
        Map<String, String> errorMessage = new HashMap<>();

        errorMessage.put(e.getFieldName(), e.getErrorMessage());

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler({ ResourceNotFoundException.class })
    @ResponseBody
    public ResponseEntity handlePaginationError(final ResourceNotFoundException e)
    {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
