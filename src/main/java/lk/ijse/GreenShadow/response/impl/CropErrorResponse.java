package lk.ijse.GreenShadow.response.impl;

//import lk.ijse.springboot.CropMonitorAPI.response.CropResponse;
import lk.ijse.GreenShadow.response.CropResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropErrorResponse implements CropResponse, Serializable {
    private int errorCode;
    private String errorMessage;
}
