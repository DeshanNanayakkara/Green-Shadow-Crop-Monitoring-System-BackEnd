package lk.ijse.cropmonitorapi.repository;

import lk.ijse.cropmonitorapi.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropRepository extends JpaRepository<Crop, String> {
}
