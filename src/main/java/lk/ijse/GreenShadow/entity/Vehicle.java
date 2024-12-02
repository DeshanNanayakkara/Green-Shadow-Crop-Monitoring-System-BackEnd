package lk.ijse.GreenShadow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vehicle")
public class Vehicle implements SuperEntity {
    @Id
    private String vehicleCode;
    @Column(unique = true)
    private String licensePlateNumber;
    private String  vehicleCategory;
    private String fuelType;
    private String status;
    private String remarks;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "staffId", referencedColumnName = "staffId")
    private Staff staff;
}
