package lk.ijse.GreenShadow.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.GreenShadow.Repository.CropRepository;
import lk.ijse.GreenShadow.Repository.FieldRepository;
import lk.ijse.GreenShadow.Repository.MonitoringLogRepository;
import lk.ijse.GreenShadow.Repository.StaffRepository;
import lk.ijse.GreenShadow.dto.MonitoringLogDTO;
import lk.ijse.GreenShadow.entity.Crop;
import lk.ijse.GreenShadow.entity.Field;
import lk.ijse.GreenShadow.entity.MonitoringLog;
import lk.ijse.GreenShadow.entity.Staff;
import lk.ijse.GreenShadow.exception.DataPersistFailedException;
import lk.ijse.GreenShadow.exception.MonitoringLogNotFoundException;
import lk.ijse.GreenShadow.response.MonitoringLogResponse;
import lk.ijse.GreenShadow.response.impl.MonitoringLogErrorResponse;
import lk.ijse.GreenShadow.service.MonitoringLogService;
import lk.ijse.GreenShadow.util.AppUtil;
import lk.ijse.GreenShadow.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MonitoringLogServiceImpl implements MonitoringLogService {
    private final MonitoringLogRepository mlRepository;
    private final Mapping mapping;
    private final FieldRepository fieldRepository;
    private final CropRepository cropRepository;
    private final StaffRepository staffRepository;
    private final MonitoringLogRepository monitoringLogRepository;

    @Override
    public void saveMonitoringLog(MonitoringLogDTO monitoringLogDTO) {
       monitoringLogDTO.setLogCode(AppUtil.generateId("ML"));
       monitoringLogDTO.setLogDate(AppUtil.getCurrentDateTime());
       MonitoringLog monitoringLog = mapping.map(monitoringLogDTO, MonitoringLog.class);
       // setFields to monitoringLog
        List<Field> fieldList = !monitoringLogDTO.getFieldCodes().isEmpty()
            ? fieldRepository.findAllById(monitoringLogDTO.getFieldCodes())
            : Collections.emptyList();
        monitoringLog.setFields(fieldList);
        // setCrops to monitoringLog
        List<Crop> cropList = !monitoringLogDTO.getCropCodes().isEmpty()
            ? cropRepository.findAllById(monitoringLogDTO.getCropCodes())
            : Collections.emptyList();
        monitoringLog.setCrops(cropList);
        // setStaff to monitoringLog
        List<Staff> staffList = !monitoringLogDTO.getStaffIds().isEmpty()
            ? staffRepository.findAllById(monitoringLogDTO.getStaffIds())
            : Collections.emptyList();
        monitoringLog.setStaff(staffList);
        MonitoringLog saved = mlRepository.save(monitoringLog);
        if (saved.getLogCode() == null){
            throw new DataPersistFailedException("Failed to save monitoring log data!");
        }
    }

    @Override
    public void deleteMonitoringLog(String logCode) {
        mlRepository.findById(logCode).ifPresentOrElse(
            monitoringLog -> mlRepository.deleteById(logCode),
            () -> {
                throw new MonitoringLogNotFoundException("Monitoring Log not found!");
            }
        );
    }

    @Override
    public void updateMonitoringLog(String logCode, MonitoringLogDTO monitoringLogDTO) {
        MonitoringLog existingMonitoringLog = mlRepository.findById(logCode)
            .orElseThrow(() -> new MonitoringLogNotFoundException("Monitoring Log not found!"));
        MonitoringLog map = mapping.map(monitoringLogDTO, MonitoringLog.class);
        map.setLogCode(existingMonitoringLog.getLogCode());
        // setFields to monitoringLog
        List<Field> fieldList = !monitoringLogDTO.getFieldCodes().isEmpty()
            ? fieldRepository.findAllById(monitoringLogDTO.getFieldCodes())
            : Collections.emptyList();
        map.setFields(fieldList);
        // setCrops to monitoringLog
        List<Crop> cropList = !monitoringLogDTO.getCropCodes().isEmpty()
            ? cropRepository.findAllById(monitoringLogDTO.getCropCodes())
            : Collections.emptyList();
        map.setCrops(cropList);
        // setStaff to monitoringLog
        List<Staff> staffList = !monitoringLogDTO.getStaffIds().isEmpty()
            ? staffRepository.findAllById(monitoringLogDTO.getStaffIds())
            : Collections.emptyList();
        map.setStaff(staffList);
        mlRepository.save(map);
    }

    @Override
    public MonitoringLogResponse getSelectedMonitoringLog(String logCode) {
        Optional<MonitoringLog> optionalLog = monitoringLogRepository.findById(logCode);

        if (!optionalLog.isPresent()) {
            return new MonitoringLogErrorResponse(404, "Monitoring Log not found");
        } else {
            MonitoringLog log = optionalLog.get();
            MonitoringLogDTO dto = mapping.map(log, MonitoringLogDTO.class);

            dto.setFieldCodes(log.getFields().stream()
                    .map(Field::getFieldCode)
                    .collect(Collectors.toList()));

            dto.setCropCodes(log.getCrops().stream()
                    .map(Crop::getCropCode)
                    .collect(Collectors.toList()));

            dto.setStaffIds(log.getStaff().stream()
                    .map(Staff::getStaffId)
                    .collect(Collectors.toList()));

            return dto;
        }
    }

    @Override
    public List<MonitoringLogDTO> getAllMonitoringLogs() {
        return mapping.mapList(mlRepository.findAll(), MonitoringLogDTO.class);
    }

}
