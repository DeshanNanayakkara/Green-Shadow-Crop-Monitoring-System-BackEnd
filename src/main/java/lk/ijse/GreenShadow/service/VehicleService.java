package lk.ijse.GreenShadow.service;


import lk.ijse.GreenShadow.dto.VehicleDTO;
import lk.ijse.GreenShadow.response.VehicleResponse;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO vehicleDTO);
    void deleteVehicle(String vehicleCode);
    void updateVehicle(String vehicleCode, VehicleDTO vehicle);
    VehicleResponse getSelectedVehicle(String vehicleCode);
    List<VehicleDTO> getAllVehicles();
}
