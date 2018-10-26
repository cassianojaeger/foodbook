package br.ufrgs.foodbook.dao;

import br.ufrgs.foodbook.model.groups.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupDao extends JpaRepository<Group, Long>
{
    Group findByName(String groupName);
    List<Group> findByNameIgnoreCaseContaining(String groupName);
}
