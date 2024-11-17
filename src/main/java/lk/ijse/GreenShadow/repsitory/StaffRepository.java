package lk.ijse.GreenShadow.repsitory;

//import lk.ijse.springboot.CropMonitorAPI.entity.Staff;
import lk.ijse.GreenShadow.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
}
