package lk.ijse.GreenShadow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String designation;
    private Gender gender;
    private Date joinedDate;
    private Date dob;
    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    private String contactNo;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany(mappedBy = "staff")
    @JsonIgnore
    private List<Field> field;

    @ManyToMany(mappedBy = "staff")
    @JsonIgnore
    private List<CropDetail> cropDetail;

    @OneToOne(mappedBy = "staff",optional = true)
    @JsonIgnore
    private Equipment equipment;

    @OneToMany(mappedBy = "staff")
    @JsonIgnore
    private List<Vehicle> vehicles;

}
