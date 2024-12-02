package lk.ijse.cropmonitorapi.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.cropmonitorapi.dto.FieldDTO;
import lk.ijse.cropmonitorapi.entity.Field;
import lk.ijse.cropmonitorapi.entity.Staff;
import lk.ijse.cropmonitorapi.exception.DataPersistFailedException;
import lk.ijse.cropmonitorapi.exception.FieldNotFoundException;
import lk.ijse.cropmonitorapi.repository.CropRepository;
import lk.ijse.cropmonitorapi.repository.FieldRepository;
import lk.ijse.cropmonitorapi.repository.StaffRepository;
import lk.ijse.cropmonitorapi.response.FieldResponse;
import lk.ijse.cropmonitorapi.response.impl.FieldErrorResponse;
import lk.ijse.cropmonitorapi.service.FieldService;
import lk.ijse.cropmonitorapi.util.AppUtil;
import lk.ijse.cropmonitorapi.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final Mapping mapping;
    private final StaffRepository staffRepository;
    private final CropRepository cropRepository;

    @Override
    public void saveField(FieldDTO fieldDTO) {
        fieldDTO.setFieldCode(AppUtil.generateId("FE"));
        Field field = mapping.map(fieldDTO, Field.class);
        if (fieldDTO.getStaffIds() != null && !fieldDTO.getStaffIds().isEmpty()){
            List<Staff> staffList = staffRepository.findAllById(fieldDTO.getStaffIds());
            field.setStaff(staffList);
        }
        Field saved = fieldRepository.save(field);
        if (saved.getFieldCode() == null){
            throw new DataPersistFailedException("Failed to save field data!");
        }
    }

    @Override
    public void deleteField(String fieldCode) {
        fieldRepository.findById(fieldCode).ifPresentOrElse(
                field -> fieldRepository.deleteById(fieldCode),
                () -> {
                    throw new FieldNotFoundException("Field not found!");
                }
        );
    }

    @Override
    public void updateField(String fieldCode, FieldDTO fieldDTO) {
        Field existingField = fieldRepository.findById(fieldCode)
                .orElseThrow(() -> new FieldNotFoundException("Field not found!"));
        Field map = mapping.map(fieldDTO, Field.class);
        map.setFieldCode(existingField.getFieldCode());
        if (fieldDTO.getStaffIds() != null && !fieldDTO.getStaffIds().isEmpty()){
            List<Staff> staffList = staffRepository.findAllById(fieldDTO.getStaffIds());
            map.setStaff(staffList);
        }
        fieldRepository.save(map);
    }

    @Override
    public FieldResponse getSelectedField(String fieldCode) {
        if (fieldRepository.existsById(fieldCode)){
            Field byId = fieldRepository.getById(fieldCode);
            FieldDTO map = mapping.map(byId, FieldDTO.class);
            for (Staff staff : byId.getStaff()) {
                map.setStaffIds(List.of(staff.getStaffId()));
            }
            return map;
        } else {
            return new FieldErrorResponse(404, "Field not found");
        }
    }

    @Override
    public List<FieldDTO> getAllFields() {
        return mapping.mapList(fieldRepository.findAll(), FieldDTO.class);
    }

}
