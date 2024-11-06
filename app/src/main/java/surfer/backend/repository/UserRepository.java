package surfer.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import surfer.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
