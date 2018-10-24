package br.ufrgs.foodbook.validator.impl;

import br.ufrgs.foodbook.dto.group.GroupRegistrationData;
import br.ufrgs.foodbook.exception.InvalidRegistrationException;
import br.ufrgs.foodbook.validator.Validator;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class FoodbookGroupValidator implements Validator<GroupRegistrationData>
{
    private static final String GROUP_NAME_FIELD = "name";
    private static final String GROUP_DESCRIPTION_FIELD = "description";
    private static final String GROUP_CREATOR_NAME_FIELD = "creatorName";

    @Override
    public void validate(GroupRegistrationData groupRegistrationData)
    {
        validateNullFields(groupRegistrationData);
    }

    private void validateNullFields(GroupRegistrationData group)
    {
        if(isNull(group.getName()))
            throw new InvalidRegistrationException(GROUP_NAME_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(group.getDescription()))
            throw new InvalidRegistrationException(GROUP_DESCRIPTION_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(group.getCreatorName()))
            throw new InvalidRegistrationException(GROUP_CREATOR_NAME_FIELD, NULL_FIELD_ERROR_MESSAGE);
    }
}
