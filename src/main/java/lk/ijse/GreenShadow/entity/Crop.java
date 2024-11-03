package lk.ijse.GreenShadow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "crop")
public class Crop {
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    @Column(columnDefinition = "LONGTEXT")
    private String cropImage;
    private String category;
    private String cropSeason;

    @ManyToOne
    @JoinColumn(name = "fieldCode", referencedColumnName = "fieldCode")
    private Field field;
    @ManyToMany(mappedBy = "crop")
    private List<CropDetail> cropDetail;

}
