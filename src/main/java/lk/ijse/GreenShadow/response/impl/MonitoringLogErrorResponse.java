package lk.ijse.GreenShadow.response.impl;

//import lk.ijse.springboot.CropMonitorAPI.response.MonitoringLogResponse;
import lk.ijse.GreenShadow.response.MonitoringLogResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonitoringLogErrorResponse implements MonitoringLogResponse {
    private int errorCode;
    private String errorMessage;
}
