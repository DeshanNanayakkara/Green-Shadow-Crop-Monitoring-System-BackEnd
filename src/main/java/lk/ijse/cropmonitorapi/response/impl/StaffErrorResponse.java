package lk.ijse.cropmonitorapi.response.impl;

import lk.ijse.cropmonitorapi.response.StaffResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffErrorResponse implements StaffResponse, Serializable {
    private int errorCode;
    private String errorMessage;
}
