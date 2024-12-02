package lk.ijse.GreenShadow.Repository;

import lk.ijse.GreenShadow.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, String> {
}
