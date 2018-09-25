package br.ufrgs.foodbook.dao.authority;

import br.ufrgs.foodbook.model.security.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityDao extends CrudRepository<Authority, Integer>
{
    Authority findAuthorityByName(String authority);
}
