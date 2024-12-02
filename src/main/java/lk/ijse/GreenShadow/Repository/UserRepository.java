package lk.ijse.GreenShadow.Repository;

import lk.ijse.GreenShadow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
