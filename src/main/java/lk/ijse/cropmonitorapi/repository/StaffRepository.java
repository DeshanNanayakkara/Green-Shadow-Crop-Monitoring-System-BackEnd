package lk.ijse.cropmonitorapi.repository;

import lk.ijse.cropmonitorapi.entity.Role;
import lk.ijse.cropmonitorapi.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    @Query("SELECT s.role FROM Staff s WHERE s.email = :email")
    Optional<Role> findRoleIfEmailExists(@Param("email") String email);
    boolean existsByEmail(String email);
}
