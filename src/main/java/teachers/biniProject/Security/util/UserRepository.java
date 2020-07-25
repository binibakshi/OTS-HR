package teachers.biniProject.Security.util;

import org.springframework.data.jpa.repository.JpaRepository;
import teachers.biniProject.Security.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserName(String userName);
}