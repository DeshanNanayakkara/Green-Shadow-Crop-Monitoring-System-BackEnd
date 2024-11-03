package lk.ijse.GreenShadow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "equipment")
public class Equipment {
    @Id
    private String equipmentId;
    private String name;
    private EquipmentType type;
    private EquipmentStatus status;

    @ManyToOne(optional = true)
    @JoinColumn(name = "fieldCode", referencedColumnName = "fieldCode")
    private Field field;

    @OneToOne(optional = true)
    @JoinColumn(name = "staffID", referencedColumnName = "staffID")
    private Staff staff;

}
