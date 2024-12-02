package lk.ijse.cropmonitorapi.service;


import lk.ijse.cropmonitorapi.dto.MonitoringLogDTO;
import lk.ijse.cropmonitorapi.response.MonitoringLogResponse;

import java.util.List;

public interface MonitoringLogService {
    void saveMonitoringLog(MonitoringLogDTO monitoringLogDTO);
    void deleteMonitoringLog(String logCode);
    void updateMonitoringLog(String logCode, MonitoringLogDTO monitoringLogDTO);
    MonitoringLogResponse getSelectedMonitoringLog(String logCode);
    List<MonitoringLogDTO> getAllMonitoringLogs();
}
