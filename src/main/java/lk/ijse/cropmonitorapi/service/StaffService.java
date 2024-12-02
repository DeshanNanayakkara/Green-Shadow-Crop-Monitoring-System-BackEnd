package lk.ijse.cropmonitorapi.service;


import lk.ijse.cropmonitorapi.dto.StaffDTO;
import lk.ijse.cropmonitorapi.response.StaffResponse;

import java.util.List;

public interface StaffService {
    void saveStaff(StaffDTO staffDTO) throws Exception;
    void deleteStaff(String staffId);
    void updateStaff(String staffId, StaffDTO staff);
    StaffResponse getSelectedStaff(String staffId);
    List<StaffDTO> getAllStaff();
}
