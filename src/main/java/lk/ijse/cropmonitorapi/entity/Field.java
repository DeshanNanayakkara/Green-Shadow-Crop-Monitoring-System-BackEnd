package lk.ijse.cropmonitorapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "field")
public class Field implements SuperEntity {
    @Id
    private String fieldCode;
    private String fieldName;
    private Point fieldLocation;
    private double fieldSize;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage1;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage2;
    @OneToMany(mappedBy = "field")
    private List<Crop> crops;
    @OneToMany(mappedBy = "field")
    private List<Equipment> equipments;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "field_staff",
            joinColumns = @JoinColumn(name = "fieldCode", referencedColumnName = "fieldCode"),
            inverseJoinColumns = @JoinColumn(name = "staffId", referencedColumnName = "staffId"))
    private List<Staff> staff;
    @ManyToMany(mappedBy = "fields")
    private List<MonitoringLog> monitoringLogs;

}
