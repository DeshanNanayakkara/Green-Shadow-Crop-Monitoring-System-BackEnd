package lk.ijse.GreenShadow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lk.ijse.GreenShadow.response.CropResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDTO implements SuperDTO, CropResponse {
    private String cropCode;
    @NotBlank
    @Size(max = 50)
    private String cropCommonName;
    @NotBlank
    @Size(max = 50)
    private String cropScientificName;
    @NotBlank
    private String category;
    @NotBlank
    private String cropSeason;
    @NotBlank
    private String fieldCode;
    private String cropImage;
}
