package lk.ijse.cropmonitorapi.jwtmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterUser {
    private String email;
    private String password;
}