package lk.ijse.GreenShadow.service;

import jakarta.validation.Valid;
import lk.ijse.GreenShadow.dto.EquipmentDTO;
import lk.ijse.GreenShadow.response.EquipmentResponse;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(@Valid EquipmentDTO equipment);
    void deleteEquipment(String equipmentId);
    void updateEquipment(String equipmentId, @Valid EquipmentDTO equipment);
    EquipmentResponse getSelectedEquipment(String equipmentId);
    List<EquipmentDTO> getAllEquipments();
}
