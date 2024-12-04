package lk.ijse.cropmonitorapi.dto;

import jakarta.validation.constraints.*;
import lk.ijse.cropmonitorapi.response.StaffResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    private Date joinedDate;

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
}
