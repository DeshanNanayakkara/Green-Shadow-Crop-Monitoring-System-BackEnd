package lk.ijse.cropmonitorapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "equipment")
public class Equipment implements SuperEntity {
    @Id
    private String equipmentId;
    private String name;
    @Enumerated(EnumType.STRING)
    private EquipmentType type;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne(optional = true)
    @JoinColumn(name = "staffId", referencedColumnName = "staffId")
    private Staff staff;
    @ManyToOne(optional = true)
    @JoinColumn(name = "fieldCode", referencedColumnName = "fieldCode")
    private Field field;
}
