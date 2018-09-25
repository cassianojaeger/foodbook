package br.ufrgs.foodbook.dao.user;

import br.ufrgs.foodbook.model.security.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Integer>
{
    User findByUsername (String username);
}
