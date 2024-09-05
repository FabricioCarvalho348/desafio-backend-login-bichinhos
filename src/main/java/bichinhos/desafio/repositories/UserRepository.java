package bichinhos.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import bichinhos.desafio.models.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);

}
