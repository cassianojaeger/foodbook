package br.ufrgs.foodbook.strategies.converter.impl;

import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.dto.user.UserInformationData;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInformationConverter extends AbstractGenericConverter<User, UserInformationData>
{
    public UserInformationConverter()
    {
        super(UserInformationData.class);
    }

    @Override
    protected List<Populator<User, UserInformationData>> getPopulators()
    {
        return this.populators;
    }

    @Autowired(required = false)
    @Override
    protected void setPopulators(List<Populator<User, UserInformationData>> populators)
    {
        this.populators = populators;
    }
}
