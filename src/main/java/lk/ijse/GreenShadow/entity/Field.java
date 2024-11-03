package lk.ijse.GreenShadow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "field")
public class Field {
    private String fieldCode;
    private String fieldName;
    private Point fieldLocation;
    private double extentSizeOfTheField;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage1;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage2;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Crop> crop;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Equipment> equipment;

    @ManyToMany
    @JoinTable(
            name = "fieldStaff",
            joinColumns = @JoinColumn(name = "fieldCode"),
            inverseJoinColumns = @JoinColumn(name = "staffId")
    )
    private List<Staff> staff;

    @ManyToMany(mappedBy = "field")
    @JsonIgnore
    private List<CropDetail> cropDetail;

}
