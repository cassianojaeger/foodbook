package br.ufrgs.foodbook.dao;

import br.ufrgs.foodbook.model.groups.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupDao extends CrudRepository<Group, Integer>
{
    Group findByName(String groupName);
}
