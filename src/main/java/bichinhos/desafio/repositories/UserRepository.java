package bichinhos.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import bichinhos.desafio.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
