package lk.ijse.cropmonitorapi.service.impl;

import lk.ijse.cropmonitorapi.dto.StaffDTO;
import lk.ijse.cropmonitorapi.entity.Staff;
import lk.ijse.cropmonitorapi.exception.DataPersistFailedException;
import lk.ijse.cropmonitorapi.exception.StaffNotFoundException;
import lk.ijse.cropmonitorapi.repository.StaffRepository;
import lk.ijse.cropmonitorapi.response.StaffResponse;
import lk.ijse.cropmonitorapi.response.impl.StaffErrorResponse;
import lk.ijse.cropmonitorapi.service.StaffService;
import lk.ijse.cropmonitorapi.util.AppUtil;
import lk.ijse.cropmonitorapi.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@Transactional
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final Mapping mapping;
    private final StaffRepository staffRepository;

    @Override
    public void saveStaff(StaffDTO staffDTO) {
       staffDTO.setStaffId(AppUtil.generateId("STAFF"));
        Staff savedEntity = staffRepository.save(mapping.map(staffDTO, Staff.class));
        if (savedEntity.getStaffId() == null){
            throw new DataPersistFailedException("Failed to save staff data!");
        }
    }

    @Override
    public void deleteStaff(String staffId) {
        Optional<Staff> staffById = staffRepository.findById(staffId);
        if (staffById.isEmpty()){
            throw new StaffNotFoundException("Staff not found");
        } else {
            staffRepository.deleteById(staffId);
        }
    }

    @Override
    public void updateStaff(String staffId, StaffDTO staff) {
        Optional<Staff> patchById = staffRepository.findById(staffId);
        if (patchById.isEmpty()){
            throw new StaffNotFoundException("Staff not found");
        } else {
            staff.setStaffId(patchById.get().getStaffId());
            staffRepository.save(mapping.map(staff, Staff.class));
        }
    }

    @Override
    public StaffResponse getSelectedStaff(String staffId) {
        if (staffRepository.existsById(staffId)){
           return mapping.map(staffRepository.getById(staffId), StaffDTO.class);
        } else {
            return new StaffErrorResponse(404, "Staff not found");
        }
    }

    @Override
    public List<StaffDTO> getAllStaff() {
        return mapping.mapList(staffRepository.findAll(), StaffDTO.class);
    }
}
