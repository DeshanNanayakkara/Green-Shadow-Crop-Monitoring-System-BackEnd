package lk.ijse.cropmonitorapi.repository;

import lk.ijse.cropmonitorapi.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {
}
