package lk.ijse.GreenShadow.entity;

import jakarta.persistence.Column;

import java.awt.*;

public class Field {
    private String fieldCode;
    private String fieldName;
    private Point fieldLocation;
    private double extentSizeOfTheField;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage1;
    @Column(columnDefinition = "LONGTEXT")
    private String fieldImage2;

}
