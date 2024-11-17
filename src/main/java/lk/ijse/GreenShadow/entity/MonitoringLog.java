package lk.ijse.GreenShadow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "monitoring_log")
public class MonitoringLog implements SuperEntity {
    @Id
    private String logCode;
    private Date logDate;
    private String observation;
    @Column(columnDefinition = "LONGTEXT")
    private String observedImage;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "monitoring_log_field",
            joinColumns = @JoinColumn(name = "logCode", referencedColumnName = "logCode"),
            inverseJoinColumns = @JoinColumn(name = "fieldCode", referencedColumnName = "fieldCode"))
    private List<Field> fields;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "monitoring_log_crop",
            joinColumns = @JoinColumn(name = "logCode", referencedColumnName = "logCode"),
            inverseJoinColumns = @JoinColumn(name = "cropCode", referencedColumnName = "cropCode"))
    private List<Crop> crops;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "monitoring_log_staff",
            joinColumns = @JoinColumn(name = "logCode", referencedColumnName = "logCode"),
            inverseJoinColumns = @JoinColumn(name = "staffId", referencedColumnName = "staffId"))
    private List<Staff> staff;
}
