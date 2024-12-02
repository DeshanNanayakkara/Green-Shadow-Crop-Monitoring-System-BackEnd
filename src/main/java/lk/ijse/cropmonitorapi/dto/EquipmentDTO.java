package lk.ijse.cropmonitorapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.ijse.cropmonitorapi.response.EquipmentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO implements SuperDTO, EquipmentResponse {
    private String equipmentId;
    @NotBlank
    @Size(max = 50)
    private String name;
    @NotBlank
    @Pattern(regexp = "^(Electrical|Mechanical)$")
    private String type;
    @NotBlank
    @Pattern(regexp = "^(Available|Not_Available)$")
    private String status;
    private String staffId;
    private String fieldCode;
}
