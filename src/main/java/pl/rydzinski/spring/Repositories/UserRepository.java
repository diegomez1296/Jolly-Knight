package pl.rydzinski.spring.Repositories;

import org.springframework.data.repository.CrudRepository;
import pl.rydzinski.spring.UserPackage.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserId (Long id);
    User findByUserName (String username);
}
