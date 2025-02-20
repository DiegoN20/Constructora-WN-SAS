package contructora.constructorabackend.repository;

import contructora.constructorabackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByCorreo(String correo);

    Optional<User> findByCorreo(String correo);
}
