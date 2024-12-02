package lk.ijse.GreenShadow.controller;

import jakarta.validation.Valid;
import lk.ijse.GreenShadow.dto.VehicleDTO;
import lk.ijse.GreenShadow.exception.DataPersistFailedException;
import lk.ijse.GreenShadow.exception.VehicleNotFoundException;
import lk.ijse.GreenShadow.response.VehicleResponse;
import lk.ijse.GreenShadow.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicle")
@RequiredArgsConstructor
public class VehicleManagementController {
    private final VehicleService vehicleService;
    static Logger logger = LoggerFactory.getLogger(VehicleManagementController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveVehicle(@Valid @RequestBody VehicleDTO vehicle){
        if (vehicle == null ){
            logger.warn("Invalid request: Vehicle object is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try{
                vehicleService.saveVehicle(vehicle);
                logger.info("Vehicle with Vehicle Code: {} saved successfully", vehicle.getLicensePlateNumber());
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (DataPersistFailedException e) {
                logger.error("Failed to save vehicle: {}", vehicle, e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                logger.error("Internal server error while saving vehicle: {}", vehicle, e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping(value = "/{vehicleCode}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("vehicleCode") String vehicleCode){
        try{
            if (vehicleCode == null || vehicleCode.isEmpty()){
                logger.warn("Invalid request: Vehicle Code is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            vehicleService.deleteVehicle(vehicleCode);
            logger.info("Vehicle with Vehicle Code: {} deleted successfully", vehicleCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (VehicleNotFoundException e){
          logger.error("Vehicle with Vehicle Code: {} not found for deletion", vehicleCode);
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Internal server error while deleting vehicle with Vehicle Code: {}", vehicleCode, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{vehicleCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateVehicle(@PathVariable("vehicleCode") String vehicleCode,@Valid @RequestBody VehicleDTO vehicle ){
        try{
            if (vehicle == null && (vehicleCode == null || vehicleCode.isEmpty())){
                logger.warn("Invalid request: Vehicle object is null or Vehicle Code is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                vehicleService.updateVehicle(vehicleCode, vehicle);
                logger.info("Vehicle with Vehicle Code: {} updated successfully", vehicleCode);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (VehicleNotFoundException e){
            logger.error("Vehicle with Vehicle Code: {} not found for update", vehicleCode);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Internal server error while updating vehicle with Vehicle Code: {}", vehicleCode, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{vehicleCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleResponse getSelectedVehicle(@PathVariable("vehicleCode") String vehicleCode) {
        return vehicleService.getSelectedVehicle(vehicleCode);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDTO> getAllVehicles(){
        return vehicleService.getAllVehicles();
    }

}
