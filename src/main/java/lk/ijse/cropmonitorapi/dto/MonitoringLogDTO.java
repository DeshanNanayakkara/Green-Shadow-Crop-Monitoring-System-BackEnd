package lk.ijse.cropmonitorapi.dto;

import jakarta.validation.constraints.NotBlank;
import lk.ijse.cropmonitorapi.response.MonitoringLogResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonitoringLogDTO implements SuperDTO, MonitoringLogResponse {
    private String logCode;
    private Date logDate;
    @NotBlank
    private String observation;
    private String observedImage;
    private List<String> fieldCodes;
    private List<String> cropCodes;
    private List<String> staffIds;
}
