package lk.ijse.cropmonitorapi.repository;

import lk.ijse.cropmonitorapi.entity.MonitoringLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringLogRepository extends JpaRepository<MonitoringLog, String> {
}
