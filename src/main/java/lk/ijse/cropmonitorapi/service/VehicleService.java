package lk.ijse.cropmonitorapi.service;


import lk.ijse.cropmonitorapi.dto.VehicleDTO;
import lk.ijse.cropmonitorapi.response.VehicleResponse;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO vehicleDTO);
    void deleteVehicle(String vehicleCode);
    void updateVehicle(String vehicleCode, VehicleDTO vehicle);
    VehicleResponse getSelectedVehicle(String vehicleCode);
    List<VehicleDTO> getAllVehicles();
}
