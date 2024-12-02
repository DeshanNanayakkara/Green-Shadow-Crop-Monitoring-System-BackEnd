package lk.ijse.cropmonitorapi.response.impl;

import lk.ijse.cropmonitorapi.response.FieldResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldErrorResponse implements FieldResponse, Serializable {
    private int errorCode;
    private String errorMessage;
}
