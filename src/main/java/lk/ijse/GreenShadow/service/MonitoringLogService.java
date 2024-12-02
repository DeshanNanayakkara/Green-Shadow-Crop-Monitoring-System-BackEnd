package lk.ijse.GreenShadow.service;



import lk.ijse.GreenShadow.dto.MonitoringLogDTO;
import lk.ijse.GreenShadow.response.MonitoringLogResponse;

import java.util.List;

public interface MonitoringLogService {
    void saveMonitoringLog(MonitoringLogDTO monitoringLogDTO);
    void deleteMonitoringLog(String logCode);
    void updateMonitoringLog(String logCode, MonitoringLogDTO monitoringLogDTO);
    MonitoringLogResponse getSelectedMonitoringLog(String logCode);
    List<MonitoringLogDTO> getAllMonitoringLogs();
}
