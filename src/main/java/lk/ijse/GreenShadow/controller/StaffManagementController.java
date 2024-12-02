package lk.ijse.GreenShadow.controller;

import jakarta.validation.Valid;
import lk.ijse.GreenShadow.dto.StaffDTO;
import lk.ijse.GreenShadow.exception.DataPersistFailedException;
import lk.ijse.GreenShadow.exception.StaffNotFoundException;
import lk.ijse.GreenShadow.response.StaffResponse;
import lk.ijse.GreenShadow.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
public class StaffManagementController {
    private final StaffService staffService;
    static Logger logger = LoggerFactory.getLogger(StaffManagementController.class);
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@Valid @RequestBody StaffDTO staff){
        if (staff == null ){
            logger.warn("Invalid request: Staff object is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try{
                staffService.saveStaff(staff);
                logger.info("Staff with Email: {} saved successfully", staff.getEmail());
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (DataPersistFailedException e) {
                logger.error("Failed to save staff: {}", staff, e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                logger.error("Internal server error while saving staff: {}", staff, e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    @DeleteMapping(value = "/{staffId}")
    public ResponseEntity<Void> deleteStaff(@PathVariable("staffId") String staffId) {
        try{
            if (staffId == null || staffId.isEmpty()){
                logger.warn("Invalid request: Staff ID is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            staffService.deleteStaff(staffId);
            logger.info("Staff with ID: {} deleted successfully", staffId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (StaffNotFoundException e) {
            logger.error("Staff with ID: {} not found for deletion", staffId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Internal server error while deleting staff with ID: {}", staffId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping(value = "/{staffId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaff(@PathVariable("staffId") String staffId,@Valid @RequestBody StaffDTO staff ){
         try{
             if (staff == null && (staffId == null || staffId.isEmpty())) {
                 logger.warn("Invalid request: Staff object is null or Staff ID is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
             } else {
                 staffService.updateStaff(staffId, staff);
                 logger.info("Staff with ID: {} updated successfully", staffId);
                 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
             }
         }catch (StaffNotFoundException e) {
             logger.error("Staff with ID: {} not found for update", staffId);
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         } catch (Exception e) {
             logger.error("Internal server error while updating staff with ID: {}", staffId, e);
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
    @GetMapping(value = "/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffResponse getSelectedStaff(@PathVariable("staffId") String staffId) {
        return staffService.getSelectedStaff(staffId);
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDTO> getAllStaff(){
        return staffService.getAllStaff();
    }
}
