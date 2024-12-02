package lk.ijse.GreenShadow.controller;

import jakarta.validation.Valid;
import lk.ijse.GreenShadow.dto.MonitoringLogDTO;
import lk.ijse.GreenShadow.exception.DataPersistFailedException;
import lk.ijse.GreenShadow.exception.MonitoringLogNotFoundException;
import lk.ijse.GreenShadow.response.MonitoringLogResponse;
import lk.ijse.GreenShadow.service.MonitoringLogService;
import lk.ijse.GreenShadow.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/monitoringLog")
@RequiredArgsConstructor
public class MonitoringLogManagementController {
    private final MonitoringLogService monitoringLogService;
    static Logger logger = LoggerFactory.getLogger(MonitoringLogManagementController.class);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveMonitoringLog(
            @Valid @RequestParam("observation") String observation,
            @Valid @RequestParam("observedImage")MultipartFile observedImage,
            @Valid @RequestParam("fieldCodes")List<String> fieldCodes,
            @Valid @RequestParam("cropCodes")List<String> cropCodes,
            @Valid @RequestParam("staffIds")List<String> staffIds
            ){
        if (observation == null || observedImage == null || fieldCodes == null ||
                cropCodes == null || staffIds == null) {
            logger.warn("Invalid request: Monitoring Log object or Observed Image is null ");
            return ResponseEntity.badRequest().build();
        } else {
            MonitoringLogDTO monitoringLogDTO = new MonitoringLogDTO();
            try {
                monitoringLogDTO.setObservation(observation);
                monitoringLogDTO.setObservedImage(AppUtil.toBase64Pic(observedImage));
                monitoringLogDTO.setFieldCodes(fieldCodes.isEmpty() ? null : fieldCodes);
                monitoringLogDTO.setCropCodes(cropCodes.isEmpty() ? null : cropCodes);
                monitoringLogDTO.setStaffIds(staffIds.isEmpty() ? null : staffIds);
                monitoringLogService.saveMonitoringLog(monitoringLogDTO);
                logger.info("Monitoring Log with Observation: {} saved successfully", monitoringLogDTO.getObservation());
                return ResponseEntity.ok().build();
            } catch (DataPersistFailedException e){
                logger.error("Failed to save monitoring log: {}", monitoringLogDTO, e);
                return ResponseEntity.badRequest().build();
            } catch (Exception e) {
                logger.error("Internal server error while saving monitoring log: {}", monitoringLogDTO, e);
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @DeleteMapping("/{logCode}")
    public ResponseEntity<Void> deleteMonitoringLog(@PathVariable String logCode){
        try{
           monitoringLogService.deleteMonitoringLog(logCode);
           logger.info("Monitoring Log with Log Code: {} deleted successfully", logCode);
           return ResponseEntity.ok().build();
        } catch (MonitoringLogNotFoundException e) {
            logger.error("Monitoring Log not found with Log Code: {}", logCode, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Internal server error while deleting monitoring log with Log Code: {}", logCode, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping(value = "/{logCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateMonitoringLog(
            @PathVariable String logCode,
            @Valid @RequestParam("observation") String observation,
            @Valid @RequestParam("observedImage")MultipartFile observedImage,
            @Valid @RequestParam("fieldCodes")List<String> fieldCodes,
            @Valid @RequestParam("cropCodes")List<String> cropCodes,
            @Valid @RequestParam("staffIds")List<String> staffIds
    ){
        if (observation == null || observedImage == null || fieldCodes == null ||
                cropCodes == null || staffIds == null) {
            logger.warn("Invalid request: Monitoring Log object or Observed Image is null");
            return ResponseEntity.badRequest().build();
        } else {
            MonitoringLogDTO monitoringLogDTO = new MonitoringLogDTO();
            try {
                monitoringLogDTO.setObservation(observation);
                monitoringLogDTO.setObservedImage(AppUtil.toBase64Pic(observedImage));
                monitoringLogDTO.setFieldCodes(fieldCodes.isEmpty() ? null : fieldCodes);
                monitoringLogDTO.setCropCodes(cropCodes.isEmpty() ? null : cropCodes);
                monitoringLogDTO.setStaffIds(staffIds.isEmpty() ? null : staffIds);
                monitoringLogService.updateMonitoringLog(logCode ,monitoringLogDTO);
                logger.info("Monitoring Log with Observation: {} updated successfully", monitoringLogDTO.getObservation());
                return ResponseEntity.ok().build();
            } catch (MonitoringLogNotFoundException e){
                logger.error("Monitoring Log not found with Log Code: {}", logCode, e);
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                logger.error("Internal server error while updating monitoring log: {}", monitoringLogDTO, e);
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @GetMapping(value = "/{logCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MonitoringLogResponse getSelectedMonitoringLog(@PathVariable("logCode") String logCode){
        return monitoringLogService.getSelectedMonitoringLog(logCode);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MonitoringLogDTO> getAllMonitoringLogs(){
        return monitoringLogService.getAllMonitoringLogs();
    }
}
