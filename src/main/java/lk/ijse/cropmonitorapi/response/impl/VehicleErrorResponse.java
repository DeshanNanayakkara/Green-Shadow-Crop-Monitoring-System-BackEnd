package lk.ijse.cropmonitorapi.response.impl;

import lk.ijse.cropmonitorapi.response.VehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleErrorResponse implements VehicleResponse, Serializable {
    private int errorCode;
    private String errorMessage;
}
