package lk.ijse.GreenShadow.controller;

import jakarta.validation.Valid;
import lk.ijse.GreenShadow.dto.EquipmentDTO;
import lk.ijse.GreenShadow.exception.DataPersistFailedException;
import lk.ijse.GreenShadow.exception.EquipmentNotFoundException;
import lk.ijse.GreenShadow.response.EquipmentResponse;
import lk.ijse.GreenShadow.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipment")
@RequiredArgsConstructor
public class EquipmentManagementController {

    private final EquipmentService equipmentService;
    static Logger logger = LoggerFactory.getLogger(EquipmentManagementController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@Valid @RequestBody EquipmentDTO equipment){
        if (equipment == null){
            logger.warn("Invalid request: Equipment object is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try{
                equipmentService.saveEquipment(equipment);
                logger.info("Equipment with Equipment Code: {} saved successfully", equipment.getEquipmentId());
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (DataPersistFailedException e){
                logger.error("Failed to save equipment: {}", equipment, e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                logger.error("Internal server error while saving equipment: {}", equipment, e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping(value = "/{equipmentId}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("equipmentId") String equipmentId) {
        try{
            if (equipmentId == null || equipmentId.isEmpty()){
                logger.warn("Invalid request : Equipment ID is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            equipmentService.deleteEquipment(equipmentId);
            logger.info("Equipment with Equipment ID: {} deleted successfully", equipmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EquipmentNotFoundException e){
            logger.error("Equipment with Equipment ID: {} not found for deletion", equipmentId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Internal server error while deleting equipment with Equipment ID: {}", equipmentId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{equipmentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEquipment(@PathVariable("equipmentId") String equipmentId,
                                                @Valid @RequestBody EquipmentDTO equipment){
        try{
            if (equipmentId == null || equipmentId.isEmpty()){
                logger.warn("Invalid request: Equipment ID is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                equipmentService.updateEquipment(equipmentId, equipment);
                logger.info("Equipment with Equipment ID: {} updated successfully", equipmentId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (EquipmentNotFoundException e){
            logger.error("Equipment with Equipment ID: {} not found for update", equipmentId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Internal server error while updating equipment with Equipment ID: {}", equipmentId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentResponse getSelectedEquipment(@PathVariable("equipmentId") String equipmentId) {
        return equipmentService.getSelectedEquipment(equipmentId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO> getAllEquipments() {
        return equipmentService.getAllEquipments();
    }
}
