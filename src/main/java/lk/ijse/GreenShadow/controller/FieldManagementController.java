package lk.ijse.GreenShadow.controller;

import jakarta.validation.Valid;
import lk.ijse.GreenShadow.dto.FieldDTO;
import lk.ijse.GreenShadow.exception.DataPersistFailedException;
import lk.ijse.GreenShadow.exception.FieldNotFoundException;
import lk.ijse.GreenShadow.response.FieldResponse;
import lk.ijse.GreenShadow.service.FieldService;
import lk.ijse.GreenShadow.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/field")
@RequiredArgsConstructor
public class FieldManagementController {
    private final FieldService fieldService;
    static Logger logger = LoggerFactory.getLogger(FieldManagementController.class);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveField(
            @Valid @RequestParam("fieldName") String fieldName,
            @Valid @RequestParam("latitude") double latitude,
            @Valid @RequestParam("longitude") double longitude,
            @Valid @RequestParam("fieldSize") double fieldSize,
            @RequestParam("fieldImage1") MultipartFile fieldImage1,
            @RequestParam("fieldImage2") MultipartFile fieldImage2,
            @RequestParam("staffIds") List<String> staffIds
            ) {
        if (fieldName == null || fieldImage1 == null || fieldImage2 == null) {
            logger.warn("Invalid request : Field object or Field Image is null");
            return ResponseEntity.badRequest().build();
        } else {
           FieldDTO fieldDTO = new FieldDTO();
           try {
               fieldDTO.setFieldName(fieldName);
               fieldDTO.setFieldLocation(new Point((int) latitude, (int) longitude));
               fieldDTO.setFieldSize(fieldSize);
               fieldDTO.setFieldImage1(AppUtil.toBase64Pic(fieldImage1));
               fieldDTO.setFieldImage2(AppUtil.toBase64Pic(fieldImage2));
               fieldDTO.setStaffIds(staffIds.isEmpty() ? null : staffIds);
               fieldService.saveField(fieldDTO);
               logger.info("Field with Field Code: {} saved successfully", fieldDTO.getFieldCode());
               return ResponseEntity.ok().build();
           } catch (DataPersistFailedException e) {
                logger.error("Failed to save field: {}", fieldDTO, e);
                return ResponseEntity.badRequest().build();
           } catch (Exception e) {
               logger.error("Internal server error while saving field: {}", fieldDTO, e);
               return ResponseEntity.internalServerError().build();
           }
        }
    }

    @DeleteMapping("/{fieldCode}")
    public ResponseEntity<Void> deleteField(@PathVariable("fieldCode") String fieldCode){
        try{
            fieldService.deleteField(fieldCode);
            logger.info("Field with Field Code: {} deleted successfully", fieldCode);
            return ResponseEntity.noContent().build();
        } catch (FieldNotFoundException e){
            logger.error("Field with Field Code: {} not found for deletion", fieldCode);
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            logger.error("Internal server error while deleting field with Field Code: {}", fieldCode, e);
          return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping(value = "/{fieldCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateField(
            @PathVariable("fieldCode") String fieldCode,
            @Valid @RequestParam("fieldName") String fieldName,
            @Valid @RequestParam("latitude") double latitude,
            @Valid @RequestParam("longitude") double longitude,
            @Valid @RequestParam("fieldSize") double fieldSize,
            @RequestParam("fieldImage1") MultipartFile fieldImage1,
            @RequestParam("fieldImage2") MultipartFile fieldImage2,
            @RequestParam("staffIds") List<String> staffIds
    ){
        if (fieldName == null || fieldImage1 == null || fieldImage2 == null) {
            logger.warn("Invalid request: Field object or Field Image is null");
            return ResponseEntity.badRequest().build();
        } else {
            FieldDTO fieldDTO = new FieldDTO();
            try {
                fieldDTO.setFieldName(fieldName);
                fieldDTO.setFieldLocation(new Point((int) latitude, (int) longitude));
                fieldDTO.setFieldSize(fieldSize);
                fieldDTO.setFieldImage1(AppUtil.toBase64Pic(fieldImage1));
                fieldDTO.setFieldImage2(AppUtil.toBase64Pic(fieldImage2));
                fieldDTO.setStaffIds(staffIds.isEmpty() ? null : staffIds);
                fieldService.updateField(fieldCode, fieldDTO);
                logger.info("Field with Field Code: {} updated successfully", fieldCode);
                return ResponseEntity.noContent().build();
            } catch (FieldNotFoundException e) {
                logger.error("Field with Field Code: {} not found for update", fieldCode);
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                logger.error("Internal server error while updating field with Field Code: {}", fieldCode, e);
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @GetMapping(value = "/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldResponse getSelectedField(@PathVariable("fieldCode") String fieldCode){
        return fieldService.getSelectedField(fieldCode);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAllFields() {
        return fieldService.getAllFields();
    }
}
