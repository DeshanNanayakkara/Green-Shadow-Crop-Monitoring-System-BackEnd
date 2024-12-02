package lk.ijse.cropmonitorapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "crop")
public class Crop implements SuperEntity {
    @Id
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    @Column(columnDefinition = "LONGTEXT")
    private String cropImage;
    private String category;
    private String cropSeason;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fieldCode", referencedColumnName = "fieldCode")
    private Field field;
    @JsonIgnore
    @ManyToMany(mappedBy = "crops")
    private List<MonitoringLog> monitoringLogs;
}
