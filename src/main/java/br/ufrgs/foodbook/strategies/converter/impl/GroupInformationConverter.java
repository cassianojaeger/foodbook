package br.ufrgs.foodbook.strategies.converter.impl;

import br.ufrgs.foodbook.dto.group.GroupInformationData;
import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupInformationConverter extends AbstractGenericConverter<Group, GroupInformationData>
{
    public GroupInformationConverter()
    {
        super(GroupInformationData.class);
    }

    @Override
    protected List<Populator<Group, GroupInformationData>> getPopulators()
    {
        return this.populators;
    }

    @Autowired(required = false)
    @Override
    protected void setPopulators(List<Populator<Group, GroupInformationData>> populators)
    {
        this.populators = populators;
    }
}
