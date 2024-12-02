package lk.ijse.cropmonitorapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lk.ijse.cropmonitorapi.response.FieldResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDTO implements SuperDTO, FieldResponse {
    private String fieldCode;
    @NotBlank
    @Size(max = 50)
    private String fieldName;
    @NotNull
    private Point fieldLocation;
    @Positive
    private double fieldSize;
    private String fieldImage1;
    private String fieldImage2;
    private List<String> staffIds;
}
