package lk.ijse.GreenShadow.dto;

import jakarta.validation.constraints.*;
//import lk.ijse.springboot.CropMonitorAPI.response.StaffResponse;
import lk.ijse.GreenShadow.response.StaffResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffDTO implements SuperDTO, StaffResponse {
    private String staffId;
    @NotBlank
    @Size(max = 50)
    private String firstName;
    @NotBlank
    @Size(max = 50)
    private String lastName;
    @NotBlank
    private String designation;
    @NotBlank
    private String gender;
    @Past
    private Date joinedDate;
    @Past
    private Date dob;
    @NotBlank
    private String addressLine01;
    @NotBlank
    private String addressLine02;
    @NotBlank
    private String addressLine03;
    @NotBlank
    private String addressLine04;
    @NotBlank
    private String addressLine05;
    @NotBlank
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$")
    private String contactNo;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String role;
    private List<String> fieldCodes;
    private List<String> monitoringLogCodes;
    private String equipmentId;
    private List<String> vehicleCodes;
}
