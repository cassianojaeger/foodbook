package br.ufrgs.foodbook.strategies.converter.impl;

import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.strategies.populator.Populator;
import br.ufrgs.foodbook.dto.user.UserRegistrationData;
import br.ufrgs.foodbook.model.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRegistrationReverseConverter extends AbstractGenericConverter<UserRegistrationData, User>
{
    private List<Populator<UserRegistrationData, User>> populators;

    public UserRegistrationReverseConverter()
    {
        super(User.class);
    }

    @Override
    protected List<Populator<UserRegistrationData, User>> getPopulators()
    {
        return this.populators;
    }

    @Autowired(required = false)
    private void setPopulators(List<Populator<UserRegistrationData, User>> populators)
    {
        this.populators = populators;
    }
}
