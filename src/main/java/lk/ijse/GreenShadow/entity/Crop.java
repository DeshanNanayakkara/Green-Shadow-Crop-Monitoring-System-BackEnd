package lk.ijse.GreenShadow.entity;

import jakarta.persistence.Column;

public class Crop {
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    @Column(columnDefinition = "LONGTEXT")
    private String cropImage;
    private String category;
    private String cropSeason;


}
