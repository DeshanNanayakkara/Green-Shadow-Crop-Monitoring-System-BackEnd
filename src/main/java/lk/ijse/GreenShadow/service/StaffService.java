package lk.ijse.GreenShadow.service;


import lk.ijse.GreenShadow.dto.StaffDTO;
import lk.ijse.GreenShadow.response.StaffResponse;

import java.util.List;

public interface StaffService {
    void saveStaff(StaffDTO staffDTO) throws Exception;
    void deleteStaff(String staffId);
    void updateStaff(String staffId, StaffDTO staff);
    StaffResponse getSelectedStaff(String staffId);
    List<StaffDTO> getAllStaff();
}
