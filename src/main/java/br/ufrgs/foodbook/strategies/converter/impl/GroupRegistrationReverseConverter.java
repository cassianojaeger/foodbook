package br.ufrgs.foodbook.strategies.converter.impl;

import br.ufrgs.foodbook.dto.group.GroupRegistrationData;
import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupRegistrationReverseConverter extends AbstractGenericConverter<GroupRegistrationData, Group>
{
    private List<Populator<GroupRegistrationData, Group>> populators;

    public GroupRegistrationReverseConverter()
    {
        super(Group.class);
    }

    @Override
    protected List<Populator<GroupRegistrationData, Group>> getPopulators()
    {
        return this.populators;
    }

    @Autowired(required = false)
    private void setPopulators(List<Populator<GroupRegistrationData, Group>> populators)
    {
        this.populators = populators;
    }
}
