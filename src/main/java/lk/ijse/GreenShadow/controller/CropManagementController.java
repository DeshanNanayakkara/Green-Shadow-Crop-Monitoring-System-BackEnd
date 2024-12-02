package lk.ijse.GreenShadow.controller;

import jakarta.validation.Valid;
import lk.ijse.GreenShadow.dto.CropDTO;
import lk.ijse.GreenShadow.exception.CropNotFoundException;
import lk.ijse.GreenShadow.exception.DataPersistFailedException;
import lk.ijse.GreenShadow.response.CropResponse;
import lk.ijse.GreenShadow.service.CropService;
import lk.ijse.GreenShadow.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crop")
@RequiredArgsConstructor
public class    CropManagementController {
    private final CropService cropService;
    static Logger logger = LoggerFactory.getLogger(CropManagementController.class);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCrop(
            @Valid @RequestParam("commonName") String commonName,
            @Valid @RequestParam("scientificName") String scientificName,
            @Valid @RequestParam("category") String category,
            @Valid @RequestParam("season") String season,
            @Valid @RequestParam("fieldCode") String fieldCode,
            @RequestParam("cropImage") MultipartFile cropImage
    ){
        if (commonName == null || scientificName == null || category == null || season == null || fieldCode == null || cropImage == null) {
            logger.warn("Invalid request : Crop object or Crop Image is null");
            return ResponseEntity.badRequest().build();
        } else {
            CropDTO cropDTO = new CropDTO();
            try{
                cropDTO.setCropCommonName(commonName);
                cropDTO.setCropScientificName(scientificName);
                cropDTO.setCategory(category);
                cropDTO.setCropSeason(season);
                cropDTO.setFieldCode(fieldCode);
                cropDTO.setCropImage(AppUtil.toBase64Pic(cropImage));
                cropService.saveCrop(cropDTO);
                logger.info("Crop with Crop name: {} saved successfully", cropDTO.getCropCommonName());
                return ResponseEntity.ok().build();
            } catch (DataPersistFailedException e) {
                logger.error("Failed to save crop: {}", cropDTO, e);
                return ResponseEntity.badRequest().build();
            } catch (Exception e) {
                logger.error("Internal server error while saving crop: {}", cropDTO, e);
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @DeleteMapping(value = "/{cropCode}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("cropCode") String cropCode){
        try{
            if (cropCode == null || cropCode.isEmpty()){
                logger.warn("Invalid request: Crop Code is null or empty");
                return ResponseEntity.badRequest().build();
            } else {
                cropService.deleteCrop(cropCode);
                logger.info("Crop with Crop Code: {} deleted successfully", cropCode);
                return ResponseEntity.noContent().build();
            }
        } catch (CropNotFoundException e){
            logger.error("Crop with Crop Code: {} not found for deletion", cropCode);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Internal server error while deleting crop with Crop Code: {}", cropCode, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping(value = "/{cropCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCrop(
            @PathVariable("cropCode") String cropCode,
            @Valid @RequestParam("commonName") String commonName,
            @Valid @RequestParam("scientificName") String scientificName,
            @Valid @RequestParam("category") String category,
            @Valid @RequestParam("season") String season,
            @Valid @RequestParam("fieldCode") String fieldCode,
            @RequestParam("cropImage") MultipartFile cropImage
    ){
        if ( cropCode == null || commonName == null || scientificName == null || category == null || season == null ||
                fieldCode == null || cropImage == null) {
            logger.warn("Invalid request: Crop object or Crop Image is null");
            return ResponseEntity.badRequest().build();
        } else {
            CropDTO cropDTO = new CropDTO();
            try{
                cropDTO.setCropCommonName(commonName);
                cropDTO.setCropScientificName(scientificName);
                cropDTO.setCategory(category);
                cropDTO.setCropSeason(season);
                cropDTO.setFieldCode(fieldCode);
                cropDTO.setCropImage(AppUtil.toBase64Pic(cropImage));
                cropService.updateCrop(cropCode, cropDTO);
                logger.info("Crop with Crop Code: {} updated successfully", cropCode);
                return ResponseEntity.noContent().build();
            } catch (CropNotFoundException e){
                logger.error("Crop with Crop Code: {} not found for update", cropCode);
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                logger.error("Internal server error while updating crop with Crop Code: {}", cropCode, e);
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @GetMapping(value = "/{cropCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropResponse getSelectedCrop(@PathVariable("cropCode") String cropCode){
        return cropService.getSelectedCrop(cropCode);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllCrops(){
        return cropService.getAllCrops();
    }
}
