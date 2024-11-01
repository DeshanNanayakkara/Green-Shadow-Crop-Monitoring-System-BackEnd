package lk.ijse.GreenShadow.entity;

import jakarta.persistence.Column;

import java.util.Date;

public class CropDetail {
    private String logCode;
    private Date date;
    private String logDetails;
    @Column(columnDefinition = "LONGTEXT")
    private String observedImage;

}
